package com.weimer.listingTest.resources;

import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiError implements Serializable {
    private static final long serialVersionUID = -1483017785983468113L;

    private int code;
    private int statusCode;
    private String transactionId;
    private String dateTime;
    private String description;
    private String[] descriptionValues;
    private String path;

    private ApiError() {
        dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date());
        descriptionValues = new String[0];
    }

    ApiError(final int code, final int statusCode) {
        this();
        this.code = code;
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
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

    public String getTransactionId() {
        return transactionId;
    }

    private void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getDescriptionValues() {
        return descriptionValues;
    }

    void setDescriptionValues(String[] descriptionValues) {
        this.descriptionValues = descriptionValues;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    void transactionId(String transactionId) {
        setTransactionId(transactionId);
    }

    public ApiError description(String description) {
        setDescription(description);
        return this;
    }

    public ApiError path(String path) {
        setPath(path);
        return this;
    }
}
