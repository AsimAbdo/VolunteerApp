package edu.aau.projects.volunteerapp.view.DashboardScreen.DonorDashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.FragmentDonorBinding;
import edu.aau.projects.volunteerapp.model.Donor;
import edu.aau.projects.volunteerapp.model.ServiceSeeker;
import edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad.ResourcesActivity;

public class DonorFragment extends Fragment {
    FragmentDonorBinding bin;
    CustomFirebaseApi api;
    Donor donor;
    public DonorFragment() {
        // Required empty public constructor
    }


    public static DonorFragment newInstance() {
        return new DonorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bin = FragmentDonorBinding.inflate(inflater);
        api = CustomFirebaseApi.getInstance();

        api.getUserInfo().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot :
                            snapshot.getChildren()) {
                        donor = dataSnapshot.getValue(Donor.class);
                    }
                    init();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return bin.getRoot();
    }

    private void init() {
        bin.donorDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(DonateActivity.makeIntent(getContext(), getString(R.string.admin), donor.getDonorId()));
            }
        });

        bin.donorViewResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(ViewUsagesActivity.makeIntent(getContext(), donor.getDonorId()));
            }
        });

        bin.donorViewReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bin.donorCashFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(ResourcesActivity.makeIntent(getContext(), getActivity().getString(R.string.admin), donor.getDonorId()));
            }
        });
    }

}