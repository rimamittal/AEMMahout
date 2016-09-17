package com.aem.mahout.core.utils;

import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import java.util.*;

public class QueryUtil {

    private static final String PROPERTY_VALUE = "1_property.value";
    private static final String PROPERTY = "1_property";


    public static Map<String, String> getQueryMapForProperty(String path, String propertyName, String propertyValue) {
        final Map<String, String> predicateMap = new HashMap<String, String>();
        predicateMap.put("path", path);
        predicateMap.put(PROPERTY, propertyName);
        predicateMap.put(PROPERTY_VALUE, propertyValue);
        predicateMap.put("p.limit","-1");
        return predicateMap;
    }

    public static List<Resource> getResultList (SearchResult result) {
        List<Resource> resourceList = new ArrayList<Resource>();
        final Iterator<Resource> itr = result.getResources();
        while (itr.hasNext()) {
            final Resource resource = itr.next();
            resourceList.add(resource);
        }
        return resourceList;
    }

    public static List<Map<String, String>> getResultListwithProperty (SearchResult result, String property1, String property2) {
        List<Map<String, String>> propertyValueList = new ArrayList<Map<String, String>>();
        final Iterator<Resource> itr = result.getResources();
        while (itr.hasNext()) {
            final Resource resource = itr.next();
            ValueMap valueMap = resource.getValueMap();
            Map<String, String> property = new HashMap<String, String>();
            property.put(property1, valueMap.get(property1, String.class));
            property.put(property2, valueMap.get(property2, String.class));
            property.put("path", resource.getPath());
            propertyValueList.add(property);
        }
        return propertyValueList;
    }
}
