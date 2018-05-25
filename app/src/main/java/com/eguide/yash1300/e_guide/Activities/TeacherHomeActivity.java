package com.eguide.yash1300.e_guide.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.eguide.yash1300.e_guide.Fragments.TeacherHomeFavStudentsFragment;
import com.eguide.yash1300.e_guide.Fragments.TeacherHomeProfileFragment;
import com.eguide.yash1300.e_guide.Fragments.TeacherHomeStatsFragment;
import com.eguide.yash1300.e_guide.Listeners.TeacherFetchDetailsListener;
import com.eguide.yash1300.e_guide.Models.TeacherModel;
import com.eguide.yash1300.e_guide.Utils.NetworkManager;
import com.eguide.yash1300.e_guide.R;

@SuppressWarnings("deprecation")
public class TeacherHomeActivity extends AppCompatActivity {

    TeacherModel currentTeacher = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        SharedPreferences sharedPreferences = getSharedPreferences("loginCache", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

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
                loadFragment(new TeacherHomeFavStudentsFragment(currentTeacher));
            }

            @Override
            public void onFailure(String message) {
                progressDialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
            }
        });

        final AHBottomNavigation ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.teacher_nav_bar);

        AHBottomNavigationItem favStudents = new AHBottomNavigationItem("Students", R.drawable.ic_people_outline_black_24dp, R.color.white);
        AHBottomNavigationItem profile = new AHBottomNavigationItem("Profile", R.drawable.ic_person_outline_black_24dp, R.color.white);
        AHBottomNavigationItem someStats = new AHBottomNavigationItem("Some Stats", R.drawable.ic_timeline_black_24dp, R.color.white);

        ahBottomNavigation.addItem(favStudents);
        ahBottomNavigation.addItem(profile);
        ahBottomNavigation.addItem(someStats);

        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahBottomNavigation.setInactiveColor(Color.parseColor("#A4A4A4"));
        ahBottomNavigation.setAccentColor(Color.parseColor("#00CAFF"));

        if (currentTeacher != null)
            ahBottomNavigation.setCurrentItem(0);

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch(position) {
                    case 0:
                        Fragment favStudentsFragment = new TeacherHomeFavStudentsFragment(currentTeacher);
                        loadFragment(favStudentsFragment);
                        break;
                    case 1:
                        Fragment profileFrag = new TeacherHomeProfileFragment(currentTeacher);
                        loadFragment(profileFrag);
                        break;
                    case 2:
                        Fragment statsFragment = new TeacherHomeStatsFragment();
                        loadFragment(statsFragment);
                        break;
                    default:
                        break;
                }
                return true;
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
