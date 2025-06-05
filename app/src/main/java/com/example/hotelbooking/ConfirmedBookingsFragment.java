package com.example.hotelbooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Toast;
import java.util.List;

public class ConfirmedBookingsFragment extends Fragment {

    public ConfirmedBookingsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirmed_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        RecyclerView recycler = view.findViewById(R.id.recyclerBookings);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        BookingDAO dao = new BookingDAO(requireContext());
        List<Booking> bookings = dao.getAllBookings();

        recycler.setAdapter(new BookingAdapter(requireContext(), bookings, booking -> {
            dao.deleteBooking(booking.fullName, booking.hotel.name);
            recycler.setAdapter(new BookingAdapter(requireContext(), dao.getAllBookings(), this::onCancel));
        }));
    }

    private void onCancel(Booking booking) {
        BookingStorage.removeBooking(booking);
        Toast.makeText(getContext(), "Бронирование отменено", Toast.LENGTH_SHORT).show();
    }
}
