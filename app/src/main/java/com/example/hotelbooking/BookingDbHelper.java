package com.example.hotelbooking;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookingDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "bookings.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_BOOKINGS = "bookings";

    public BookingDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "full_name TEXT," +
                        "hotel_name TEXT," +
                        "check_in TEXT," +
                        "check_out TEXT," +
                        "payment TEXT," +
                        "price INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }
}
