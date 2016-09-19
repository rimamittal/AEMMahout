package com.aem.mahout.core.models;


import com.adobe.cq.commerce.api.Product;
import com.aem.mahout.core.utils.CRXUtil;
import com.aem.mahout.core.utils.QueryUtil;
import com.aem.mahout.core.utils.SortUtil;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import org.apache.lucene.search.Sort;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.*;

public class JCRDataModel {

    static Logger log = LoggerFactory.getLogger(JCRDataModel.class);

    public static String USERGENERATED_PATH = "/content/usergenerated/asi/jcr";
    public static String PRODUCT_PATH = "/etc/commerce/products/geometrixx-outdoors";
    public static String RATING_RESOURCE_TYPE = "social/tally/components/response";

    private static Map<Long,Product> productMap;
    private static Map<Long,String> userMap;


    /** The Constant queryBuilder. */
    private static final QueryBuilder queryBuilder = CRXUtil.getServiceReference(QueryBuilder.class);



    public static DataModel createDataModel(ResourceResolver resourceResolver) {

        productMap = new HashMap<Long, Product>();
        userMap = new HashMap<Long, String>();

        FastByIDMap<PreferenceArray> dataMap = new FastByIDMap<PreferenceArray>();

        final Map<String, String> predicateMap = QueryUtil.getQueryMapForProperty((USERGENERATED_PATH+PRODUCT_PATH),"sling:resourceType",RATING_RESOURCE_TYPE);
        final SearchResult result = queryBuilder.createQuery(PredicateGroup.create(predicateMap),
                resourceResolver.adaptTo(Session.class)).getResult();

        if(result != null ){

            List<Map<String, String>> resources = QueryUtil.getResultListwithProperty(result,"userIdentifier","response");
            Collections.sort(resources, SortUtil.mapComparator);
            Map<String,List<Map<String,String>>> userIdPreferenceMap = SortUtil.splitList(resources);

            for(List<Map<String,String>> userLists : userIdPreferenceMap.values()){

                long userIdHash = 0;
                List<GenericPreference> preferenceUserList = new ArrayList<GenericPreference>();
                for(Map<String,String> userPreference : userLists){
                    userIdHash = HashEncoder.convertToId(userPreference.get("userIdentifier"));
                    String itemPath = substring(userPreference.get("path"),"r/etc","/jcr:content");
                    //
                    Resource productResource = resourceResolver.getResource(itemPath);
                    Product product = (productResource != null) ? productResource.adaptTo(Product.class) : null;
                    //If product exists in JCR, then add it to preference list
                    if (product != null) {
                        long itemIdHash = HashEncoder.convertToId(itemPath);
                        setProduct(itemIdHash,product);
                        float preference = Float.parseFloat(userPreference.get("response"));
                        preferenceUserList.add(new GenericPreference(userIdHash,itemIdHash,preference));
                    }
                }
                PreferenceArray preferenceArray = new GenericUserPreferenceArray(preferenceUserList);
                dataMap.put(userIdHash,preferenceArray);

            }

        } else {
            log.info("no user ratings saved");
        }

        log.info("datamap == " + dataMap);

        DataModel model = new GenericDataModel(dataMap);
        return model;

    }

    public static void setProduct(long productId, Product product){
        if(!productMap.containsKey(productId)){
            productMap.put(productId,product);
        }
    }

    public static Product getProduct(Long id){
        return productMap.get(id);
    }


    public static String substring(String s, String delimiter1, String delimiter2){
        String result  = "";
        if(s != null ){
            result = s.substring(s.indexOf(delimiter1) + 1, s.indexOf(delimiter2));
        }
        return result;
    }
}
