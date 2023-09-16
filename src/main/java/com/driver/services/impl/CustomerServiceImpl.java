package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer = customerRepository2.findById(customerId).get();
		List<TripBooking> tripBookings = customer.getTripBookings();

		for(TripBooking trip: tripBookings){
			Driver driver = trip.getDriver();
			Cab cab = driver.getCab();
			cab.setAvailable(true);
			driverRepository2.save(driver);
			trip.setStatus(TripStatus.CANCELED);
		}


		customerRepository2.delete(customer);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> allDriver = driverRepository2.findAll();
		Driver driver=null;
		for(Driver currDriver : allDriver){
			if (currDriver.getCab().isAvailable()){
				if ( (driver==null) || (currDriver.getDiverId() < driver.getDiverId())){
					driver=currDriver;
				}
			}
		}
		if (driver==null){
			throw new Exception("No cab available");
		}

		TripBooking tripBooking=new TripBooking();
		tripBooking.setCustomer(customerRepository2.findById(customerId).get());
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setStatus(TripStatus.CONFIRMED);
		tripBooking.setDriver(driver);
		int rate = driver.getCab().getPerKmRate();
		tripBooking.setBill(rate*distanceInKm);

//		make cab avalibility false

		driver.getCab().setAvailable(false);

//		save in trip details in Db
		driverRepository2.save(driver);

//		in customer trip list add trip details
		Customer customer=customerRepository2.findById(customerId).get();
		customer.getTripBookings().add(tripBooking);
		customerRepository2.save(customer);


		tripBookingRepository2.save(tripBooking);

		return tripBooking;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooked = tripBookingRepository2.findById(tripId).get();
		tripBooked.setStatus(TripStatus.CANCELED);
		tripBooked.getDriver().getCab().setAvailable(true);
		tripBooked.setBill(0);
		tripBookingRepository2.save(tripBooked);


	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripCompleted = tripBookingRepository2.findById(tripId).get();
		tripCompleted.setStatus(TripStatus.COMPLETED);
		tripCompleted.getBill();
		tripCompleted.getDriver().getCab().setAvailable(true);
		tripBookingRepository2.save(tripCompleted);

	}
}
