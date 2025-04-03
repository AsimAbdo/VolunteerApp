package edu.aau.projects.volunteerapp.controller.uiadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.databinding.CustomSpItemBinding;
import edu.aau.projects.volunteerapp.model.BankAccount;

public class BankAccountSpAdapter extends BaseAdapter {

    List<BankAccount> accounts;
    Context context;
    int resource;

    public BankAccountSpAdapter(List<BankAccount> accounts, Context context) {
        this.accounts = accounts;
        this.context = context;
        this.resource = R.layout.custom_sp_item;
    }

    @Override
    public int getCount() {
        return accounts == null ? 0 : accounts.size();
    }

    @Override
    public BankAccount getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BankAccount account = accounts.get(position);
        View v = convertView == null? LayoutInflater.from(context).inflate(resource, null) : convertView;

        CustomSpItemBinding bin = CustomSpItemBinding.bind(v);
        bin.cSpTvTitle.setText(account.getBankName());
        return bin.getRoot();
    }
}
