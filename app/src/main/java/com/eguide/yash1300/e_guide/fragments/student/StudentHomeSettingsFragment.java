package com.eguide.yash1300.e_guide.fragments.student;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eguide.yash1300.e_guide.R;
import com.eguide.yash1300.e_guide.activities.MainActivity;

@SuppressLint("ValidFragment")
public class StudentHomeSettingsFragment extends Fragment {

    Context context;

    LinearLayout logout;
    LinearLayout about;

    public StudentHomeSettingsFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_student_settings, container, false);

        logout = rootView.findViewById(R.id.student_setting_logout);
        about = rootView.findViewById(R.id.student_setting_about);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = context.getSharedPreferences("loginCache", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(context, MainActivity.class));

            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_about_moi, null, false);
                builder.setView(dialogView);
                AlertDialog aboutDialog = builder.create();
                aboutDialog.show();
            }
        });

        return rootView;

    }
}
