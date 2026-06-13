package com.techiguru.aiinterview;

import android.os.Bundle;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Button;
import android.os.Looper;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.speech.RecognizerIntent;

import java.util.Locale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuestionActivity extends AppCompatActivity {
    private static final String API_KEY = "REDACTED_GCP_API_KEY";
    private String category;
    private TextView txtCategory, txtQuestion, txtStatus, txtAnswer;
    private Button btnNext;
    private OkHttpClient client =
            new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
    private ArrayList<String> questions = new ArrayList<>();
    private ArrayList<String> userAnswers = new ArrayList<>();
    private int currentIndex = 0;
    private String experience;
    private static final int REQUEST_SPEECH = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        txtCategory = findViewById(R.id.txtCategory);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtStatus = findViewById(R.id.txtStatus);
        txtAnswer = findViewById(R.id.txtAnswer);

        btnNext = findViewById(R.id.btnNext);

        Button btnSpeak = findViewById(R.id.btnSpeak);

        category =
                getIntent().getStringExtra("category");

        experience =
                getIntent().getStringExtra("experience");

        txtCategory.setText(
                category + " - " + experience
        );

        txtQuestion.setText("Click Next to generate AI questions");

        btnSpeak.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
                    );

            intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            );

            intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault()
            );

            intent.putExtra(
                    RecognizerIntent.EXTRA_PROMPT,
                    "Answer the interview question"
            );

            try {

                startActivityForResult(
                        intent,
                        REQUEST_SPEECH
                );

            } catch (Exception e) {

                txtAnswer.setText(
                        "Speech recognition not supported"
                );
            }
        });

        btnNext.setOnClickListener(v -> {

            if(questions.isEmpty()){

                txtStatus.setText(
                        "Generating Questions..."
                );

                generateQuestions();

            }else{
                userAnswers.add(
                        txtAnswer.getText().toString()
                );

                currentIndex++;

                if(currentIndex < questions.size()){

                    txtQuestion.setText(
                            questions.get(currentIndex)
                    );
                    txtAnswer.setText("");

                }else{

                    evaluateInterview();
                }
            }
        });
    }
    private void generateQuestions() {
        new Thread(() -> {
            try {
                String prompt = "Generate 5 technical interview questions for a " + category +
                        " role at " + experience + " experience level. " +
                        "Return ONLY the questions, one per line, without numbers or introductory text.";

                JSONObject textPart = new JSONObject();
                textPart.put("text", prompt);
                JSONArray parts = new JSONArray();
                parts.put(textPart);
                JSONObject content = new JSONObject();
                content.put("parts", parts);
                JSONArray contents = new JSONArray();
                contents.put(content);
                JSONObject bodyJson = new JSONObject();
                bodyJson.put("contents", contents);

                RequestBody body = RequestBody.create(
                        bodyJson.toString(),
                        MediaType.parse("application/json")
                );

                String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="
                        + API_KEY;

                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }

                    String responseBody = response.body().string();
                    JSONObject json = new JSONObject(responseBody);
                    JSONArray candidates = json.getJSONArray("candidates");
                    JSONObject candidate = candidates.getJSONObject(0);
                    JSONObject contentObj = candidate.getJSONObject("content");
                    JSONArray responseParts = contentObj.getJSONArray("parts");
                    String generatedText = responseParts.getJSONObject(0).getString("text");

                    questions.clear();
                    String[] lines = generatedText.split("\n");
                    for (String line : lines) {
                        line = line.trim();
                        // Remove leading numbers if they exist (e.g., "1. ")
                        line = line.replaceFirst("^\\d+\\.\\s*", "");
                        if (!line.isEmpty()) {
                            questions.add(line);
                        }
                    }

                    runOnUiThread(() -> {
                        txtStatus.setText(questions.size() + " Questions Generated");
                        currentIndex = 0;
                        if (!questions.isEmpty()) {
                            txtQuestion.setText(questions.get(0));
                        }
                    });
                }

            } catch (Exception e) {
                runOnUiThread(() -> {
                    txtStatus.setText("Error: " + e.getMessage());
                });
            }
        }).start();
    }

    private void evaluateInterview() {
        txtStatus.setText("Evaluating Interview...");
        new Thread(() -> {
            try {
                StringBuilder interviewData = new StringBuilder();
                for (int i = 0; i < questions.size() && i < userAnswers.size(); i++) {
                    interviewData.append("Question: ").append(questions.get(i)).append("\n");
                    interviewData.append("Answer: ").append(userAnswers.get(i)).append("\n\n");
                }

                String prompt = "Evaluate this interview and return ONLY valid JSON.\n" +
                        "Score must be a number from 0 to 10 in the format X/10.\n\n" +
                        "JSON structure:\n" +
                        "{\n" +
                        "\"score\":\"8/10\",\n" +
                        "\"strengths\":\"...\",\n" +
                        "\"weaknesses\":\"...\",\n" +
                        "\"recommendation\":\"...\"\n" +
                        "}\n\n" +
                        interviewData;

                JSONObject textPart = new JSONObject();
                textPart.put("text", prompt);
                JSONArray parts = new JSONArray();
                parts.put(textPart);
                JSONObject content = new JSONObject();
                content.put("parts", parts);
                JSONArray contents = new JSONArray();
                contents.put(content);
                JSONObject bodyJson = new JSONObject();
                bodyJson.put("contents", contents);

                RequestBody body = RequestBody.create(
                        bodyJson.toString(),
                        MediaType.parse("application/json")
                );

                String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="
                        + API_KEY;

                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }

                    String responseBody = response.body().string();
                    JSONObject json = new JSONObject(responseBody);

                    if (json.has("candidates")) {
                        String rawReport = json.getJSONArray("candidates")
                                .getJSONObject(0)
                                .getJSONObject("content")
                                .getJSONArray("parts")
                                .getJSONObject(0)
                                .getString("text");

                        final String report = cleanJsonResponse(rawReport);

                        runOnUiThread(() -> {
                            String uid = FirebaseAuth.getInstance().getCurrentUser() != null ?
                                    FirebaseAuth.getInstance().getCurrentUser().getUid() : "anonymous";

                            DatabaseReference historyRef = FirebaseDatabase.getInstance()
                                    .getReference("users")
                                    .child(uid)
                                    .child("interviews");

                            String interviewId = historyRef.push().getKey();
                            String score = "N/A";

                            try {
                                JSONObject reportJson = new JSONObject(report);
                                score = reportJson.getString("score");
                            } catch (Exception e) {
                                android.util.Log.e("JSON_PARSE_ERROR", "Failed to parse: " + report);
                            }

                            if (interviewId != null) {
                                historyRef.child(interviewId).child("report").setValue(report);
                                historyRef.child(interviewId).child("category").setValue(category);
                                historyRef.child(interviewId).child("score").setValue(score);
                                String date = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                                        .format(new java.util.Date());
                                historyRef.child(interviewId).child("date").setValue(date);
                            }

                            Intent intent = new Intent(QuestionActivity.this, EvaluationActivity.class);
                            intent.putExtra("report", report);
                            startActivity(intent);
                            finish();
                        });
                    } else {
                        throw new Exception("No candidates in response: " + responseBody);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    txtStatus.setText("Error: " + e.getMessage());
                });
            }
        }).start();
    }

    private String cleanJsonResponse(String response) {
        String cleaned = response.trim();
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3);
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }
        return cleaned.trim();
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if(requestCode == REQUEST_SPEECH
                && resultCode == RESULT_OK
                && data != null){

            ArrayList<String> result =
                    data.getStringArrayListExtra(
                            RecognizerIntent.EXTRA_RESULTS
                    );

            if(result != null
                    && !result.isEmpty()){

                txtAnswer.setText(
                        result.get(0)
                );
            }
        }
    }
}