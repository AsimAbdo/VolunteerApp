package edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.ActivityCreateAccountBinding;
import edu.aau.projects.volunteerapp.model.BankAccount;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.EntriesUtils;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class CreateAccountActivity extends BaseActivity {

    private static final String OWNER_TYPE_EXTRA = "ownerType";
    private static final String OWNER_ID_EXTRA = "owner_id";
    ActivityCreateAccountBinding bin;
    CustomFirebaseApi api;
    int ownerId;
    private String ownerType;
    private String bankName = "";

    public static Intent makeIntent(Context context, String ownerType, int ownerId) {
        return new Intent(context, CreateAccountActivity.class)
                .putExtra(OWNER_ID_EXTRA, ownerId)
                .putExtra(OWNER_TYPE_EXTRA, ownerType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());

        Bundle bun = getIntent().getExtras();
        ownerId = bun == null ? -1 : bun.getInt(OWNER_ID_EXTRA, -1);
        ownerType = bun == null ? "" : bun.getString(OWNER_TYPE_EXTRA, "");

        api = CustomFirebaseApi.getInstance();

        bin.accountSpBankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bin.accountEtlBankName.setVisibility(position == 3 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bin.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UiUtils.verifyFields(bin.accountEtAccountNumber, bin.accountEtIBAN)){
                    UiUtils.makeToast(R.string.empty_fields, getBaseContext());
                    return;
                }
                if (bin.accountSpBankName.getSelectedItemPosition() == 3)
                    if (!UiUtils.verifyFields(bin.accountEtBankName)){
                        UiUtils.makeToast(R.string.empty_fields, getBaseContext());
                        return;
                    }
                bankName = bin.accountEtBankName.getText().toString();
                BankAccount account = new BankAccount();
                account.setAccountNumber(bin.accountEtAccountNumber.getText().toString());
                account.setIBAN(bin.accountEtIBAN.getText().toString());
                account.setOwnerId(ownerId);
                account.setOwnerType(ownerType);
                account.setBankName(
                        bankName.equals("") ?
                                EntriesUtils.getBankList()[bin.accountSpBankName.getSelectedItemPosition()]
                                : bankName
                );

                UiUtils.showProgressbar(CreateAccountActivity.this);
                api.createBankAccount(account).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        UiUtils.dismissDialog();
                        if (task.isSuccessful()){
                            UiUtils.makeToast(R.string.op_done, getBaseContext());
                            finish();
                        }
                        else {
                            UiUtils.makeToast(task.getException().getMessage(), getBaseContext());
                        }
                    }
                });
            }
        });
    }
}