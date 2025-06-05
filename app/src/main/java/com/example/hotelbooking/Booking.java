package com.example.hotelbooking;
import java.io.Serializable;
public class Booking implements Serializable {
    public String fullName;
    public Hotel hotel;
    public String checkIn;
    public String checkOut;
    public String paymentMethod;

    public Booking(String fullName, Hotel hotel, String checkIn, String checkOut, String paymentMethod) {
        this.fullName = fullName;
        this.hotel = hotel;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.paymentMethod = paymentMethod;
    }
}
