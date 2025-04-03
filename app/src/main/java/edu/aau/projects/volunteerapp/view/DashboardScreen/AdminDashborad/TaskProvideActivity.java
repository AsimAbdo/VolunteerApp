package edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import edu.aau.projects.volunteerapp.databinding.ActivityTaskProvideBinding;
import edu.aau.projects.volunteerapp.model.BankAccount;
import edu.aau.projects.volunteerapp.model.MTask;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class TaskProvideActivity extends AppCompatActivity {
    private static final String TASK_EXTRA = "tats";
    private static final String OWNER_ID_EXTRA = "tats";
    ActivityTaskProvideBinding bin;
    MTask mTask;
    int adminId;
    CustomFirebaseApi api;
    List<BankAccount> accounts;
    public static Intent makeIntent(Context baseContext, int ownerId, MTask task) {
        return new Intent(baseContext, TaskProvideActivity.class)
                .putExtra(TASK_EXTRA, task)
                .putExtra(OWNER_ID_EXTRA, ownerId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityTaskProvideBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());

        Bundle bun = getIntent().getExtras();
        if (bun != null){
            mTask = (MTask) bun.getSerializable(TASK_EXTRA);
            adminId = bun.getInt(OWNER_ID_EXTRA, -1);
        }

        api = CustomFirebaseApi.getInstance();
        accounts = new ArrayList<>();

        api.getBankAccounts(adminId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    accounts.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        BankAccount account = child.getValue(BankAccount.class);
                        accounts.add(account);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bin.btnProvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UiUtils.verifyFields(bin.prEtBalance)){
                    UiUtils.makeToast(R.string.empty_fields, getBaseContext());
                    return;
                }
                double balance = Double.parseDouble(bin.prEtBalance.getText().toString());
                BankAccount account = accounts.get(bin.prSpBankAccounts.getSelectedItemPosition());
                if (account.withdraw(balance) == 0){
                    UiUtils.makeToast(R.string.insufficentResources, getBaseContext());
                }
                else {
                    UiUtils.showProgressbar(getParent());
                    provideMoney(account, balance);
                }
            }
        });
    }

    private void provideMoney(BankAccount account, double balance){
        api.updateBankAccount(account).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                UiUtils.dismissDialog();
                if (task.isSuccessful()){
                    mTask.getDescription().setProvidedAmount(mTask.getDescription().getAmount() + balance);
                    api.updateTask(mTask).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                UiUtils.makeToast(R.string.op_done, getBaseContext());
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
}