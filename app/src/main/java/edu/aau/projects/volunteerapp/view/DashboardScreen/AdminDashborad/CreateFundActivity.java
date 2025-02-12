package edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.ActivityCreateFundBinding;
import edu.aau.projects.volunteerapp.utils.BaseActivity;

public class CreateFundActivity extends BaseActivity {

    private static final String ADMIN_ID_EXTRA = "admin_id";
    ActivityCreateFundBinding bin;
    CustomFirebaseApi api;
    int adminId;

    public static Intent makeIntent(Context context, int adminId) {
        return new Intent(context, CreateFundActivity.class).putExtra(ADMIN_ID_EXTRA,  adminId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityCreateFundBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());

        Bundle bun = getIntent().getExtras();
        adminId = bun == null ? -1 : bun.getInt(ADMIN_ID_EXTRA, -1);

        api = CustomFirebaseApi.getInstance();

    }
}