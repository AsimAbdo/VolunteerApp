package edu.aau.projects.volunteerapp.view.DashboardScreen.ServiceSeekerDashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.uiadapters.TaskAdapter;
import edu.aau.projects.volunteerapp.databinding.FragmentServiceSeekerHomeBinding;
import edu.aau.projects.volunteerapp.model.MTask;
import edu.aau.projects.volunteerapp.model.ServiceSeeker;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class ServiceSeekerHomeFragment extends Fragment {
    FragmentServiceSeekerHomeBinding bin;
    ServiceSeeker serviceSeeker;
    TaskAdapter adapter;
    List<MTask> tasks;
    CustomFirebaseApi api;

    public ServiceSeekerHomeFragment() {
        // Required empty public constructor
    }


    public static ServiceSeekerHomeFragment newInstance() {
        return new ServiceSeekerHomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bin = FragmentServiceSeekerHomeBinding.inflate(inflater);
        init();

        return bin.getRoot();
    }

    private void init(){
        api = CustomFirebaseApi.getInstance();
        tasks = new ArrayList<>();
        adapter = new TaskAdapter(tasks);

        bin.ssRvRequests.setAdapter(adapter);
        bin.ssRvRequests.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        bin.ssRvRequests.setHasFixedSize(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        getFirebaseInfo();
    }

    private void getFirebaseInfo(){
//        UiUtils.showProgressbar(getActivity());
        api.getUserInfo().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :
                        snapshot.getChildren()) {
                    serviceSeeker = dataSnapshot.getValue(ServiceSeeker.class);
                }
                enableButton();
                getTasks();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTasks(){
        api.getTasks(serviceSeeker.getSeekerId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tasks.clear();
                for (DataSnapshot dataSnapshot :
                        snapshot.getChildren()) {
                    MTask task = dataSnapshot.getValue(MTask.class);
                    tasks.add(task);
                }
                adapter.setTasks(tasks);
//                UiUtils.dismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("TAG", "onCancelled: ");
            }
        });
    }

    private void enableButton(){
        bin.uploadRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(UploadRequestActivity.makeIntent(getContext(), serviceSeeker.getSeekerId()));
            }
        });
    }
}