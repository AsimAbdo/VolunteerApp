package edu.aau.projects.volunteerapp.view.DashboardScreen.VolunteerDashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.uiadapters.TaskAdapter;
import edu.aau.projects.volunteerapp.controller.uiadapters.TaskV2Adapter;
import edu.aau.projects.volunteerapp.databinding.ActivityAvailableTasksBinding;
import edu.aau.projects.volunteerapp.model.MTask;
import edu.aau.projects.volunteerapp.model.Volunteer;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad.OnAcceptRejectClickListener;

public class AvailableTasksActivity extends BaseActivity {
    private static final String VOLUNTEER_ID_EXTRA = "vol_id";
    ActivityAvailableTasksBinding bin;
    CustomFirebaseApi api;
    List<MTask> tasks, filteredTasks;
    TaskV2Adapter adapter;
    int vol_id;

    public static Intent makeIntent(Context context, int vol_id){
        return new Intent(context, AvailableTasksActivity.class).putExtra(VOLUNTEER_ID_EXTRA, vol_id);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    Volunteer volunteer;

    private void init(){
        bin = ActivityAvailableTasksBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());
        setSupportActionBar(bin.atToolbar);

        Bundle bun = getIntent().getExtras();
        if (bun != null)
            vol_id = bun.getInt(VOLUNTEER_ID_EXTRA, -1);

        api = CustomFirebaseApi.getInstance();

        tasks = new ArrayList<>();
        filteredTasks = new ArrayList<>();

        adapter = new TaskV2Adapter(tasks, TaskV2Adapter.TASK_VIEW);

        bin.atRvTasks.setAdapter(adapter);
        bin.atRvTasks.setLayoutManager(new GridLayoutManager(this, 1));
        bin.atRvTasks.setHasFixedSize(true);

        bin.atSvArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getTasksByAddress(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter.setListener(new OnAcceptRejectClickListener() {
            @Override
            public void onCLick(MTask task, int accept) {
                if (accept == 1){
                    updateTaskStatus(task);
                }
            }
        });
    }

    private void updateTaskStatus(MTask task) {
        task.setStatus(getString(R.string.need_resources));
        task.setAssignedTo(getString(R.string.assignToMember));
        task.setAssignedToId(vol_id);

        api.updateTask(task);
    }


    @Override
    protected void onStart() {
        super.onStart();
        getTasks(getString(R.string.not_taken));
    }

    private void getTasksByAddress(String address) {
        filteredTasks.clear();
        for (MTask task : tasks)
            if (task.getDescription().getLocation().contains(address))
                filteredTasks.add(task);
        if (filteredTasks.size() == 0){

        }
        adapter.setTasks(filteredTasks);
    }

    private void getTasks(String status){
        api.getTasks(status).addListenerForSingleValueEvent(new ValueEventListener() {
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