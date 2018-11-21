package com.eguide.yash1300.e_guide.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.eguide.yash1300.e_guide.activities.LoginActivity;
import com.eguide.yash1300.e_guide.adapters.SignUpSkillsAdapter;
import com.eguide.yash1300.e_guide.listeners.RegisterListener;
import com.eguide.yash1300.e_guide.listeners.SignUpSkillUpdateListener;
import com.eguide.yash1300.e_guide.listeners.teacher.TeacherCheckListener;
import com.eguide.yash1300.e_guide.listeners.teacher.TeacherFetchAllSkillsListener;
import com.eguide.yash1300.e_guide.models.SkillModel;
import com.eguide.yash1300.e_guide.utils.NetworkManager;
import com.eguide.yash1300.e_guide.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressLint("ValidFragment")
public class SignUpDetailsFragment extends Fragment {

    EditText name, username, email, password, conpassword, contact;
    RadioGroup signupRadioGroup;
    RadioButton studRbtn, teacherRbtn;
    Button proceed;
    ProgressDialog progressDialog;
    Boolean studReg = true;

    List<String> includeSkills;
    List<String> excludeSkills;

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

        includeSkills = new ArrayList<>();
        excludeSkills = new ArrayList<>();


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

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registering you...");
        progressDialog.setTitle("Please Wait");
        progressDialog.setCancelable(false);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                if (!password.getText().toString().equals(conpassword.getText().toString())) {
                    progressDialog.dismiss();
                    Snackbar.make(parentLayoutView, "Password and Confirm password don't match", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (studReg) {
                    NetworkManager.getInstance().studentRegister(name.getText().toString(), username.getText().toString(), email.getText().toString(), password.getText().toString(), contact.getText().toString(), new RegisterListener() {
                        @Override
                        public void onSuccess(String message) {
                            progressDialog.dismiss();
                            Snackbar.make(parentLayoutView, message, Snackbar.LENGTH_LONG).show();
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(context, LoginActivity.class));
                        }

                        @Override
                        public void onFailure(String message) {
                            progressDialog.dismiss();
                            Snackbar.make(parentLayoutView, message, Snackbar.LENGTH_LONG).show();
                        }
                    });
                } else {

                    progressDialog.dismiss();
                    final ProgressDialog teacherCheckDialog;
                    teacherCheckDialog = new ProgressDialog(context);
                    teacherCheckDialog.setMessage("Verifying email/username...");
                    teacherCheckDialog.setTitle("Please Wait");
                    teacherCheckDialog.setCancelable(false);


                    NetworkManager.getInstance().checkTeacherExistense(email.getText().toString(), username.getText().toString(), new TeacherCheckListener() {
                        @Override
                        public void onSuccess() {
                            teacherCheckDialog.dismiss();


                            // Creating the skill confirm dialog
                            AlertDialog.Builder skillConfirmationDialogBuilder = new AlertDialog.Builder(context);
                            View skillsConfirmationView = LayoutInflater.from(context).inflate(R.layout.dialog_skill_confirmation, null, false);
                            Button yes = skillsConfirmationView.findViewById(R.id.skill_conf_yes);
                            Button no = skillsConfirmationView.findViewById(R.id.skill_conf_no);
                            skillConfirmationDialogBuilder.setView(skillsConfirmationView);
                            final AlertDialog skillConfirmationDialog = skillConfirmationDialogBuilder.create();
                            skillConfirmationDialog.setCancelable(false);
                            skillConfirmationDialog.show();

                            // Setting up confirmation button listeners
                            // No confirmation button listener
                            no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final ProgressDialog baseRegisterProgDialog = new ProgressDialog(context);
                                    baseRegisterProgDialog.setMessage("Registering you...");
                                    baseRegisterProgDialog.setTitle("Please wait");
                                    baseRegisterProgDialog.setCancelable(false);
                                    baseRegisterProgDialog.show();

                                    NetworkManager.getInstance().teacherRegister(name.getText().toString(), username.getText().toString(), email.getText().toString(), password.getText().toString(), contact.getText().toString(), null, new RegisterListener() {

                                        @Override
                                        public void onSuccess(String message) {
                                            baseRegisterProgDialog.dismiss();
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(context, LoginActivity.class));
                                        }

                                        @Override
                                        public void onFailure(String message) {
                                            baseRegisterProgDialog.dismiss();
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });

                            // Yes confirmation button listener
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    skillConfirmationDialog.dismiss();

                                    final AlertDialog.Builder skillDialogBuilder = new AlertDialog.Builder(context);
                                    final View skillsSelectionView = LayoutInflater.from(context).inflate(R.layout.dialog_skills, null, false);

                                    final RecyclerView skillsRecyclerView = skillsSelectionView.findViewById(R.id.skills_recycler_view);
                                    final Button signUpSkills = skillsSelectionView.findViewById(R.id.btn_update_skills);
                                    final Button later = skillsSelectionView.findViewById(R.id.btn_dialog2_later);

                                    skillsRecyclerView.setLayoutManager(new LinearLayoutManager(context));

                                    final ProgressDialog skillsLoadingProgDialog = new ProgressDialog(context);
                                    skillsLoadingProgDialog.setCancelable(false);
                                    skillsLoadingProgDialog.setTitle("Please Wait");
                                    skillsLoadingProgDialog.setMessage("Fetching the skills...");
                                    skillsLoadingProgDialog.show();

                                    NetworkManager.getInstance().teacherFetchAllSkills(new TeacherFetchAllSkillsListener() {
                                        @Override
                                        public void onSuccess(List<SkillModel> skillModels) {
                                            skillsLoadingProgDialog.dismiss();

                                            // Sorting the skills by their names
                                            Collections.sort(skillModels, new Comparator<SkillModel>() {
                                                @Override
                                                public int compare(SkillModel o1, SkillModel o2) {
                                                    return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                                                }
                                            });

                                            skillsRecyclerView.setAdapter(new SignUpSkillsAdapter(context, skillModels, new SignUpSkillUpdateListener() {
                                                @Override
                                                public void includeSkill(String skill_id) {
                                                    includeSkills.add(skill_id);
                                                }

                                                @Override
                                                public void excludeSkill(String skill_id) {
                                                    excludeSkills.add(skill_id);
                                                }
                                            }));


                                            skillDialogBuilder.setView(skillsSelectionView);
                                            final AlertDialog skillsSelectionDialog = skillDialogBuilder.create();
                                            skillsSelectionDialog.setCancelable(false);
                                            skillsSelectionDialog.show();

                                            skillsSelectionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                @Override
                                                public void onDismiss(DialogInterface dialog) {
                                                    includeSkills.clear();
                                                    excludeSkills.clear();
                                                    includeSkills = new ArrayList<>();
                                                    excludeSkills = new ArrayList<>();
                                                }
                                            });
                                            skillsSelectionDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                @Override
                                                public void onCancel(DialogInterface dialog) {
                                                    includeSkills.clear();
                                                    excludeSkills.clear();
                                                    includeSkills = new ArrayList<>();
                                                    excludeSkills = new ArrayList<>();
                                                }
                                            });

                                            // Setting up skill selection listeners
                                            later.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    final ProgressDialog baseRegisterProgDialog = new ProgressDialog(context);
                                                    baseRegisterProgDialog.setMessage("Registering you...");
                                                    baseRegisterProgDialog.setTitle("Please wait");
                                                    baseRegisterProgDialog.setCancelable(false);
                                                    baseRegisterProgDialog.show();

                                                    NetworkManager.getInstance().teacherRegister(name.getText().toString(), username.getText().toString(), email.getText().toString(), password.getText().toString(), contact.getText().toString(), null, new RegisterListener() {
                                                        @Override
                                                        public void onSuccess(String message) {
                                                            baseRegisterProgDialog.dismiss();
                                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                                            startActivity(new Intent(context, LoginActivity.class));
                                                        }

                                                        @Override
                                                        public void onFailure(String message) {
                                                            baseRegisterProgDialog.dismiss();
                                                            skillConfirmationDialog.dismiss();
                                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            });

                                            signUpSkills.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    final ProgressDialog baseRegisterProgDialog = new ProgressDialog(context);
                                                    baseRegisterProgDialog.setMessage("Registering you...");
                                                    baseRegisterProgDialog.setTitle("Please wait");
                                                    baseRegisterProgDialog.setCancelable(false);
                                                    baseRegisterProgDialog.show();

                                                    if (includeSkills.size() == 0){
                                                        baseRegisterProgDialog.dismiss();
                                                        Snackbar.make(skillsSelectionView, "Please select at least one of the skills", Snackbar.LENGTH_LONG).show();
                                                        return;
                                                    }

                                                    for (int i=0; i<excludeSkills.size(); i++) {
                                                        String currentSkillToBeExcluded = excludeSkills.get(i);
                                                        for (int j=0; j<includeSkills.size(); j++) {
                                                            String includeSkill = includeSkills.get(j);
                                                            if (includeSkill.equals(currentSkillToBeExcluded)) {
                                                                includeSkills.remove(j);
                                                                break;
                                                            }
                                                        }
                                                    }

                                                    if (includeSkills.size() == 0){
                                                        baseRegisterProgDialog.dismiss();
                                                        Snackbar.make(skillsSelectionView, "Please select at least one of the skills", Snackbar.LENGTH_LONG).show();
                                                        return;
                                                    }

                                                    String[] finalSkillsArr = includeSkills.toArray(new String[includeSkills.size()]);


                                                    NetworkManager.getInstance().teacherRegister(name.getText().toString(), username.getText().toString(), email.getText().toString(), password.getText().toString(), contact.getText().toString(), finalSkillsArr, new RegisterListener() {

                                                        @Override
                                                        public void onSuccess(String message) {
                                                            baseRegisterProgDialog.dismiss();
                                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                                            startActivity(new Intent(context, LoginActivity.class));
                                                        }

                                                        @Override
                                                        public void onFailure(String message) {
                                                            baseRegisterProgDialog.dismiss();
                                                            skillsSelectionDialog.dismiss();
                                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(String message) {
                                            skillsLoadingProgDialog.dismiss();
                                            skillConfirmationDialog.dismiss();
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                        }

                        @Override
                        public void onFailure(String message) {
                            teacherCheckDialog.dismiss();
                            Snackbar.make(parentLayoutView, message, Snackbar.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

        return v;
    }


}
