package com.techiguru.captain;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
ImageView iv_speak;
MediaPlayer mp;
private static final int PROCESS_ID = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        iv_speak = findViewById(R.id.iv_speak);
        //to click on speak icon
        iv_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to recognize the text
                Intent voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //to get all language
                voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                //to show the message for speak
                voice.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Now");
                //pass the intent to os
                startActivityForResult(voice, PROCESS_ID);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void openApp(String packageName, String appName) {

        Intent launchIntent =
                getPackageManager().getLaunchIntentForPackage(packageName);

        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {

            Toast.makeText(this,
                    appName + " is not installed. Opening Play Store...",
                    Toast.LENGTH_LONG).show();

            Intent storeIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + packageName));

            startActivity(storeIntent);
        }
    }

    //to get the os output voice to text format

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PROCESS_ID && data != null)
        {
            //to get text from intent
            ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Toast.makeText(this, list.get(0).toString(), Toast.LENGTH_SHORT).show();

            String cmd = list.get(0).toString().toLowerCase();

            if(cmd.startsWith("search ")) {

                String query = cmd.substring(7);

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/search?q=" + query));

                startActivity(intent);
                return;
            }

            //to pass the no of operation based on statement
            switch (cmd)
            {
                case "open camera" :
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(camera);
                    break;

                case "play song":
                    mp = MediaPlayer.create(this,
                            Uri.parse("https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3"));
                    mp.start();
                    break;

                case "stop song":
                    if(mp != null && mp.isPlaying()) {
                        mp.stop();
                        mp.release();
                        mp = null;
                    }
                    break;

                case "open whatsapp":
                    openApp("com.whatsapp", "WhatsApp");
                    break;

                case "open spotify":
                    openApp("com.spotify.music", "Spotify");
                    break;

                case "open youtube":
                    openApp("com.google.android.youtube", "YouTube");
                    break;

                case "open instagram":
                    openApp("com.instagram.android", "Instagram");
                    break;

                case "open Chrome":
                    openApp("com.android.chrome", "Chrome");
                    break;

                case "open calculator":
                    Intent calculator = new Intent();
                    calculator.setAction(Intent.ACTION_MAIN);
                    calculator.addCategory(Intent.CATEGORY_APP_CALCULATOR);
                    startActivity(calculator);
                    break;

                case "back":
                    finish();
                    break;

                default:
                    Intent share = new Intent(Intent.ACTION_SEND);
                    //to attach the message with intent
                    share.putExtra(Intent.EXTRA_TEXT, list.get(0).toString());
                    //to define the data type
                    share.setType("text/plain");
                    startActivity(Intent.createChooser(share, "Share via"));
            }
        }
    }
}