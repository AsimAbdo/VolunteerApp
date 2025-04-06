package edu.aau.projects.volunteerapp.view.DashboardScreen.DonorDashboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.uiadapters.BankAccountSpAdapter;
import edu.aau.projects.volunteerapp.controller.uiadapters.BankAccountsAdapter;
import edu.aau.projects.volunteerapp.databinding.ActivityDonateBinding;
import edu.aau.projects.volunteerapp.model.BankAccount;
import edu.aau.projects.volunteerapp.model.Donation;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class DonateActivity extends BaseActivity {
    private static final String DONOR_ID_EXTRA = "dono_id";
    private static final String DONOR_TYPE_EXTRA = "donorType";
    private static final String ENTITY_ID = "entityId";
    ActivityDonateBinding bin;
    CustomFirebaseApi api;
    int donorId, entityId;
    String donorType;
    List<BankAccount> donorAccounts;
    List<BankAccount> receiverAccounts;
    BankAccountSpAdapter donorAccountsAdapter;
    BankAccountsAdapter receiverAccountsAdapter;

    public static Intent makeIntent(Context context, String donorType, int donorId){
        return new Intent(context, DonateActivity.class)
                .putExtra(DONOR_ID_EXTRA, donorId)
                .putExtra(DONOR_TYPE_EXTRA, donorType);
    }

    public static Intent makeIntent(Context context, String donorType, int donorId, int entityId){
        return new Intent(context, DonateActivity.class)
                .putExtra(DONOR_ID_EXTRA, donorId)
                .putExtra(ENTITY_ID, entityId)
                .putExtra(DONOR_TYPE_EXTRA, donorType);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityDonateBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());
        setSupportActionBar(bin.donateToolbar);

        api = CustomFirebaseApi.getInstance();

        Bundle bun = getIntent().getExtras();
        donorId = bun == null ? -1: bun.getInt(DONOR_ID_EXTRA, -1);
        donorType = bun == null ? "": bun.getString(DONOR_TYPE_EXTRA, "");
        entityId = bun == null ? -1: bun.getInt(ENTITY_ID, -1);

        donorAccounts = new ArrayList<>();
        receiverAccounts = new ArrayList<>();

        receiverAccountsAdapter = new BankAccountsAdapter(receiverAccounts);

        bin.rvAccount.setAdapter(receiverAccountsAdapter);
        bin.rvAccount.setLayoutManager(new GridLayoutManager(this, 1));
        bin.rvAccount.setHasFixedSize(true);

        getAccounts();

        bin.donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UiUtils.verifyFields(bin.donateEtAmount)){
                    UiUtils.makeToast(R.string.empty_fields, getBaseContext());
                    return;
                }
                double amount = Double.parseDouble(bin.donateEtAmount.getText().toString());

                Donation donation = new Donation();
                donation.setDonationDate(UiUtils.getCurrentDate());
                donation.setDonorId(donorId);
                donation.setReceivingEntity(donorType);
                if (receiverAccounts.size() > 0)
                    donation.setEntityId(receiverAccounts.get(0).getOwnerId());
                donation.setAmount(amount);

                UiUtils.showProgressbar(DonateActivity.this);
                api.createDonation(donation).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        UiUtils.dismissDialog();
                        if (task.isSuccessful()){
                            UiUtils.makeToast(
                                    donorType.equals(
                                            getString(R.string.admin))?
                                            R.string.donationSuccessfull : R.string.op_done,
                                    getBaseContext()
                            );
                        }
                    }
                });
            }
        });
    }

    private void getAccounts() {
        api.getBankAccounts(donorId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    donorAccounts.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        BankAccount account = child.getValue(BankAccount.class);
                        donorAccounts.add(account);
                    }
                    donorAccountsAdapter = new BankAccountSpAdapter(donorAccounts, getBaseContext());
                    bin.donateSpAccounts.setAdapter(donorAccountsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getReceivingEntityAccounts(){
        if (donorType.equals(getString(R.string.admin))){
            api.getBankAccounts(getString(R.string.admin)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        receiverAccounts.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            BankAccount account = child.getValue(BankAccount.class);
                            receiverAccounts.add(account);
                        }
                        receiverAccountsAdapter.setBankAccounts(receiverAccounts);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            api.getBankAccounts(entityId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        receiverAccounts.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            BankAccount account = child.getValue(BankAccount.class);
                            receiverAccounts.add(account);
                        }
                        receiverAccountsAdapter.setBankAccounts(receiverAccounts);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}