package com.aem.mahout.core.utils;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Dictionary;

/**
 * Utility class for crx operations.
 */
public final class CRXUtil {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CRXUtil.class);

    /**
     * private constructor for denying direct instantiation.
     */
    private CRXUtil() {
    }

    /**
     * Gets the service reference either from SlingBindings or from BundleContext.
     *
     * @param <T>
     *            the generic type
     * @param serviceClass
     *            the class whose reference has to be returned.
     * @param request
     *            as SlingHttpServletRequest.
     * @return T the service reference
     */
    @SuppressWarnings("unchecked")
    public static <T> T getServiceReference(final Class<T> serviceClass, final SlingHttpServletRequest request) {

        final SlingBindings bindings = (SlingBindings) request.getAttribute(SlingBindings.class.getName());
        T serviceRef = null;

        if (null == bindings) {
            /**
             * Get the BundleContext associated with the passed class reference.
             */
            final BundleContext bundleContext = FrameworkUtil.getBundle(serviceClass).getBundleContext();
            final ServiceReference osgiRef = bundleContext.getServiceReference(serviceClass.getName());
            serviceRef = (T) bundleContext.getService(osgiRef);
        } else {
            final SlingScriptHelper helper = bindings.getSling();
            if (null != helper) {
                serviceRef = helper.getService(serviceClass);
            }

        }
        return serviceRef;
    }

    /**
     * Gets the service reference either from BundleContext.
     *
     * @param <T>
     *            the generic type
     * @param serviceClass
     *            the class whose reference has to be returned.
     * @return T the service reference
     */
    @SuppressWarnings("unchecked")
    public static <T> T getServiceReference(final Class<T> serviceClass) {
        T serviceRef;
        /**
         * Get the BundleContext associated with the passed class reference.
         */
        final BundleContext bundleContext = FrameworkUtil.getBundle(serviceClass).getBundleContext();
        final ServiceReference osgiRef = bundleContext.getServiceReference(serviceClass.getName());
        serviceRef = (T) bundleContext.getService(osgiRef);
        return serviceRef;
    }

    /**
     * Gets the service reference.
     *
     * @param <T>
     *            the generic type
     * @param serviceClass
     *            the service class
     * @param filter
     *            the filter
     * @return the service reference
     */
    @SuppressWarnings("unchecked")
    public static <T> T getServiceReference(final Class<T> serviceClass, final String filter) {
        T serviceRef = null;
        /**
         * Get the BundleContext associated with the passed class reference.
         */
        final BundleContext bundleContext = FrameworkUtil.getBundle(serviceClass).getBundleContext();
        ServiceReference[] osgiRef;
        try {
            osgiRef = bundleContext.getServiceReferences(serviceClass.getName(), filter);
            if (null != osgiRef && osgiRef.length > 0) {
                serviceRef = (T) bundleContext.getService(osgiRef[0]);
            }
        } catch (final InvalidSyntaxException e) {
            LOGGER.error("Error while getting service reference ::: ", e);
        }
        return serviceRef;
    }

    /**
     * Gets the property value from the felix console.
     *
     * @param properties
     *            the properties
     * @param propertyName
     *            the property name whose value has to be returned.
     * @return property value
     */
    public static String getStringProperty(final Dictionary<String, Object> properties, final String propertyName) {
        return (String) properties.get(propertyName);
    }

}
