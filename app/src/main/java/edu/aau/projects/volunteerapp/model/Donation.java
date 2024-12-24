package edu.aau.projects.volunteerapp.model;

public class Donation {
    private int donationId;
    private int donorId;
    private String receivingEntity; // type of receiving entity
    private int entityId;
    private double amount;
    private String donationDate;
    private String purpose;

    public Donation() {
    }

    public int getDonationId() {
        return donationId;
    }

    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public String getReceivingEntity() {
        return receivingEntity;
    }

    public void setReceivingEntity(String receivingEntity) {
        this.receivingEntity = receivingEntity;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
