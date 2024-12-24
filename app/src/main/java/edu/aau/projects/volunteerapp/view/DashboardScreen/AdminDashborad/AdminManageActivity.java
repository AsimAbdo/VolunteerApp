package edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.uiadapters.TaskV2Adapter;
import edu.aau.projects.volunteerapp.controller.uiadapters.UsersAdapter;
import edu.aau.projects.volunteerapp.databinding.ActivityViewDetailsBinding;
import edu.aau.projects.volunteerapp.model.MTask;
import edu.aau.projects.volunteerapp.model.Volunteer;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class AdminManageActivity extends BaseActivity {
    ActivityViewDetailsBinding bin;
    private static final String DATA_TYPE_KEY = "data_type";
    private int dataType = -1;
    CustomFirebaseApi api;

    UsersAdapter adapter;
    TaskV2Adapter taskAdapter;
    List<Volunteer> volunteers;
    List<MTask> tasks;

    public static Intent makeIntent(Context context, int dataType){
        return new Intent(context, AdminManageActivity.class).putExtra(DATA_TYPE_KEY, dataType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityViewDetailsBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());
        setSupportActionBar(bin.viewToolbar);

        api = CustomFirebaseApi.getInstance();
        adapter = new UsersAdapter();
        taskAdapter = new TaskV2Adapter();

        bin.viewRvData.setLayoutManager(new GridLayoutManager(this, 1));
        bin.viewRvData.setHasFixedSize(true);


        Bundle bun = getIntent().getExtras();
        if (bun != null)
            dataType = bun.getInt(DATA_TYPE_KEY, -1);
        if (dataType == 1)
            getUsers();
        else if (dataType == 2)
            getTasks(getString(R.string.new_task));
    }

    private void getUsers(){
        bin.viewRvData.setAdapter(adapter);

        volunteers = new ArrayList<>();
        UiUtils.showProgressbar(this);
        api.getUsers().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UiUtils.dismissDialog();
                if (snapshot.exists()){
                    volunteers.clear();
                    for (DataSnapshot dataSnapshot :
                            snapshot.getChildren()) {
                        Volunteer volunteer = dataSnapshot.getValue(Volunteer.class);
                        volunteers.add(volunteer);
                    }
                    adapter.setVolunteers(volunteers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTasks(String status){
        bin.viewRvData.setAdapter(taskAdapter);

        tasks = new ArrayList<>();
        UiUtils.showProgressbar(this);
        api.getTasks(status).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UiUtils.dismissDialog();
                if (snapshot.exists()){
                    tasks.clear();
                    for (DataSnapshot dataSnapshot :
                            snapshot.getChildren()) {
                        MTask task = dataSnapshot.getValue(MTask.class);
                        tasks.add(task);
                    }
                    taskAdapter.setTasks(tasks);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    };
}