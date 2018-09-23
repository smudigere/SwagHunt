package tech.swaghunt.app.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import tech.swaghunt.app.PlayActivity;
import tech.swaghunt.app.R;
import tech.swaghunt.app.utility.Functions;

public class PlayFragment extends Fragment implements
        View.OnClickListener{

    public static boolean isPlayFragment;

    public static PlayFragment newInstance(String json, int taskCount, int totalTaskCount) {

        PlayFragment fragment = new PlayFragment();
        fragment.json = json;
        fragment.taskCount = taskCount + 1;
        fragment.totalTaskCount = totalTaskCount;

        return fragment;
    }

    private String json;
    private int taskCount, totalTaskCount;

    private PlayActivity mPlayActivity;

    private Button mCameraButton, mSubmitButton;
    private TextView mTaskCountTextView, mClueText;

    private TextInputLayout mTextInputLayout0;
    private TextInputEditText mClueEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPlayActivity = (PlayActivity) getActivity();
        isPlayFragment = true;

        init_button(view);
        init_text(view);
        init_input(view);

        try {
            mCameraButton.setVisibility(View.INVISIBLE);
            mTextInputLayout0.setVisibility(View.INVISIBLE);

            JSONObject jsonObject = new JSONObject(json);

            switch (jsonObject.getString("taskType"))   {

                case "text":
                    mTextInputLayout0.setVisibility(View.VISIBLE);
                    break;
                case "image":
                    mCameraButton.setVisibility(View.VISIBLE);
                    mCameraButton.setText("Open Image to Take Picture");

                    mCameraButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            captureImage();
                        }
                    });

                    break;
                case "qr":
                    mCameraButton.setVisibility(View.VISIBLE);
                    mCameraButton.setText("Scan QR Code");


                    mCameraButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPlayActivity.requestCameraAccess();
                        }
                    });

                    break;
            }


        } catch (Exception e)   {
            e.printStackTrace();
        }
    }

    private void init_input(View view)  {
        mTextInputLayout0 = view.findViewById(R.id.text_input_layout0);
        mClueEditText = view.findViewById(R.id.clue_input_edit_text);
    }

    private void init_text(View view)    {

        mTaskCountTextView = view.findViewById(R.id.task_count_text);
        @SuppressLint("DefaultLocale")
        String taskCountMessage = String.format("Task %d out of %d", taskCount, totalTaskCount);
        mTaskCountTextView.setText(taskCountMessage);


        mClueText = view.findViewById(R.id.clue_text);
    }


    private void init_button(View view) {
        mCameraButton = view.findViewById(R.id.scan_image_button);
        mSubmitButton = view.findViewById(R.id.submit_button);

        mCameraButton.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
    }


    private void captureImage() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(
                intent,
                0
        );
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            mPlayActivity.replaceFragment(PhotoPreviewFragment.newInstance(bitmap));

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())   {

            case R.id.submit_button:
                Functions.popBackStack(getFragmentManager());
                break;
        }
    }
}
