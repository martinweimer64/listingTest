package com.weimer.listingTest.config;

import com.weimer.listingTest.jaxrs.ListingResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JaxRsConfig extends ResourceConfig {

    private String PACKAGE = "com.weimer.listingTest.jaxrs";

    @Autowired
    public JaxRsConfig() {
        register(ListingResource.class);
        register(WadlResource.class);
    }

}