package com.techiguru.aiinterview;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView listHistory;
    ArrayList<String> historyList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listHistory = findViewById(R.id.listHistory);

        historyList = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                historyList
        );

        listHistory.setAdapter(adapter);

        loadHistory();
    }

    private void loadHistory() {

        String uid =
                FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getUid();

        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid)
                .child("interviews")
                .get()
                .addOnSuccessListener(snapshot -> {

                    historyList.clear();

                    for(var interview : snapshot.getChildren()) {

                        String category =
                                interview.child("category")
                                        .getValue(String.class);

                        String score =
                                interview.child("score")
                                        .getValue(String.class);

                        String date =
                                interview.child("date")
                                        .getValue(String.class);

                        historyList.add(
                                "📋 " + category +
                                        "\n🏆 Score: " + score +
                                        "\n📅 " + date
                        );
                    }

                    adapter.notifyDataSetChanged();
                });
    }
}