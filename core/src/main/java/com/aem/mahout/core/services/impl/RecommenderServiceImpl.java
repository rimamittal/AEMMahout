package com.aem.mahout.core.services.impl;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Component
@Service
public class RecommenderServiceImpl implements RecommenderService {

    Logger log = LoggerFactory.getLogger(RecommenderServiceImpl.class);

    @Override
    public List<RecommendedItem> showRecommendations() {
        List<RecommendedItem> recommendations = null;
        try {

            DataModel model = JCRDataModel.createDataModel();
            if (model != null) {
                UserSimilarity userSimilarity = getSimilarity(model);
                UserNeighborhood neighborhood = getNeighbourHood(100,userSimilarity,model);
                GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
                recommendations = recommender.recommend(2, 3, null, false);

            }
        } catch (TasteException e) {
            log.error("Taste Exception", e);
            return null;
        }
        return recommendations;
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
