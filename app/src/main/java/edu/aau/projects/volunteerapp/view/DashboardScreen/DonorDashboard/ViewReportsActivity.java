package edu.aau.projects.volunteerapp.view.DashboardScreen.DonorDashboard;

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
import edu.aau.projects.volunteerapp.controller.uiadapters.ReportAdapter;
import edu.aau.projects.volunteerapp.databinding.ActivityViewReportsBinding;
import edu.aau.projects.volunteerapp.model.Report;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class ViewReportsActivity extends BaseActivity {
    ActivityViewReportsBinding bin;
    CustomFirebaseApi api;
    ReportAdapter adapter;
    List<Report> reports;

    public static Intent makeIntent(Context context){
        return new Intent(context, ViewReportsActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityViewReportsBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());
        setSupportActionBar(bin.repToolbar);

        api = CustomFirebaseApi.getInstance();
        reports = new ArrayList<>();
        adapter = new ReportAdapter(reports);

        bin.repRv.setAdapter(adapter);
        bin.repRv.setLayoutManager(new GridLayoutManager(this, 1));
        bin.repRv.setHasFixedSize(true);

        getReports();
    }

    private void getReports() {
        UiUtils.showProgressbar(this);
        api.getReports().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UiUtils.dismissDialog();
                if (snapshot.exists()){
                    reports.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Report report = child.getValue(Report.class);
                        reports.add(report);
                    }
                    adapter.setReports(reports);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}