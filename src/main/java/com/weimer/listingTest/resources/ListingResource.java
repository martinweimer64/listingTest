package com.weimer.listingTest.resources;

import com.weimer.listingTest.entities.ListingEntity;
import com.weimer.listingTest.repositories.ListingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
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

    @Inject
    ListingRepository listingRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ListingEntity> getAll() {
        LOGGER.info("ListingEntity getAll invoked");
        final List<ListingEntity> listings = new ArrayList<>();
        listingRepository.findAll().forEach(listings::add);
        LOGGER.info("list result: {}", listings);
        return listings;
    }
}