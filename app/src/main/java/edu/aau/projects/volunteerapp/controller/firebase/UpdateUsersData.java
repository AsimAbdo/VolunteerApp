package edu.aau.projects.volunteerapp.controller.firebase;

import com.google.android.gms.tasks.Task;

import java.util.Map;

public class UpdateUsersData implements FirebaseAccess {
    public static Task<Void> updateVolunteer(int volunteerId, Map<String, Object> volData){
        return volunteersRef.child(String.valueOf(volunteerId)).updateChildren(volData);
    }
}
