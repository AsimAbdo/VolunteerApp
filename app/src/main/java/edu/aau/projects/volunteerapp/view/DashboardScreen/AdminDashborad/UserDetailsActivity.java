package edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.ActivityUserDetailsBinding;
import edu.aau.projects.volunteerapp.model.Volunteer;

public class UserDetailsActivity extends AppCompatActivity {

    private static final String USERS_EXTRA = "users";
    ActivityUserDetailsBinding bin;
    CustomFirebaseApi api;
    int volunteerId;
    Volunteer volunteer;

    public static Intent makeIntent(Context context, int userId) {
        return new Intent(context, UserDetailsActivity.class).putExtra(USERS_EXTRA, userId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());

        api = CustomFirebaseApi.getInstance();

        Bundle bun = getIntent().getExtras();
        volunteerId = bun == null ? -1 : bun.getInt(USERS_EXTRA, -1);
        api.getVolunteer(volunteerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot child : snapshot.getChildren()) {
                        volunteer = child.getValue(Volunteer.class);
                    }
                    putDataToFields(volunteer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void putDataToFields(Volunteer volunteer){
        bin.detailsTvUserAvailability.setText(
                bin.detailsTvUserAvailability.getText().toString()
                        .concat(volunteer.getAvailability())
        );

        bin.detailsTvUserCompletedTasks.setText(
                bin.detailsTvUserCompletedTasks.getText().toString()
                        .concat(String.valueOf(volunteer.getCompletedTasks()))
        );

        bin.detailsTvUserName.setText(volunteer.getUser().getName());
        bin.detailsTvUserBalance.setText(String.valueOf(volunteer.getCashFund().getBalance()));
        bin.detailsTvUserType.setText(volunteer.getUser().getRole());

        bin.detailsBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void onUpgradeButtonPressed(){
        api.upgradeToTeamLeader(volunteer);
    }
}