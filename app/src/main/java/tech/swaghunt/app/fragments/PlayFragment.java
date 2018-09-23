package tech.swaghunt.app.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tech.swaghunt.app.R;
import tech.swaghunt.app.utility.Functions;

public class PlayFragment extends Fragment implements
        View.OnClickListener{

    private Button mCameraButton, mSubmitButton;
    private TextView mTaskCountTextView, mClueText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init_button(view);
        init_text(view);
    }


    private void init_text(View view)    {

        mTaskCountTextView = view.findViewById(R.id.task_count_text);
        mClueText = view.findViewById(R.id.clue_text);
    }


    private void init_button(View view) {
        mCameraButton = view.findViewById(R.id.scan_image_button);
        mSubmitButton = view.findViewById(R.id.submit_button);

        mCameraButton.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())   {

            case R.id.scan_image_button:



                break;
            case R.id.submit_button:
                Functions.popBackStack(getFragmentManager());
                break;
        }
    }
}
