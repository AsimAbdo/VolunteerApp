package edu.aau.projects.volunteerapp.view.DashboardScreen.VolunteerDashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.ActivityManageSkillsBinding;
import edu.aau.projects.volunteerapp.utils.BaseActivity;

public class ManageSkillsActivity extends BaseActivity {

    private static final String VOLUNTEER_EXTRA = "skills";
    private static final String SKILLS_EXTRA = "skills";
    private String skills;
    private int volunteerId;
    CustomFirebaseApi api;
    ActivityManageSkillsBinding bin;

    public static Intent makeIntent(Context context, int volunteerId, String skills){
        return new Intent(context, ManageSkillsActivity.class)
                .putExtra(SKILLS_EXTRA, skills)
                .putExtra(VOLUNTEER_EXTRA, volunteerId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityManageSkillsBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());

        api = CustomFirebaseApi.getInstance();

        Bundle bun = getIntent().getExtras();
        if (bun != null){
            skills = bun.getString(SKILLS_EXTRA, "");
            volunteerId = bun.getInt(VOLUNTEER_EXTRA, -1);
        }
        bin.msTvSkills.setText(skills);
    }
}