package edu.aau.projects.volunteerapp.view.LoginScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.ActivityLoginBinding;
import edu.aau.projects.volunteerapp.model.User;
import edu.aau.projects.volunteerapp.utils.AppSharedPreferences;
import edu.aau.projects.volunteerapp.utils.UiUtils;
import edu.aau.projects.volunteerapp.view.DashboardScreen.DashboardActivity;
import edu.aau.projects.volunteerapp.view.HomeScreen.MainActivity;
import edu.aau.projects.volunteerapp.view.SignUpScreen.SignUpActivity;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding bin;
    CustomFirebaseApi api;
    AppSharedPreferences sharedPreferences;

    public static Intent makeIntent(Context context){
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());
        setSupportActionBar(bin.loginToolbar);

        sharedPreferences = new AppSharedPreferences(this);
        api = CustomFirebaseApi.getInstance();



        bin.loginBtnSignUp.setOnClickListener(v -> {
            startActivity(SignUpActivity.makeIntent(getBaseContext()));
            finish();
        });

        bin.loginBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UiUtils.verifyFields(bin.loginEtEmailOrName, bin.loginEtPassword)){
                    UiUtils.makeToast(R.string.empty_fields, getBaseContext());
                    return;
                }

                String email = bin.loginEtEmailOrName.getText().toString();
                String password = bin.loginEtPassword.getText().toString();
                if (!UiUtils.checkEmail(email)){
                    UiUtils.makeToast(R.string.invalid_email, getBaseContext());
                    return;
                }
                UiUtils.showProgressbar(LoginActivity.this);
                api.validateUser(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        UiUtils.dismissDialog();
                        if (task.isSuccessful()){
                            if (api.getCurrentUser().isEmailVerified()) {
                                startActivity(DashboardActivity.makeIntent(getBaseContext()));
                                finish();
                            }
                            else
                                UiUtils.makeToast(R.string.un_activated_account, getBaseContext());
                        }
                        else {
                            Log.d("onComplete: ", task.getException().getClass().getSimpleName());
                            UiUtils.makeToast(api.handleFirebaseException(task), getBaseContext());
                        }
                    }
                });
            }
        });

        bin.loginTvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UiUtils.verifyFields(bin.loginEtEmailOrName)){
                    UiUtils.makeToast(R.string.empty_email, getBaseContext());
                    return;
                }
                if (!UiUtils.checkEmail(bin.loginEtEmailOrName.getText().toString())){
                    UiUtils.makeToast(R.string.invalid_email, getBaseContext());
                    return;
                }
                String email = bin.loginEtEmailOrName.getText().toString();
                UiUtils.showProgressbar(LoginActivity.this);
                api.checkEmail(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                            resetPassword(email);
                        else
                            UiUtils.makeToast(R.string.email_not_used, getBaseContext());
                        UiUtils.dismissDialog();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void resetPassword(String email){
        api.resetUserPassword(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    UiUtils.makeToast(R.string.rest_password_send_successfully, getBaseContext());
                }
                else {
                    UiUtils.makeToast(task.getException().getMessage(), getBaseContext());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.service_seeker_menu, menu);
        return true;
    }
}