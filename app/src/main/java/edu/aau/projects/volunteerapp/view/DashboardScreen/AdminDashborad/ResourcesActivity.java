package edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.uiadapters.BankAccountsAdapter;
import edu.aau.projects.volunteerapp.controller.uiadapters.CashFundAdapter;
import edu.aau.projects.volunteerapp.databinding.ActivityResourcesBinding;
import edu.aau.projects.volunteerapp.model.BankAccount;
import edu.aau.projects.volunteerapp.model.CashFund;
import edu.aau.projects.volunteerapp.utils.BaseActivity;

public class ResourcesActivity extends BaseActivity {

    private static final String OWNER_ID_EXTRA = "ownerIdExtra";
    private static final String OWNER_TYPE_EXTRA = "ownerTypeExtra";

    ActivityResourcesBinding bin;
    CustomFirebaseApi api;
    List<BankAccount> bankAccounts;
    BankAccountsAdapter adapter;
    private int ownerId = -1;
    private String ownerType;

    public static Intent makeIntent(Context context, String ownerType, int ownerId) {
        return new Intent(context, ResourcesActivity.class)
                .putExtra(OWNER_ID_EXTRA, ownerId)
                .putExtra(OWNER_TYPE_EXTRA, ownerType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityResourcesBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());
        setSupportActionBar(bin.resToolbar);

        Bundle bun = getIntent().getExtras();
        ownerId = bun == null? -1 : bun.getInt(OWNER_ID_EXTRA, -1);
        ownerType = bun == null? "" : bun.getString(OWNER_TYPE_EXTRA, "");

        api = CustomFirebaseApi.getInstance();
        adapter = new BankAccountsAdapter(bankAccounts);

        bin.accountRv.setAdapter(adapter);
        bin.accountRv.setLayoutManager(new GridLayoutManager(this, 1));
        bin.accountRv.setHasFixedSize(true);

        bin.accountAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreateAccountActivity.makeIntent(getBaseContext(), ownerType, ownerId));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        api.getBankAccounts(ownerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    bankAccounts.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        BankAccount bankAccount = child.getValue(BankAccount.class);
                        bankAccounts.add(bankAccount);
                    }
                    adapter.setBankAccounts(bankAccounts);
                }
                // TODO in case he does not have any account.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}