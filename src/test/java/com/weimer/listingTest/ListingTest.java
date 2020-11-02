package com.weimer.listingTest;

import com.weimer.listingTest.entities.CheckOutEntity;
import com.weimer.listingTest.entities.CheckOutResponse;
import com.weimer.listingTest.entities.ListingEntity;
import com.weimer.listingTest.repositories.ListingRepository;
import com.weimer.listingTest.repositories.SpecialPriceRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.unitils.reflectionassert.ReflectionAssert;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ListingTest {

    public static final String TEST_LISTING_API_BASE_PATH = "/test/v1/";
    final ParameterizedTypeReference<ListingEntity> LISTING_TYPE_REFERENCE = new ParameterizedTypeReference<ListingEntity>() {
    };
    final ParameterizedTypeReference<CheckOutResponse> CHECKOUT_TYPE_REFERENCE = new ParameterizedTypeReference<CheckOutResponse>() {
    };
    final ParameterizedTypeReference<List<ListingEntity>> LISTING_LIST_TYPE_REFERENCE =
            new ParameterizedTypeReference<List<ListingEntity>>() {
            };
    @Inject
    ListingRepository listingRepository;
    @Inject
    SpecialPriceRepository specialPriceRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;

    ResponseEntity<ListingEntity> listingResponse, listingResponse2;
    ResponseEntity<CheckOutResponse> checkOutResponse;
    ResponseEntity<List<ListingEntity>> listingListResponse, listingListResponse2;
    ListingEntity listingEntity;

    @BeforeEach
    public void beforeTest() {
        listingEntity = new ListingEntity();
        listingEntity.setBasePrice(10);
        listingEntity.setCleaningFee(2);
        listingEntity.setWeeklyDiscount(0.25);
        listingEntity.setMonthlyDiscount(0.5);

        try {
            listingRepository.deleteAll();
            specialPriceRepository.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addListing(ListingEntity listingEntity) {
        final HttpEntity<ListingEntity> request = new HttpEntity<>(listingEntity);
        listingResponse = testRestTemplate
                .exchange(TEST_LISTING_API_BASE_PATH + "/listings", POST, request, LISTING_TYPE_REFERENCE);
        listingResponse2 = testRestTemplate
                .exchange(TEST_LISTING_API_BASE_PATH + "/listings/" + listingResponse.getBody().getId(), GET, null,
                        LISTING_TYPE_REFERENCE);
        listingEntity.setId(listingResponse.getBody().getId());
        ListingEntity responseEntity = listingResponse.getBody();
        ReflectionAssert.assertReflectionEquals(listingEntity, responseEntity);
        Assert.assertTrue(listingResponse.getStatusCode().is2xxSuccessful());
    }


    void deleteListing(ListingEntity listingEntity) {
        final HttpEntity<ListingEntity> request = new HttpEntity<>(listingEntity);
        listingResponse = testRestTemplate
                .exchange(TEST_LISTING_API_BASE_PATH + "/listings/" + listingEntity.getId(), DELETE, request,
                        LISTING_TYPE_REFERENCE);
        Assert.assertTrue(listingResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void addListingTest() {
        addListing(listingEntity);
    }

    @Test
    void lstListingsCommandTest() {

        listingListResponse = testRestTemplate
                .exchange(TEST_LISTING_API_BASE_PATH + "/listings", GET, null,
                        LISTING_LIST_TYPE_REFERENCE);
        addListing(listingEntity);
        addListing(listingEntity);
        addListing(listingEntity);

        listingListResponse2 = testRestTemplate
                .exchange(TEST_LISTING_API_BASE_PATH + "/listings", GET, null,
                        LISTING_LIST_TYPE_REFERENCE);
        assertEquals(listingListResponse.getBody().size() + 3, listingListResponse2.getBody().size());
        Assert.assertTrue(listingListResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void cntAndDelListingTest() {
        addListing(listingEntity);
        addListing(listingEntity);

        listingListResponse = testRestTemplate
                .exchange(TEST_LISTING_API_BASE_PATH + "/listings", GET, null,
                        LISTING_LIST_TYPE_REFERENCE);
        int response1Count = listingListResponse.getBody().size();

        deleteListing(listingListResponse.getBody().get(0));
        listingListResponse2 = testRestTemplate
                .exchange(TEST_LISTING_API_BASE_PATH + "/listings", GET, null,
                        LISTING_LIST_TYPE_REFERENCE);
        int response2Count = listingListResponse2.getBody().size();
        assertEquals(response1Count - 1, response2Count);
        Assert.assertTrue(listingListResponse2.getStatusCode().is2xxSuccessful());

    }

    @Test
    void listingUpdateTest() {
        addListing(listingEntity);
        listingResponse = testRestTemplate
                .exchange(TEST_LISTING_API_BASE_PATH + "/listings/" + listingEntity.getId(), GET, null,
                        LISTING_TYPE_REFERENCE);
        listingEntity = listingResponse.getBody();
        listingEntity.setDescription("New description for update");
        final HttpEntity<ListingEntity> request = new HttpEntity<>(listingEntity);

        listingResponse2 = testRestTemplate
                .exchange(TEST_LISTING_API_BASE_PATH + "/listings/" + listingEntity.getId(), PUT, request,
                        LISTING_TYPE_REFERENCE);

        listingResponse = testRestTemplate
                .exchange(TEST_LISTING_API_BASE_PATH + "/listings/" + listingEntity.getId(), GET, null,
                        LISTING_TYPE_REFERENCE);

        ReflectionAssert.assertReflectionEquals(listingEntity, listingResponse.getBody());
        Assert.assertTrue(listingResponse2.getStatusCode().is2xxSuccessful());
    }

    @Test
    void calculateCheckoutTest() {
        addListing(listingEntity);

        //solucion rapida, se podria mejorar con Joda-Time library o JSR 310 API, por ejemplo.
        Date checkInDate = new Date();
        Date checkOutDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(checkInDate);
        c.add(Calendar.DATE, 1);
        checkInDate = c.getTime();
        c.add(Calendar.DATE, 10);
        checkOutDate = c.getTime();

        CheckOutEntity checkOut = new CheckOutEntity(checkInDate, checkOutDate);
        final HttpEntity<CheckOutEntity> request = new HttpEntity<>(checkOut);
        checkOutResponse = testRestTemplate
                .exchange(TEST_LISTING_API_BASE_PATH + "/listings/" + listingEntity.getId() + "/checkout", POST, request,
                        CHECKOUT_TYPE_REFERENCE);
        System.out.println(checkOutResponse);
        assertEquals(90, checkOutResponse.getBody().getTotal());
        Assert.assertTrue(checkOutResponse.getStatusCode().is2xxSuccessful());
    }

    @AfterEach
    public void afterTest() {
        try {
            listingRepository.deleteAll();
            specialPriceRepository.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}