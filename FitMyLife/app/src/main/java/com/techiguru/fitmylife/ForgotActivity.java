package com.techiguru.fitmylife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgotActivity extends AppCompatActivity {
EditText et_emailID, et_new_password;
Button bt_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot);
        et_emailID = findViewById(R.id.et_emailID);
        et_new_password = findViewById(R.id.et_new_password);
        bt_submit = findViewById(R.id.bt_submit);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switch to main activity
                Intent flogin = new Intent(ForgotActivity.this,LoginActivity.class);
                //To start the intent
                startActivity(flogin);
                finish();
            }
        });
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }
}