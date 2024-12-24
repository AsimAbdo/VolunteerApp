package edu.aau.projects.volunteerapp.view.SignUpScreen;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.ActivitySignUpBinding;
import edu.aau.projects.volunteerapp.model.Admin;
import edu.aau.projects.volunteerapp.model.Donor;
import edu.aau.projects.volunteerapp.model.ServiceSeeker;
import edu.aau.projects.volunteerapp.model.User;
import edu.aau.projects.volunteerapp.model.Volunteer;
import edu.aau.projects.volunteerapp.utils.AppSharedPreferences;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class SignUpActivity extends BaseActivity {
    ActivitySignUpBinding bin;
    CustomFirebaseApi api;
    AppSharedPreferences sharedPreferences;

    public static Intent getIntent(Context context){
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());
        setSupportActionBar(bin.signToolbar);

        api = CustomFirebaseApi.getInstance();
        sharedPreferences = new AppSharedPreferences(this);

        bin.loginBtnSignUp.setOnClickListener(v -> {
//            if (!checkInternet()){
//                UiUtils.makeToast(R.string.not_connected, getBaseContext());
//                return;
//            }

            if (UiUtils.verifyFields(
                    bin.signEtName,
                    bin.signEtEmail,
                    bin.signEtPassword,
                    bin.signEtPasswordConfirm) && bin.signSpRole.getSelectedItemPosition() == 0){
                UiUtils.makeToast(R.string.empty_fields, getBaseContext());
                return;
            }
            String name = bin.signEtName.getText().toString();
            String email = bin.signEtEmail.getText().toString();
            String role = bin.signSpRole.getSelectedItem().toString();
            String password = bin.signEtPassword.getText().toString();
            String passwordConfirm = bin.signEtPassword.getText().toString();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String joinedDate = sdf.format(c.getTime());

            if (!password.equals(passwordConfirm)){
                UiUtils.makeToast(R.string.password_doesnt_match, getBaseContext());
                return;
            }

            if (!UiUtils.checkName(name)){
                UiUtils.makeToast(R.string.invalid_name, getBaseContext());
                return;
            }

            if (!UiUtils.checkEmail(email)){
                UiUtils.makeToast(R.string.invalid_email, getBaseContext());
                return;
            }

            User user = new User(name, email, "", password, role, "", joinedDate);

            api.createUser(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        completeInfoUserInfo(user);
                    }
                    else {
                        UiUtils.makeToast(api.handleFirebaseException(task), getBaseContext());
                    }
                }
            });
        });
    }

    private void insertUser(User user){
        UiUtils.showProgressbar(this);
        api.insertUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    insertByRole(user);
                    UiUtils.makeToast("done", getBaseContext());
                    sharedPreferences.saveUserId(user);
                    UiUtils.dismissDialog();
                    finish();
                }
                else {
                    UiUtils.makeToast(task.getException().getMessage(), getBaseContext());
                }
            }
        });
    }

    private void insertByRole(User user){
        if (user.getRole().equals(getString(R.string.volunteer))){
            api.insertVolunteer(new Volunteer(user))
                    .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Log.d("onSuccess: ", dataSnapshot.getKey());
                            int volunteerId = 1;
                            if (dataSnapshot.getValue(Long.class) != null)
                                volunteerId = dataSnapshot.getValue(Long.class).intValue() + 1;
                            Log.d("onSuccess: ", "id : " + volunteerId);
                            sharedPreferences.saveVolunteerId(volunteerId);
                        }
                    });

        } else if (user.getRole().equals(getString(R.string.service_seeker))){
            api.insertServiceSeeker(new ServiceSeeker(user))
                    .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Log.d("onSuccess: ", dataSnapshot.getKey());
                            int seekerId = 1;
                            if (dataSnapshot.getValue(Long.class) != null)
                                seekerId = dataSnapshot.getValue(Long.class).intValue() + 1;
                            Log.d("onSuccess: ", "id : " + seekerId);
                            sharedPreferences.saveServiceSeekerId(seekerId);
                        }
                    });
        } else if (user.getRole().equals(getString(R.string.donor))){
            api.insertDonor(new Donor(user))
                    .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Log.d("onSuccess: ", dataSnapshot.getKey());
                            int donorId = 1;
                            if (dataSnapshot.getValue(Long.class) != null)
                                donorId = dataSnapshot.getValue(Long.class).intValue() + 1;
                            Log.d("onSuccess: ", "id : " + donorId);
                            sharedPreferences.saveDonorId(donorId);
                        }
                    });
        }
    }

    private void completeInfoUserInfo(User user){
        api.sendActivationLink().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    UiUtils.makeToast(R.string.activate_account_label, getBaseContext());
                    insertUser(user);
                    UserProfileChangeRequest updateProfile = new UserProfileChangeRequest.Builder()
                            .setDisplayName(user.getRole()).build();
                    api.getCurrentUser().updateProfile(updateProfile);
                }
                else {
                    UiUtils.makeToast(task.getException().getClass().getSimpleName(), getBaseContext());
                }
            }
        });
    }

}