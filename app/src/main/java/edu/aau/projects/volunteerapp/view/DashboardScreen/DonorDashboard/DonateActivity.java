package edu.aau.projects.volunteerapp.view.DashboardScreen.DonorDashboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import edu.aau.projects.volunteerapp.model.MTask;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class DonateActivity extends BaseActivity {
    private static final String DONOR_ID_EXTRA = "dono_id";
    private static final String DONOR_TYPE_EXTRA = "donorType";
    private static final String TASK_EXTRA = "task";
    private static final String ENTITY_ID = "entityId";
    ActivityDonateBinding bin;
    CustomFirebaseApi api;
    int donorId, entityId;
    String donorType;
    MTask mTask;
    List<BankAccount> donorAccounts;
    List<BankAccount> receiverAccounts;
    BankAccountSpAdapter donorAccountsAdapter;
    BankAccountsAdapter receiverAccountsAdapter;
    BankAccount receiverAccount;

    public static Intent makeIntent(Context context, String donorType, int donorId){
        return new Intent(context, DonateActivity.class)
                .putExtra(DONOR_ID_EXTRA, donorId)
                .putExtra(DONOR_TYPE_EXTRA, donorType);
    }

    public static Intent makeIntent(Context context, String donorType, int donorId, int entityId, MTask task){
        return new Intent(context, DonateActivity.class)
                .putExtra(DONOR_ID_EXTRA, donorId)
                .putExtra(TASK_EXTRA, task)
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
        if (bun != null){
            donorId = bun.getInt(DONOR_ID_EXTRA, -1);
            donorType = bun.getString(DONOR_TYPE_EXTRA, "");
            entityId = bun.getInt(ENTITY_ID, -1);
            mTask = (MTask) bun.getSerializable(TASK_EXTRA);
        }


        donorAccounts = new ArrayList<>();
        receiverAccounts = new ArrayList<>();

        receiverAccountsAdapter = new BankAccountsAdapter(receiverAccounts, true);

        bin.rvAccount.setAdapter(receiverAccountsAdapter);
        bin.rvAccount.setLayoutManager(new GridLayoutManager(this, 1));
        bin.rvAccount.setHasFixedSize(true);

        getAccounts();
        getReceivingEntityAccounts();

        bin.donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UiUtils.verifyFields(bin.donateEtAmount)){
                    UiUtils.makeToast(R.string.empty_fields, getBaseContext());
                    return;
                }
                if (receiverAccounts.size() < 1){
                    UiUtils.makeToast(R.string.invalid_receiverAccount, getBaseContext());
                    return;
                }

                if (donorAccounts.size() < 1){
                    UiUtils.makeToast(R.string.invalid_donorAccount, getBaseContext());
                    return;
                }
                if (receiverAccountsAdapter.getCheckedAccounts().size() > 1){
                    UiUtils.makeToast(R.string.select_more_than_one, getBaseContext());
                    return;
                } else if (receiverAccountsAdapter.getCheckedAccounts().size() < 1) {
                    UiUtils.makeToast(R.string.select_less_than_one, getBaseContext());
                    return;
                }
                double amount = Double.parseDouble(bin.donateEtAmount.getText().toString());
                BankAccount donorAccount = donorAccounts.get(bin.donateSpAccounts.getSelectedItemPosition());
                receiverAccount = receiverAccountsAdapter.getCheckedAccounts().get(0);
                if (amount > donorAccount.getBalance() && entityId != -1){
                    UiUtils.makeToast(R.string.insufficentResources, getBaseContext());
                    return;
                }

                if (!donorAccount.getBankName().equals(receiverAccount.getBankName())){
                    UiUtils.makeToast(R.string.transfertoDifferentBank, getBaseContext());
                    return;
                }
                Donation donation = new Donation();
                donation.setDonationDate(UiUtils.getCurrentDate());
                donation.setDonorId(donorId);
                donation.setReceivingEntity(donorType);
                if (receiverAccounts.size() > 0)
                    donation.setEntityId(receiverAccounts.get(0).getOwnerId());
                donation.setAmount(amount);

                createDonation(donation, donorAccount, amount);
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
                    Log.d(TAG, "onDataChange: " + snapshot.getKey());
                    if (snapshot.exists()){
                        Log.d(TAG, "onDataChange: hhh " + snapshot.getKey());
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

    private void createDonation(Donation donation, BankAccount donorAccount, double amount){
        UiUtils.showProgressbar(this);
        donorAccount.setBalance(donorAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);

        if (entityId != -1){
            mTask.getDescription().setProvidedAmount(mTask.getDescription().getAmount() + amount);
        }
        api.updateBankAccount(donorAccount).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    api.updateBankAccount(receiverAccount).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                               if (entityId != -1){
                                   api.updateTask(mTask);
                               }
                                api.createDonation(donation).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        UiUtils.dismissDialog();
                                        if (task.isSuccessful()) {
                                            UiUtils.makeToast(
                                                    donorType.equals(
                                                            getString(R.string.admin)) ?
                                                            R.string.donationSuccessfull : R.string.op_done,
                                                    getBaseContext()
                                            );
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
                else {
                    UiUtils.makeToast(task.getException().getMessage(), getBaseContext());
                }
            }
        });

    }
}