package com.aem.mahout.core.utils;

import com.day.cq.search.Query;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import java.util.*;

/**
 * Query Util class for querying JCR to fetch result
 */
public class QueryUtil {

    private static final String PROPERTY_VALUE = "1_property.value";
    private static final String PROPERTY = "1_property";

    /**
     * Utility method for creating a property map before querying the JCR
     *
     * @param path
     * @param propertyName
     * @param propertyValue
     *
     * @return map containing attributes to search the JCR
     */
    public static Map<String, String> getQueryMapForProperty(String path, String propertyName, String propertyValue) {
        final Map<String, String> predicateMap = new HashMap<String, String>();
        predicateMap.put(AEMMahoutAppConstants.PATH_PROPERTY, path);
        predicateMap.put(PROPERTY, propertyName);
        predicateMap.put(PROPERTY_VALUE, propertyValue);
        predicateMap.put("p.limit","-1");
        return predicateMap;
    }

    /**
     * Utility method for creating a list of resources using SearchResult object as input
     *
     * @param result
     * @return list of resource objects of queryed result
     */
    public static List<Resource> getResultList (SearchResult result) {
        List<Resource> resourceList = new ArrayList<Resource>();
        final Iterator<Resource> itr = result.getResources();
        while (itr.hasNext()) {
            final Resource resource = itr.next();
            resourceList.add(resource);
        }
        return resourceList;
    }

    /**
     * Utility method for creating list of properties from the search result object
     * @param result
     * @param property1
     * @param property2
     * @return list of properties to be fetched
     */
    public static List<Map<String, String>> getResultListwithProperty (SearchResult result, String property1, String property2) {
        List<Map<String, String>> propertyValueList = new ArrayList<Map<String, String>>();
        final Iterator<Resource> itr = result.getResources();
        while (itr.hasNext()) {
            final Resource resource = itr.next();
            ValueMap valueMap = resource.getValueMap();
            Map<String, String> property = new HashMap<String, String>();
            property.put(property1, valueMap.get(property1, String.class));
            property.put(property2, valueMap.get(property2, String.class));
            property.put(AEMMahoutAppConstants.PATH_PROPERTY, resource.getPath());
            propertyValueList.add(property);
        }
        return propertyValueList;
    }
}
