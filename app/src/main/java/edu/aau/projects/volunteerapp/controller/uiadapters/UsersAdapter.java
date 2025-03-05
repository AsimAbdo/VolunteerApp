package edu.aau.projects.volunteerapp.controller.uiadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.databinding.CustomUserItemBinding;
import edu.aau.projects.volunteerapp.model.Volunteer;
import edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad.OnAcceptRejectClickListener;
import edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad.UserDetailsActivity;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder> {

    List<Volunteer> volunteers;
    Context context;
    OnAcceptRejectClickListener listener;

    public UsersAdapter(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    public UsersAdapter() {
    }

    public void setOnItemClickListener(OnAcceptRejectClickListener onItemClickListener){
        this.listener = onItemClickListener;
    }

    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.custom_user_item, null);
        return new UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return volunteers == null? 0 : volunteers.size();
    }

    public class UsersHolder extends RecyclerView.ViewHolder {
        CustomUserItemBinding bin;
        public UsersHolder(@NonNull View itemView) {
            super(itemView);
            bin = CustomUserItemBinding.bind(itemView);
        }

        public void onBind(int position) {
            Volunteer volunteer = volunteers.get(position);

            bin.userTvEmail.setText(volunteer.getUser().getEmail());
            bin.userTvName.setText(volunteer.getUser().getName());
            bin.userTvJobs.setText(String.valueOf(volunteer.getCompletedTasks()));
            bin.userTvRole.setText(volunteer.getUser().getRole());
            bin.userTvSkills.setText(volunteer.toFormattedForm());
            bin.userTvLoc.setText(volunteer.getUser().getAddress());
            bin.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null )
                        listener.onCLick(null, volunteer.getV_id());
                }
            });
        }
    }
}
