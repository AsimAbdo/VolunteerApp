package edu.aau.projects.volunteerapp.model;

public class Admin {
    private int adminId;
    private User user;

    public Admin() {
        user = new User();
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
