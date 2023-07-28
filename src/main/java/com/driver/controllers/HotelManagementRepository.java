package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {

    Map<String, Hotel> hotelMap = new HashMap<>();
    Map<Integer, User> userMap = new HashMap<>();
    Map<String, Booking> bookingMap = new HashMap<>();


    public String addHotel(Hotel hotel) {
        if(hotel == null || hotel.getHotelName()==null){
            return "FAILURE";
        }
        else if (hotelMap.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        else {
            hotelMap.put(hotel.getHotelName(), hotel);
        }
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        //You need to add a User Object to the database
        //Assume that user will always be a valid user and return the aadharCardNo of the user
        Integer userAadhar = user.getaadharCardNo();
        userMap.put(user.getaadharCardNo(), user);
        return userAadhar;
    }

    public String hotelWithMostFacility() {
        //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
        //Incase there is a tie return the lexicographically smaller hotelName
        //Incase there is not even a single hotel with atleast 1 facility return "" (empty string)

        int count=0;
        String ans="";
        for(Hotel hotel : hotelMap.values()) {
            count=Math.max(count, hotel.getFacilities().size());
        }
        if(count==0) return "";
        for(Hotel hotel : hotelMap.values()){
            if(count==hotel.getFacilities().size()){
                if(hotel.getHotelName().compareTo(ans)<0){
                    ans=hotel.getHotelName();
                }
            }
        }
        return ans;
    }

    public int booking(Booking booking) {
        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there arent enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);
        Hotel hotel = hotelMap.get(booking.getHotelName());
        int totalAmount=0;
        if(hotel!=null && hotel.getAvailableRooms() >= booking.getNoOfRooms()){
            hotel.setAvailableRooms(hotel.getAvailableRooms()-booking.getNoOfRooms());
            totalAmount=hotel.getPricePerNight()*booking.getNoOfRooms();
            bookingMap.put(bookingId, booking);
            return totalAmount;
        }else {
            return -1;
        }
    }

    public int getBookings(Integer aadharCard) {
        int count=0;
        for(String key:bookingMap.keySet()){
            if(aadharCard.equals(bookingMap.get(key).getBookingAadharCard())){
                count++;
            }
        }
        return count;
    }

    public Hotel updateFacility(List<Facility> newFacilities, String hotelName) {

        //We are having a new facilites that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible
        Hotel hotel = hotelMap.get(hotelName);
        if(hotel == null) return null;
        Set<Facility> facilities = new HashSet<>(hotel.getFacilities());
        for(Facility facility : facilities){
            facilities.add(facility);
        }
        hotel.setFacilities(new ArrayList<>(facilities));
        hotelMap.put(hotelName, hotel);
        return hotel;
    }
}
