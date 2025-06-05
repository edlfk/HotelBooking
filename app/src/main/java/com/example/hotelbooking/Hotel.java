package com.example.hotelbooking;

import java.io.Serializable;

public class Hotel implements Serializable {
    public String name;
    public String city;
    public String description;
    public int imageResId;
    public int stars;
    public float rating;
    public int pricePerNight;

    public Hotel(String name, String city, String description,
                 int imageResId, int stars, float rating, int pricePerNight) {
        this.name = name;
        this.city = city;
        this.description = description;
        this.imageResId = imageResId;
        this.stars = stars;
        this.rating = rating;
        this.pricePerNight = pricePerNight;
    }
}


