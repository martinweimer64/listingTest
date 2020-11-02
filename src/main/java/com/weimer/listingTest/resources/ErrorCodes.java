package com.weimer.listingTest.resources;

import javax.ws.rs.core.Response;

public enum ErrorCodes {
    LISTING_NOT_FOUND(1000, Response.Status.NOT_FOUND.getStatusCode(), "Listing Not Found in DB"),
    LISTING_NOT_CREATED(1001, Response.Status.BAD_REQUEST.getStatusCode(), "Listing cannot be created"),
    SPECIAL_PRICE_NOT_CREATED(1002, Response.Status.BAD_REQUEST.getStatusCode(), "SpecialPrice cannot be created"),
    LISTING_NOT_UPDATED(1003, Response.Status.BAD_REQUEST.getStatusCode(), "Listing cannot be updated"),
    SPECIAL_PRICE_NOT_LISTING(1004, Response.Status.BAD_REQUEST.getStatusCode(),
            "SpecialPrice does not belong to selected listing"),
    SPECIAL_PRICE_NOT_FOUND(1005, Response.Status.NOT_FOUND.getStatusCode(), "SpecialPrice Not Found in DB"),
    CHECKIN_BEFORE_TODAY(1006, Response.Status.BAD_REQUEST.getStatusCode(), "Checking must be a date after today"),
    CHECKOUT_BEFORE_CHECKIN(1007, Response.Status.BAD_REQUEST.getStatusCode(), "Checking must be before checkout"),
    MORE_THAN_28(1008, Response.Status.BAD_REQUEST.getStatusCode(), "Cannot book a listing for more than 28 days");
    private int code;
    private int statusCode;
    private String description;

    ErrorCodes(final int code, final int statusCode, final String description) {
        this.code = code;
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }
}
