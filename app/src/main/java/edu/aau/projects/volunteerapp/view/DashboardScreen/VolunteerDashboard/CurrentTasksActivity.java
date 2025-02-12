package edu.aau.projects.volunteerapp.view.DashboardScreen.VolunteerDashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.uiadapters.TaskV2Adapter;
import edu.aau.projects.volunteerapp.databinding.ActivityCurrentTasksBinding;
import edu.aau.projects.volunteerapp.model.MTask;
import edu.aau.projects.volunteerapp.utils.BaseActivity;

public class CurrentTasksActivity extends BaseActivity {
    private static final String VOLUNTEER_ID_EXTRA = "vol_id";
    ActivityCurrentTasksBinding bin;
    CustomFirebaseApi api;
    List<MTask> tasks;
    TaskV2Adapter adapter;
    int volunteerId = -1;

    public static Intent makeIntent(Context context, int volunteerId){
        return new Intent(context, CurrentTasksActivity.class)
                .putExtra(VOLUNTEER_ID_EXTRA, volunteerId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityCurrentTasksBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());

        Bundle bun = getIntent().getExtras();
        if (bun != null)
            volunteerId = bun.getInt(VOLUNTEER_ID_EXTRA, -1);

        api = CustomFirebaseApi.getInstance();

        tasks = new ArrayList<>();
        adapter = new TaskV2Adapter(tasks, TaskV2Adapter.CURRENT_TASK_VIEW);
        bin.ctRvCurrentTasks.setAdapter(adapter);
        bin.ctRvCurrentTasks.setLayoutManager(new GridLayoutManager(this, 1));
        bin.ctRvCurrentTasks.setHasFixedSize(true);

        List<String> status = new ArrayList<>();
        status.add(getString(R.string.new_task));
    }

    @Override
    protected void onStart() {
        super.onStart();

        getCurrentTasks(getString(R.string.need_resources));
    }

    private void getCurrentTasks(String status){
        api.getCurrentTasks(volunteerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    tasks.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        MTask task = child.getValue(MTask.class);
                        if (task.getStatus().equals(status))
                            tasks.add(task);
                    }
                    adapter.setTasks(tasks);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}