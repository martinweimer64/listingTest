package com.weimer.listingTest.entities;

import com.google.gson.Gson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;

@Entity(name = "special_prices")
public class SpecialPriceEntity {

    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private ListingEntity listingEntity;

    @Column(name = "date")
    private Date date;

    @Column(name = "price")
    private double price;

    public SpecialPriceEntity() {
        //POJO JAVA OBJECT
    }

    public SpecialPriceEntity(int id, ListingEntity listingEntity, Date date, double price) {
        this.id = id;
        this.listingEntity = listingEntity;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ListingEntity getListingEntity() {
        return listingEntity;
    }

    public void setListingEntity(ListingEntity listingEntity) {
        this.listingEntity = listingEntity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SpecialPriceEntity that = (SpecialPriceEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
