package com.techiguru.aiinterview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    private TextView txtWelcome;

    private MaterialCardView cardStartInterview;
    private MaterialCardView cardHistory;
    private MaterialCardView cardProfile;
    private MaterialCardView cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        txtWelcome = findViewById(R.id.txtWelcome);

        cardStartInterview = findViewById(R.id.cardStartInterview);
        cardHistory = findViewById(R.id.cardHistory);
        cardProfile = findViewById(R.id.cardProfile);
        cardLogout = findViewById(R.id.cardLogout);

        String uid = FirebaseAuth.getInstance()
                .getCurrentUser()
                .getUid();

        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference("users");

        databaseReference.child(uid)
                .get()
                .addOnSuccessListener(snapshot -> {

                    if (snapshot.exists()) {

                        String name =
                                snapshot.child("name")
                                        .getValue(String.class);

                        txtWelcome.setText(
                                "Welcome, " + name + " 👋"
                        );
                    }
                });

        cardHistory.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            HomeActivity.this,
                            HistoryActivity.class
                    )
            );

        });

        cardLogout.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            startActivity(
                    new Intent(
                            HomeActivity.this,
                            LoginActivity.class
                    )
            );

            finish();
        });

        cardStartInterview.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            HomeActivity.this,
                            CategoryActivity.class
                    )
            );

        });

        cardProfile.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            HomeActivity.this,
                            ProfileActivity.class
                    )
            );

        });

    }
}