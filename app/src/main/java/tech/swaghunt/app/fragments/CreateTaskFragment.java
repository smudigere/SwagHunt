package tech.swaghunt.app.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import tech.swaghunt.app.CreateActivity;
import tech.swaghunt.app.R;

public class CreateTaskFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {

    private CreateActivity mCreateActivity;
    private Spinner mSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_task, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCreateActivity = (CreateActivity) getActivity();

        init_spinner(view);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mCreateActivity.setTaskCount(i + 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

}
