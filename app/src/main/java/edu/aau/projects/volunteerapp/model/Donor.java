package edu.aau.projects.volunteerapp.model;

public class Donor implements CashFundHolder{
    private int donorId;
    private User user;
    private double totalDonations;
    private int donationFrequency;
    private CashFund cashFund;

    public Donor() {
        cashFund = new CashFund();
    }

    public Donor(User user){
        this();
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

    public void setUser(User user) {
        this.user = user;
    }

    public CashFund getCashFund() {
        return cashFund;
    }

    public void createCashFund() {
        this.cashFund = getCashFundData();
    }

    @Override
    public CashFund getCashFundData() {
        CashFund cashFund = new CashFund();
        cashFund.setBalance(0.0);
        cashFund.setFundName(this.getUser().getName().concat(" Cash Fund"));
        cashFund.setOwnerType("Individual");
        cashFund.setOwnerId(getDonorId());
        return cashFund;
    }

    public void setCashFund(CashFund cashFund) {
        this.cashFund = cashFund;
    }
}
