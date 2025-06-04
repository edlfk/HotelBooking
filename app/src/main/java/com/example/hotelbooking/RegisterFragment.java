package com.example.hotelbooking;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private FirebaseAuth auth;

    public RegisterFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
        registerButton = view.findViewById(R.id.registerButton);

        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(v -> attemptRegister());

        return view;
    }

    private void attemptRegister() {
        String email = emailEditText.getText().toString().trim();
        String pass = passwordEditText.getText().toString();
        String confirm = confirmPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirm)) {
            Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirm)) {
            Toast.makeText(getContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack(); // назад на экран входа
                    } else {
                        Toast.makeText(getContext(), "Ошибка: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
