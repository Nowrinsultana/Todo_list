package com.example.todolist;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                boolean check = preferences.getBoolean("flag", false);
                Intent intent;

                if (check) {
                    intent = new Intent(Splash.this, todolist.class);

                } else {
                    intent = new Intent(Splash.this, login.class);  // Navigate to the login or main screen
                }

                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
