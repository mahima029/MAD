package com.techiguru.fitmylife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
//declare the java objects based on UI widgets
    EditText et_emailId, et_password;
    TextView tv_forgotPassword, tv_createAccount;
    Button bt_login;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("myPref",0);

        bt_login = findViewById(R.id.bt_login);
        et_emailId = findViewById(R.id.et_emailId);
        et_password = findViewById(R.id.et_password);
        tv_forgotPassword = findViewById(R.id.tv_forgotPassword);
        tv_createAccount = findViewById(R.id.tv_createAccount);

//To click on the button....
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the data from shared preferences and pass into string
                String email = sp.getString("emailId",null);
                String password = sp.getString("password",null);
                //To validate the email and the password
                if (et_emailId.getText().toString().equals(email)
                        && et_password.getText().toString().equals(password))
                {
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putBoolean("isLoggedIn",true);
                    ed.apply();
                    //Switch to main activity
                    Intent main = new Intent(LoginActivity.this,MainActivity.class);
                    //To start the intent
                    startActivity(main);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Please fill the correct details.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //to click on create account and redirect to register activity.
        tv_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switch to register activity
                Intent register = new Intent(LoginActivity.this,RegisterActivity.class);
                //To start the intent
                startActivity(register);
                finish();
            }
        });

        tv_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switch to main activity
                Intent forgot = new Intent(LoginActivity.this,ForgotActivity.class);
                //To start the intent
                startActivity(forgot);
                finish();
            }
        });

       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }
}