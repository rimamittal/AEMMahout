package com.aem.mahout.core.services.impl;

import com.adobe.cq.commerce.api.Product;
import com.aem.mahout.core.models.HashEncoder;
import com.aem.mahout.core.models.JCRDataModel;
import com.aem.mahout.core.services.RecommenderService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service(RecommenderService.class)
@Component(label = "Recommender Service", description = "Recommender Service used for generating the recommendations based on user reviews")
public class RecommenderServiceImpl implements RecommenderService {

    Logger log = LoggerFactory.getLogger(RecommenderServiceImpl.class);

    private static final int N_NEIGHOBUR_HOOD = 100;

    @Override
    public JSONArray getUserBasedRecommendations(final ResourceResolver resourceResolver, final String userId, final int numberOfRecommedations) {
        List<RecommendedItem> recommendations = null;
        JSONArray jsonArray = new JSONArray();
        try {
            //Creating JCRDataModel to fetch information from JCR
            DataModel model = JCRDataModel.createDataModel(resourceResolver);
            if (!"".equals(userId) && model != null) {
                //Getting user similarity object to get the similarity between any USER with similar taste as of current user based on PearsonCorrelation formula
                UserSimilarity userSimilarity = getSimilarity(model);
                //Find the 100 or n nearest users based on the user similarity from user data set (User DataModel)
                UserNeighborhood neighborhood = getNeighbourHood(N_NEIGHOBUR_HOOD, userSimilarity, model);
                //Initialising the UserBased recommender to get recommendations based on other users reviews
                GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
                //Calculating the Hash code of the given user to get the recommendations
                long userIdHash = HashEncoder.convertToId(userId);
                //Getting the N number of recommendations for the given user
                recommendations = recommender.recommend(userIdHash, numberOfRecommedations, null, false);

                //Creating a JSON array of recommendations
                for (RecommendedItem recommendation : recommendations) {
                    JSONObject jsonObject = new JSONObject();
                    Product product = JCRDataModel.getProduct(recommendation.getItemID());
                    jsonObject.put("product_sku",product.getSKU());
                    jsonObject.put("product_path",product.getPath());
                    jsonObject.put("product_title",product.getTitle());
                    jsonObject.put("product_description",product.getDescription());
                    jsonObject.put("product_thumbnailSrc",product.getThumbnail().getSrc());
                    jsonObject.put("product_pagePath",product.getPagePath());
                    jsonObject.put("product_ImageSrc",product.getImage().getSrc());
                    jsonObject.put("Preference",recommendation.getValue());
                    jsonArray.put(jsonObject);
                }
            }
        } catch (TasteException e) {
            log.error("Taste Exception", e);
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public JSONArray getItemBasedRecommendations(ResourceResolver resourceResolver, String productId, int numberOfRecommedations) {
        /**
         *  Implement this method to generate item based recommendations. GenericItemBasedRecommender can be used
         */

        return null;
    }


    private UserSimilarity getSimilarity(DataModel model) {

        UserSimilarity similarity = null;
        try {
            similarity = new PearsonCorrelationSimilarity(model);
            similarity.setPreferenceInferrer(new AveragingPreferenceInferrer(model));
        } catch (TasteException e) {
            log.error("Taste Exception", e);
        }
        return similarity;

    }

    private UserNeighborhood getNeighbourHood(int n, UserSimilarity userSimilarity, DataModel dataModel){
        UserNeighborhood userNeighborhood = null;

        try {
            userNeighborhood = new NearestNUserNeighborhood(n, userSimilarity, dataModel);
        } catch (TasteException e) {
            log.error("Taste Exception", e);        }

        return userNeighborhood;
    }

}
