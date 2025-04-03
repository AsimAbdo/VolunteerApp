package edu.aau.projects.volunteerapp.model;

import java.util.HashMap;
import java.util.Map;

public class BankAccount {
    private int accountId;
    private String ownerType;
    private int ownerId;
    private String bankName;
    private String accountNumber;
    private String IBAN;
    private double balance;

    public BankAccount() {
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", accountId);
        map.put("ownerType", ownerType);
        map.put("ownerId", ownerId);
        map.put("bankName", bankName);
        map.put("accountNumber", accountNumber);
        map.put("IBAN", IBAN);
        map.put("balance", balance);

        return map;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int withdraw(double mBalance) {
        if (mBalance > this.balance)
            return 0;
        this.balance = getBalance() - mBalance;
        return 1;
    }
}
