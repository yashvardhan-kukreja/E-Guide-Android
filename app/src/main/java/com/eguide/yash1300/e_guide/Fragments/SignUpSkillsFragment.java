package com.eguide.yash1300.e_guide.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eguide.yash1300.e_guide.Activities.SignUpActivity;
import com.eguide.yash1300.e_guide.R;

@SuppressLint("ValidFragment")
public class SignUpSkillsFragment extends Fragment {

    Context context;
    String name, username, email, password, contact;
    Button signup;
    RecyclerView recyclerView;

    @SuppressLint("ValidFragment")
    public SignUpSkillsFragment(Context context, String name, String username, String email, String password, String contact) {
        this.context = context;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.contact = contact;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup_skills, container, false);
        recyclerView = v.findViewById(R.id.skills_recycler_view);
        signup = v.findViewById(R.id.btn_signup);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return v;
    }
}
