package edu.aau.projects.volunteerapp.model;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;
    private String address;
    private String dateJoined;
    private String email_password;

    public User() {
        this.name = "";
        this.password = "";
        this.email = "";
        this.dateJoined = "";
        this.role = "";
        this.address = "";
        this.phone = "";
        this.email_password = "";
    }

    public User(String name, String email, String phone, String password, String role, String address, String dateJoined) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.address = address;
        this.dateJoined = dateJoined;
        this.email_password = email + "_" + password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail_password() {
        return email_password;
    }
}
