package edu.aau.projects.volunteerapp.controller.uiadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.model.CashFund;

public class CashFundAdapter extends RecyclerView.Adapter<CashFundAdapter.CashFundHolder> {
    List<CashFund> cashFundList;

    public CashFundAdapter(List<CashFund> cashFundList) {
        this.cashFundList = cashFundList;
    }

    @NonNull
    @Override
    public CashFundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_provide_list_item, null);//TODO change the layout
        return new CashFundHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CashFundHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return cashFundList == null ? 0 : cashFundList.size();
    }

    public class CashFundHolder extends RecyclerView.ViewHolder {
        public CashFundHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(int position) {

        }
    }
}
