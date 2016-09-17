package com.aem.mahout.core.services.impl;

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

@Component
@Service
public class RecommenderServiceImpl implements RecommenderService {

    Logger log = LoggerFactory.getLogger(RecommenderServiceImpl.class);

    @Override
    public JSONArray showRecommendations(ResourceResolver resourceResolver) {
        List<RecommendedItem> recommendations = null;
        JSONArray jsonArray = new JSONArray();
        try {

            DataModel model = JCRDataModel.createDataModel(resourceResolver);
            if (model != null) {
                UserSimilarity userSimilarity = getSimilarity(model);
                UserNeighborhood neighborhood = getNeighbourHood(100,userSimilarity,model);
                GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);

                long userId = HashEncoder.convertToId("jason.werner@dodgit.com");
                recommendations = recommender.recommend(userId, 3, null, false);



                for (RecommendedItem recommendation : recommendations) {
                    JSONObject jsonObject = new JSONObject();
                    String product = JCRDataModel.getProduct(recommendation.getItemID());
                    jsonObject.put("Product",product);
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
