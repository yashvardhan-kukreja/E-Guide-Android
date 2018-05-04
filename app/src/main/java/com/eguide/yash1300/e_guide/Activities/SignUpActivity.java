package com.eguide.yash1300.e_guide.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.eguide.yash1300.e_guide.Fragments.SignUpDetailsFragment;
import com.eguide.yash1300.e_guide.Fragments.SignUpSkillsFragment;
import com.eguide.yash1300.e_guide.Listeners.RegisterDetails2SkillsListener;
import com.eguide.yash1300.e_guide.R;

public class SignUpActivity extends AppCompatActivity implements RegisterDetails2SkillsListener{

    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        container = findViewById(R.id.container_signup_details);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_signup_details, new SignUpDetailsFragment(SignUpActivity.this, findViewById(android.R.id.content)));
        fragmentTransaction.addToBackStack("Hello");
        fragmentTransaction.commit();



    }

    @Override
    public void sendDetails(String name, String username, String email, String password, String contact) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_signup_details, new SignUpSkillsFragment(SignUpActivity.this, name, username, email, password, contact));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
