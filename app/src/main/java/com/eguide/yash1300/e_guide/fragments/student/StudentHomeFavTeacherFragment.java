package com.eguide.yash1300.e_guide.fragments.student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

import com.eguide.yash1300.e_guide.R;
import com.eguide.yash1300.e_guide.adapters.StudentsFavoriteTeachersAdapter;
import com.eguide.yash1300.e_guide.listeners.student.StudentFetchFavoriteTeachersListener;
import com.eguide.yash1300.e_guide.models.FavoriteModel;
import com.eguide.yash1300.e_guide.utils.NetworkManager;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class StudentHomeFavTeacherFragment extends Fragment {


    EditText searchText;
    RecyclerView teacherRecyclerView;
    LinearLayout noTeachersFound;

    String token;
    Context context;

    public StudentHomeFavTeacherFragment(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_student_fav_teachers, container, false);

        searchText = rootView.findViewById(R.id.fav_search_bar_skill);
        teacherRecyclerView = rootView.findViewById(R.id.fav_teachers_recycler_view);
        teacherRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        noTeachersFound = rootView.findViewById(R.id.fav_no_teachers_found);

        NetworkManager.getInstance().studentFetchFavoriteTeachers(token, new StudentFetchFavoriteTeachersListener() {
            @Override
            public void onSuccess(String message, final List<FavoriteModel> favTeachers) {
                if (favTeachers == null || favTeachers.size() == 0) {
                    noTeachersFound.setVisibility(View.VISIBLE);
                    teacherRecyclerView.setVisibility(View.GONE);
                    return;
                }


                noTeachersFound.setVisibility(View.GONE);
                teacherRecyclerView.setVisibility(View.VISIBLE);
                StudentsFavoriteTeachersAdapter studentAllTeachersAdapter = new StudentsFavoriteTeachersAdapter(context, favTeachers, token);
                teacherRecyclerView.setAdapter(studentAllTeachersAdapter);

                searchText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<FavoriteModel> slicedTeachers = new ArrayList<>();
                        if (!(s.toString().equals(null) || s.toString().equals(""))) {
                            for (FavoriteModel teacher : favTeachers) {
                                if (teacher.getSkill().getName().toLowerCase().contains(s.toString().toLowerCase()))
                                    slicedTeachers.add(teacher);
                            }
                            if (slicedTeachers == null || slicedTeachers.size() == 0) {
                                noTeachersFound.setVisibility(View.VISIBLE);
                                teacherRecyclerView.setVisibility(View.GONE);
                                return;
                            }
                            noTeachersFound.setVisibility(View.GONE);
                            teacherRecyclerView.setVisibility(View.VISIBLE);
                            teacherRecyclerView.setAdapter(new StudentsFavoriteTeachersAdapter(context, slicedTeachers, token));
                        } else {
                            teacherRecyclerView.setAdapter(new StudentsFavoriteTeachersAdapter(context, favTeachers, token));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.toString().equals(null) || s.toString().equals(""))
                            teacherRecyclerView.setAdapter(new StudentsFavoriteTeachersAdapter(context, favTeachers, token));

                    }
                });
            }

            @Override
            public void onFailure(String message) {
                noTeachersFound.setVisibility(View.VISIBLE);
                teacherRecyclerView.setVisibility(View.GONE);
                Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
            }
        });

        return rootView;

    }
}
