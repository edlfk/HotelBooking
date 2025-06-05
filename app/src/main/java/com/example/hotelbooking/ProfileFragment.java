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

public class ProfileFragment extends Fragment {

    private TextView textEmail;
    private EditText editFullName;
    private Button buttonLogout, buttonEditName;

    private FirebaseAuth auth;

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
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            textEmail.setText("Email: " + user.getEmail());
            editFullName.setText(user.getDisplayName() != null ? user.getDisplayName() : "");
        }

        buttonEditName.setOnClickListener(v -> {
            String newName = editFullName.getText().toString().trim();
            if (!newName.isEmpty()) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newName)
                        .build();
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Имя обновлено", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Ошибка обновления", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        buttonLogout.setOnClickListener(v -> {
            auth.signOut();
            NavController nav = Navigation.findNavController(v);
            nav.navigate(R.id.loginFragment);
        });

        return view;
    }
}
