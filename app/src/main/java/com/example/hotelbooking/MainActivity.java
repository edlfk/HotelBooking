package com.example.hotelbooking;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.google.firebase.auth.FirebaseAuth;
import android.view.View;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);

// Скрытие BottomNavigationView на экранах входа и регистрации:
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.loginFragment || destination.getId() == R.id.registerFragment) {
                bottomNav.setVisibility(View.GONE);
            } else {
                bottomNav.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        if (auth.getCurrentUser() != null && navController.getCurrentDestination() != null &&
                navController.getCurrentDestination().getId() == R.id.loginFragment) {
            navController.navigate(R.id.action_loginFragment_to_homeFragment);
        }
    }
}
