package edu.aau.projects.volunteerapp.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.databinding.FragmentCustomDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "param1";
    private static final String ARG_MESSAGE = "param2";
    private static final String ARG_CONTENT = "param3";
    private static final String ARG_ICON_RESOURCE = "param4";
    private static final String ARG_SHOW_EDIT_TEXT = "param5";
    private static final String ARG_BUTTON_TEXT = "param6";

    private String title;
    private String message;
    private boolean showEDitText;
    private String btnText;
    private String content;
    private int iconResource;

    private OnDialogButtonPressedListener listener;

    FragmentCustomDialogBinding bin;
    public CustomDialogFragment() {
        // Required empty public constructor
    }

    public static CustomDialogFragment newInstance(
            String title,
            String message,
            String content,
            int imageRes,
            boolean showEDitText,
            String btnText
    ) {
        CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putString(ARG_CONTENT, content);
        args.putString(ARG_BUTTON_TEXT, btnText);
        args.putInt(ARG_ICON_RESOURCE, imageRes);
        args.putBoolean(ARG_SHOW_EDIT_TEXT, showEDitText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogButtonPressedListener)
            listener = (OnDialogButtonPressedListener) context;
        else
            throw new RuntimeException("YOU HAVE TO IMPLEMENT OnDialogButtonPressedListener in your Activity");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            message = getArguments().getString(ARG_MESSAGE);
            content = getArguments().getString(ARG_CONTENT);
            iconResource = getArguments().getInt(ARG_ICON_RESOURCE);
            showEDitText = getArguments().getBoolean(ARG_SHOW_EDIT_TEXT);
            btnText = getArguments().getString(ARG_BUTTON_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bin = FragmentCustomDialogBinding.inflate(getLayoutInflater());

        bin.cfBtn.setText(btnText);
        bin.cfTvMessage.setText(message);
        bin.cfTvTitle.setText(title);
        if (iconResource != 0)
            bin.cfIvIcon.setImageResource(iconResource);
        if (showEDitText)
            bin.cfEtEditText.setVisibility(View.VISIBLE);
        bin.cfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showEDitText){
                    if (!UiUtils.verifyFields(bin.cfEtEditText))
                        UiUtils.makeToast(R.string.empty_fields, getContext());
                    else
                        listener.onDialogButtonPressedClick(bin.cfEtEditText.getText().toString());
                }
            }
        });

        return bin.getRoot();
    }

    public interface OnDialogButtonPressedListener {
        void onDialogButtonPressedClick(String text);
    }
}