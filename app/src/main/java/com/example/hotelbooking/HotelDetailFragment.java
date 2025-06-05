package com.example.hotelbooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.LinearLayout;



import com.example.hotelbooking.Hotel;

public class HotelDetailFragment extends Fragment {

    public HotelDetailFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Hotel hotel = (Hotel) getArguments().getSerializable("hotel");

        TextView name = view.findViewById(R.id.hotelName);
        TextView description = view.findViewById(R.id.hotelDescription);
        ImageView image = view.findViewById(R.id.hotelImage);
        Button book = view.findViewById(R.id.buttonBook);
        Button back = view.findViewById(R.id.buttonBack);

        LinearLayout starContainer = view.findViewById(R.id.starContainer);
        TextView ratingText = view.findViewById(R.id.hotelRating);
        TextView priceText = view.findViewById(R.id.hotelPrice);


        if (hotel != null) {
            name.setText(hotel.name);
            description.setText(hotel.description);
            image.setImageResource(hotel.imageResId);
            ratingText.setText("Оценка: " + hotel.rating);
            priceText.setText("Цена: " + hotel.pricePerNight + " ₽ за ночь");
            starContainer.removeAllViews();
            for (int i = 0; i < hotel.stars; i++) {
                ImageView star = new ImageView(requireContext());
                star.setImageResource(R.drawable.ic_star);
                star.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
                starContainer.addView(star);
            }
        }


        book.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("hotel", hotel);
            Navigation.findNavController(view).navigate(R.id.action_hotelDetailFragment_to_bookingFragment, bundle);
        });

        back.setOnClickListener(v -> requireActivity().onBackPressed());
    }
}
