package edu.aau.projects.volunteerapp.controller.uiadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.databinding.CustomReportItemBinding;
import edu.aau.projects.volunteerapp.model.Report;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {
    List<Report> reports;

    public ReportAdapter(List<Report> reports) {
        this.reports = reports;
    }

    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_report_item, null);
        return new ReportHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return reports == null ? 0 : reports.size();
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
        notifyDataSetChanged();
    }

    public class ReportHolder extends RecyclerView.ViewHolder {
        CustomReportItemBinding bin;
        public ReportHolder(@NonNull View itemView) {
            super(itemView);
            bin = CustomReportItemBinding.bind(itemView);
        }

        void onBind(int pos){
            Report report = reports.get(pos);
            bin.reportTitle.setText(R.string.report);
            bin.reportTitle.setText(bin.reportTitle.getText().toString() + (pos + 1));
            bin.reportAuther.setText(report.getAuther().split(" ")[0]);
            bin.reportDate.setText(report.getDate());
            bin.reportDescription.setText(report.getDescription());
        }
    }
}
