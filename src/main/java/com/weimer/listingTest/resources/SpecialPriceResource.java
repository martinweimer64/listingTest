package com.weimer.listingTest.resources;

import com.weimer.listingTest.ApiException;
import com.weimer.listingTest.entities.ListingEntity;
import com.weimer.listingTest.entities.SpecialPriceEntity;
import com.weimer.listingTest.repositories.ListingRepository;
import com.weimer.listingTest.repositories.SpecialPriceRepository;
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
@Path("/v1/listings/{id}/special-prices")
public class SpecialPriceResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialPriceResource.class);

    @Inject
    SpecialPriceRepository specialPriceRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SpecialPriceEntity> getAllByListingID(@PathParam("id") Integer id) {
        LOGGER.info("SpecialPriceEntity getAllByListingID invoked, ID: {}", id);
        final List<SpecialPriceEntity> specialPrices = new ArrayList<>();
        specialPriceRepository.findAllByListingEntity_id(id).forEach(specialPrices::add);
        LOGGER.info("specialPrices result: {}", specialPrices);
        return specialPrices;
    }
}
