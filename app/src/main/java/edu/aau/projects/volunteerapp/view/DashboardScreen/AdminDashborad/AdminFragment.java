package edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad;

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
import edu.aau.projects.volunteerapp.databinding.FragmentAdminBinding;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.model.Admin;

public class AdminFragment extends Fragment {
    FragmentAdminBinding bin;
    CustomFirebaseApi api;

    Admin admin;
    public AdminFragment() {
        // Required empty public constructor
    }


    public static AdminFragment newInstance(int roleId, String role) {
        return new AdminFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bin = FragmentAdminBinding.inflate(inflater);
        api = CustomFirebaseApi.getInstance();

        if (admin == null)
            api.getUserInfo().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot child : snapshot.getChildren()) {
                        admin = child.getValue(Admin.class);
                    }
                    enableFields();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return bin.getRoot();
    }

    private void enableFields(){
        bin.adminUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(AdminManageActivity.makeIntent(getContext(), admin.getAdminId(), 1));
            }
        });

        bin.adminResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(ResourcesActivity.makeIntent(getContext(), getString(R.string.admin), 1));
            }
        });

        bin.adminTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(AdminManageActivity.makeIntent(getContext(), admin.getAdminId(), 2));
            }
        });
    }

}