package edu.aau.projects.volunteerapp.view.DashboardScreen.VolunteerDashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.uiadapters.SkillAdapter;
import edu.aau.projects.volunteerapp.databinding.ActivityManageSkillsBinding;
import edu.aau.projects.volunteerapp.model.Volunteer;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class ManageSkillsActivity extends BaseActivity {

    private static final String VOLUNTEER_EXTRA = "vol_id";
    private static final String SKILLS_EXTRA = "skills";
    private String skills;
    private int volunteerId;
    private SkillAdapter adapter;
    private List<String> skillsList;
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
        setSupportActionBar(bin.msToolbar);

        api = CustomFirebaseApi.getInstance();

        adapter = new SkillAdapter();

        bin.msRvSkills.setAdapter(adapter);
        bin.msRvSkills.setLayoutManager(new GridLayoutManager(this, 1));
        bin.msRvSkills.setHasFixedSize(true);

        bin.msBtnAddSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UiUtils.verifyFields(bin.msEtSkills)){
                    UiUtils.makeToast(R.string.empty_fields, getBaseContext());
                    return;
                }
                String skill = bin.msEtSkills.getText().toString();
                if (adapter.addSkill(skill)){
                    UiUtils.makeToast(R.string.currentSkills, getBaseContext());
                }
                UiUtils.makeToast(skillsList.toString(), getBaseContext());
            }
        });

        bin.msBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("skills", adapter.getSkills());
                UiUtils.makeToast("id : " + volunteerId + " \n" + data.toString(), getBaseContext());
                UiUtils.showProgressbar(ManageSkillsActivity.this);
                api.updateVolunteer(volunteerId, data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        UiUtils.dismissDialog();
                        if (task.isSuccessful()){
                            UiUtils.makeToast("R.string.op_done", getBaseContext());
                            finish();
                        }
                        else {
                            UiUtils.makeToast(task.getException().getMessage(), getBaseContext());
                        }
                    }
                });
            }
        });



        Bundle bun = getIntent().getExtras();
        if (bun != null){
            skills = bun.getString(SKILLS_EXTRA, "");
            volunteerId = bun.getInt(VOLUNTEER_EXTRA, -1);
        }
        skillsList = Volunteer.getFromFormatted(skills);
        adapter.setSkills(skillsList);
    }
}