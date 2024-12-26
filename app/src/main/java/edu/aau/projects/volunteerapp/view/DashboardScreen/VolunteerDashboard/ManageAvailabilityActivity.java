package edu.aau.projects.volunteerapp.view.DashboardScreen.VolunteerDashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.uiadapters.DayAdapter;
import edu.aau.projects.volunteerapp.databinding.ActivityManageAvailabilityBinding;
import edu.aau.projects.volunteerapp.model.CustomDay;
import edu.aau.projects.volunteerapp.model.DaysTitle;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class ManageAvailabilityActivity extends BaseActivity implements DaysTitle {

    private static final String VOLUNTEER_ID_EXTRA = "volunteer_id";
    private static final String AVAILABILITY_EXTRA = "availability";
    private int volunteerId = -1;
    private String availability = "";
    ActivityManageAvailabilityBinding bin;
    DayAdapter adapter;
    List<CustomDay> days;
    CustomFirebaseApi api;

    public static Intent makeIntent(Context context, int volunteerId, String availability){
        return new Intent(context, ManageAvailabilityActivity.class)
                .putExtra(AVAILABILITY_EXTRA, availability)
                .putExtra(VOLUNTEER_ID_EXTRA, volunteerId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityManageAvailabilityBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());
        setSupportActionBar(bin.mvToolbar);

        adapter = new DayAdapter();
        api = CustomFirebaseApi.getInstance();

        bin.mvRvDays.setAdapter(adapter);
        bin.mvRvDays.setLayoutManager(new GridLayoutManager(this, 1));
        bin.mvRvDays.setHasFixedSize(true);

        Bundle bun = getIntent().getExtras();
        if (bun != null){
            volunteerId = bun.getInt(VOLUNTEER_ID_EXTRA, -1);
            availability = bun.getString(AVAILABILITY_EXTRA, "");
        }
        adapter.setDays(CustomDay.getDays(availability));
        bin.mvBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("availability", CustomDay.getAvailability(adapter.getDays()));

                UiUtils.showProgressbar(ManageAvailabilityActivity.this);
                api.updateVolunteer(volunteerId, data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        UiUtils.dismissDialog();
                        if (task.isSuccessful()){
                            UiUtils.makeToast(R.string.op_done, getBaseContext());
                            finish();
                        }
                        else {
                            UiUtils.makeToast(task.getException().getMessage(), getBaseContext());
                        }
                    }
                });
            }
        });
    }
}