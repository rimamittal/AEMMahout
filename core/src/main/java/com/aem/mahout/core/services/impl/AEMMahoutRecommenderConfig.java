package com.aem.mahout.core.services.impl;

import org.apache.felix.scr.annotations.*;
import org.osgi.service.component.ComponentContext;

import java.util.Dictionary;

@Component(label = "", description = "",
        enabled = true, immediate = true, metatype = true)
@Properties({@Property(name = "user.generated.path", label = "User Generated Path",
        description = "Enter the path where user generated data would be stored for getting the user reviews",
        value = "/content/usergenerated/asi/jcr"),
        @Property(name = "search.product.path", label = "Search Product Path",
                description = "Enter the path under which search is to be performed",
                value = "/etc/commerce/products"),
        @Property(name = "component.resource.type", label = "Component Resource Type",
                description = "Enter the resourceType of the compnent which is to be searched",
                value = "social/tally/components/response")})
public class AEMMahoutRecommenderConfig {

    private static final String PROP_USERGENERATE_PATH = "user.generated.path";
    public static String USERGENERATED_PATH = "/content/usergenerated/asi/jcr";

    private static final String PROP_SEARCH_PRODUCT_PATH = "search.product.path";
    public static String PRODUCT_PATH = "/etc/commerce/products/geometrixx-outdoors";

    private static final String PROP_COMPONENT_RESOURCE_TYPE = "component.resource.type";
    public static String RATING_RESOURCE_TYPE = "social/tally/components/response";

    @Activate
    protected void activate(final ComponentContext componentContext) {
        Dictionary properties = componentContext.getProperties();

        USERGENERATED_PATH = (String) properties.get(PROP_USERGENERATE_PATH);
        PRODUCT_PATH = (String) properties.get(PROP_SEARCH_PRODUCT_PATH);
        RATING_RESOURCE_TYPE = (String) properties.get(PROP_COMPONENT_RESOURCE_TYPE);
    }

    @Modified
    protected void modified(final ComponentContext componentContext) {
        activate(componentContext);
    }
}
