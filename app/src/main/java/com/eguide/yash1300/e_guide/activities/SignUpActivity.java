package com.eguide.yash1300.e_guide.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.eguide.yash1300.e_guide.fragments.SignUpDetailsFragment;
import com.eguide.yash1300.e_guide.R;

public class SignUpActivity extends AppCompatActivity{

    FrameLayout container;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        container = findViewById(R.id.container_signup_details);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_signup_details, new SignUpDetailsFragment(SignUpActivity.this, findViewById(android.R.id.content)));
        fragmentTransaction.commit();
    }
}
