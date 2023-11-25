package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Register extends AppCompatActivity {

    TextInputEditText editTextName, editTextEmail, editTextMobile, editTextDob, editTextPassword, editTextConfirmPassword, editworkplace;
    Button buttonReg;
    FirebaseAuth auth;
    ProgressBar progressBar;
    TextView textview;

    //@Override
    /*public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.editText_register_full_name);
        editTextEmail = findViewById(R.id.editText_register_email);
        editTextMobile = findViewById(R.id.editText_register_mobile);
        editTextDob = findViewById(R.id.editText_register_dob);
        editTextPassword = findViewById(R.id.editText_register_password);
        editTextConfirmPassword = findViewById(R.id.editText_confirm_password);
        editworkplace = findViewById(R.id.workplace);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textview = findViewById(R.id.loginNow);

        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String name, email, mobile, dob, password, work;
                name = String.valueOf(editTextName.getText());
                email = String.valueOf(editTextEmail.getText());
                mobile = String.valueOf(editTextMobile.getText());
                dob = String.valueOf(editTextDob.getText());
                work = String.valueOf(editworkplace.getText());
                password = String.valueOf(editTextPassword.getText());
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = auth.getCurrentUser();

                                    saveUserInfoInFirestore(currentUser.getUid(), name, email, mobile, dob, work);

                                    Toast.makeText(Register.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Register.this, "failed to create.",
                                            Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(Register.this, "Registration failed: " + task.getException().getMessage(),
                                    //      Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }

    private void saveUserInfoInFirestore(String userId, String name, String email, String mobile, String dob, String work) {
        // Access the Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user object with the desired fields
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("mobile", mobile);
        user.put("dob", dob);
        user.put("work", work);

        // Add a new document with a generated ID
        db.collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User information saved successfully to Firestore");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding user information to Firestore", e);
                    }
                });
    }



}
