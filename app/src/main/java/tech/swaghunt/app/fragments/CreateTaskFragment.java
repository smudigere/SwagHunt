package tech.swaghunt.app.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tech.swaghunt.app.CameraScanActvity;
import tech.swaghunt.app.CreateActivity;
import tech.swaghunt.app.R;

public class CreateTaskFragment extends Fragment implements
        AdapterView.OnItemSelectedListener,
        View.OnClickListener{

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;

    private CreateActivity mCreateActivity;
    private Spinner mSpinner;
    private TextView mTaskCountTextView;
    private Button mSubmitButton, mScanQrButton;
    private TextInputLayout mTextInputLayout;
    private TextInputEditText mClueEditText;

    private int taskCounter, totalTaskCount;

    public static CreateTaskFragment newInstance(int taskCounter, int totalTaskCount)   {
        CreateTaskFragment createTaskFragment = new CreateTaskFragment();
        createTaskFragment.taskCounter = taskCounter + 1;
        createTaskFragment.totalTaskCount = totalTaskCount;

        return createTaskFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_task, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCreateActivity = (CreateActivity) getActivity();

        init_ui(view);

    }


    private void init_ui(View view) {
        init_spinner(view);
        init_textView(view);
        init_button(view);
        init_text_input_layout(view);
        init_clue_input_edit_text(view);
    }


    private void init_spinner(View view) {

        // Spinner element
        mSpinner = view.findViewById(R.id.spinner);

        // Spinner click listener
        mSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();

        categories.add("Text");
        categories.add("Image");
        categories.add("QR Code");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mCreateActivity, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mSpinner.setAdapter(dataAdapter);
    }

    private void init_textView(View view)    {

        mTaskCountTextView = view.findViewById(R.id.task_count_text);

        @SuppressLint("DefaultLocale")
        String taskCountMessage = String.format("Task %d out of %d", taskCounter, totalTaskCount);

        mTaskCountTextView.setText(taskCountMessage);
        mTaskCountTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));


    }

    private void init_button(View view) {

        mSubmitButton = view.findViewById(R.id.submit_button);
        mScanQrButton = view.findViewById(R.id.scan_qr_button);

        mSubmitButton.setOnClickListener(this);
        mScanQrButton.setOnClickListener(this);
    }

    private void init_text_input_layout(View view)  {
        mTextInputLayout = view.findViewById(R.id.text_input_layout);
    }

    private void init_clue_input_edit_text(View view)   {
        mClueEditText = view.findViewById(R.id.clue_input_edit_text);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        mTextInputLayout.setVisibility(View.INVISIBLE);
        mScanQrButton.setVisibility(View.INVISIBLE);

        switch (i)  {
            case 0:
                mTextInputLayout.setVisibility(View.VISIBLE);
                mClueEditText.setHint(R.string.Enter_Clue);
                break;
            case 1:
                mTextInputLayout.setVisibility(View.VISIBLE);
                mClueEditText.setHint(R.string.Enter_the_name_of_the_Object);
                break;
            case 2:
                mScanQrButton.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}


    @Override
    public void onClick(View view) {

        switch (view.getId())   {

            case R.id.submit_button:
                mCreateActivity.onTaskCreated();
                break;
            //case R.id.select_photo_button:
                //mCreateActivity.selectPhotoFromLibrary();
            //    break;
            case R.id.scan_qr_button:

                mCreateActivity.requestCameraAccess();

                break;
        }

    }

}