package com.techiguru.aiinterview;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    TextView txtName, txtEmail, txtInterviews, txtBestScore, txtAverageScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtInterviews = findViewById(R.id.txtInterviews);
        txtBestScore = findViewById(R.id.txtBestScore);

        txtAverageScore = findViewById(R.id.txtAverageScore);

        loadProfile();
    }

    private void loadProfile() {

        String uid =
                FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getUid();

        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid)
                .get()
                .addOnSuccessListener(snapshot -> {

                    String name =
                            snapshot.child("name")
                                    .getValue(String.class);

                    String email =
                            snapshot.child("email")
                                    .getValue(String.class);

                    long totalInterviews =
                            snapshot.child("interviews")
                                    .getChildrenCount();

                    int bestScore = 0;
                    int totalScore = 0;
                    int count = 0;

                    for (com.google.firebase.database.DataSnapshot interview :
                            snapshot.child("interviews").getChildren()) {

                        String score =
                                interview.child("score")
                                        .getValue(String.class);

                        if (score != null && score.contains("/10")) {

                            try {

                                int value =
                                        Integer.parseInt(
                                                score.split("/")[0]
                                        );

                                totalScore += value;

                                count++;

                                if (value > bestScore) {
                                    bestScore = value;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    double averageScore = 0;

                    if (count > 0) {
                        averageScore =
                                (double) totalScore / count;
                    }

                    txtName.setText("👤 " + name);
                    txtEmail.setText("📧 " + email);
                    txtInterviews.setText(
                            "📊 Total Interviews: "
                                    + totalInterviews
                    );
                    txtBestScore.setText(
                            "🏆 Best Score: "
                                    + bestScore + "/10"
                    );

                    txtAverageScore.setText(
                            "📈 Average Score: "
                                    + String.format(
                                    java.util.Locale.getDefault(),
                                    "%.1f",
                                    averageScore
                            ) + "/10"
                    );
                });
    }
}