package edu.aau.projects.volunteerapp.controller.firebase;

import com.google.android.gms.tasks.Task;

import java.util.Map;

import edu.aau.projects.volunteerapp.model.Admin;
import edu.aau.projects.volunteerapp.model.Donor;
import edu.aau.projects.volunteerapp.model.ServiceSeeker;
import edu.aau.projects.volunteerapp.model.User;
import edu.aau.projects.volunteerapp.model.Volunteer;

public class UpdateUsersData implements FirebaseAccess {
    public static Task<Void> updateVolunteer(int volunteerId, Map<String, Object> volData){
        return volunteersRef.child(String.valueOf(volunteerId)).updateChildren(volData);
    }

    public static Task<Void> deleteUser(User user, ServiceSeeker serviceSeeker) {
        usersRef.child(user.getUserId()).removeValue();
        return serviceSeekersRef.child(String.valueOf(serviceSeeker.getSeekerId())).removeValue();
    }
    public static Task<Void> deleteUser(User user, Admin admin) {
        usersRef.child(user.getUserId()).removeValue();
        return adminsRef.child(String.valueOf(admin.getAdminId())).removeValue();
    }
    public static Task<Void> deleteUser(User user, Volunteer volunteer) {
        usersRef.child(user.getUserId()).removeValue();
        return volunteersRef.child(String.valueOf(volunteer.getV_id())).removeValue();
    }
    public static Task<Void> deleteUser(User user, Donor donor) {
        usersRef.child(user.getUserId()).removeValue();
        return donorsRef.child(String.valueOf(donor.getDonorId())).removeValue();
    }
}
