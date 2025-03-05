package edu.aau.projects.volunteerapp.controller.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class GetUsers implements FirebaseAccess {

    public static Query getUserData(){
        FirebaseUser user = CustomAuthentication.getCurrentUser();
        if (user == null)
            throw new RuntimeException("There is no user data");
        String userId = user.getUid();
        String role = CustomAuthentication.getCurrentUserName();
        Log.d("ServiceSeekerActivity", "getUserData: " + role);
        Query query = null;
        if (role.equals("Service Seeker"))
            query = serviceSeekersRef.orderByChild("user/userId").equalTo(userId);
        else if (role.equals("Donor"))
            query = donorsRef.orderByChild("user/userId").equalTo(userId);
        else if (role.equals("Volunteer"))
            query = volunteersRef.orderByChild("user/userId").equalTo(userId);
        else if (role.equals("Admin"))
            query = adminsRef.orderByChild("user/userId").equalTo(userId);
//        else
//            query = serviceSeekersRef.orderByChild("user/userId").equalTo(userId);
        return query;
    }

    public static Query getUser(){
        FirebaseUser user = CustomAuthentication.getCurrentUser();
        if (user == null)
            throw new RuntimeException("There is no user data");
        String userId = user.getUid();
        String role = CustomAuthentication.getCurrentUserName();
        Query query = usersRef.orderByChild("userId").equalTo(userId);

//        else
//            query = serviceSeekersRef.orderByChild("user/userId").equalTo(userId);
        return query;
    }

    public static DatabaseReference getUsers() {
        return volunteersRef;
    }

    public static Query getVolunteer(int volunteerId) {
        return volunteersRef.orderByChild("v_id").equalTo(volunteerId);
    }
}
