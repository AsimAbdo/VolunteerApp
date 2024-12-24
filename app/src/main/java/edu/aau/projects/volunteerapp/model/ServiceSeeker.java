package edu.aau.projects.volunteerapp.model;

import java.io.Serializable;

public class ServiceSeeker implements Serializable {
    private int seekerId;
    private User user;
    private String serviceType;
    private String urgency;
    private String requestDate;
    private String requestStatus;

    public ServiceSeeker() {
        this.serviceType = "";
        this.requestDate = "";
        this.requestStatus = "";
        this.urgency = "";
        this.user = new User();
    }


    public ServiceSeeker(User user){
        this.user = user;
    }



    public int getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(int seekerId) {
        this.seekerId = seekerId;
    }

    public User getUser() {
        return user;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
