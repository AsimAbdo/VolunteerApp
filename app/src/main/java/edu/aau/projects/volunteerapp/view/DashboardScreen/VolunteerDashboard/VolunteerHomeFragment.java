package edu.aau.projects.volunteerapp.view.DashboardScreen.VolunteerDashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.FragmentVolunteerHomeBinding;
import edu.aau.projects.volunteerapp.model.Volunteer;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class VolunteerHomeFragment extends Fragment {

    FragmentVolunteerHomeBinding bin;
    CustomFirebaseApi api;
    Volunteer volunteer;

    public VolunteerHomeFragment() {
        // Required empty public constructor
    }

    public static VolunteerHomeFragment newInstance() {
        return new VolunteerHomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bin = FragmentVolunteerHomeBinding.inflate(inflater);
        api = CustomFirebaseApi.getInstance();

        getVolunteer();

        bin.volCvAvailableTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AvailableTasksActivity.makeIntent(getContext(), volunteer.getV_id()));
            }
        });
        bin.volCvManageSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(
                        ManageSkillsActivity.makeIntent(
                                getContext(),
                                volunteer.getV_id(),volunteer.toFormattedForm()
                        )
                );
            }
        });

        bin.volCvAvailableManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(
                        ManageAvailabilityActivity.makeIntent(
                                getContext(),
                                volunteer.getV_id(),volunteer.getAvailability()
                        )
                );
            }
        });
        return bin.getRoot();
    }

    private void getVolunteer(){
        UiUtils.showProgressbar(getActivity());
        api.getUserInfo().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot :
                            snapshot.getChildren()) {
                        volunteer = dataSnapshot.getValue(Volunteer.class);
                    }
                }
                UiUtils.dismissDialog();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}