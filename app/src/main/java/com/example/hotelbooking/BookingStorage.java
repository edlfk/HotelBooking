package com.example.hotelbooking;

import java.util.ArrayList;
import java.util.List;

public class BookingStorage {
    private static final List<Booking> bookings = new ArrayList<>();

    public static void addBooking(Booking b) {
        if (b != null) {  // Проверка на null
            bookings.add(b);
        }
    }

    public static List<Booking> getBookings() {
        return new ArrayList<>(bookings);  // Возвращаем копию для безопасности
    }

    public static void removeBooking(Booking b) {
        if (b != null) {  // Проверка на null
            bookings.remove(b);
        }
    }
}

