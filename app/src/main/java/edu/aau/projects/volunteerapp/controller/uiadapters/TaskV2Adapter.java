package edu.aau.projects.volunteerapp.controller.uiadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.DrawableUtils;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.databinding.CurrentTasksListItemBinding;
import edu.aau.projects.volunteerapp.databinding.CustomTaskItemBinding;
import edu.aau.projects.volunteerapp.databinding.CustomTasksItemBinding;
import edu.aau.projects.volunteerapp.databinding.TaskProvideListItemBinding;
import edu.aau.projects.volunteerapp.model.MTask;
import edu.aau.projects.volunteerapp.view.DashboardScreen.AdminDashborad.OnAcceptRejectClickListener;

public class TaskV2Adapter extends RecyclerView.Adapter<TaskV2Adapter.TaskHolder> {
    public static final String TASK_VIEW = "view";
    public static final String CURRENT_TASK_VIEW = "currentTaskView";
    public static final String TASK_NEED_RESOURCE_VIEW = "taskNeedResourceView";
    private String mTaskView;
    private int showAcceptRejectButtons = 0;
    List<MTask> tasks;
    OnAcceptRejectClickListener listener;

    public TaskV2Adapter(List<MTask> tasks, String mTaskView) {
        this.tasks = tasks;
        this.mTaskView = mTaskView;
    }


    public TaskV2Adapter() {
    }

    public void setShowAcceptRejectButtons(int showAcceptRejectButtons) {
        this.showAcceptRejectButtons = showAcceptRejectButtons;
    }

    public void setListener(OnAcceptRejectClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return mTaskView.equals(TASK_VIEW) ?
                new TaskViewHolder(inflater.inflate(R.layout.custom_tasks_item, null))
                : mTaskView.equals(TASK_VIEW) ?
                    new CurrentTaskViewHolder(inflater.inflate(R.layout.current_tasks_list_item, null))
                :
                    new CurrentTaskViewHolder(inflater.inflate(R.layout.task_provide_list_item, null));
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

    abstract class TaskHolder extends RecyclerView.ViewHolder {

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void onBind(int position);
    }

    class TaskViewHolder extends TaskHolder {
        CustomTasksItemBinding bin;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            bin = CustomTasksItemBinding.bind(itemView);
        }

        public void onBind(int position) {
            MTask task = tasks.get(position);
            bin.taskTitle.setText(task.getDescription().getType());
            bin.taskStatus.setText(task.getStatus());
            bin.taskLocation.setText(task.getDescription().getLocation());
            bin.taskStartDate.setText(task.getStartDate());
            bin.taskEndDate.setText(task.getEndDate());

            if (showAcceptRejectButtons == 0){
                bin.taskAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null)
                            listener.onCLick(task, 1);
                        else
                            throw new RuntimeException("you have to set OnAcceptRejectClickListener");
                    }
                });

                bin.taskReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null)
                            listener.onCLick(task, 0);
                        else
                            throw new RuntimeException("you have to set OnAcceptRejectClickListener");
                    }
                });
            } else if (showAcceptRejectButtons == 1) {
                bin.taskAccept.setVisibility(View.GONE);
                bin.taskReject.setVisibility(View.GONE);
                bin.taskImage.setVisibility(View.GONE);
                bin.taskIvBefore.setVisibility(View.GONE);
                bin.taskIvNext.setVisibility(View.GONE);
            }

//            TODO task item images
        }

        private void showAcceptRejectButtons(String status){
            if (!status.equals(R.string.new_task)){

            }
        }
    }

    class CurrentTaskViewHolder extends TaskHolder {
        CurrentTasksListItemBinding bin;
        public CurrentTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            bin = CurrentTasksListItemBinding.bind(itemView);
        }

        public void onBind(int position) {
            MTask task = tasks.get(position);
            bin.ctTvTitle.setText(task.getDescription().getType());
            bin.ctTvStatus.setText(task.getStatus());
            bin.ctTvLocation.setText(task.getDescription().getLocation());
            bin.ctTvStartDate.setText(task.getStartDate());
            bin.ctTvEndDate.setText(task.getEndDate());

//            TODO task item images
        }
    }

    class TaskNeedResourceHolder extends TaskHolder {
        TaskProvideListItemBinding bin;
        OnProvideButtonClickListener onProvideButtonClickListener;
        public TaskNeedResourceHolder(@NonNull View itemView) {
            super(itemView);
            bin = TaskProvideListItemBinding.bind(itemView);
        }

        public void setOnProvideButtonClickListener(OnProvideButtonClickListener listener) {
            this.onProvideButtonClickListener = listener;
        }

        @Override
        public void onBind(int position) {
            MTask task = tasks.get(position);
            bin.taskTitle.setText(task.getDescription().getType());
            bin.taskStatus.setText(task.getStatus());
            bin.taskStartDate.setText(task.getStartDate());
            bin.taskEndDate.setText(task.getEndDate());
            bin.taskAssignTo.setText(task.getAssignedTo());
            bin.taskAssignToId.setText(String.valueOf(task.getAssignedToId()));

            bin.btnProvide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProvideButtonClickListener != null)
                        onProvideButtonClickListener.onButtonClick(task);
                    else
                        throw new RuntimeException("YOU MUST CALL setOnProvideButtonClickListener() method");
                }
            });
        }

    }
    interface OnProvideButtonClickListener {
        void onButtonClick(MTask task);
    }
}
