package com.eguide.yash1300.e_guide.fragments.student;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eguide.yash1300.e_guide.R;
import com.eguide.yash1300.e_guide.adapters.StudentAllTeachersAdapter;
import com.eguide.yash1300.e_guide.listeners.student.StudentFetchAllTeachersListener;
import com.eguide.yash1300.e_guide.models.SkillModel;
import com.eguide.yash1300.e_guide.models.TeacherModel;
import com.eguide.yash1300.e_guide.utils.NetworkManager;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class StudentHomeAllTeachersFragment extends Fragment {

    EditText searchText;
    RecyclerView teacherRecyclerView;
    LinearLayout noTeachersFound;

    Context context;
    List<TeacherModel> favTeachersList;
    String token;

    public StudentHomeAllTeachersFragment(Context context, List<TeacherModel> favTeachersList, String token) {
        this.context = context;
        this.favTeachersList = favTeachersList;
        this.token = token;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_student_all_teachers, container, false);
        searchText = rootView.findViewById(R.id.search_bar_skill);
        noTeachersFound = rootView.findViewById(R.id.no_teachers_found);
        teacherRecyclerView = rootView.findViewById(R.id.teachers_recycler_view);
        teacherRecyclerView.setLayoutManager(new LinearLayoutManager(context));


        //TODO: Remove the commented part below
/*
        if (favTeacherFragment) {

            if (favTeachersList == null || favTeachersList.size() == 0) {
                noTeachersFound.setVisibility(View.VISIBLE);
                teacherRecyclerView.setVisibility(View.GONE);
            } else {

                noTeachersFound.setVisibility(View.GONE);
                teacherRecyclerView.setVisibility(View.VISIBLE);

                StudentAllTeachersAdapter studentAllTeachersAdapter = new StudentAllTeachersAdapter(favTeachersList, context);
                teacherRecyclerView.setAdapter(studentAllTeachersAdapter);

                searchText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().equals(null) || s.toString().equals("")) {
                            List<TeacherModel> slicedTeachers = new ArrayList<>();
                            for (TeacherModel teacher : favTeachersList) {
                                for (SkillModel skill : teacher.getSkills()) {
                                    if (skill.getName().contains(s))
                                        slicedTeachers.add(teacher);
                                }
                            }
                            if (slicedTeachers == null || slicedTeachers.size() == 0) {
                                noTeachersFound.setVisibility(View.VISIBLE);
                                teacherRecyclerView.setVisibility(View.GONE);
                                return;
                            }

                            noTeachersFound.setVisibility(View.GONE);
                            teacherRecyclerView.setVisibility(View.VISIBLE);
                            teacherRecyclerView.setAdapter(new StudentAllTeachersAdapter(slicedTeachers, context));
                        } else {
                            noTeachersFound.setVisibility(View.GONE);
                            teacherRecyclerView.setVisibility(View.VISIBLE);
                            teacherRecyclerView.setAdapter(new StudentAllTeachersAdapter(favTeachersList, context));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }


        } else {

        }*/


        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading the teachers...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkManager.getInstance().studentFetchAllTeachers(token, new StudentFetchAllTeachersListener() {
            @Override
            public void onSuccess(String message, final List<TeacherModel> teachers) {

                progressDialog.dismiss();

                if (teachers == null || teachers.size() == 0) {
                    noTeachersFound.setVisibility(View.VISIBLE);
                    teacherRecyclerView.setVisibility(View.GONE);
                    return;
                }


                noTeachersFound.setVisibility(View.GONE);
                teacherRecyclerView.setVisibility(View.VISIBLE);
                StudentAllTeachersAdapter studentAllTeachersAdapter = new StudentAllTeachersAdapter(teachers, context, token);
                teacherRecyclerView.setAdapter(studentAllTeachersAdapter);

                searchText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().equals(null) || s.toString().equals("")) {
                            List<TeacherModel> slicedTeachers = new ArrayList<>();
                            for (TeacherModel teacher : teachers) {
                                for (SkillModel skill : teacher.getSkills()) {
                                    if (skill.getName().contains(s))
                                        slicedTeachers.add(teacher);
                                }
                            }
                            if (slicedTeachers == null || slicedTeachers.size() == 0) {
                                noTeachersFound.setVisibility(View.VISIBLE);
                                teacherRecyclerView.setVisibility(View.GONE);
                                return;
                            }
                            noTeachersFound.setVisibility(View.GONE);
                            teacherRecyclerView.setVisibility(View.VISIBLE);
                            teacherRecyclerView.setAdapter(new StudentAllTeachersAdapter(slicedTeachers, context, token));
                        } else {
                            teacherRecyclerView.setAdapter(new StudentAllTeachersAdapter(teachers, context, token));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

            @Override
            public void onFailure(String message) {
                progressDialog.dismiss();
                noTeachersFound.setVisibility(View.VISIBLE);
                teacherRecyclerView.setVisibility(View.GONE);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });

        return rootView;

    }
}
