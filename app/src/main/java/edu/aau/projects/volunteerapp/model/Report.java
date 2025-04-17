package edu.aau.projects.volunteerapp.model;

import edu.aau.projects.volunteerapp.utils.UiUtils;

public class Report {
    int id;
    private String description, auther, date;

    public Report(int id, String description, String auther) {
        this(description, auther);
        this.id = id;
    }

    public Report() {
        this.date = UiUtils.getCurrentDate();
    }

    public Report(String description, String auther) {
        this();
        this.auther = auther;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }
}
