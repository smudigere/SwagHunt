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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tech.swaghunt.app.CreateActivity;
import tech.swaghunt.app.R;
import tech.swaghunt.app.utility.Functions;

public class TaskCountInputFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {

    private CreateActivity mCreateActivity;
    private Spinner mSpinner;
    private Button mSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_count_input, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCreateActivity = (CreateActivity) getActivity();

        init_spinner(view);
        init_button(view);
    }


    private void init_button(View view) {

        mSubmit = view.findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.popBackStack(getFragmentManager());

                mCreateActivity.onNumberPicked();
            }
        });
    }


    private void init_spinner(View view) {

        // Spinner element
        mSpinner = view.findViewById(R.id.spinner);

        // Spinner click listener
        mSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();

        for (int i = 1; i <= 10; i++)
            categories.add(String.valueOf(i));

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