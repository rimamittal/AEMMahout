package com.aem.mahout.core.services.impl;

import com.aem.mahout.core.services.RecommenderService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Service
public class RecommenderServiceImpl implements RecommenderService {

    //Logger log = LoggerFactory.getLogger(RecommenderServiceImpl.class);

    @Override
    public void showRecommendations() {

        try {
            DataModel model = new FileDataModel(new File("D:\\aem\\AEMMahout\\mahout\\dataset.csv"));
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
            List<RecommendedItem> recommendations = recommender.recommend(2, 3);
            for (RecommendedItem recommendation : recommendations) {
                //log.info("recommendation"+recommendation);
            }
        } catch (IOException e) {
           //log.error("File Not Found", e);
        } catch (TasteException e) {
           // log.error("Taste Exception", e);
        }

    }
}
