package com.weimer.listingTest.resources;

import com.weimer.listingTest.entities.SpecialPriceEntity;
import com.weimer.listingTest.repositories.SpecialPriceRepository;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Api(value = "special-price", tags = {"special-price"})
public class SpecialPriceResource extends Resource {

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

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void deleteSpecialPrice(@PathParam("id") Integer id) {
        LOGGER.info("Delete SpecialPrice invoked, ID: {}", id);
        SpecialPriceEntity specialPrice = specialPriceRepository.findById(id).orElseThrow(() -> {
            return getApiException(ErrorCodes.SPECIAL_PRICE_NOT_FOUND, "SpecialPriceEntity(" + id
                    + ") cannot fetch SpecialPrice, entity not found");
        });
        specialPriceRepository.delete(specialPrice);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SpecialPriceEntity save(SpecialPriceEntity specialPriceEntity) {
        LOGGER.info("SpecialPrice update invoked, params: {}", specialPriceEntity);
        SpecialPriceEntity lastSpecial = specialPriceRepository.findTopByOrderByIdDesc();
        try {
            int nextId = lastSpecial == null ? 1 : lastSpecial.getId() + 1;
            specialPriceEntity.setId(nextId);
            specialPriceRepository.save(specialPriceEntity);
            LOGGER.info("SpecialPrice save result: {}", specialPriceEntity);
            return specialPriceEntity;
        } catch (Exception e) {
            LOGGER.info("SpecialPrice was not created, ERROR: {}", e);
            getApiException(ErrorCodes.SPECIAL_PRICE_NOT_CREATED, "SpecialPrice(" + specialPriceEntity
                    + ") error creating SpecialPrice");
        }
        return specialPriceEntity;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{spId}")
    public void deleteSpecialPrice(@PathParam("id") Integer listingId, @PathParam("spId") Integer id) {
        LOGGER.info("Delete Special price invoked, ID: {}", id);
        SpecialPriceEntity specialPriceEntity = specialPriceRepository.findById(id).orElseThrow(() -> {
            return getApiException(ErrorCodes.SPECIAL_PRICE_NOT_FOUND, "SpecialPriceEntity(" + id
                    + ") cannot fetch Special Price, entity not found");
        });
        if (specialPriceEntity.getListingEntity().getId() == listingId) {
            specialPriceRepository.delete(specialPriceEntity);
        }
        throw getApiException(ErrorCodes.SPECIAL_PRICE_NOT_LISTING, "SpecialPriceEntity(" + id
                + ") does not belong to this listing (" + listingId + ").");
    }
}
