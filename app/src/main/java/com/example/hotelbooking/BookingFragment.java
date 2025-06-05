package com.example.hotelbooking;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import java.util.Calendar;
import java.util.List;
import com.example.hotelbooking.Booking;

public class BookingFragment extends Fragment {

    private EditText fullName, checkIn, checkOut;
    private Spinner paymentSpinner;
    private TextView totalText;
    private Hotel selectedHotel;

    public BookingFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        BookingDAO dao = new BookingDAO(requireContext());
        fullName = view.findViewById(R.id.editFullName);
        checkIn = view.findViewById(R.id.editCheckIn);
        checkOut = view.findViewById(R.id.editCheckOut);
        paymentSpinner = view.findViewById(R.id.spinnerPayment);
        totalText = view.findViewById(R.id.textTotal);
        Button confirm = view.findViewById(R.id.buttonConfirm);

        selectedHotel = (Hotel) getArguments().getSerializable("hotel");

        checkIn.setOnClickListener(v -> showDatePicker(checkIn));
        checkOut.setOnClickListener(v -> showDatePicker(checkOut));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, List.of("Карта", "Наличные", "Онлайн"));
        paymentSpinner.setAdapter(adapter);

        confirm.setOnClickListener(v -> {
            if (fullName.getText().toString().isEmpty() ||
                    checkIn.getText().toString().isEmpty() ||
                    checkOut.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            Booking b = new Booking(
                    fullName.getText().toString(),
                    selectedHotel,
                    checkIn.getText().toString(),
                    checkOut.getText().toString(),
                    paymentSpinner.getSelectedItem().toString()
            );

            dao.addBooking(b);
            Toast.makeText(getContext(), "Бронирование сохранено", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.action_bookingFragment_to_confirmedBookingsFragment);
        });

        if (selectedHotel != null) {
            totalText.setText("Сумма к оплате: " + selectedHotel.pricePerNight + "₽");
        }
    }

    private void showDatePicker(EditText target) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) ->
                target.setText(String.format("%02d.%02d.%d", dayOfMonth, month + 1, year)),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
