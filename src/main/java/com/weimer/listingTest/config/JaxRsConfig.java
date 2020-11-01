package com.weimer.listingTest.config;

import com.weimer.listingTest.resources.ListingResource;
import com.weimer.listingTest.resources.SpecialPriceResource;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JaxRsConfig extends ResourceConfig {

    private String PACKAGE = "com.weimer.listingTest.resources";

    @Autowired
    public JaxRsConfig(@Value("${spring.jersey.applicationPath}") String applicationPath)    {
        register(ListingResource.class);
        register(SpecialPriceResource.class);
        register(WadlResource.class);
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);
        BeanConfig swaggerConfigBean = new BeanConfig();
        swaggerConfigBean.setConfigId("Swagger");
        swaggerConfigBean.setTitle("LISTING API");
        swaggerConfigBean.setContact("mweimer");
        swaggerConfigBean.setBasePath(applicationPath);
        swaggerConfigBean.setSchemes(new String[] { "http", "https" });
        swaggerConfigBean.setResourcePackage(PACKAGE);
        swaggerConfigBean.setScan(true);
    }

}