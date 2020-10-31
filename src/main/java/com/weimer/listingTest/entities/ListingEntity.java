package com.weimer.listingTest.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity(name = "listings")
public class ListingEntity {

    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity userEntity;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "description")
    private String description;

    @Column(name = "adults")
    private int adults;

    @Column(name = "children")
    private int children;

    @Column(name = "is_pets_allowed")
    private Boolean isPetsAllowed;

    @Column(name = "base_price")
    private double basePrice;

    @Column(name = "cleaning_fee")
    private double cleaningFee;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "weekly_discount")
    private double weeklyDiscount;

    @Column(name = "monthly_discount")
    private double monthlyDiscount;

    public ListingEntity() {
        //POJO JAVA OBJECT
    }

    public ListingEntity(int id, UserEntity userEntity, String name, String slug, String description, int adults,
                         int children, Boolean isPetsAllowed, double basePrice, double cleaningFee,
                         String imageUrl, double weeklyDiscount, double monthlyDiscount) {
        this.id = id;
        this.userEntity = userEntity;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.adults = adults;
        this.children = children;
        this.isPetsAllowed = isPetsAllowed;
        this.basePrice = basePrice;
        this.cleaningFee = cleaningFee;
        this.imageUrl = imageUrl;
        this.weeklyDiscount = weeklyDiscount;
        this.monthlyDiscount = monthlyDiscount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public Boolean getPetsAllowed() {
        return isPetsAllowed;
    }

    public void setPetsAllowed(Boolean petsAllowed) {
        isPetsAllowed = petsAllowed;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getCleaningFee() {
        return cleaningFee;
    }

    public void setCleaningFee(double cleaningFee) {
        this.cleaningFee = cleaningFee;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getWeeklyDiscount() {
        return weeklyDiscount;
    }

    public void setWeeklyDiscount(double weeklyDiscount) {
        this.weeklyDiscount = weeklyDiscount;
    }

    public double getMonthlyDiscount() {
        return monthlyDiscount;
    }

    public void setMonthlyDiscount(double monthlyDiscount) {
        this.monthlyDiscount = monthlyDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListingEntity that = (ListingEntity) o;
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
