package edu.aau.projects.volunteerapp.view.DashboardScreen;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.controller.firebase.CustomFirebaseApi;
import edu.aau.projects.volunteerapp.controller.firebase.FirebaseAccess;
import edu.aau.projects.volunteerapp.databinding.FragmentProfileBinding;
import edu.aau.projects.volunteerapp.model.Admin;
import edu.aau.projects.volunteerapp.model.Donor;
import edu.aau.projects.volunteerapp.model.ServiceSeeker;
import edu.aau.projects.volunteerapp.model.User;
import edu.aau.projects.volunteerapp.model.Volunteer;
import edu.aau.projects.volunteerapp.utils.BaseActivity;
import edu.aau.projects.volunteerapp.utils.ImageUtils;
import edu.aau.projects.volunteerapp.utils.UiUtils;
import edu.aau.projects.volunteerapp.view.HomeScreen.MainActivity;

public class ProfileFragment extends Fragment {

//    private static final String ROLE_ID_EXTRA = "roleId";
    private static final String ROLE_EXTRA = "role";
    private static final String IMAGE_EXTRA = "image";
    private static final String TAG = "ProfileFragment";
    private int roleId;
    private String role;
    FragmentProfileBinding bin;
    CustomFirebaseApi api;
    User currentUser, original;
    OnPickImageClickListener listener;
    private String image;
    Donor donor;
    Volunteer volunteer;
    ServiceSeeker serviceSeeker;
    Admin admin;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String role, String image) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bun = new Bundle();

        bun.putString(ROLE_EXTRA, role);
        bun.putString(IMAGE_EXTRA, image);
//        bun.putInt(ROLE_ID_EXTRA, roleId);

        fragment.setArguments(bun);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnPickImageClickListener)
            listener = (OnPickImageClickListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = CustomFirebaseApi.getInstance();

        Bundle args = getArguments();
        if (args != null){
            role = args.getString(ROLE_EXTRA, "");
            image = args.getString(IMAGE_EXTRA, "");
//            roleId = args.getInt(ROLE_EXTRA, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bin = FragmentProfileBinding.inflate(inflater);
        if (!image.equals("")){
            bin.profileIvImage.setImageBitmap(ImageUtils.getBitmapImage(image));
        }
        api.getUserInfo().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    User user = null;
                    for (DataSnapshot child : snapshot.getChildren()) {
                        if (role.equals("Service Seeker")) {
                            ServiceSeeker serviceSeeker = child.getValue(ServiceSeeker.class);
                            user = serviceSeeker.getUser();
                            roleId = serviceSeeker.getSeekerId();
                        }
                        else if (role.equals("Admin")) {
                            admin = child.getValue(Admin.class);
                            user = admin.getUser();
                            roleId = admin.getAdminId();
                        }
                        else if (role.equals("Volunteer")) {
                            volunteer = child.getValue(Volunteer.class);
                            user = volunteer.getUser();
                            roleId = volunteer.getV_id();
                        }
                        else if (role.equals("Donor")) {
                            donor = child.getValue(Donor.class);
                            user = donor.getUser();
                            roleId = donor.getDonorId();
                        }
                        else if (role.equals("Team Leader")) {
                            donor = child.getValue(Donor.class);
                            user = donor.getUser();
                            roleId = donor.getDonorId();
                        }
                    }
                    original = user;
                    currentUser = user.copy();
                    putData(currentUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bin.profileIvPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPickImageClick();
            }
        });

        return bin.getRoot();
    }
    private void putData(User user){
        bin.profileEtUsername.setText(user.getName());
        bin.profileEtAddress.setText(user.getAddress());
        bin.profileEtPhone.setText(user.getPhone());
        bin.profileTvDateJoined.setText(user.getDateJoined());
        bin.profileTvRole.setText(user.getRole());
        bin.profileTvEmail.setText(user.getEmail());

        Log.d("TAG", "putData: " + user.getImage());
        if (!user.getImage().equals("") && image.equals(""))
            bin.profileIvImage.setImageBitmap(ImageUtils.getBitmapImage(user.getImage()));

        enableFields(bin.profileIvAdressEdit, bin.profileEtAddress);
        enableFields(bin.profileIvPhoneEdit, bin.profileEtPhone);
        enableFields(bin.profileIvUsernameEdit, bin.profileEtUsername);

        bin.profileBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = bin.profileEtAddress.getText().toString();
                String phone = bin.profileEtPhone.getText().toString();
                String username = bin.profileEtUsername.getText().toString();
                currentUser.setAddress(address);
                currentUser.setPhone(phone);
                currentUser.setName(username);
                if (!image.equals(""))
                    currentUser.setImage(image);
                if (! currentUser.isEqual(original)){
                    UiUtils.showProgressbar(getActivity());
                    api.updateUser(currentUser, roleId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            UiUtils.dismissDialog();
                            if (task.isSuccessful()){
                                UiUtils.makeToast(R.string.op_done, getContext());
                            }
                            else {
                                UiUtils.makeToast(task.getException().getMessage(), getContext());
                            }
                        }
                    });
                }
            }
        });

        bin.profileBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showAlertDialog(getActivity(), R.string.del_title, R.string.del_account_message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProfileAccount();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UiUtils.dismissDialog();
                    }
                });
            }
        });
    }

    private void deleteProfileAccount() {
        UiUtils.showProgressbar(getActivity());
        Log.d(TAG, "deleteProfileAccount: ");

        AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), currentUser.getPassword());
        api.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    api.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "onComplete: ");
                            if (task.isSuccessful()){
                                Log.d(TAG, "onComplete: successful");
                                Task<Void> voidTask = null;
                                if (admin != null)
                                    voidTask = api.deleteUser(currentUser, admin);
                                else if (volunteer != null)
                                    voidTask = api.deleteUser(currentUser, volunteer);
                                else if (serviceSeeker != null)
                                    voidTask = api.deleteUser(currentUser, serviceSeeker);
                                else if (donor != null)
                                    voidTask = api.deleteUser(currentUser, donor);

                                voidTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d(TAG, "onComplete: 2");
                                        UiUtils.dismissDialog();
                                        if (task.isSuccessful()){
                                            Log.d(TAG, "onComplete: 2 successful");
                                            UiUtils.makeToast(R.string.account_deleted, getContext());
                                            getActivity().startActivity(MainActivity.makeIntent(getContext()));
                                            getActivity().finish();
                                        }
                                    }
                                });
                            }
                            else
                                Log.d(TAG, "onComplete: " + task.getException().getMessage());
                        }
                    });
                }
                else {
                    Log.d(TAG, "onComplete: 3" + task.getException().getMessage());
                }
            }
        });

    }

    private void enableFields(ImageView editImage, EditText editText){
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEnable = editText.isEnabled();
                if (isEnable)
                    editImage.setImageResource(R.drawable.ic_edit);
                else
                    editImage.setImageResource(R.drawable.ic_check);
                editText.setEnabled(! isEnable);
            }
        });
    }

    interface OnPickImageClickListener {
        void onPickImageClick();
    }
}