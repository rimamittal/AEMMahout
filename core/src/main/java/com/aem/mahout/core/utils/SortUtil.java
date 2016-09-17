package com.aem.mahout.core.utils;


import java.util.*;

public class SortUtil {

    public static Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            return m1.get("userIdentifier").compareTo(m2.get("userIdentifier"));
        }
    };

    public static Map<String,List<Map<String,String>>> splitList(List<Map<String, String>> list){

        Map<String, List<Map<String,String>>> result = new HashMap<String, List<Map<String, String>>>();
        
        for( Map<String,String> listItem : list){
            String key = listItem.get("userIdentifier");
            if(result.containsKey(key)){
                List<Map<String,String>> existingList = result.get(key);
                existingList.add(listItem);
                result.put(key,existingList);
            } else {
                List<Map<String,String>> newList = new ArrayList<Map<String,String>>();
                newList.add(listItem);
                result.put(key,newList);
            }
        }

        return result;
    }

}
