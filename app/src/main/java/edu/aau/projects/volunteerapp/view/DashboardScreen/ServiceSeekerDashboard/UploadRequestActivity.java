package edu.aau.projects.volunteerapp.view.DashboardScreen.ServiceSeekerDashboard;

import androidx.annotation.NonNull;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.ActivityUploadRequestBinding;
import edu.aau.projects.volunteerapp.model.MTask;
import edu.aau.projects.volunteerapp.model.TaskDescription;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class UploadRequestActivity extends BaseActivity {

    ActivityUploadRequestBinding bin;
    CustomFirebaseApi api;
    int serviceSeekerId;
    String startDate = "", endDate = "";

    public static Intent makeIntent(Context context, int serviceSeekerId){
        return new Intent(context, UploadRequestActivity.class).putExtra("serviceSeeker", serviceSeekerId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityUploadRequestBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());

        api = CustomFirebaseApi.getInstance();
        serviceSeekerId = getIntent().getIntExtra("serviceSeeker", -1);

        bin.uploadStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.createDatePicker(UploadRequestActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                        bin.uploadStartDate.setText(startDate);
                    }
                });
            }
        });

        bin.uploadEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.createDatePicker(UploadRequestActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                        bin.uploadEndDate.setText(endDate);
                    }
                });
            }
        });

        bin.uploadBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UiUtils.verifyFields(bin.uploadEtDesc, bin.uploadEtLocation)
                        || bin.uploadSpType.getSelectedItemPosition() == 0) {
                    UiUtils.makeToast(R.string.empty_fields, getBaseContext());
                    return;
                }

                if (startDate.equals("")){
                    UiUtils.makeToast(R.string.startdate_not_selected, getBaseContext());
                    return;
                }
                if (endDate.equals("")){
                    UiUtils.makeToast(R.string.enddate_not_selected, getBaseContext());
                    return;
                }
                String location = bin.uploadEtLocation.getText().toString();
                String desc = bin.uploadEtDesc.getText().toString();
                String type = bin.uploadSpType.getSelectedItem().toString();

                TaskDescription description = new TaskDescription(type, location, desc);
                MTask mTask = new MTask(serviceSeekerId, description, startDate, endDate, getString(R.string.new_task));
                createTask(mTask);
            }
        });
    }

    private void createTask(MTask mTask){
        UiUtils.showProgressbar(this);
        api.createTask(mTask).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    UiUtils.makeToast(R.string.taskCreatedSuccessfully, getBaseContext());
                    finish();
                }
                else {
                    UiUtils.makeToast(task.getException().getMessage(), getBaseContext());
                }
                UiUtils.dismissDialog();
            }
        });
    }
}