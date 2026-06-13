package com.techiguru.fitmylife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        //Hold this frame for 2sec and switch to login page.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*mp = MediaPlayer.create(getApplicationContext(),
                        Uri.parse("https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3"));
                mp.start();*/

                /*//Switch to login activity
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                //To start the intent
                startActivity(intent);
                finish();*/
                SharedPreferences sp = getSharedPreferences("myPref", MODE_PRIVATE);

                if (sp.getBoolean("isLoggedIn", false)) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        },3000);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}