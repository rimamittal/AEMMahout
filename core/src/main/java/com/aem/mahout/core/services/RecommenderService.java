package com.aem.mahout.core.services;


import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;

public interface RecommenderService {

    public List<RecommendedItem> showRecommendations(ResourceResolver resourceResolver);
}
