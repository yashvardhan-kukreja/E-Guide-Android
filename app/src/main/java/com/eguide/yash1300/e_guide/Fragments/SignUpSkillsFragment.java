package com.eguide.yash1300.e_guide.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.eguide.yash1300.e_guide.Activities.SignUpActivity;
import com.eguide.yash1300.e_guide.Adapters.SignUpSkillsAdapter;
import com.eguide.yash1300.e_guide.Listeners.RegisterListener;
import com.eguide.yash1300.e_guide.Listeners.SignUpSkillUpdateListener;
import com.eguide.yash1300.e_guide.Listeners.TeacherFetchAllSkillsListener;
import com.eguide.yash1300.e_guide.Models.SkillModel;
import com.eguide.yash1300.e_guide.R;
import com.eguide.yash1300.e_guide.Utils.NetworkManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressLint("ValidFragment")
public class SignUpSkillsFragment extends Fragment {

    Context context;
    Button signup;
    RecyclerView recyclerView;
    List<String> skillsTobeIncluded;
    List<String> skillsToBeExcluded;

    public SignUpSkillsFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_signup_skills, container, false);
        recyclerView = v.findViewById(R.id.skills_recycler_view);
        signup = v.findViewById(R.id.btn_update_skills);


        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        SharedPreferences sharedPreferences = context.getSharedPreferences("loginCache", Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", null);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Fetching the skills...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkManager.getInstance().teacherFetchAllSkills(token, new TeacherFetchAllSkillsListener() {
            @Override
            public void onSuccess(List<SkillModel> skills) {
                progressDialog.dismiss();
                SignUpSkillsAdapter adapter = new SignUpSkillsAdapter(context, skills);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {
                progressDialog.dismiss();
                signup.setVisibility(View.GONE);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });

        SignUpSkillUpdateListener skillUpdateListener = new SignUpSkillUpdateListener() {
            @Override
            public void includeSkill(String skill_id) {
                skillsTobeIncluded.add(skill_id);
            }

            @Override
            public void excludeSkill(String skill_id) {
                skillsToBeExcluded.add(skill_id);
            }
        };

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skillsTobeIncluded.size() > 0) {

                    StringBuilder finalSkillIdBuilder = new StringBuilder();

                    Set<String> skillSetTobeIncluded = new HashSet<String>();
                    Set<String> skillSetTobeExcluded = new HashSet<String>();

                    skillSetTobeIncluded.addAll(skillsTobeIncluded);
                    skillSetTobeExcluded.addAll(skillsToBeExcluded);

                    Set<String> finalSkillsTobeIncluded = new HashSet<String>(skillSetTobeIncluded);

                    finalSkillsTobeIncluded.removeAll(skillSetTobeExcluded);

                    for (String skill_id : finalSkillsTobeIncluded) {
                        finalSkillIdBuilder.append(skill_id);
                        finalSkillIdBuilder.append(" ");
                    }

                    String finalSkillIds = finalSkillIdBuilder.toString();

                    ProgressDialog progressDialog1 = new ProgressDialog(context);
                    progressDialog1.setMessage("Adding new skills...");
                    progressDialog1.setCancelable(false);
                    progressDialog1.show();

                    NetworkManager.getInstance().teacherAddSkills(token, finalSkillIds, new RegisterListener() {
                        @Override
                        public void onSuccess(String message) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String message) {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(context, "No skills chosen!", Toast.LENGTH_LONG).show();
                }

            }
        });
        return v;
    }
}
