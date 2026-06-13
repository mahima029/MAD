package com.techiguru.aiinterview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private FirebaseAuth auth;
    private TextView txtForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*EdgeToEdge.enable(this);*/
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        CheckBox checkRemember =
                findViewById(R.id.checkRemember);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {

            Intent intent = new Intent(
                    LoginActivity.this,
                    HomeActivity.class
            );

            startActivity(intent);
            finish();
            return;
        }
        TextView txtRegister = findViewById(R.id.txtRegister);

        txtRegister.setOnClickListener(v -> {
            startActivity(
                    new Intent(LoginActivity.this,
                            RegisterActivity.class)
            );
        });

        btnLogin.setOnClickListener(v -> {

            String email =
                    etEmail.getText().toString().trim();

            String password =
                    etPassword.getText().toString().trim();

            if(email.isEmpty()){
                etEmail.setError("Enter Email");
                return;
            }

            if(password.isEmpty()){
                etPassword.setError("Enter Password");
                return;
            }

            loginUser(email,password);
        });

        txtForgotPassword.setOnClickListener(v -> {

            String email =
                    etEmail.getText()
                            .toString()
                            .trim();

            if(email.isEmpty()){

                etEmail.setError(
                        "Enter your email first"
                );

                return;
            }

            auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(unused -> {

                        Toast.makeText(
                                LoginActivity.this,
                                "Password reset link sent to email",
                                Toast.LENGTH_LONG
                        ).show();

                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                LoginActivity.this,
                                e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    });

        });
    }
    private void loginUser(String email, String password){

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){

                        Toast.makeText(
                                LoginActivity.this,
                                "Login Successful",
                                Toast.LENGTH_SHORT
                        ).show();

                        startActivity(
                                new Intent(
                                        LoginActivity.this,
                                        HomeActivity.class
                                )
                        );

                        finish();

                    }else{

                        Toast.makeText(
                                LoginActivity.this,
                                task.getException().getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}