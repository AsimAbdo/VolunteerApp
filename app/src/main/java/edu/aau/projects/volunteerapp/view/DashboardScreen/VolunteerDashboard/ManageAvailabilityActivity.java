package edu.aau.projects.volunteerapp.view.DashboardScreen.VolunteerDashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.utils.BaseActivity;

public class ManageAvailabilityActivity extends BaseActivity {

    private static final String VOLUNTEER_ID_EXTRA = "availability";
    private static final String AVAILABILITY_EXTRA = "availability";

    public static Intent makeIntent(Context context, int volunteerId, String availability){
        return new Intent(context, ManageAvailabilityActivity.class)
                .putExtra(AVAILABILITY_EXTRA, availability)
                .putExtra(VOLUNTEER_ID_EXTRA, volunteerId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_availability);
    }
}