package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int diverId;

    private String mobile;

    private String password;

    @OneToMany(mappedBy = "driver" , cascade = CascadeType.ALL)
    List<TripBooking> tripBookingList=new ArrayList<>();

    @OneToOne(mappedBy = "driver" , cascade = CascadeType.ALL)
    Cab cab;

    public Driver() {
    }

    public Driver(int diverId, String mobile, String password, List<TripBooking> tripBookingList, Cab cab) {
        this.diverId = diverId;
        this.mobile = mobile;
        this.password = password;
        this.tripBookingList = tripBookingList;
        this.cab = cab;
    }

    public int getDiverId() {
        return diverId;
    }

    public void setDiverId(int diverId) {
        this.diverId = diverId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "diverId=" + diverId +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", tripBookingList=" + tripBookingList +
                ", cab=" + cab +
                '}';
    }
}