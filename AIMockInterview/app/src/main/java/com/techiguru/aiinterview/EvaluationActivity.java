package com.techiguru.aiinterview;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class EvaluationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        TextView txtScore =
                findViewById(R.id.txtScore);

        TextView txtStrengths =
                findViewById(R.id.txtStrengths);

        TextView txtWeaknesses =
                findViewById(R.id.txtWeaknesses);

        TextView txtRecommendation =
                findViewById(R.id.txtRecommendation);

        String report =
                getIntent().getStringExtra("report");

        try {

            JSONObject json =
                    new JSONObject(report);

            txtScore.setText(
                    "🏆 Score\n\n" +
                            json.getString("score")
            );

            txtStrengths.setText(
                    "💪 Strengths\n\n" +
                            json.getString("strengths")
            );

            txtWeaknesses.setText(
                    "⚠ Weaknesses\n\n" +
                            json.getString("weaknesses")
            );

            txtRecommendation.setText(
                    "📚 Recommendation\n\n" +
                            json.getString("recommendation")
            );

        } catch (Exception e) {

            txtScore.setText(report);
        }
    }
}