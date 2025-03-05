package edu.aau.projects.volunteerapp.controller.firebase;

import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

import edu.aau.projects.volunteerapp.model.User;
import edu.aau.projects.volunteerapp.model.Volunteer;

public class UpdateUsers implements FirebaseAccess {
    private static String usersPath = "/" + usersRef.getKey() + "/";
    private static String volunteerPath = "/" + volunteersRef.getKey();
    private static String donorPath = "/" + donorsRef.getKey();
    private static String serviceSeekerPath = "/" + serviceSeekersRef.getKey();
    private static String adminPath = "/" + adminsRef.getKey();
    private static String leaderPath = "/" + teamLeadersRef.getKey();

    public static Task<Void> updateUser(User user, int roleId){
        Map<String, Object> updateChildren = new HashMap<>();
        updateChildren.put(usersPath + user.getUserId() + "/", user.toMap());
        updateChildren.put(getRightPath(user.getRole()) + "/" + roleId + "/user/", user.toMap());
        return DATABASE_REFERENCE.updateChildren(updateChildren);
    }

    private static String getRightPath(String role){
        String path = "";
        switch (role){
            case "Volunteer":
                path = volunteerPath;
                break;
            case "Admin":
                path = adminPath;
                break;
            case "Donor":
                path = donorPath;
                break;
            case "Service Seeker":
                path = serviceSeekerPath;
                break;
            case "Team Leader":
                path = leaderPath;
                break;
        }
        return path;
    }

    public static void updateToTeamLeader(Volunteer volunteer) {
        volunteer.setTeamLeader(1);
        volunteersRef.child(String.valueOf(volunteer.getV_id())).setValue(volunteer);
    }
}
