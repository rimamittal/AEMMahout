package com.aem.mahout.core.services;


import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONArray;

import java.util.List;

public interface RecommenderService {

    public JSONArray showRecommendations(ResourceResolver resourceResolver, String userId, int numberOfRecommedations);
}
