package com.aem.mahout.core.models;


public class HashEncoder {

    /**
     * Utility method to get the hashcode of string
     * @param str  : String for which hascode is to be calculated
     * @return long
     */
    public static long convertToId(final String str){
        long hash = str.hashCode();
        return hash;
    }
}
