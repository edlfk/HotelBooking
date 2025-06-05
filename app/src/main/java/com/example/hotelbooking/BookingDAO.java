package com.example.hotelbooking;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import com.example.hotelbooking.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private final SQLiteDatabase db;

    public BookingDAO(Context context) {
        BookingDbHelper helper = new BookingDbHelper(context);
        db = helper.getWritableDatabase();
    }

    public void addBooking(Booking b) {
        ContentValues values = new ContentValues();
        values.put("full_name", b.fullName);
        values.put("hotel_name", b.hotel.name);
        values.put("check_in", b.checkIn);
        values.put("check_out", b.checkOut);
        values.put("payment", b.paymentMethod);
        values.put("price", b.hotel.pricePerNight);
        db.insert(BookingDbHelper.TABLE_BOOKINGS, null, values);
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        Cursor c = db.query(BookingDbHelper.TABLE_BOOKINGS, null, null, null, null, null, "id DESC");

        while (c.moveToNext()) {
            Booking b = new Booking(
                    c.getString(c.getColumnIndexOrThrow("full_name")),
                    new com.example.hotelbooking.Hotel(
                            c.getString(c.getColumnIndexOrThrow("hotel_name")),
                            "", "", 0, 0, 0, c.getInt(c.getColumnIndexOrThrow("price"))
                    ),
                    c.getString(c.getColumnIndexOrThrow("check_in")),
                    c.getString(c.getColumnIndexOrThrow("check_out")),
                    c.getString(c.getColumnIndexOrThrow("payment"))
            );
            list.add(b);
        }
        c.close();
        return list;
    }

    public void deleteBooking(String fullName, String hotelName) {
        db.delete(BookingDbHelper.TABLE_BOOKINGS,
                "full_name=? AND hotel_name=?", new String[]{fullName, hotelName});
    }
}
