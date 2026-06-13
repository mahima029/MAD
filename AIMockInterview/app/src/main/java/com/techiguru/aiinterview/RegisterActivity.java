package com.techiguru.aiinterview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference("users");

        /*EdgeToEdge.enable(this);*/
        setContentView(R.layout.activity_register);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        TextView txtLogin =
                findViewById(R.id.txtLogin);

        txtLogin.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            RegisterActivity.this,
                            LoginActivity.class
                    )
            );

            finish();
        });

        auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if(name.isEmpty()){
                etName.setError("Enter Name");
                return;
            }

            if(email.isEmpty()){
                etEmail.setError("Enter Email");
                return;
            }

            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.setError("Enter valid email");
                return;
            }

            if(password.isEmpty()){
                etPassword.setError("Enter Password");
                return;
            }

            if(password.length() < 6){
                etPassword.setError("Password must be at least 6 characters");
                return;
            }
            if(confirmPassword.isEmpty()){
                etConfirmPassword.setError("Confirm your password");
                return;
            }
            if(!password.equals(confirmPassword)){
                etConfirmPassword.setError("Passwords do not match");
                return;
            }

            registerUser(email, password);
        });

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }
    private void registerUser(String email, String password){

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){

                        String uid = auth.getCurrentUser().getUid();

                        String name =
                                etName.getText().toString().trim();

                        String useremail =
                                etEmail.getText().toString().trim();

                        user user =
                                new user(name, useremail);

                        databaseReference
                                .child(uid)
                                .setValue(user)
                                .addOnSuccessListener(unused -> {

                                    Toast.makeText(
                                            RegisterActivity.this,
                                            "Registration Successful",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    Intent intent =
                                            new Intent(RegisterActivity.this,
                                                    HomeActivity.class);

                                    startActivity(intent);
                                    finish();

                                });

                    }else{

                        Toast.makeText(
                                RegisterActivity.this,
                                task.getException().getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}