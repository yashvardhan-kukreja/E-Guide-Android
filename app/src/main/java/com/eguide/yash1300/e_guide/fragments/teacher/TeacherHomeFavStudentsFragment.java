package com.eguide.yash1300.e_guide.fragments.teacher;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eguide.yash1300.e_guide.adapters.TeacherFavStudentsAdapter;
import com.eguide.yash1300.e_guide.listeners.teacher.TeacherFetchFavoredStudentsListener;
import com.eguide.yash1300.e_guide.models.FavoriteModel;
import com.eguide.yash1300.e_guide.models.TeacherModel;
import com.eguide.yash1300.e_guide.R;
import com.eguide.yash1300.e_guide.utils.NetworkManager;

import java.util.List;

@SuppressLint("ValidFragment")
public class TeacherHomeFavStudentsFragment extends Fragment {


    Context context;
    String token;

    public TeacherHomeFavStudentsFragment(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_teacher_fav_students, container, false);
        final RecyclerView recyclerView = v.findViewById(R.id.teacher_fav_students_recycler);
        final LinearLayout noStudentsFavoredPlaceholder = v.findViewById(R.id.no_studs_favored);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching the students who favored you...");
        progressDialog.setCancelable(false);
        //progressDialog.show();

        noStudentsFavoredPlaceholder.setVisibility(View.GONE);

        NetworkManager.getInstance().fetchTeacherFavoredStudents(token, new TeacherFetchFavoredStudentsListener() {
            @Override
            public void onSuccess(String message, List<FavoriteModel> favoredStudents) {
                progressDialog.dismiss();
                if (favoredStudents.size() == 0) {
                    noStudentsFavoredPlaceholder.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    progressDialog.dismiss();
                    noStudentsFavoredPlaceholder.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    TeacherFavStudentsAdapter adapter = new TeacherFavStudentsAdapter(getContext(), favoredStudents);
                    recyclerView.setAdapter(adapter);
                }

                Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();
            }
        });

        return v;
    }
}
