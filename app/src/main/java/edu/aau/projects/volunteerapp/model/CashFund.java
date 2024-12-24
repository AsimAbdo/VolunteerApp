package edu.aau.projects.volunteerapp.model;

public class CashFund {
    private int cashFundId;
    private String ownerType;
    private int ownerId;
    private String fundName;
    private double balance;

    public CashFund() {
    }

    public int getCashFundId() {
        return cashFundId;
    }

    public void setCashFundId(int cashFundId) {
        this.cashFundId = cashFundId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
