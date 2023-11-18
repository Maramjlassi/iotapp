package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.ListenerRegistration;

public class Getuser extends AppCompatActivity {

    private TextView nameTextView, dobTextView, workplaceTextView;
    private Button displayInfoButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ListenerRegistration userListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getuser);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nameTextView = findViewById(R.id.nameTextView);
        dobTextView = findViewById(R.id.dobTextView);
        workplaceTextView = findViewById(R.id.workplaceTextView);
        displayInfoButton = findViewById(R.id.displayInfoButton);

        displayInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayUserInfo();
            }
        });
    }

    private void displayUserInfo() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userListener = db.collection("users")
                    .document(currentUser.getUid())
                    .addSnapshotListener(this, (documentSnapshot, e) -> {
                        if (e != null) {
                            // Handle errors
                            return;
                        }

                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            String dob = documentSnapshot.getString("dob");
                            String workplace = documentSnapshot.getString("mobile");

                            nameTextView.setText("Name: " + name);
                            dobTextView.setText("Date of Birth: " + dob);
                            workplaceTextView.setText("Workplace: " + workplace);
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        if (userListener != null) {
            userListener.remove();
        }
        super.onDestroy();
    }
}

