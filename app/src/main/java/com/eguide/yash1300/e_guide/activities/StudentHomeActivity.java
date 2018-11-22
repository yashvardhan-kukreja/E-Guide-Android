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
import com.eguide.yash1300.e_guide.R;
import com.eguide.yash1300.e_guide.fragments.student.StudentHomeFavTeacherFragment;
import com.eguide.yash1300.e_guide.fragments.student.StudentHomeSettingsFragment;
import com.eguide.yash1300.e_guide.fragments.student.StudentHomeAllTeachersFragment;
import com.eguide.yash1300.e_guide.listeners.student.StudentFetchFavoriteTeachersListener;
import com.eguide.yash1300.e_guide.models.FavoriteModel;
import com.eguide.yash1300.e_guide.models.TeacherModel;
import com.eguide.yash1300.e_guide.utils.NetworkManager;

import java.util.ArrayList;
import java.util.List;

public class StudentHomeActivity extends AppCompatActivity {

    RelativeLayout noConnection, yesConnection;
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
        setContentView(R.layout.activity_student_home);

        SharedPreferences sharedPreferences = getSharedPreferences("loginCache", Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", null);

        noConnection = findViewById(R.id.no_connection_student_home);
        yesConnection = findViewById(R.id.yes_network_student_home);
        reloadConnection = findViewById(R.id.reload_connection_student_home);

        noConnection.setVisibility(View.GONE);
        yesConnection.setVisibility(View.VISIBLE);

        final ProgressDialog progressDialog = new ProgressDialog(StudentHomeActivity.this);
        progressDialog.setMessage("Fetching details...");
        progressDialog.setTitle("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkManager.getInstance().studentFetchFavoriteTeachers(token, new StudentFetchFavoriteTeachersListener() {
            @Override
            public void onSuccess(String message, List<FavoriteModel> favTeachers) {

                noConnection.setVisibility(View.GONE);
                yesConnection.setVisibility(View.VISIBLE);

                progressDialog.dismiss();

                AHBottomNavigation ahBottomNavigation = findViewById(R.id.student_nav_bar);
                ahBottomNavigation.setVisibility(View.VISIBLE);

                AHBottomNavigationItem favorites = new AHBottomNavigationItem("Favorites", R.drawable.ic_people_outline_black_24dp, R.color.white);
                AHBottomNavigationItem teachers = new AHBottomNavigationItem("Teachers", R.drawable.ic_people_outline_black_24dp, R.color.white);
                //AHBottomNavigationItem profile = new AHBottomNavigationItem("Profile", R.drawable.ic_person_outline_black_24dp, R.color.white);
                AHBottomNavigationItem settings = new AHBottomNavigationItem("Settings", R.drawable.ic_settings_black_24dp, R.color.white);

                ahBottomNavigation.addItem(favorites);
                ahBottomNavigation.addItem(teachers);
                //ahBottomNavigation.addItem(profile);
                ahBottomNavigation.addItem(settings);

                ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
                ahBottomNavigation.setInactiveColor(Color.parseColor("#A4A4A4"));
                ahBottomNavigation.setAccentColor(Color.parseColor("#00CAFF"));

                ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
                    @Override
                    public boolean onTabSelected(int position, boolean wasSelected) {
                        switch (position) {
                            case 0:
                                Fragment fragment = new StudentHomeFavTeacherFragment(StudentHomeActivity.this, token);
                                loadFragment(fragment);
                                break;
                            case 1:
                                Fragment fragment1 = new StudentHomeAllTeachersFragment(StudentHomeActivity.this, token);
                                loadFragment(fragment1);
                                break;
                            case 2:
                                Fragment fragment2 = new StudentHomeSettingsFragment(StudentHomeActivity.this);
                                loadFragment(fragment2);
                                break;
                            default:
                                break;

                        }
                        return true;
                    }
                });
                ahBottomNavigation.setCurrentItem(1);
            }

            @Override
            public void onFailure(String message) {
                progressDialog.dismiss();
                noConnection.setVisibility(View.VISIBLE);
                yesConnection.setVisibility(View.GONE);
                reloadConnection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(StudentHomeActivity.this, StudentHomeActivity.class));
                    }
                });
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.student_main_container, fragment);
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.commit();

    }
}
