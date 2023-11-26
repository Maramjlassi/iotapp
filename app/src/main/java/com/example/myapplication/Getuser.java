package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Getuser extends AppCompatActivity {

    private static final String TAG = "Getuser";

    FirebaseAuth auth;
    FirebaseFirestore db;
    DatabaseReference databaseReference;
    ListView listView;
    Button RegisterButton;
    private String workPlace;
    String userType;
    TextView welcome, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getuser);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        listView = findViewById(R.id.listView);
        RegisterButton = findViewById(R.id.RegisterButton);
        welcome = findViewById(R.id.welcomeTextView);
        location = findViewById(R.id.location);


        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            db.collection("users")
                    .document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    userType = document.getString("name");
                                    workPlace = document.getString("work");
                                    if (workPlace != null) {
                                        displayKeysForWorkPlace(workPlace);
                                    }
                                    welcome.setText(userType + ", you are in: " +workPlace);
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.w(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the click event for the item at position
                // 'position' is the position of the clicked item in the adapter

                // Your code for item click event goes here
                Intent intent = new Intent(getApplicationContext(), LedStates.class);
                startActivity(intent);
                finish();
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user is an admin
                if ("Admin".equals(userType)) {
                    Intent intent = new Intent(getApplicationContext(), Register.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Only admin can perform this action", Toast.LENGTH_SHORT).show();
                    // Handle the case where the user is not an admin
                }
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Location.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayKeysForWorkPlace(String workPlace) {
        // Your existing code for displaying keys
        db.collection("Feu")
                .document(workPlace)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String, Object> data = document.getData();
                                if (data != null) {
                                    List<String> keys = new ArrayList<>(data.keySet());
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Getuser.this, android.R.layout.simple_list_item_1, keys);
                                    listView.setAdapter(adapter);
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.w(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    public void logout(View view) {
        auth.signOut();
        Intent intent = new Intent(Getuser.this, Login.class);
        startActivity(intent);
        finish();
    }
}
