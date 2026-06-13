package com.techiguru.fitmylife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
//declare the java objects based on UI widgets
    EditText et_fullName, et_emailId, et_password, et_confirmPassword;
    TextView tv_alreadyAccount;
    Button bt_register;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
//to create or open the shared preferences file with mode
        sp = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        ed = sp.edit();

        bt_register = findViewById(R.id.bt_register);
        et_emailId = findViewById(R.id.et_emailId);
        et_fullName = findViewById(R.id.et_fullName);
        et_password = findViewById(R.id.et_password);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);
        tv_alreadyAccount = findViewById(R.id.tv_alreadyAccount);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switch to main activity
                Intent rtlogin = new Intent(RegisterActivity.this, MainActivity.class);
                //To start the intent
                startActivity(rtlogin);
                finish();
                //To save the user details in shared preferences
                ed.putString("name",et_fullName.getText().toString());
                ed.putString("emailId",et_emailId.getText().toString());
                ed.putString("password",et_password.getText().toString());
                ed.commit();
            }
        });
        //to click on create account and redirect to register activity.
        tv_alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switch to login activity
                Intent login = new Intent(RegisterActivity.this,LoginActivity.class);
                //To start the intent
                startActivity(login);
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