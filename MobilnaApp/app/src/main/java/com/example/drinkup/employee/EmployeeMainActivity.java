package com.example.drinkup.employee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.drinkup.R;
import com.example.drinkup.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class EmployeeMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private Integer currentUserId;

    private Integer currentRoleId;

    private Integer workingBarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_main_activity);
        DrawerLayout drawer = findViewById(R.id.employee_drawer_layout);
        NavigationView navigationView = findViewById(R.id.employee_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_offer_list,
                R.id.nav_offer_creation,
                R.id.nav_token_scanning
        ).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.employee_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        loadState(savedInstanceState);

        findViewById(R.id.nav_user_logout).setOnClickListener(item -> {
            SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor Ed=sp.edit();
            Ed.putString("Unm", null);
            Ed.putString("Psw", null);
            Ed.commit();

            Intent login = new Intent(EmployeeMainActivity.this, LoginActivity.class);
            startActivity(login);
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.employee_nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private void loadState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                currentUserId = extras.getInt("userId");
                currentRoleId = extras.getInt("roleId");
                workingBarId = extras.getInt("barId");
            }
        }
        else {
            currentUserId = savedInstanceState.getInt("userId");
            currentRoleId = savedInstanceState.getInt("roleId");
            workingBarId = savedInstanceState.getInt("barId");
        }
    }

    public Integer getCurrentUserId() {
        return currentUserId;
    }

    public Integer getCurrentRoleId() {
        return currentRoleId;
    }

    public Integer getWorkingBarId() {
        return workingBarId;
    }

    public void customizeActionBar(String actionBarTitle)
    {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.navigation_icon);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatTextView) findViewById(R.id.actionBarTitle)).setText(actionBarTitle);
    }
}