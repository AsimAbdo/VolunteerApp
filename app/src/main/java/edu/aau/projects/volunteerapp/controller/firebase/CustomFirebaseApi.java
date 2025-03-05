package edu.aau.projects.volunteerapp.controller.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.Map;

import edu.aau.projects.volunteerapp.model.Admin;
import edu.aau.projects.volunteerapp.model.BankAccount;
import edu.aau.projects.volunteerapp.model.Donor;
import edu.aau.projects.volunteerapp.model.MTask;
import edu.aau.projects.volunteerapp.model.ServiceSeeker;
import edu.aau.projects.volunteerapp.model.User;
import edu.aau.projects.volunteerapp.model.Volunteer;

public class CustomFirebaseApi implements FirebaseAccess {

    public static CustomFirebaseApi api;
    private CustomFirebaseApi() {
    }

    public static CustomFirebaseApi getInstance() {
        if (api == null)
            api = new CustomFirebaseApi();
        return api;
    }

    public Task<AuthResult> createUser(String email, String password){
        return CustomAuthentication.createUser(email, password);
    }
    public Task<Void> sendActivationLink(){
        return CustomAuthentication.sendActivationLink();
    }
    public FirebaseUser getCurrentUser(){
        return CustomAuthentication.getCurrentUser();
    }
    public String getCurrentUserName(){
        return CustomAuthentication.getCurrentUserName();
    }
    public Query checkEmail(String email){
        return usersRef.orderByChild("email").equalTo(email);
    }
    public void logout(){
        CustomAuthentication.logout();
    }
    public Task<AuthResult> validateUser(String email, String password){
        return CustomAuthentication.validateUser(email, password);
    }
    public Task<Void> resetUserPassword(String email){
        return CustomAuthentication.resetUserPassword(email);
    }
    public int handleFirebaseException(Task<AuthResult> task){
        return CustomAuthentication.handleFirebaseException(task);
    }
    public Task<Void> insertUser(User user){
        return InsertUsers.insertUser(user);
    }
    public Task<DataSnapshot> insertVolunteer(Volunteer volunteer){
        return InsertUsers.insertVolunteer(volunteer);
    }

    public Task<DataSnapshot> insertServiceSeeker(ServiceSeeker serviceSeeker){
        return InsertUsers.insertServiceSeeker(serviceSeeker);
    }
    public Task<DataSnapshot> insertDonor(Donor donor){
        return InsertUsers.insertDonor(donor);
    }
    public Task<DataSnapshot> insertAdmin(Admin admin){
        return InsertUsers.insertAdmin(admin);
    }
    public Query getUserInfo(){
        return GetUsers.getUserData();
    }
    public Task<DataSnapshot> createTask(MTask mTask){
        return TaskOperations.createTask(mTask);
    }
    public Query getTasks(int seekerId){
        return TaskOperations.getTasks(seekerId);
    }
    public Query getCurrentTasks(int volunteerId){
        return TaskOperations.getCurrentTasks(volunteerId);
    }

    public Query getTasks(String status){
        return TaskOperations.getTasks(status);
    }

    public Query getUser(){
        return GetUsers.getUser();
    }

    public DatabaseReference getUsers() {
        return GetUsers.getUsers();
    }

    public Task<Void> updateVolunteer(int volunteerId, Map<String, Object> volunteerData){
        return UpdateUsersData.updateVolunteer(volunteerId, volunteerData);
    }

    public Task<Void> updateUser(User user, int roleId){
        return UpdateUsers.updateUser(user, roleId);
    }

    public Task<Void> updateTask(MTask task) {
        return TaskOperations.updateTask(task);
    }

    public Task<DataSnapshot> createBankAccount(BankAccount bankAccount){
        return CashFundOperation.insertBankAccount(bankAccount);
    }

    public Query getBankAccounts(int ownerId){
        return CashFundOperation.getBankAccounts(ownerId);
    }

    public Query getVolunteer(int volunteerId) {
        return GetUsers.getVolunteer(volunteerId);
    }

    public void upgradeToTeamLeader(Volunteer volunteer) {
        UpdateUsers.updateToTeamLeader(volunteer);
    }
}
