package edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.aau.projects.volunteerapp.databinding.FragmentAdminBinding;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;

public class AdminFragment extends Fragment {
    FragmentAdminBinding bin;
    CustomFirebaseApi api;

    public AdminFragment() {
        // Required empty public constructor
    }


    public static AdminFragment newInstance() {
        return new AdminFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bin = FragmentAdminBinding.inflate(inflater);
        api = CustomFirebaseApi.getInstance();

        bin.adminUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(AdminManageActivity.makeIntent(getContext(), 1));
            }
        });

        bin.adminTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(AdminManageActivity.makeIntent(getContext(), 2));
            }
        });
        return bin.getRoot();
    }

}