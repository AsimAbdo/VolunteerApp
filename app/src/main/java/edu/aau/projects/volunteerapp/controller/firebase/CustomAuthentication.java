package edu.aau.projects.volunteerapp.controller.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.Query;

import edu.aau.projects.volunteerapp.R;

public class CustomAuthentication implements FirebaseAccess {

    public static Task<AuthResult> createUser(String email, String password){
        return FIREBASE_AUTH.createUserWithEmailAndPassword(email, password);
    }

    public static Task<Void> sendActivationLink(){
        return FIREBASE_AUTH.getCurrentUser().sendEmailVerification();
    }

    public static FirebaseUser getCurrentUser(){
        return FIREBASE_AUTH.getCurrentUser();
    }

    public static String getCurrentUserName(){
        String name = "";
        if (getCurrentUser() != null){
            for (UserInfo userInfo:
                    getCurrentUser().getProviderData()) {
                name = userInfo.getDisplayName();
            }
        }
        return name;
    }


    public static void logout(){
        if (getCurrentUser() != null)
            FIREBASE_AUTH.signOut();
    }

    public static Task<AuthResult> validateUser(String email, String password){
        return FIREBASE_AUTH.signInWithEmailAndPassword(email, password);
    }

    public static Task<Void> resetUserPassword(String email){
        return FIREBASE_AUTH.sendPasswordResetEmail(email);
    }

    public static int handleFirebaseException(Task<AuthResult> task){
        int message;
        if (task.getException() instanceof FirebaseAuthInvalidUserException)
            message = R.string.user_exception_message;
        else if (task.getException() instanceof FirebaseAuthUserCollisionException)
            message = R.string.user_collisionException;
        else
            message = R.string.wrong_validation;
        return message;
    }
}
