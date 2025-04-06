package edu.aau.projects.volunteerapp.controller.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public interface FirebaseAccess {
    String ROOT = "database";
    String COUNTERS_TB_NAME = "counters";
    String USERS_TB_NAME = "Users";
    String VOLUNTEERS_TB_NAME = "Volunteer";
    String ADMINS_TB_NAME = "admins";
    String TEAM_LEADERS_TB_NAME = "Volunteer";
    String VOLUNTEER_GROUPS_TB_NAME = "VolunteerGroups";
    String DONORS_TB_NAME = "Donors";
    String DONATIONS_TB_NAME = "Donations";
    String CASH_FUNDS_TB_NAME = "CashFunds";
    String GROUP_MEMBERS_TB_NAME = "Groups";
    String MESSAGES_TB_NAME = "Messages";
    String SERVICE_SEEKERS_TB_NAME = "ServiceSeekers";
    String TASKS_TB_NAME = "Tasks";
    String WAREHOUSES_TB_NAME = "Warehouses";
    String WAREHOUSE_ITEMS_TB_NAME = "WarehouseItems";
    String BANK_ACCOUNTS_TB_NAME = "BankAccounts";

    FirebaseAuth FIREBASE_AUTH = FirebaseAuth.getInstance();
    FirebaseDatabase FIREBASE_DATABASE = FirebaseDatabase.getInstance();
    DatabaseReference DATABASE_REFERENCE = FIREBASE_DATABASE.getReference(ROOT);
    DatabaseReference countersRef = DATABASE_REFERENCE.child(COUNTERS_TB_NAME);
    DatabaseReference usersRef = DATABASE_REFERENCE.child(USERS_TB_NAME);
    DatabaseReference volunteersRef = DATABASE_REFERENCE.child(VOLUNTEERS_TB_NAME);
    DatabaseReference serviceSeekersRef = DATABASE_REFERENCE.child(SERVICE_SEEKERS_TB_NAME);
    DatabaseReference donorsRef = DATABASE_REFERENCE.child(DONORS_TB_NAME);
    DatabaseReference adminsRef = DATABASE_REFERENCE.child(ADMINS_TB_NAME);
    DatabaseReference teamLeadersRef = DATABASE_REFERENCE.child(TEAM_LEADERS_TB_NAME);
    DatabaseReference tasksRef = DATABASE_REFERENCE.child(TASKS_TB_NAME);
    DatabaseReference cashFundsRef = DATABASE_REFERENCE.child(CASH_FUNDS_TB_NAME);
    DatabaseReference bankAccountsRef = DATABASE_REFERENCE.child(BANK_ACCOUNTS_TB_NAME);
    DatabaseReference donationsRef = DATABASE_REFERENCE.child(DONATIONS_TB_NAME);


}
