package edu.aau.projects.volunteerapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import edu.aau.projects.volunteerapp.model.Donor;
import edu.aau.projects.volunteerapp.model.ServiceSeeker;
import edu.aau.projects.volunteerapp.model.User;
import edu.aau.projects.volunteerapp.model.Volunteer;

public class AppSharedPreferences {
    SharedPreferences sp;
    Activity activity;

    public AppSharedPreferences(Activity activity) {
        this.activity = activity;
    }

    public void saveUserId(User user){
        sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user_id", user.getUserId())
                .putString("user_role", user.getRole())
                .apply();
    }

    public void saveVolunteerId(int volunteerId){
        sp = activity.getSharedPreferences("VolunteerData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("volunteer_id", volunteerId)
                .apply();
    }

    public void saveServiceSeekerId(int seekerId){
        sp = activity.getSharedPreferences("ServiceSeekerData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("service_seeker_id", seekerId)
                .apply();
    }
    public void saveDonorId(int donorId){
        sp = activity.getSharedPreferences("DonorData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("donor_id", donorId)
                .apply();
    }

    public int getUerId(){
        sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sp.getInt("user_id", -1);
    }
    public int getVolunteerId(){
        sp = activity.getSharedPreferences("VolunteerData", Context.MODE_PRIVATE);
        return sp.getInt("volunteer_id", -1);
    }
    public int getServiceSeekerId(){
        sp = activity.getSharedPreferences("ServiceSeekerData", Context.MODE_PRIVATE);
        return sp.getInt("service_seeker_id", -1);
    }
    public int getDonorId(){
        sp = activity.getSharedPreferences("DonorData", Context.MODE_PRIVATE);
        return sp.getInt("donor_id", -1);
    }

    public String getUserRole(){
        sp = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sp.getString("user_role", "");
    }
}
