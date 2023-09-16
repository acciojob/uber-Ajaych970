package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    private int mobile;

    private String password;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<TripBooking> tripBookings=new ArrayList<>();

    public Customer() {
    }


    public Customer(int customerId, int mobile, String password, List<TripBooking> tripBookings) {
        this.customerId = customerId;
        this.mobile = mobile;
        this.password = password;
        this.tripBookings = tripBookings;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TripBooking> getTripBookings() {
        return tripBookings;
    }

    public void setTripBookings(List<TripBooking> tripBookings) {
        this.tripBookings = tripBookings;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", mobile=" + mobile +
                ", password='" + password + '\'' +
                ", tripBookings=" + tripBookings +
                '}';
    }
}