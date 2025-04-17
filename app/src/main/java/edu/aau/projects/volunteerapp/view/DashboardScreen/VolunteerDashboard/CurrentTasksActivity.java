package edu.aau.projects.volunteerapp.view.DashboardScreen.VolunteerDashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import edu.aau.projects.volunteerapp.model.Report;
import edu.aau.projects.volunteerapp.model.Volunteer;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.CustomDialogFragment;
import edu.aau.projects.volunteerapp.utils.EntriesUtils;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class CurrentTasksActivity extends BaseActivity implements TaskV2Adapter.OnProvideButtonClickListener, CustomDialogFragment.OnDialogButtonPressedListener {
    private static final String VOLUNTEER_ID_EXTRA = "vol_id";
    private static final String VOLUNTEER_NAME_EXTRA = "vol_name";
    ActivityCurrentTasksBinding bin;
    CustomFirebaseApi api;
    List<MTask> tasks;
    MTask currentTask;
    TaskV2Adapter adapter;
    int volunteerId = -1;
    String volunteerName = "";

    public static Intent makeIntent(Context context, int volunteerId){
        return new Intent(context, CurrentTasksActivity.class)
                .putExtra(VOLUNTEER_ID_EXTRA, volunteerId);
    }

    public static Intent makeIntent(Context context, int volunteerId, String volunteerName){
        return new Intent(context, CurrentTasksActivity.class)
                .putExtra(VOLUNTEER_NAME_EXTRA, volunteerName)
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
        setSupportActionBar(bin.ctToolbar);

        Bundle bun = getIntent().getExtras();
        if (bun != null) {
            volunteerId = bun.getInt(VOLUNTEER_ID_EXTRA, -1);
            volunteerName = bun.getString(VOLUNTEER_NAME_EXTRA, "");
        }

        api = CustomFirebaseApi.getInstance();

        tasks = new ArrayList<>();
        adapter = new TaskV2Adapter(tasks, TaskV2Adapter.CURRENT_TASK_VIEW);
        adapter.setOnProvideButtonClickListener(this);

        bin.ctRvCurrentTasks.setAdapter(adapter);
        bin.ctRvCurrentTasks.setLayoutManager(new GridLayoutManager(this, 1));
        bin.ctRvCurrentTasks.setHasFixedSize(true);

        List<String> status = new ArrayList<>();
        status.add(EntriesUtils.getStatusList()[0]);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getCurrentTasks(EntriesUtils.getStatusList()[2]);
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

    @Override
    public void onButtonClick(MTask mTask) {
        UiUtils.showDialogFragment(getSupportFragmentManager(),
                getString(R.string.generateReportTitle),
                getString(R.string.generateReportMessage),
                getString(R.string.btn_save),
                true
                );
        currentTask = mTask;
    }

    private void finishTask(MTask mTask){
        mTask.setStatus(EntriesUtils.getStatusList()[3]);
        api.updateTask(mTask).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                UiUtils.dismissDialog();
                if (task.isSuccessful()){
                    api.getVolunteer(volunteerId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                Volunteer volunteer = new Volunteer();
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    volunteer = child.getValue(Volunteer.class);
                                }
                                volunteer.setCompletedTasks(volunteer.getCompletedTasks() + 1);
                                api.updateVolunteer(volunteerId, volunteer.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            UiUtils.makeToast(R.string.op_done, getBaseContext());
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    tasks.remove(mTask);
                    adapter.setTasks(tasks);
                }
            }
        });
    }

    @Override
    public void onDialogButtonPressedClick(String text) {
        if (text.equals("")){
            UiUtils.makeToast(R.string.empty_fields, getBaseContext());
            return;
        }
        Report report = new Report(text, volunteerName);
        UiUtils.showProgressbar(this);
        api.generateReport(report).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                UiUtils.dismissDialogFragment();
                if (task.isSuccessful()){
                    finishTask(currentTask);
                }
            }
        });
    }
}