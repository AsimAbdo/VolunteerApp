package edu.aau.projects.volunteerapp.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import edu.aau.projects.volunteerapp.R;

public class UiUtils {

    private static AlertDialog dialog;

    public static void createDatePicker(Context context, DatePickerDialog.OnDateSetListener listener){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, year, month, day);
        datePickerDialog.show();
    }
    public static boolean isEmpty(EditText editText){
        return TextUtils.isEmpty(editText.getText());
    }

    public static boolean verifyFields(EditText... fields){
        if (fields.length == 0)
            return false;
        else {
            for (EditText field : fields) {
                if (isEmpty(field))
                    return false;
            }
        }
        return true;
    }

    public static boolean checkEmail(String email){
        return Pattern.compile("^[a-zA-z](\\w|.?){3,}@[a-z]{1,7}\\.[a-z]{3}").matcher(email).matches();
    }

    public static boolean checkName(String name){
        return name.length() > 8;
    }
    public static boolean checkPassword(String password){
        return password.length() >= 6;
    }

    public static void makeToast(String msg, Context context){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void makeToast(int msg, Context context){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showProgressbar(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(R.layout.progress_layout)
                .setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    public static void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    static CustomDialogFragment fragment;
    public static void showDialogFragment(FragmentManager fm, String title, String message, String btnText, boolean showEDitText){
         fragment = CustomDialogFragment.newInstance(
                title,
                message,
                "",
                0,
                showEDitText,
                btnText
        );
        fragment.show(fm, null);
        fragment.setCancelable(false);
    }

    public static String getCurrentDate(){
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
        return df.format(date);
    }

    public static void dismissDialogFragment(){
        if (fragment != null)
            fragment.dismiss();
    }
    public static void showDialogFragment(FragmentManager fm, String title, String message, String btnText, boolean showEDitText, int iconRes){
        CustomDialogFragment fragment = CustomDialogFragment.newInstance(
                title,
                message,
                "",
                iconRes,
                showEDitText,
                btnText
        );
        fragment.show(fm, null);
    }

    public static void pickImage(FragmentActivity activity, ImageView imageView){

    }
}
