package edu.aau.projects.volunteerapp.view.DashboardScreen.VolunteerDashboard;

import android.os.Bundle;

import java.util.List;

import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.uiadapters.TaskV2Adapter;
import edu.aau.projects.volunteerapp.databinding.ActivityCurrentTasksBinding;
import edu.aau.projects.volunteerapp.model.MTask;
import edu.aau.projects.volunteerapp.utils.BaseActivity;

public class CurrentTasksActivity extends BaseActivity {
    ActivityCurrentTasksBinding bin;
    CustomFirebaseApi api;
    List<MTask> tasks;
    TaskV2Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityCurrentTasksBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());

    }
}