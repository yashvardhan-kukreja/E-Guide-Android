package com.eguide.yash1300.e_guide.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.eguide.yash1300.e_guide.Listeners.LoginResultListener;
import com.eguide.yash1300.e_guide.NetworkManager;
import com.eguide.yash1300.e_guide.R;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    RadioGroup radioGroup;
    RadioButton studRbtn, teacherRbtn;
    Boolean studLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        radioGroup = findViewById(R.id.signin_radio_group);
        studRbtn = findViewById(R.id.signin_stud_rbtn);
        teacherRbtn = findViewById(R.id.signin_teacher_rbtn);

        login = findViewById(R.id.btn_signin);

        studRbtn.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.signin_stud_rbtn:
                        studLogin = true;
                        break;
                    case R.id.signin_teacher_rbtn:
                        studLogin = false;
                        break;
                    default:
                        break;
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studLogin) {
                    NetworkManager.getInstance().studentLogin(email.getText().toString(), password.getText().toString(), new LoginResultListener() {
                        @Override
                        public void onSuccess(String token) {
                            SharedPreferences preferences = getSharedPreferences("loginCache", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", token);
                            editor.putString("type", "student");
                            editor.apply();
                        }

                        @Override
                        public void onFailure(String message) {
                            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
                        }
                    });
                } else {
                    NetworkManager.getInstance().teacherLogin(email.getText().toString(), password.getText().toString(), new LoginResultListener() {
                        @Override
                        public void onSuccess(String token) {
                            SharedPreferences preferences = getSharedPreferences("loginCache", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", token);
                            editor.putString("type", "teacher");
                            editor.apply();
                        }

                        @Override
                        public void onFailure(String message) {
                            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
}
