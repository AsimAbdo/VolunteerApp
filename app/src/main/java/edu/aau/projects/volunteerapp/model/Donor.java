package edu.aau.projects.volunteerapp.model;

public class Donor {
    private int donorId;
    private User user;
    private double totalDonations;
    private int donationFrequency;

    public Donor() {
    }

    public Donor(User user){
        this.user = user;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public User getUser() {
        return user;
    }

    public double getTotalDonations() {
        return totalDonations;
    }

    public void setTotalDonations(double totalDonations) {
        this.totalDonations = totalDonations;
    }

    public int getDonationFrequency() {
        return donationFrequency;
    }

    public void setDonationFrequency(int donationFrequency) {
        this.donationFrequency = donationFrequency;
    }
}
