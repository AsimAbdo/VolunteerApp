package edu.aau.projects.volunteerapp.model;

import java.io.Serializable;

public class TaskDescription implements Serializable {

    private String type;
    private String location;
    private String description;

    private double amount;
    private double providedAmount;

    public TaskDescription() {
        this.type = "";
        this.location = "";
        this.description = "";
        this.amount = 0;
        this.providedAmount = 0;
    }

    public TaskDescription(String type, String location, String description) {
        this();
        this.type = type;
        this.location = location;
        this.description = description;
    }

    public TaskDescription(String type, String location, String description, double amount) {
        this(type, location, description);
        this.amount = amount;
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

    public double getAmount() {
        return amount;
    }

    public double getProvidedAmount() {
        return providedAmount;
    }

    public void setProvidedAmount(double providedAmount) {
        this.providedAmount = providedAmount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
