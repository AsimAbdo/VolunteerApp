package edu.aau.projects.volunteerapp.model;

public class TaskDescription {

    private String type;
    private String location;
    private String description;

    private double amount;

    public TaskDescription() {
        this.type = "";
        this.location = "";
        this.description = "";
    }

    public TaskDescription(String type, String location, String description) {
        this.type = type;
        this.location = location;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public double getAmount() {
//        return amount;
//    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
