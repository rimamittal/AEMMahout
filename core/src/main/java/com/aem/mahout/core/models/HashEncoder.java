package com.aem.mahout.core.models;


public class HashEncoder {

    public static long convertToId(String s){
        long hash = s.hashCode();
        return hash;
    }
}
