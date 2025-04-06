package edu.aau.projects.volunteerapp.controller.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import edu.aau.projects.volunteerapp.model.BankAccount;
import edu.aau.projects.volunteerapp.model.CashFund;
import edu.aau.projects.volunteerapp.model.Donation;

public class CashFundOperation implements FirebaseAccess {

    private static final String CASH_FUND_COUNTER = "cash_fund_counter";
    private static final String BANK_ACCOUNT_COUNTER = "bank_account_counter";
    private static final String DONATIONS_COUNTER = "donations_counter";

    public static Task<DataSnapshot> insertCashFund(CashFund cashFund){
        return Counter.getCounter(CASH_FUND_COUNTER).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    int cashFundId = 1;
                    if (task.getResult().getValue(Integer.class) != null)
                        cashFundId = task.getResult().getValue(Integer.class);
                    cashFund.setCashFundId(cashFundId);
                    cashFundsRef.child(String.valueOf(cashFundId)).setValue(cashFund);
                }
            }
        });
    }

    public static Task<DataSnapshot> insertBankAccount(BankAccount bankAccount) {
        return Counter.getCounter(BANK_ACCOUNT_COUNTER).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    int accountId = 1;
                    if (task.getResult().getValue(Integer.class) != null)
                        accountId = task.getResult().getValue(Integer.class) + 1;
                    bankAccount.setAccountId(accountId);
                    bankAccountsRef.child(String.valueOf(accountId)).setValue(bankAccount);
                    Counter.updateCounter(BANK_ACCOUNT_COUNTER, accountId);
                }
            }
        });
    }

    public static Query getBankAccounts(int ownerId){
        return bankAccountsRef.orderByChild("ownerId").equalTo(ownerId);
    }

    public static Query getBankAccounts(String ownerType){
        return bankAccountsRef.orderByChild("ownerType").equalTo(ownerType);
    }

    public static Task<Void> updateBankAccount(BankAccount account) {
        return bankAccountsRef.child(String.valueOf(account.getAccountId())).updateChildren(account.toMap());
    }

    public static Task<DataSnapshot> insertDonation(Donation donation) {
        return Counter.getCounter(DONATIONS_COUNTER).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    int donoationId = 1;
                    if (task.getResult().getValue(Integer.class) != null)
                        donoationId = task.getResult().getValue(Integer.class) + 1;
                    donation.setDonationId(donoationId);
                    donationsRef.child(String.valueOf(donation.getDonationId())).setValue(donation);
                    Counter.updateCounter(DONATIONS_COUNTER, donoationId);
                }
            }
        });
    }

    public static Query getDonations(int donorId) {
        return donationsRef.orderByChild("donorId").equalTo(donorId);
    }
}
