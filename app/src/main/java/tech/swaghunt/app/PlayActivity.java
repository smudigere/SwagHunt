package tech.swaghunt.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlayActivity extends AppCompatActivity {

    public static int CAMERA_SCAN_ACTIVITY_REQUEST = 1000, CAMERA_SCAN_ACTIVITY_RESULT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        startActivityForResult(
                new Intent(this, CameraScanActvity.class),
                CAMERA_SCAN_ACTIVITY_REQUEST
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERA_SCAN_ACTIVITY_REQUEST &&
                resultCode == CAMERA_SCAN_ACTIVITY_RESULT)  {



        }
    }
}