package edu.aau.projects.volunteerapp.controller.uiadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.databinding.CustomBankaccountItemBinding;
import edu.aau.projects.volunteerapp.model.BankAccount;

public class BankAccountsAdapter extends RecyclerView.Adapter<BankAccountsAdapter.BankAccountHolder> {
    List<BankAccount> bankAccounts;

    public BankAccountsAdapter(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    @NonNull
    @Override
    public BankAccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_bankaccount_item, null);
        return new BankAccountHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BankAccountHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return bankAccounts == null ? 0 : bankAccounts.size();
    }

    public class BankAccountHolder extends RecyclerView.ViewHolder {
        CustomBankaccountItemBinding bin;
        public BankAccountHolder(@NonNull View itemView) {
            super(itemView);
            bin = CustomBankaccountItemBinding.bind(itemView);
        }

        public void onBind(int position) {
            BankAccount account = bankAccounts.get(position);
            bin.acItemTvIBAN.setText(account.getIBAN());
            bin.acItemTvBankName.setText(account.getBankName());
            bin.acItemTvBalance.setText(String.valueOf(account.getBalance()));
            bin.acItemTvAccountNumber.setText(account.getAccountNumber());
        }
    }
}
