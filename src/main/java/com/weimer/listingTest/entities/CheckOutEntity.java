package com.weimer.listingTest.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class CheckOutEntity {


    private Date checkIn;
    private Date checkOut;

    public CheckOutEntity(Date checkIn, Date checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }
}
