package edu.aau.projects.volunteerapp.controller.uiadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.databinding.CustomDonationItemBinding;
import edu.aau.projects.volunteerapp.model.Donation;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationHolder> {

    List<Donation> donations;

    public DonationAdapter(List<Donation> donations) {
        this.donations = donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_donation_item, null);
        return new DonationHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return donations == null? 0 : donations.size();
    }

    public class DonationHolder extends RecyclerView.ViewHolder {
        CustomDonationItemBinding bin;
        public DonationHolder(@NonNull View itemView) {
            super(itemView);
            bin = CustomDonationItemBinding.bind(itemView);
        }

        void onBind(int pos){
            Donation donation = donations.get(pos);
            bin.donTvDate.setText(donation.getDonationDate());
            bin.donTvAmount.setText(String.valueOf(donation.getAmount()));
            bin.donTvReceivingEntity.setText(donation.getReceivingEntity());
        }
    }
}
