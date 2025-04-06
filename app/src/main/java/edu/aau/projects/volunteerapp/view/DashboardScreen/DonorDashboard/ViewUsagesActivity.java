
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
import edu.aau.projects.volunteerapp.controller.uiadapters.DonationAdapter;
import edu.aau.projects.volunteerapp.databinding.ActivityViewUsagesBinding;
import edu.aau.projects.volunteerapp.model.Donation;
import edu.aau.projects.volunteerapp.utils.BaseActivity;

public class ViewUsagesActivity extends BaseActivity {
    private static final String DONOR_ID_EXTRA = "donorIdExtra";
    ActivityViewUsagesBinding bin;
    List<Donation> donations;
    DonationAdapter adapter;
    CustomFirebaseApi api;
    int donorId;

    public static Intent makeIntent(Context context, int donorId){
        return new Intent(context, ViewUsagesActivity.class)
                .putExtra(DONOR_ID_EXTRA, donorId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityViewUsagesBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());
        setSupportActionBar(bin.vuToolbar);

        api = CustomFirebaseApi.getInstance();

        Bundle bun = getIntent().getExtras();
        donorId = bun == null? -1 : bun.getInt(DONOR_ID_EXTRA, -1);

        donations = new ArrayList<>();
        adapter = new DonationAdapter(donations);

        bin.vuRvDonations.setLayoutManager(new GridLayoutManager(this, 1));
        bin.vuRvDonations.setAdapter(adapter);
        bin.vuRvDonations.setHasFixedSize(true);

        api.getDonations(donorId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    donations.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Donation donation = child.getValue(Donation.class);
                        donations.add(donation);
                    }
                    adapter.setDonations(donations);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}