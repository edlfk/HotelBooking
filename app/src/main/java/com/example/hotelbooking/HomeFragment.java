package com.example.hotelbooking;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class HomeFragment extends Fragment {

    private EditText editDestination, editCheckIn, editCheckOut;
    private Spinner spinnerAdults, spinnerChildren, spinnerRooms;
    private Button buttonSearch;
    private RecyclerView recyclerViewHotels;

    public HomeFragment() {}

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
        setupRecycler();

        buttonSearch.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Поиск по параметрам...", Toast.LENGTH_SHORT).show();
            // можно добавить фильтрацию
        });

        return view;
    }

    private void setupSpinners() {
        // Для взрослых и номеров: от 1 до 5
        ArrayAdapter<String> adapterStandard = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                Arrays.asList("1", "2", "3", "4", "5"));
        adapterStandard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Для детей: от 0 до 5
        ArrayAdapter<String> adapterWithZero = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                Arrays.asList("0", "1", "2", "3", "4", "5"));
        adapterWithZero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAdults.setAdapter(adapterStandard);
        spinnerRooms.setAdapter(adapterStandard);
        spinnerChildren.setAdapter(adapterWithZero); // 👈 начинаем с 0
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

    private void setupRecycler() {
        recyclerViewHotels.setLayoutManager(new LinearLayoutManager(getContext()));

        // временные отели-заглушки
        List<String> hotels = Arrays.asList("Отель Европа", "Grand Palace", "Holiday Inn", "Hilton Plaza");

        recyclerViewHotels.setAdapter(new HotelAdapter(hotels));
    }
}
