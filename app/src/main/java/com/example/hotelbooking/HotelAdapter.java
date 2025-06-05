package com.example.hotelbooking;

import android.content.Context;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    public interface OnHotelClickListener {
        void onHotelClick(Hotel hotel);
    }

    private final Context context;
    private final List<Hotel> hotels;
    private final OnHotelClickListener listener;

    public HotelAdapter(Context context, List<Hotel> hotels, OnHotelClickListener listener) {
        this.context = context;
        this.hotels = hotels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotels.get(position);

        holder.name.setText(hotel.name);
        holder.image.setImageResource(hotel.imageResId);
        holder.rating.setText("Оценка: " + hotel.rating);
        holder.price.setText("от " + hotel.pricePerNight + " ₽");

        holder.stars.removeAllViews();
        for (int i = 0; i < hotel.stars; i++) {
            ImageView star = new ImageView(context);
            star.setImageResource(R.drawable.ic_star);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
            params.setMargins(0, 0, 8, 0);
            star.setLayoutParams(params);
            holder.stars.addView(star);
        }

        holder.itemView.setOnClickListener(v -> listener.onHotelClick(hotel));
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView name, rating, price;
        ImageView image;
        LinearLayout stars;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hotelName);
            rating = itemView.findViewById(R.id.hotelRating);
            price = itemView.findViewById(R.id.hotelPrice);
            image = itemView.findViewById(R.id.hotelImage);
            stars = itemView.findViewById(R.id.starContainer);
        }
    }
}
