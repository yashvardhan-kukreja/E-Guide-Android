package com.eguide.yash1300.e_guide.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eguide.yash1300.e_guide.R;

public class MainActivity extends AppCompatActivity {

    Button signin, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("loginCache", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("token") && sharedPreferences.getString("type", "").equals("student"))
            startActivity(new Intent(MainActivity.this, StudentHomeActivity.class));
        if (sharedPreferences.contains("token") && sharedPreferences.getString("type", "").equals("teacher"))
            startActivity(new Intent(MainActivity.this, TeacherHomeActivity.class));

        signin = findViewById(R.id.btn_main_signin);
        signup = findViewById(R.id.btn_main_signup);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

    }
}
