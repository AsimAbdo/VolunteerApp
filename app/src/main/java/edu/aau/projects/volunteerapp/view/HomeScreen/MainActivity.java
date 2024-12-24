package edu.aau.projects.volunteerapp.view.HomeScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.ActivityMainBinding;
import edu.aau.projects.volunteerapp.utils.UiUtils;
import edu.aau.projects.volunteerapp.view.DashboardScreen.DashboardActivity;
import edu.aau.projects.volunteerapp.view.LoginScreen.LoginActivity;
import edu.aau.projects.volunteerapp.view.SignUpScreen.SignUpActivity;
import edu.aau.projects.volunteerapp.utils.BaseActivity;

public class MainActivity extends BaseActivity {
    ActivityMainBinding bin;
    CustomFirebaseApi api;

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());

        api = CustomFirebaseApi.getInstance();

        if (api.getCurrentUser() != null && api.getCurrentUser().isEmailVerified()) {
            startActivity(DashboardActivity.makeIntent(this));
            finish();
        }

        bin.mainBtnLogin.setOnClickListener(view -> {
            startActivity(LoginActivity.getIntent(getBaseContext()));
        });

        bin.mainBtnCreate.setOnClickListener(v -> {
            startActivity(SignUpActivity.getIntent(getBaseContext()));
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (api.getCurrentUser() != null && api.getCurrentUser().isEmailVerified())
            finish();
    }
}