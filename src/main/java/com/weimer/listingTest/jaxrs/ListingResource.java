package com.weimer.listingTest.jaxrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/v1/listings")
public class ListingResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListingResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list() {
        return "Listing Working!";
    }
}
