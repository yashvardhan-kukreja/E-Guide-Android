package com.eguide.yash1300.e_guide.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eguide.yash1300.e_guide.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("loginCache", Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", "");
        final String type = sharedPreferences.getString("type", "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (token.equals("") || token.equals(null))
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                else {
                    if (type.equals("student"))
                        startActivity(new Intent(SplashScreenActivity.this, StudentHomeActivity.class));
                    else
                        startActivity(new Intent(SplashScreenActivity.this, TeacherHomeActivity.class));
                }
            }
        }, 2800);

    }
}
