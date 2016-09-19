package com.aem.mahout.core.services;


import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONArray;

import java.util.List;

public interface RecommenderService {

    /**
     * Get JSON array of user based recommendations
     * @param resourceResolver
     * @param userId
     * @param numberOfRecommedations
     *
     * @return JSONArray
     */
    public JSONArray getUserBasedRecommendations(ResourceResolver resourceResolver, String userId, int numberOfRecommedations);

    /**
     * Get JSON Array of Item based recommendations
     *
     * @param resourceResolver
     * @param productId
     * @param numberOfRecommedations
     *
     * @return JSONArray
     */
    public JSONArray getItemBasedRecommendations(ResourceResolver resourceResolver, String productId, int numberOfRecommedations);
}
