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
import edu.aau.projects.volunteerapp.databinding.CustomDayItemBinding;
import edu.aau.projects.volunteerapp.model.CustomDay;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayHolder> {
    List<CustomDay> days;

    public DayAdapter(String availability) {
        this.days = CustomDay.getDays(availability);
    }

    public DayAdapter() {
    }

    public List<CustomDay> getDays() {
        return days;
    }

    public void setDays(List<CustomDay> days) {
        this.days = days;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_day_item, null);
        return new DayHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DayHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return days == null? 0 : days.size();
    }

    public class DayHolder extends RecyclerView.ViewHolder {
        CustomDayItemBinding bin;
        public DayHolder(@NonNull View itemView) {
            super(itemView);
            bin = CustomDayItemBinding.bind(itemView);
        }

        public void onBind(int position) {
            CustomDay day = days.get(position);
            bin.dayCbTitle.setText(day.getDayName());
            bin.dayCbTitle.setChecked(day.isChecked());

            bin.dayCbTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    days.get(position).setChecked(isChecked);
                }
            });
        }
    }
}
