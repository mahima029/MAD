package com.techiguru.aiinterview;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class CategoryActivity extends AppCompatActivity {
    MaterialCardView cardJava;
    MaterialCardView cardTechnical;
    MaterialCardView cardPython;
    MaterialCardView cardHR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*EdgeToEdge.enable(this);*/
        setContentView(R.layout.activity_category);

        cardJava = findViewById(R.id.cardJava);
        cardTechnical = findViewById(R.id.cardTechnical);
        cardPython = findViewById(R.id.cardPython);
        cardHR = findViewById(R.id.cardHR);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        cardJava.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            CategoryActivity.this,
                            ExperienceActivity.class
                    );

            intent.putExtra("category", "Java");

            startActivity(intent);

        });

        cardPython.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            CategoryActivity.this,
                            ExperienceActivity.class
                    );

            intent.putExtra("category", "Python");

            startActivity(intent);
        });

        cardTechnical.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            CategoryActivity.this,
                            ExperienceActivity.class
                    );

            intent.putExtra("category", "Technical");

            startActivity(intent);
        });

        cardHR.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            CategoryActivity.this,
                            ExperienceActivity.class
                    );

            intent.putExtra("category", "HR");

            startActivity(intent);
        });
    }
}