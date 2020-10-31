package com.weimer.listingTest.resources;

import javax.ws.rs.core.Response;

public enum ErrorCodes {
    LISTING_NOT_FOUND(1000, Response.Status.NOT_FOUND.getStatusCode(), "Listing Not Found in DB"),
    LISTING_NOT_CREATED(1001, Response.Status.BAD_REQUEST.getStatusCode(), "Listing cannot be created");


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