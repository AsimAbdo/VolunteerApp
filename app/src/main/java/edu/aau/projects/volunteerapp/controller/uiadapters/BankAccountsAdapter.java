package edu.aau.projects.volunteerapp.controller.uiadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.databinding.CustomBankaccountItemBinding;
import edu.aau.projects.volunteerapp.model.BankAccount;

public class BankAccountsAdapter extends RecyclerView.Adapter<BankAccountsAdapter.BankAccountHolder> {
    List<BankAccount> bankAccounts, checkedAccounts = new ArrayList<>();
    boolean showCheckButton = false;

    public BankAccountsAdapter(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public BankAccountsAdapter(List<BankAccount> bankAccounts, boolean showCheckButton) {
        this.bankAccounts = bankAccounts;
        this.showCheckButton = showCheckButton;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
        notifyDataSetChanged();
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

    public List<BankAccount> getCheckedAccounts() {
        return checkedAccounts;
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
            bin.acItemTvIBAN.setText(bin.acItemTvIBAN.getText() + account.getIBAN());
            bin.acItemTvBankName.setText(account.getBankName());
            bin.acItemTvBalance.setText(String.valueOf(account.getBalance()));
            bin.acItemTvAccountNumber.setText(bin.acItemTvAccountNumber.getText() + account.getAccountNumber());
            if (showCheckButton){
                {
                    bin.acItemCbCheck.setVisibility(View.VISIBLE);
                    bin.acItemIvDelete.setVisibility(View.GONE);
                }

                bin.acItemCbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            checkedAccounts.add(account);
                        }
                        else {
                            checkedAccounts.remove(account);
                        }
                    }
                });

                bin.acItemIvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO on delete bankAccount
                    }
                });
            }
        }
    }
}
