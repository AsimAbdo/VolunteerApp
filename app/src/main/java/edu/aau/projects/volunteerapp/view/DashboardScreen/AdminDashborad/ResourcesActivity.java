package edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad;

import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.List;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.uiadapters.CashFundAdapter;
import edu.aau.projects.volunteerapp.databinding.ActivityResourcesBinding;
import edu.aau.projects.volunteerapp.model.CashFund;
import edu.aau.projects.volunteerapp.utils.BaseActivity;

public class ResourcesActivity extends BaseActivity {

    private static final String ADMIN_ID_EXTRA = "adminIdExtra";

    ActivityResourcesBinding bin;
    CustomFirebaseApi api;
    List<CashFund> cashFundList;
    CashFundAdapter adapter;
    int adminId = -1;
    public static Intent makeIntent(Context context, int admin_id) {
        return new Intent(context, ResourcesActivity.class).putExtra(ADMIN_ID_EXTRA, admin_id);
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
        adminId = bun == null? -1 : bun.getInt(ADMIN_ID_EXTRA, -1);

        api = CustomFirebaseApi.getInstance();
        adapter = new CashFundAdapter(cashFundList);

        bin.fundRv.setAdapter(adapter);
        bin.fundRv.setLayoutManager(new GridLayoutManager(this, 1));
        bin.fundRv.setHasFixedSize(true);

        bin.fundAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreateFundActivity.makeIntent(getBaseContext(), adminId));
            }
        });
    }
}