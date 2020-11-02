package com.weimer.listingTest.entities;

import com.google.gson.Gson;

import java.util.Date;

public class CheckOutResponse {

    private long nights_count;
    private double nights_cost;
    private double discount;
    private double cleaning_fee;
    private double total;


    public CheckOutResponse() {
    }

    public CheckOutResponse(long nights_count, double nights_cost, double discount, double cleaning_fee,
                            double total) {
        this.nights_count = nights_count;
        this.nights_cost = nights_cost;
        this.discount = discount;
        this.cleaning_fee = cleaning_fee;
        this.total = total;
    }

    public long getNights_count() {
        return nights_count;
    }

    public void setNights_count(long nights_count) {
        this.nights_count = nights_count;
    }

    public double getNights_cost() {
        return nights_cost;
    }

    public void setNights_cost(double nights_cost) {
        this.nights_cost = nights_cost;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getCleaning_fee() {
        return cleaning_fee;
    }

    public void setCleaning_fee(double cleaning_fee) {
        this.cleaning_fee = cleaning_fee;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
