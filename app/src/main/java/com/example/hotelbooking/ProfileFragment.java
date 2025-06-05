package com.example.hotelbooking;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private TextView textEmail;
    private EditText editFullName;
    private Button buttonLogout, buttonEditName;

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseUser user;

    public ProfileFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textEmail = view.findViewById(R.id.textEmail);
        editFullName = view.findViewById(R.id.editFullName);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        buttonEditName = view.findViewById(R.id.buttonEditName);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        if (user != null) {
            textEmail.setText("Email: " + user.getEmail());
            loadUserProfile();
        }

        buttonEditName.setOnClickListener(v -> updateFullName());
        buttonLogout.setOnClickListener(v -> {
            auth.signOut();
            Navigation.findNavController(v).navigate(R.id.loginFragment);
        });

        return view;
    }

    private void loadUserProfile() {
        db.collection("users").document(user.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String fullName = documentSnapshot.getString("fullName");
                        editFullName.setText(fullName);
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Ошибка загрузки профиля", Toast.LENGTH_SHORT).show());
    }

    private void updateFullName() {
        String newName = editFullName.getText().toString().trim();
        if (newName.isEmpty()) {
            Toast.makeText(getContext(), "Введите ФИО", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        updates.put("fullName", newName);
        updates.put("email", user.getEmail());

        db.collection("users").document(user.getUid())
                .set(updates, SetOptions.merge())
                .addOnSuccessListener(unused ->
                        Toast.makeText(getContext(), "Профиль обновлён", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Ошибка при обновлении", Toast.LENGTH_SHORT).show());
    }
}
