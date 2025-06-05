package com.example.hotelbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    public interface OnCancelListener {
        void onCancel(Booking booking);
    }

    private final Context context;
    private final List<Booking> bookings;
    private final OnCancelListener listener;

    public BookingAdapter(Context context, List<Booking> bookings, OnCancelListener listener) {
        this.context = context;
        this.bookings = bookings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Используем кастомный layout вместо simple_list_item_2
        View v = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookings.get(position);

        // Устанавливаем данные бронирования
        holder.hotelName.setText(booking.hotel.name);
        holder.dates.setText(booking.checkIn + " - " + booking.checkOut);
        holder.guestInfo.setText("Гость: " + booking.fullName);
        holder.paymentInfo.setText("Оплата: " + booking.paymentMethod);

        // Обработчик нажатия на кнопку
        holder.cancelButton.setOnClickListener(v -> {
            listener.onCancel(booking);
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hotelName, dates, guestInfo, paymentInfo;
        Button cancelButton;

        public ViewHolder(View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.textHotelName);
            dates = itemView.findViewById(R.id.textDates);
            guestInfo = itemView.findViewById(R.id.textGuestInfo);
            paymentInfo = itemView.findViewById(R.id.textPaymentInfo);
            cancelButton = itemView.findViewById(R.id.buttonCancel);
        }
    }
}