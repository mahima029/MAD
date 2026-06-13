package com.techiguru.dietplanner;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String[] foodItems = {
          "Warm water",
          "Lemon",
          "Soaked almonds",
          "Walnut",
          "Honey",
          "Oats",
          "Milk",
          "Fruits",
          "Eggs",
          "Brown bread",
          "Poha",
          "Upma",
          "Daliya",
          "Tea",
          "Coffee",
          "Apple",
          "Banana",
          "Papaya",
          "Orange",
          "Coconut water",
          "Peanuts",
          "Chana",
          "Roti",
          "Rice",
          "Dal",
          "Sabzi",
          "Salad",
          "Cucumber",
          "Carrot",
          "Onion",
          "Curd",
          "Green tea",
          "Black tea",
          "Makhana",
          "Sandwich",
          "Grilled chicken",
          "Paneer",
          "Vegetables",
          "Warm milk",
          "Water"
    };

    ListView lv_diet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv_diet = findViewById(R.id.lv_diet);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodItems);
        lv_diet.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}