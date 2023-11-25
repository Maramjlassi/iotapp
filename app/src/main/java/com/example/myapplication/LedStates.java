package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;

public class LedStates extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_states);

        // Initialize FirebaseAuth instance
        auth = FirebaseAuth.getInstance();

        // Additional initialization or setup code can be added here
    }

    public void logout(View view) {
        try {
            // Sign out the user
            auth.signOut();

            // Navigate to the login activity
            Intent intent = new Intent(LedStates.this, Login.class);
            startActivity(intent);

            // Finish the current activity to prevent going back to it
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle errors or log them as needed
        }
    }
}
