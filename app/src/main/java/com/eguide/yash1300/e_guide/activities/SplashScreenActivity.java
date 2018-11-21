package com.eguide.yash1300.e_guide.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eguide.yash1300.e_guide.R;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView splashImg;
    TextView splashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashImg = findViewById(R.id.splash_img);
        splashText = findViewById(R.id.splash_text);

        SharedPreferences sharedPreferences = getSharedPreferences("loginCache", Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", "");
        final String type = sharedPreferences.getString("type", "");

        YoYo.with(Techniques.FadeIn).duration(2500).repeat(0).playOn(splashImg);
        YoYo.with(Techniques.FadeIn).duration(2500).repeat(0).playOn(splashText);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (token.equals("") || token.equals(null))
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                else {
                    if (type.equals("student"))
                        startActivity(new Intent(SplashScreenActivity.this, StudentHomeActivity.class));
                    else
                        startActivity(new Intent(SplashScreenActivity.this, TeacherHomeActivity.class));
                }
            }
        }, 3000);

    }
}
