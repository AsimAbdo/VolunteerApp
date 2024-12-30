package edu.aau.projects.volunteerapp.view.DashboardScreen;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.databinding.ActivityDashboardBinding;
import edu.aau.projects.volunteerapp.model.Admin;
import edu.aau.projects.volunteerapp.model.User;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.UiUtils;
import edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad.AdminFragment;
import edu.aau.projects.volunteerapp.view.DashboardScreen.ServiceSeekerDashboard.ServiceSeekerHomeFragment;
import edu.aau.projects.volunteerapp.view.DashboardScreen.VolunteerDashboard.VolunteerHomeFragment;
import edu.aau.projects.volunteerapp.view.HomeScreen.MainActivity;

public class DashboardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityDashboardBinding bin;
    ActionBarDrawerToggle toggle;
    CustomFirebaseApi api;
    String role = "";


    public static Intent makeIntent(Context context){
        return new Intent(context, DashboardActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        bin = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(bin.getRoot());
        setSupportActionBar(bin.dashboardToolbar);

        toggle = new ActionBarDrawerToggle(
                this,
                bin.getRoot(),
                bin.dashboardToolbar,
                R.string.open_drawer,
                R.string.close_drawer);
        bin.getRoot().addDrawerListener(toggle);
        toggle.syncState();

        bin.dashboardNavView.setNavigationItemSelectedListener(this);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (bin.getRoot().isDrawerOpen(GravityCompat.START))
                    bin.getRoot().closeDrawer(GravityCompat.START);
                else
                    finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(callback);
        api = CustomFirebaseApi.getInstance();
        role = api.getCurrentUserName();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_logout) {
            api.logout();
            startActivity(MainActivity.makeIntent(this));
            finish();
        } else if (item.getItemId() == R.id.nav_profile) {
            viewHeader(false);
            pushFragment(this, ProfileFragment.newInstance(role), bin.dashboardContainer.getId(), false);
        }
        if (bin.getRoot().isDrawerOpen(GravityCompat.START))
            bin.getRoot().closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewHeader(true);

        if (role.equals(getString(R.string.service_seeker))) {
            pushFragment(this, new ServiceSeekerHomeFragment(), bin.dashboardContainer.getId(), false);
        }
        else if (role.equals(getString(R.string.admin))) {
            bin.dashTitle.setText(R.string.admin_title);
            pushFragment(this, new AdminFragment(), bin.dashboardContainer.getId(), false);
        }
        else if (role.equals(getString(R.string.volunteer))) {
            pushFragment(this, new VolunteerHomeFragment(), bin.dashboardContainer.getId(), false);
        }
        else if (role.equals(getString(R.string.donor))) {

        }
    }

    private void viewHeader(boolean visible){
        if (visible){
            bin.dashTitle.setVisibility(View.VISIBLE);
            bin.dashIv.setVisibility(View.VISIBLE);
        }
        else {
            bin.dashTitle.setVisibility(View.GONE);
            bin.dashIv.setVisibility(View.GONE);
        }
    }
}