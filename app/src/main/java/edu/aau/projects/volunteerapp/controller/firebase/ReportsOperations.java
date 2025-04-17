package edu.aau.projects.volunteerapp.controller.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import edu.aau.projects.volunteerapp.model.Report;

public class ReportsOperations implements FirebaseAccess {
    private static String  reportCounter = "reportCounter";
    public static Task<DataSnapshot> generateReport(Report report){
        return Counter.getCounter(reportCounter).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    int repId = 1;
                    if (task.getResult().getValue(Integer.class) != null){
                        repId = task.getResult().getValue(Integer.class) + 1;
                    }
                    report.setId(repId);
                    reportsRef.child(String.valueOf(report.getId())).setValue(report);
                    Counter.updateCounter(reportCounter, repId);
                }
            }
        });
    }

    public static Query getReports(){
        return reportsRef.orderByKey();
    }
}
