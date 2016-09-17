package com.aem.mahout.core.models;


import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class JCRDataModel {
    static Logger log = LoggerFactory.getLogger(JCRDataModel.class);


    public static DataModel createDataModel() {

        FastByIDMap<PreferenceArray> dataMap = new FastByIDMap<PreferenceArray>();


        List<GenericPreference> preferenceUser1List = new ArrayList<GenericPreference>();
        List<GenericPreference> preferenceUser2List = new ArrayList<GenericPreference>();
        List<GenericPreference> preferenceUser3List = new ArrayList<GenericPreference>();
        List<GenericPreference> preferenceUser4List = new ArrayList<GenericPreference>();

        preferenceUser1List.add(new GenericPreference(1, 10, 0.9f));
        preferenceUser1List.add(new GenericPreference(1, 11, 5.0f));
        preferenceUser1List.add(new GenericPreference(1, 12, 3.5f));
        preferenceUser1List.add(new GenericPreference(1, 13, 0.6f));
        preferenceUser1List.add(new GenericPreference(1, 14, 0.9f));
        preferenceUser1List.add(new GenericPreference(1, 21, 5.0f));
        preferenceUser1List.add(new GenericPreference(1, 16, 3.5f));
        preferenceUser1List.add(new GenericPreference(1, 17, 0.6f));
        preferenceUser1List.add(new GenericPreference(1, 18, 0.6f));

        preferenceUser2List.add(new GenericPreference(2, 10, 4.9f));
        preferenceUser2List.add(new GenericPreference(2, 11, 0.7f));
        preferenceUser2List.add(new GenericPreference(2, 15, 3.8f));
        preferenceUser2List.add(new GenericPreference(2, 16, 0.3f));
        preferenceUser2List.add(new GenericPreference(2, 17, 0.3f));
        preferenceUser2List.add(new GenericPreference(2, 18, 0.3f));

        preferenceUser3List.add(new GenericPreference(3, 11, 0.9f));
        preferenceUser3List.add(new GenericPreference(3, 12, 4.0f));
        preferenceUser3List.add(new GenericPreference(3, 13, 0.5f));
        preferenceUser3List.add(new GenericPreference(3, 21, 9.8f));
        preferenceUser3List.add(new GenericPreference(3, 15, 9.8f));
        preferenceUser3List.add(new GenericPreference(3, 16, 9.8f));
        preferenceUser3List.add(new GenericPreference(3, 17, 9.8f));


        preferenceUser4List.add(new GenericPreference(4, 24, 8.9f));
        preferenceUser4List.add(new GenericPreference(4, 25, 3.0f));
        preferenceUser4List.add(new GenericPreference(4, 26, 7.5f));
        preferenceUser4List.add(new GenericPreference(4, 28, 9.6f));
        preferenceUser4List.add(new GenericPreference(4, 29, 9.6f));
        preferenceUser4List.add(new GenericPreference(4, 21, 10.2f));
        preferenceUser4List.add(new GenericPreference(4, 22, 9.6f));
        preferenceUser4List.add(new GenericPreference(4, 23, 9.6f));
        preferenceUser4List.add(new GenericPreference(4, 24, 9.6f));


        PreferenceArray preferenceArray1 = new GenericUserPreferenceArray(preferenceUser1List);
        PreferenceArray preferenceArray2 = new GenericUserPreferenceArray(preferenceUser2List);
        PreferenceArray preferenceArray3 = new GenericUserPreferenceArray(preferenceUser3List);
        PreferenceArray preferenceArray4 = new GenericUserPreferenceArray(preferenceUser4List);


        dataMap.put(1, preferenceArray1);
        dataMap.put(2, preferenceArray2);
        dataMap.put(3, preferenceArray3);
        dataMap.put(4, preferenceArray4);

        log.info("datamap == " + dataMap);

        DataModel model = new GenericDataModel(dataMap);
        return model;

    }
}
