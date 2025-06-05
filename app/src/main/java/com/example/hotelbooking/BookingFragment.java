package com.example.hotelbooking;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.hotelbooking.Hotel;

public class BookingFragment extends Fragment {
    public BookingFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Hotel hotel = (Hotel) getArguments().getSerializable("hotel");

        TextView info = view.findViewById(R.id.bookingInfo);
        if (hotel != null) {
            info.setText("Бронирование отеля: " + hotel.name);
        }
    }
}
