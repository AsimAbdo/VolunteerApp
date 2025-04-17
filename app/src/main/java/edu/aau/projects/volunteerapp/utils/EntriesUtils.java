package edu.aau.projects.volunteerapp.utils;

public class EntriesUtils {
    private final static String[] roleList = {"Select a Role", "Service Seeker", "Volunteer", "Donor", "Admin"};
    private final static String[] bankList = {"Omdurman Bank", "Faisal Bank", "Al Khortoum Bank", "Other"};
    private final static String[] statusList = {"Not verified yet", "Not taken yet", "Need resources", "Finished Tasks"};
    private final static String statusNotVer = "Not verified yet";
    private final static String statusNotTa = "Not taken yet";
    private final static String statusNeed = "Need resources";
    private final static String statusFini = "Finished Tasks";
    private final static String admin = "Admin";
    private final static String reject = "reject";
    private final static String serviceSeeker = "Service Seeker";
    private final static String volunteer = "Volunteer";
    private final static String donor = "Donor";

    public static String[] getRoleList() {
        return roleList;
    }

    public static String getAdmin() {
        return admin;
    }

    public static String getDonor() {
        return donor;
    }

    public static String getServiceSeeker() {
        return serviceSeeker;
    }

    public static String getVolunteer() {
        return volunteer;
    }

    public static String[] getBankList() {
        return bankList;
    }

    public static String[] getStatusList() {
        return statusList;
    }

    public static String getReject() {
        return reject;
    }
}
