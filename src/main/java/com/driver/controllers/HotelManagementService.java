package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelManagementService {
    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();
    public String addHotel(Hotel hotel) {

        return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return hotelManagementRepository.addUser(user);
    }

    public String hotelWithMostFacility() {
        return hotelManagementRepository.hotelWithMostFacility();
    }

    public int booking(Booking booking) {
        return hotelManagementRepository.booking(booking);
    }

    public int getBookings(Integer aadharCard) {
        return hotelManagementRepository.getBookings(aadharCard);
    }

    public Hotel updateFacility(List<Facility> newFacilities, String hotelName) {
        return hotelManagementRepository.updateFacility(newFacilities, hotelName);
    }
}
