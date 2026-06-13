package com.techiguru.aiinterview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO );

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {

            Intent intent =
                    new Intent(SplashActivity.this,
                            LoginActivity.class);

            startActivity(intent);

            finish();

        }, 2500);

    }
}