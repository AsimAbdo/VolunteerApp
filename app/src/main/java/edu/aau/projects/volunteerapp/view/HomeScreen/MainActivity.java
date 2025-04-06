package edu.aau.projects.volunteerapp.view.HomeScreen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.ActivityMainBinding;
import edu.aau.projects.volunteerapp.model.Admin;
import edu.aau.projects.volunteerapp.model.User;
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