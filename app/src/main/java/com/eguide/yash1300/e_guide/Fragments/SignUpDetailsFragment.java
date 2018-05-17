package com.eguide.yash1300.e_guide.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.eguide.yash1300.e_guide.Activities.LoginActivity;
import com.eguide.yash1300.e_guide.Listeners.RegisterListener;
import com.eguide.yash1300.e_guide.NetworkManager;
import com.eguide.yash1300.e_guide.R;

@SuppressLint("ValidFragment")
public class SignUpDetailsFragment extends Fragment {

    EditText name, username, email, password, conpassword, contact;
    RadioGroup signupRadioGroup;
    RadioButton studRbtn, teacherRbtn;
    Button proceed;

    Boolean studReg = true;

    //Constructor vars
    Context context;
    View parentLayoutView;

    @SuppressLint("ValidFragment")
    public SignUpDetailsFragment(Context context, View parentLayoutView) {
        this.context = context;
        this.parentLayoutView = parentLayoutView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup_details, container, false);

        name = v.findViewById(R.id.signup_name);
        username = v.findViewById(R.id.signup_username);
        email = v.findViewById(R.id.signup_email);
        password = v.findViewById(R.id.signup_password);
        conpassword = v.findViewById(R.id.signup_conpassword);
        contact = v.findViewById(R.id.signup_contact);
        proceed = v.findViewById(R.id.btn_signup_proceed);

        signupRadioGroup = v.findViewById(R.id.signup_radio_group);

        studRbtn = v.findViewById(R.id.signup_stud_rbtn);
        teacherRbtn = v.findViewById(R.id.signup_teacher_rbtn);

        studRbtn.setChecked(true);

        signupRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.signup_stud_rbtn:
                        studReg = true;
                        break;
                    case R.id.signup_teacher_rbtn:
                        studReg = false;
                        break;
                    default:
                        break;
                }
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().toString().equals(conpassword.getText().toString())) {
                    Snackbar.make(parentLayoutView, "Password and Confirm password don't match", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (studReg) {
                    NetworkManager.getInstance().studentRegister(name.getText().toString(), username.getText().toString(), email.getText().toString(), password.getText().toString(), contact.getText().toString(), new RegisterListener() {
                        @Override
                        public void onSuccess(String message) {
                            Snackbar.make(parentLayoutView, message, Snackbar.LENGTH_LONG).show();
                            startActivity(new Intent(context, LoginActivity.class));
                        }

                        @Override
                        public void onFailure(String message) {
                            Snackbar.make(parentLayoutView, message, Snackbar.LENGTH_LONG).show();
                        }
                    });
                } else {
                    NetworkManager.getInstance().teacherRegister(name.getText().toString(), username.getText().toString(), email.getText().toString(), password.getText().toString(), contact.getText().toString(), new RegisterListener() {
                        @Override
                        public void onSuccess(String message) {
                            Snackbar.make(parentLayoutView, message, Snackbar.LENGTH_LONG).show();
                            startActivity(new Intent(context, LoginActivity.class));
                        }

                        @Override
                        public void onFailure(String message) {
                            Snackbar.make(parentLayoutView, message, Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        return v;
    }
}
