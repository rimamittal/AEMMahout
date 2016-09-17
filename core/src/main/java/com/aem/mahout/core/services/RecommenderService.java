package com.aem.mahout.core.services;


import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;

public interface RecommenderService {

    public List<RecommendedItem> showRecommendations();
}
