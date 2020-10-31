package com.weimer.listingTest.resources;

import com.weimer.listingTest.ApiException;
import com.weimer.listingTest.entities.ListingEntity;
import com.weimer.listingTest.repositories.ListingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/v1/listings")
public class ListingResource extends Resource {

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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ListingEntity getListingById(@PathParam("id") Integer id) {
        LOGGER.info("getListingById invoked, ID: {}", id);
        ListingEntity listingEntity = listingRepository.findById(id).orElseThrow(() -> {
            return getApiException(ErrorCodes.LISTING_NOT_FOUND, "ListingEntity(" + id
                    + ") cannot fetch Listing, entity not found");
        });
        LOGGER.info("Find Listing by id result: {}", listingEntity);
        return listingEntity;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ListingEntity save(ListingEntity listingEntity) {
        LOGGER.info("Listing save invoked, params: {}", listingEntity);
        try {
            listingRepository.save(listingEntity);
            LOGGER.info("listing save result: {}", listingEntity);
            return listingEntity;
        } catch (Exception e) {
            LOGGER.info("Listing was not created, ERROR: {}", e);
            getApiException(ErrorCodes.LISTING_NOT_CREATED, "ListingEntity(" + listingEntity
                    + ") error creating Listing");
        }
        return listingEntity;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void deleteListing(@PathParam("id") Integer id) {
        LOGGER.info("Delete Listing invoked, ID: {}", id);
        ListingEntity listingEntity = listingRepository.findById(id).orElseThrow(() -> {
            return getApiException(ErrorCodes.LISTING_NOT_FOUND, "ListingEntity(" + id
                    + ") cannot fetch Listing, entity not found");
        });
        listingRepository.delete(listingEntity);
    }
}
