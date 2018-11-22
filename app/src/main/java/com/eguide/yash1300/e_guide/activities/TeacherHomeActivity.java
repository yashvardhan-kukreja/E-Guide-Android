package com.eguide.yash1300.e_guide.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.eguide.yash1300.e_guide.fragments.teacher.TeacherHomeFavStudentsFragment;
import com.eguide.yash1300.e_guide.fragments.teacher.TeacherHomeProfileFragment;
import com.eguide.yash1300.e_guide.fragments.teacher.TeacherHomeSettingsFragment;
import com.eguide.yash1300.e_guide.listeners.teacher.TeacherFetchDetailsListener;
import com.eguide.yash1300.e_guide.models.TeacherModel;
import com.eguide.yash1300.e_guide.utils.NetworkManager;
import com.eguide.yash1300.e_guide.R;

@SuppressWarnings("deprecation")
public class TeacherHomeActivity extends AppCompatActivity {

    TeacherModel currentTeacher = null;
    RelativeLayout onError;
    RelativeLayout noError;
    LinearLayout reloadConnection;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        onError = findViewById(R.id.on_error);
        noError = findViewById(R.id.no_error);
        reloadConnection = findViewById(R.id.reload_connection);


        SharedPreferences sharedPreferences = getSharedPreferences("loginCache", Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", "");


        final AHBottomNavigation ahBottomNavigation = findViewById(R.id.teacher_nav_bar);

        AHBottomNavigationItem favStudents = new AHBottomNavigationItem("Students", R.drawable.ic_people_outline_black_24dp, R.color.white);
        AHBottomNavigationItem profile = new AHBottomNavigationItem("Profile", R.drawable.ic_person_outline_black_24dp, R.color.white);
        AHBottomNavigationItem someStats = new AHBottomNavigationItem("Settings", R.drawable.ic_settings_black_24dp, R.color.white);

        ahBottomNavigation.addItem(favStudents);
        ahBottomNavigation.addItem(profile);
        ahBottomNavigation.addItem(someStats);

        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahBottomNavigation.setInactiveColor(Color.parseColor("#A4A4A4"));
        ahBottomNavigation.setAccentColor(Color.parseColor("#00CAFF"));


        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch(position) {
                    case 0:
                        Fragment favStudentsFragment = new TeacherHomeFavStudentsFragment(TeacherHomeActivity.this, token);
                        loadFragment(favStudentsFragment);
                        break;
                    case 1:
                        Fragment profileFrag = new TeacherHomeProfileFragment(currentTeacher);
                        loadFragment(profileFrag);
                        break;
                    case 2:
                        Fragment settingsFragment = new TeacherHomeSettingsFragment(TeacherHomeActivity.this);
                        loadFragment(settingsFragment);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(TeacherHomeActivity.this);
        progressDialog.setMessage("Fetching Details...");
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        NetworkManager.getInstance().teacherFetchDetails(token, new TeacherFetchDetailsListener() {
            @Override
            public void onSuccess(String message, TeacherModel teacher) {
                progressDialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
                currentTeacher = teacher;

                noError.setVisibility(View.VISIBLE);
                onError.setVisibility(View.GONE);

                if (currentTeacher != null) {
                    ahBottomNavigation.setCurrentItem(1);
                }
            }

            @Override
            public void onFailure(String message) {
                progressDialog.dismiss();
                noError.setVisibility(View.GONE);
                onError.setVisibility(View.VISIBLE);

                reloadConnection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TeacherHomeActivity.this, TeacherHomeActivity.class));
                    }
                });

                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
            }
        });

    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.teacher_main_container, fragment);
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
