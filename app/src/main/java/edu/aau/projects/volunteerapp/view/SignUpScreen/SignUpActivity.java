package edu.aau.projects.volunteerapp.view.SignUpScreen;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.uiadapters.BankAccountSpAdapter;
import edu.aau.projects.volunteerapp.controller.uiadapters.SpAdapter;
import edu.aau.projects.volunteerapp.databinding.ActivitySignUpBinding;
import edu.aau.projects.volunteerapp.model.Admin;
import edu.aau.projects.volunteerapp.model.Donor;
import edu.aau.projects.volunteerapp.model.ServiceSeeker;
import edu.aau.projects.volunteerapp.model.User;
import edu.aau.projects.volunteerapp.model.Volunteer;
import edu.aau.projects.volunteerapp.utils.AppSharedPreferences;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.EntriesUtils;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class SignUpActivity extends BaseActivity {
    private static final String ADMIN_ID_EXTRA = "adIdExtra";
    ActivitySignUpBinding bin;
    CustomFirebaseApi api;
    AppSharedPreferences sharedPreferences;
    int adminId = -1;
    public static Intent makeIntent(Context context){
        return new Intent(context, SignUpActivity.class);
    }

    public static Intent makeIntent(Context context, int adminId){
        return new Intent(context, SignUpActivity.class)
                .putExtra(ADMIN_ID_EXTRA, adminId);
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

        Bundle bun = getIntent().getExtras();
        adminId = bun == null? -1 : bun.getInt(ADMIN_ID_EXTRA, -1);

        if (adminId != -1){
            List<String> roles = Arrays.asList(getResources().getStringArray(R.array.sign_sp_roles_entriesA));
            ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roles);
            bin.signSpRole.setAdapter(roleAdapter);
        }
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
            String role = EntriesUtils.getRoleList()[bin.signSpRole.getSelectedItemPosition()];
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

            if (!UiUtils.checkPassword(password)){
                UiUtils.makeToast(R.string.invalid_password, getBaseContext());
                return;
            }

            if (!UiUtils.checkEmail(email)){
                UiUtils.makeToast(R.string.invalid_email, getBaseContext());
                return;
            }

            User user = new User(name, email, "", password, role, "", joinedDate, "");

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
                    UiUtils.makeToast(R.string.op_done, getBaseContext());
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
        if (user.getRole().equals(EntriesUtils.getRoleList()[2])){
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

        } else if (user.getRole().equals(EntriesUtils.getRoleList()[1])){
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
        } else if (user.getRole().equals(EntriesUtils.getRoleList()[3])){
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

        else if (user.getRole().equals(EntriesUtils.getRoleList()[4])){
            api.insertAdmin(new Admin(user))
                    .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Log.d("onSuccess: ", dataSnapshot.getKey());
                            int adminId = 1;
                            if (dataSnapshot.getValue(Long.class) != null)
                                adminId = dataSnapshot.getValue(Long.class).intValue() + 1;
                            Log.d("onSuccess: ", "id : " + adminId);
                            sharedPreferences.saveDonorId(adminId);
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
                    api.updateProfileName(user.getRole());
                }
                else {
                    UiUtils.makeToast(task.getException().getClass().getSimpleName(), getBaseContext());
                }
            }
        });
    }

}