package edu.aau.projects.volunteerapp.controller.uiadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.databinding.CustomTaskItemBinding;
import edu.aau.projects.volunteerapp.model.MTask;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    List<MTask> tasks;

    public TaskAdapter(List<MTask> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_task_item, null);
        return new TaskHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return tasks == null? 0 : tasks.size();
    }

    public void setTasks(List<MTask> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        CustomTaskItemBinding bin;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            bin = CustomTaskItemBinding.bind(itemView);
        }

        public void onBind(int position) {
            MTask task = tasks.get(position);
            bin.taskTitle.setText(task.getDescription().getType());
            bin.taskStatus.setText(task.getStatus());
            bin.taskStartDate.setText(task.getStartDate());
            bin.taskEndDate.setText(task.getEndDate());
        }
    }
}
