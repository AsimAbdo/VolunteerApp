package edu.aau.projects.volunteerapp.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;
    private String address;
    private String image;
    private String dateJoined;

    public User() {
        this.name = "";
        this.password = "";
        this.email = "";
        this.dateJoined = "";
        this.role = "";
        this.image = "";
        this.address = "";
        this.phone = "";
    }

    public User(String name, String email, String phone, String password, String role, String address, String dateJoined, String image) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.address = address;
        this.dateJoined = dateJoined;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("address", address);
        map.put("name", name);
        map.put("password", password);
        map.put("role", role);
        map.put("email", email);
        map.put("userId", userId);
        map.put("image", image);
        map.put("phone", phone);
        map.put("dateJoined", dateJoined);
        return map;
    }

    public boolean isEqual(User user) {
        return name.equals(user.getName())
                && address.equals(user.getAddress())
                && phone.equals(user.getPhone())
                && image.equals(user.getImage());
    }

    public User copy() {
        User user = new User();
        user.setUserId(userId);
        user.setRole(role);
        user.setPhone(phone);
        user.setEmail(email);
        user.setName(name);
        user.setAddress(address);
        user.setDateJoined(dateJoined);
        user.setImage(image);
        user.setPassword(password);
        return user;
    }
}
