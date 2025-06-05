package com.example.hotelbooking;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.Hotel;

import java.util.*;

public class HomeFragment extends Fragment {

    private EditText editDestination, editCheckIn, editCheckOut;
    private Spinner spinnerAdults, spinnerChildren, spinnerRooms;
    private Button buttonSearch;
    private RecyclerView recyclerViewHotels;

    private final List<Hotel> allHotels = new ArrayList<>();

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        editDestination = view.findViewById(R.id.editDestination);
        editCheckIn = view.findViewById(R.id.editCheckIn);
        editCheckOut = view.findViewById(R.id.editCheckOut);
        spinnerAdults = view.findViewById(R.id.spinnerAdults);
        spinnerChildren = view.findViewById(R.id.spinnerChildren);
        spinnerRooms = view.findViewById(R.id.spinnerRooms);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        recyclerViewHotels = view.findViewById(R.id.recyclerViewHotels);

        setupSpinners();
        setupDatePickers();
        setupHotelsData();

        recyclerViewHotels.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonSearch.setOnClickListener(v -> {
            String city = editDestination.getText().toString().trim();
            if (city.isEmpty()) {
                Toast.makeText(getContext(), "Введите направление", Toast.LENGTH_SHORT).show();
                return;
            }

            List<Hotel> filtered = new ArrayList<>();
            for (Hotel hotel : allHotels) {
                if (hotel.city.equalsIgnoreCase(city)) {
                    filtered.add(hotel);
                }
            }

            if (filtered.isEmpty()) {
                Toast.makeText(getContext(), "Отели не найдены", Toast.LENGTH_SHORT).show();
            } else {
                recyclerViewHotels.setAdapter(new HotelAdapter(requireContext(), filtered, hotel -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("hotel", hotel);
                    NavController nav = Navigation.findNavController(requireView());
                    nav.navigate(R.id.action_homeFragment_to_hotelDetailFragment, bundle);
                }));
            }
        });

        return view;
    }

    private void setupSpinners() {
        ArrayAdapter<String> adapterStandard = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                Arrays.asList("1", "2", "3", "4", "5"));
        adapterStandard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterWithZero = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                Arrays.asList("0", "1", "2", "3", "4", "5"));
        adapterWithZero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAdults.setAdapter(adapterStandard);
        spinnerRooms.setAdapter(adapterStandard);
        spinnerChildren.setAdapter(adapterWithZero);
    }

    private void setupDatePickers() {
        editCheckIn.setOnClickListener(v -> showDatePicker(editCheckIn));
        editCheckOut.setOnClickListener(v -> showDatePicker(editCheckOut));
    }

    private void showDatePicker(EditText target) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            target.setText(String.format(Locale.getDefault(), "%02d.%02d.%d", dayOfMonth, month + 1, year));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setupHotelsData() {
        allHotels.clear();
        allHotels.add(new Hotel("Hilton Moscow", "Москва",
                "Пятизвёздочный отель рядом с центром...",
                R.drawable.hilton, 5, 9.4f, 11500));

        allHotels.add(new Hotel("Grand Sochi", "Сочи",
                "Роскошный отель на берегу моря...",
                R.drawable.sochi, 4, 8.8f, 8200));

        allHotels.add(new Hotel("Nevsky Hotel", "Санкт-Петербург",
                "Уютный отель в центре Петербурга...",
                R.drawable.nevsky, 3, 8.3f, 6200));



    }
}
