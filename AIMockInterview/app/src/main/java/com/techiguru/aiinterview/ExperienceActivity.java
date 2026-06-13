package com.techiguru.aiinterview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExperienceActivity extends AppCompatActivity {

    private RadioGroup radioGroupExperience;
    private Button btnGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        radioGroupExperience =
                findViewById(R.id.radioGroupExperience);

        btnGenerate =
                findViewById(R.id.btnGenerate);

        String category =
                getIntent().getStringExtra("category");

        btnGenerate.setOnClickListener(v -> {

            int selectedId =
                    radioGroupExperience.getCheckedRadioButtonId();

            if(selectedId == -1){

                Toast.makeText(
                        this,
                        "Select Experience Level",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            RadioButton rb =
                    findViewById(selectedId);

            String experience =
                    rb.getText().toString();

            Intent intent =
                    new Intent(
                            ExperienceActivity.this,
                            QuestionActivity.class
                    );

            intent.putExtra(
                    "category",
                    category
            );

            intent.putExtra(
                    "experience",
                    experience
            );

            startActivity(intent);
        });
    }
}