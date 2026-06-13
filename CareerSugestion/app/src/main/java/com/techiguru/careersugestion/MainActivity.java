package com.techiguru.careersugestion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
Button bt_suggest;
TextView tv_result;
EditText et_interest;
String groq_key = "REDACTED_GROQ_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bt_suggest = findViewById(R.id.bt_suggest);
        tv_result = findViewById(R.id.tv_result);
        et_interest = findViewById(R.id.et_interest);

        bt_suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestCareer();
            }
        });
    }

    private void suggestCareer() {
        String interest = et_interest.getText().toString();

        try {

            JSONObject body = new JSONObject();
            body.put("model", "llama-3.3-70b-versatile");

            JSONArray messages = new JSONArray();

            JSONObject msg = new JSONObject();
            msg.put("role", "user");
            msg.put("content",
                    "Suggest 3 careers for " + interest);

            messages.put(msg);
            body.put("messages", messages);

            JsonObjectRequest request =
                    new JsonObjectRequest(
                            Request.Method.POST,
                            "https://api.groq.com/openai/v1/chat/completions",
                            body,

                            response -> {
                                try {
                                    String result =
                                            response.getJSONArray("choices")
                                                    .getJSONObject(0)
                                                    .getJSONObject("message")
                                                    .getString("content");

                                    tv_result.setText(result);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            },

                            error -> tv_result.setText("Error")
                    ) {

                        @Override
                        public Map<String, String> getHeaders()
                                throws AuthFailureError {

                            Map<String, String> headers =
                                    new HashMap<>();

                            headers.put("Authorization",
                                    "Bearer " + groq_key);

                            headers.put("Content-Type",
                                    "application/json");

                            return headers;
                        }
                    };

            RequestQueue queue =
                    Volley.newRequestQueue(this);

            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}