package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Login;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LedStates extends AppCompatActivity {

    private static final String TAG = "LedStates";
    private DatabaseReference databaseReference, databaseReference2, databaseReference3, databaseReference4;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_states);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("ledStates").child("pair1");
        View red1 = findViewById(R.id.red1);
        View green1 = findViewById(R.id.green1);
        Button change1 = findViewById(R.id.change1);
        databaseReference2 = FirebaseDatabase.getInstance().getReference("ledStates").child("pair2");
        View red2 = findViewById(R.id.red2);
        View green2 = findViewById(R.id.green2);
        Button change2 = findViewById(R.id.change2);
        databaseReference3 = FirebaseDatabase.getInstance().getReference("ledStates").child("pair3");
        View red3 = findViewById(R.id.red3);
        View green3 = findViewById(R.id.green3);
        Button change3 = findViewById(R.id.change3);
        databaseReference4 = FirebaseDatabase.getInstance().getReference("ledStates").child("pair4");
        View red4 = findViewById(R.id.red4);
        View green4 = findViewById(R.id.green4);
        Button change4 = findViewById(R.id.change4);

        readLedStates(red1, green1);
        readLedStates2(red2, green2);
        readLedStates3(red3, green3);
        readLedStates4(red4, green4);

        change1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            boolean currentGreenState = dataSnapshot.child("green").getValue(Boolean.class);
                            boolean currentRedState = dataSnapshot.child("red").getValue(Boolean.class);

                            // Inverser les valeurs
                            boolean newGreenState = !currentGreenState;
                            boolean newRedState = !currentRedState;

                            // Mettre à jour les valeurs dans la base de données
                            databaseReference.child("green").setValue(newGreenState);
                            databaseReference.child("red").setValue(newRedState);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                });
                fetchUserInfo();
                readLedStates(red1, green1);
            }
        });
        change2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            boolean currentGreenState = dataSnapshot.child("green").getValue(Boolean.class);
                            boolean currentRedState = dataSnapshot.child("red").getValue(Boolean.class);

                            // Inverser les valeurs
                            boolean newGreenState = !currentGreenState;
                            boolean newRedState = !currentRedState;

                            // Mettre à jour les valeurs dans la base de données
                            databaseReference2.child("green").setValue(newGreenState);
                            databaseReference2.child("red").setValue(newRedState);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                });
                fetchUserInfo();
                readLedStates2(red2, green2);
            }
        });
        change3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            boolean currentGreenState = dataSnapshot.child("green").getValue(Boolean.class);
                            boolean currentRedState = dataSnapshot.child("red").getValue(Boolean.class);

                            // Inverser les valeurs
                            boolean newGreenState = !currentGreenState;
                            boolean newRedState = !currentRedState;

                            // Mettre à jour les valeurs dans la base de données
                            databaseReference3.child("green").setValue(newGreenState);
                            databaseReference3.child("red").setValue(newRedState);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                });
                fetchUserInfo();
                readLedStates3(red3, green3);
            }
        });
        change4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            boolean currentGreenState = dataSnapshot.child("green").getValue(Boolean.class);
                            boolean currentRedState = dataSnapshot.child("red").getValue(Boolean.class);

                            // Inverser les valeurs
                            boolean newGreenState = !currentGreenState;
                            boolean newRedState = !currentRedState;

                            // Mettre à jour les valeurs dans la base de données
                            databaseReference4.child("green").setValue(newGreenState);
                            databaseReference4.child("red").setValue(newRedState);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                });
                fetchUserInfo();
                readLedStates4(red4, green4);
            }
        });

    }

    private void readLedStates(View redView, View greenView) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get LED states from the database
                    boolean redState = dataSnapshot.child("red").getValue(Boolean.class);
                    boolean greenState = dataSnapshot.child("green").getValue(Boolean.class);

                    // Update the background color of red1 based on the redState
                    if (redState) {redView.setBackgroundResource(R.drawable.circle_background);}
                    else {
                        redView.setBackgroundResource(R.drawable.changecircle_background);
                    }

                    // Update the background color of green1 based on the greenState
                    if (greenState) {greenView.setBackgroundResource(R.drawable.circle_backgroung2);}
                    else {
                        greenView.setBackgroundResource(R.drawable.changecircle_background);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
    private void readLedStates2(View redView, View greenView) {
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get LED states from the database
                    boolean redState = dataSnapshot.child("red").getValue(Boolean.class);
                    boolean greenState = dataSnapshot.child("green").getValue(Boolean.class);

                    // Update the background color of red1 based on the redState
                    if (redState) {redView.setBackgroundResource(R.drawable.circle_background);}
                    else {
                        redView.setBackgroundResource(R.drawable.changecircle_background);
                    }

                    // Update the background color of green1 based on the greenState
                    if (greenState) {greenView.setBackgroundResource(R.drawable.circle_backgroung2);}
                    else {
                        greenView.setBackgroundResource(R.drawable.changecircle_background);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
    private void readLedStates3(View redView, View greenView) {
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get LED states from the database
                    boolean redState = dataSnapshot.child("red").getValue(Boolean.class);
                    boolean greenState = dataSnapshot.child("green").getValue(Boolean.class);

                    // Update the background color of red1 based on the redState
                    if (redState) {redView.setBackgroundResource(R.drawable.circle_background);}
                    else {
                        redView.setBackgroundResource(R.drawable.changecircle_background);
                    }

                    // Update the background color of green1 based on the greenState
                    if (greenState) {greenView.setBackgroundResource(R.drawable.circle_backgroung2);}
                    else {
                        greenView.setBackgroundResource(R.drawable.changecircle_background);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
    private void readLedStates4(View redView, View greenView) {
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get LED states from the database
                    boolean redState = dataSnapshot.child("red").getValue(Boolean.class);
                    boolean greenState = dataSnapshot.child("green").getValue(Boolean.class);

                    // Update the background color of red1 based on the redState
                    if (redState) {redView.setBackgroundResource(R.drawable.circle_background);}
                    else {
                        redView.setBackgroundResource(R.drawable.changecircle_background);
                    }

                    // Update the background color of green1 based on the greenState
                    if (greenState) {greenView.setBackgroundResource(R.drawable.circle_backgroung2);}
                    else {
                        greenView.setBackgroundResource(R.drawable.changecircle_background);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    //Google sheet
    private void fetchUserInfo() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("users").document(currentUser.getUid());

            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String userName = documentSnapshot.getString("name");
                    // Directly add the user's name to the server without using an EditText
                    addData(userName);
                }
            }).addOnFailureListener(e -> {
                // Handle failure
                Toast.makeText(LedStates.this, "Failed to fetch user information", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void addData(String name) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzcYhSZmHg93O8zNfFEvmq-lJBuFnNUeyjm3oDw8W8wQc2HGXpInFpxEgDgWkJ-LTEIfg/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(LedStates.this, "LedStates switched", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors
                Toast.makeText(LedStates.this, "Failed to update data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "addname");
                params.put("name", name);

                return params;
            }
        };

        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

