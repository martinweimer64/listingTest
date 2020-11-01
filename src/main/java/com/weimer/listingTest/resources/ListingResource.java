package com.weimer.listingTest.resources;

import com.weimer.listingTest.entities.ListingEntity;
import com.weimer.listingTest.repositories.ListingRepository;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@Path("/v1/listings")
@Api(value = "listings", tags = {"listings"})
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
    @Path("{id}")
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
        LOGGER.info("Listing update invoked, params: {}", listingEntity);
        ListingEntity lastListing = listingRepository.findTopByOrderByIdDesc();
        try {
            int nextId = lastListing == null ? 1 : lastListing.getId() + 1;
            listingEntity.setId(nextId);
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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public ListingEntity update(@PathParam("id") Integer id, ListingEntity newListing) {
        LOGGER.info("Listing update invoked, id: {} - params: {}", id, newListing);
        ListingEntity listingEntity = listingRepository.findById(id).orElseThrow(() -> {
            return getApiException(ErrorCodes.LISTING_NOT_FOUND, "ListingEntity(" + id
                    + ") cannot fetch Listing, entity not found");
        });
        newListing.setId(id);
        try {
            listingRepository.save(newListing);
            LOGGER.info("listing update result: {}", newListing);
            return newListing;
        } catch (Exception e) {
            LOGGER.info("Listing was not updated, ERROR: {}", e);
            getApiException(ErrorCodes.LISTING_NOT_UPDATED, "ListingEntity(" + listingEntity
                    + ") error updating Listing");
        }
        return newListing;
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
