package com.eguide.yash1300.e_guide.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eguide.yash1300.e_guide.Adapters.TeacherFavStudentsAdapter;
import com.eguide.yash1300.e_guide.Models.TeacherModel;
import com.eguide.yash1300.e_guide.R;

@SuppressLint("ValidFragment")
public class TeacherHomeFavStudentsFragment extends Fragment {

    TeacherModel model;

    public TeacherHomeFavStudentsFragment(TeacherModel model) {
        this.model = model;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_teacher_fav_students, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.teacher_fav_students_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TeacherFavStudentsAdapter adapter = new TeacherFavStudentsAdapter(getContext(), model.getStudents());

        recyclerView.setAdapter(adapter);

        return v;
    }
}
