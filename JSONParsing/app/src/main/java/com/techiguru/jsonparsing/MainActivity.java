package com.techiguru.jsonparsing;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView lv_data;
ArrayList <String> nameList;
ArrayAdapter<String> adapter;
String apiUrl = "https://jsonplaceholder.typicode.com/users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv_data = findViewById(R.id.lv_data);
        nameList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);
        lv_data.setAdapter(adapter);
        fetchdata();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void fetchdata() {

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,

                response -> {
                    //JSON error can occur here like missing parameter in data like missing id or mismatch parenthesis
                    try {

                        for (int i = 0; i < response.length(); i++) {

                            JSONObject obj = response.getJSONObject(i);

                            int id = obj.getInt("id");
                            String name = obj.getString("name");
                            String emailId = obj.getString("email");
                            String city = obj.getJSONObject("address").getString("city");

                            nameList.add(
                                    "ID : " + id +
                                            "\nName : " + name +
                                                 "\nEmail : " + emailId +
                                                    "\nCity : " + city
                            );
                        }

                        //this method refresh the data
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                },

                error -> Toast.makeText(
                        MainActivity.this,
                        error.getMessage(),
                        Toast.LENGTH_SHORT
                ).show()
        );

        queue.add(request);
    }
}