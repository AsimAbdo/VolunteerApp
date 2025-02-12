package edu.aau.projects.volunteerapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BaseActivity extends LogActivity {

    public boolean checkPermission(String permission, Context context){
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissions(Activity activity, int req_code, String... permissions){
        if (permissions.length > 0){
            ActivityCompat.requestPermissions(activity, permissions, req_code);
        }
    }

    public void pushFragment(FragmentActivity activity, Fragment fragment, int container_id, String title){
        FragmentManager fm = activity.getSupportFragmentManager();

        if (fm.findFragmentByTag(title) != null)
            fm.popBackStack(title, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(container_id, fragment, title);
        ft.addToBackStack(title);
        ft.commit();
    }

    public void pushFragment(FragmentActivity activity, Fragment fragment, int container_id, boolean addToBackStack){
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(container_id, fragment);
        if (addToBackStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    public boolean checkInternet(){
        if (checkNetworkAvailable()){
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnectedOrConnecting());
        }
        return false;
    }
    public void makeToast(String msg){
        UiUtils.makeToast(msg, this);
    }
    public void makeToast(int msg){
        UiUtils.makeToast(msg, this);
    }

    private boolean checkNetworkAvailable(){
        boolean mobileNet = false;
        boolean wifiNet = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        for (Network network :
                cm.getAllNetworks()) {
            NetworkInfo networkInfo = cm.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                mobileNet = true;
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                wifiNet = true;
        }
        return mobileNet || wifiNet;
    }
}
