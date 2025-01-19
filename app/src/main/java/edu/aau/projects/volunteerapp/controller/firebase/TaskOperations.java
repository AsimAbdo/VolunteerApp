package edu.aau.projects.volunteerapp.controller.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import edu.aau.projects.volunteerapp.model.MTask;

public class TaskOperations implements FirebaseAccess {
    private static String taskCounter = "taskCounter";
    public static Task<DataSnapshot> createTask(MTask mTask){
        return Counter.getCounter(taskCounter).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    int taskId = 1;
                    if (task.getResult().getValue(Integer.class) != null){
                        taskId = task.getResult().getValue(Integer.class) + 1;
                    }
                    mTask.setTaskId(taskId);
                    insertTask(mTask);
                    Counter.updateCounter(taskCounter,taskId );
                }
            }
        });
    }

    private static Task<Void> insertTask(MTask task){
        return tasksRef.child(String.valueOf(task.getTaskId())).setValue(task);
    }

    public static Query getTasks(int seekerId){
        return tasksRef.orderByChild("seekerId").equalTo(seekerId);
    }

    public static Query getCurrentTasks(int volunteerId){
        return tasksRef.orderByChild("assignedToId").equalTo(volunteerId);
    }

    public static Query getTasks(String status) {
        return status != null && !status.equals("") ? tasksRef.orderByChild("status").equalTo(status): tasksRef.orderByKey();
    }

    public static Task<Void> updateTask(MTask task) {
        return tasksRef.child(String.valueOf(task.getTaskId())).updateChildren(task.toMap());
    }
}
