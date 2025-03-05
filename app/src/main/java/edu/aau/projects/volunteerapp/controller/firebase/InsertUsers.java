package edu.aau.projects.volunteerapp.controller.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import edu.aau.projects.volunteerapp.model.Admin;
import edu.aau.projects.volunteerapp.model.Donor;
import edu.aau.projects.volunteerapp.model.ServiceSeeker;
import edu.aau.projects.volunteerapp.model.User;
import edu.aau.projects.volunteerapp.model.Volunteer;

public class InsertUsers implements FirebaseAccess {

    private static final String TEAM_LEADER_COUNTER = "teamLeaderCounter";
    private static String volunteerCounter = "volunteerCounter";
    private static String serviceSeekerCounter = "serviceSeekerCounter";
    private static String donorCounter = "donorCounter";
    private static String adminCounter = "adminCounter";
    private static String teamLeaderCounter = "donorCounter";

    public static Task<Void> insertUser(User user){
        String userId = CustomAuthentication.getCurrentUser().getUid();
        user.setUserId(userId);
        return usersRef.child(userId).setValue(user);
    }

    public static Task<DataSnapshot> insertVolunteer(Volunteer volunteer){
        return Counter.getCounter(volunteerCounter).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                int volId = 1;
                if (task.getResult().getValue(Integer.class) != null)
                    volId = task.getResult().getValue(Integer.class) + 1;
                volunteer.setV_id(volId);
                volunteersRef.child(String.valueOf(volunteer.getV_id())).setValue(volunteer);
                volunteer.createCashFund();
                CashFundOperation.insertCashFund(volunteer.getCashFund());
                Counter.updateCounter(volunteerCounter, volId);
            }
        });
    }

    public static Task<DataSnapshot> insertServiceSeeker(ServiceSeeker serviceSeeker){
        return Counter.getCounter(serviceSeekerCounter).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                int serviceSeekerId = 1;
                if (task.getResult().getValue(Integer.class) != null)
                    serviceSeekerId = task.getResult().getValue(Integer.class) + 1;
                serviceSeeker.setSeekerId(serviceSeekerId);
                serviceSeekersRef.child(String.valueOf(serviceSeeker.getSeekerId())).setValue(serviceSeeker);
                Counter.updateCounter(serviceSeekerCounter, serviceSeekerId);
            }
        });
    }

    public static Task<DataSnapshot> insertAdmin(Admin admin){
        return Counter.getCounter(adminCounter).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                int adminId = 1;
                if (task.getResult().getValue(Integer.class) != null)
                    adminId = task.getResult().getValue(Integer.class) + 1;
                admin.setAdminId(adminId);
                admin.createCashFund();
                adminsRef.child(String.valueOf(adminId)).setValue(admin);
                CashFundOperation.insertCashFund(admin.getCashFund());
                Counter.updateCounter(adminCounter, adminId);
            }
        });
    }

    public static Task<DataSnapshot> insertDonor(Donor donor){
        return Counter.getCounter(donorCounter).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                int donorId = 1;
                if (task.getResult().getValue(Integer.class) != null)
                    donorId = task.getResult().getValue(Integer.class) + 1;
                donor.setDonorId(donorId);
                donorsRef.child(String.valueOf(donor.getDonorId())).setValue(donor);
                Counter.updateCounter(donorCounter, donorId);
            }
        });

    }

}
