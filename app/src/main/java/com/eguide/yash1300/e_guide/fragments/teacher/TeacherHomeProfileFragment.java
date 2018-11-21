package com.eguide.yash1300.e_guide.fragments.teacher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.eguide.yash1300.e_guide.models.TeacherModel;
import com.eguide.yash1300.e_guide.R;

@SuppressLint("ValidFragment")
public class TeacherHomeProfileFragment extends Fragment{

    TextView name, email, username, contact, noSkills;
    ListView skillsList;
    TeacherModel teacher;
    FloatingActionButton addSkillsFAB;

    public TeacherHomeProfileFragment(TeacherModel teacher) {
        this.teacher = teacher;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_teacher_profile, container, false);

        name = v.findViewById(R.id.teacher_profile_name);
        username = v.findViewById(R.id.teacher_profile_username);
        email = v.findViewById(R.id.teacher_profile_email);
        contact = v.findViewById(R.id.teacher_profile_contact);
        noSkills = v.findViewById(R.id.teacher_profile_no_skills_yet);
        skillsList = v.findViewById(R.id.teacher_profile_skills);
        addSkillsFAB = v.findViewById(R.id.teacher_profile_add_skills_fab);

        name.setText("Name: " + teacher.getName());
        username.setText("Username: " + teacher.getUsername());
        email.setText("E-mail: " + teacher.getEmail());
        contact.setText("Contact: " + teacher.getContact());

        if (teacher.getSkills().size() > 0) {
            noSkills.setVisibility(View.GONE);
            skillsList.setVisibility(View.VISIBLE);
            String[] skillsArray = new String[teacher.getSkills().size()];
            for (int i=0; i<skillsArray.length; i++) {
                skillsArray[i] = teacher.getSkills().get(i).getName();
            }

            skillsList.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, skillsArray));

        } else {
            noSkills.setVisibility(View.VISIBLE);
            skillsList.setVisibility(View.GONE);
        }
        return v;
    }
}
