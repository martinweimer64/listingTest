package com.weimer.listingTest.resources;

import com.weimer.listingTest.entities.CheckOutEntity;
import com.weimer.listingTest.entities.CheckOutResponse;
import com.weimer.listingTest.entities.ListingEntity;
import com.weimer.listingTest.entities.SpecialPriceEntity;
import com.weimer.listingTest.repositories.ListingRepository;
import com.weimer.listingTest.repositories.SpecialPriceRepository;
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
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RestController
@Path("/v1/listings")
@Api(value = "listings", tags = {"listings"})
public class ListingResource extends Resource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListingResource.class);

    @Inject
    ListingRepository listingRepository;

    @Inject
    SpecialPriceRepository specialPriceRepository;

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
        final ListingEntity listingEntity = listingRepository.findById(id)
                .orElseThrow(() -> getApiException(ErrorCodes.LISTING_NOT_FOUND, "ListingEntity(" + id
                        + ") cannot fetch Listing, entity not found"));
        LOGGER.info("Find Listing by id result: {}", listingEntity);
        return listingEntity;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ListingEntity save(ListingEntity listingEntity) {
        LOGGER.info("Listing update invoked, params: {}", listingEntity);
        final ListingEntity lastListing = listingRepository.findTopByOrderByIdDesc();
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
        final ListingEntity listingEntity = listingRepository.findById(id)
                .orElseThrow(() -> getApiException(ErrorCodes.LISTING_NOT_FOUND, "ListingEntity(" + id
                        + ") cannot fetch Listing, entity not found"));
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
        final ListingEntity listingEntity = listingRepository.findById(id)
                .orElseThrow(() -> getApiException(ErrorCodes.LISTING_NOT_FOUND, "ListingEntity(" + id
                        + ") cannot fetch Listing, entity not found"));
        listingRepository.delete(listingEntity);
        specialPriceRepository.deleteAll(specialPriceRepository.findAllByListingEntity_id(id));
    }

    @POST
    @Path("{id}/checkout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CheckOutResponse checkOut(@PathParam("id") Integer id, CheckOutEntity checkOutEntity) {
        checkOutEntity = fixDate(checkOutEntity);
        LOGGER.info("checkOut invoked, ID: {} - CheckOutEntity: {}", id, checkOutEntity);
        final ListingEntity listingEntity = listingRepository.findById(id)
                .orElseThrow(() -> getApiException(ErrorCodes.LISTING_NOT_FOUND, "ListingEntity(" + id
                        + ") cannot fetch Listing, entity not found"));
        validateCheckOutDates(checkOutEntity);
        CheckOutResponse response = calculateCheckOut(listingEntity, checkOutEntity);
        return response;
    }

    private CheckOutEntity fixDate(CheckOutEntity entity) {
        LOGGER.info("fixDate invoked, CheckOutEntity: {}", entity);
        entity.setCheckIn(new Date(entity.getCheckIn().getTime() + (1000 * 60 * 60 * 24)));
        entity.setCheckOut(new Date(entity.getCheckOut().getTime() + (1000 * 60 * 60 * 24)));
        LOGGER.info("fixDate result, CheckOutEntity: {}", entity);
        return entity;
    }

    private CheckOutResponse calculateCheckOut(ListingEntity listingEntity, CheckOutEntity dates) {
        LOGGER.info("calculateCheckOut invoked, ListingEntity: {}, CheckOutEntity: {}", listingEntity, dates);
        long bookingDays = calcultateBookingDays(dates);
        long days = calcultateDaysBetweenDates(dates);
        if (bookingDays > 28) {
            throwApiException(ErrorCodes.MORE_THAN_28, "Cannot book a listing for more than 28 days");
        }

        CheckOutResponse response = new CheckOutResponse();
        final List<SpecialPriceEntity> specialPrices = new ArrayList<>();
        specialPriceRepository
                .findAllByListingEntity_idAndDateLessThanEqualAndDateGreaterThanEqual(listingEntity.getId(),
                        dates.getCheckOut(), dates.getCheckIn()).forEach(specialPrices::add);
        LOGGER.info("specialPrices result: {}", specialPrices);

        //Se podrian ir seteando directamente las varibles a response, pero por tema de claridad preferi de este modo.
        double discountPercent = calculateDiscountPercent(days, listingEntity);
        double totalSpecialPriceCost = specialPrices.stream().mapToDouble(SpecialPriceEntity::getPrice).sum();
        double totalBase = (days - specialPrices.size()) * listingEntity.getBasePrice();
        double totalCleaningFee = (days - specialPrices.size()) * listingEntity.getCleaningFee();
        double totalNightCost = totalBase + totalSpecialPriceCost;
        double totalDiscount =
                ((days - specialPrices.size()) * listingEntity.getBasePrice() + totalCleaningFee) * discountPercent;
        double total = (totalBase + totalCleaningFee) * (1 - discountPercent) + totalSpecialPriceCost;

        response.setCleaning_fee(totalCleaningFee);
        response.setDiscount(totalDiscount);
        response.setNights_cost(totalNightCost);
        response.setNights_count(days);
        response.setTotal(total);
        return response;
    }

    private double calculateDiscountPercent(long days, ListingEntity listingEntity) {
        LOGGER.info("calculateDiscountPercent invoked, days: {}, ListingEntity: {}", days, listingEntity);
        if (days >= 30) {
            return listingEntity.getMonthlyDiscount();
        } else if (days >= 7) {
            return listingEntity.getWeeklyDiscount();
        }
        return 0;
    }

    private void validateCheckOutDates(CheckOutEntity dates) {
        LOGGER.info("validateCheckOutDates invoked, CheckOutEntity: {}", dates.toString());
        Date today = java.util.Calendar.getInstance().getTime();
        if (dates.getCheckOut().before(dates.getCheckIn())) {
            LOGGER.info("Error: {}", ErrorCodes.CHECKOUT_BEFORE_CHECKIN);
            throw getApiException(ErrorCodes.CHECKOUT_BEFORE_CHECKIN, "Dates(" + dates.toString()
                    + ") checking must be before checkout");
        } else if (dates.getCheckIn().before(today)) {
            LOGGER.info("Error: {}", ErrorCodes.CHECKIN_BEFORE_TODAY);
            throw getApiException(ErrorCodes.CHECKIN_BEFORE_TODAY, "CheckIn(" + dates.getCheckIn()
                    + ") must be a date after today");
        }
    }

    private long calcultateDaysBetweenDates(CheckOutEntity dates) {
        LOGGER.info("calcultateDaysBetweenDates invoked, CheckOutEntity: {}", dates);
        LocalDate checkIn = new java.sql.Date(dates.getCheckIn().getTime()).toLocalDate();
        LocalDate checkOut = new java.sql.Date(dates.getCheckOut().getTime()).toLocalDate();
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    private long calcultateBookingDays(CheckOutEntity dates) {
        LOGGER.info("calcultateDaysBetweenDates invoked, CheckOutEntity: {}", dates);
        LocalDate checkIn = new java.sql.Date(dates.getCheckIn().getTime()).toLocalDate();
        LocalDate today = new java.sql.Date(new Date().getTime()).toLocalDate();
        return ChronoUnit.DAYS.between(checkIn, today);
    }
}
