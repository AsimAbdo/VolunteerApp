package edu.aau.projects.volunteerapp.controller.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class Counter implements FirebaseAccess{
    public static Task<DataSnapshot> getCounter(String counterName){
        return countersRef.child(counterName).get();
    }

    public static Task<Void> updateCounter(String name, int value){
        return countersRef.child(name).setValue(value);
    }
}
