package tech.swaghunt.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import tech.swaghunt.app.fragments.PlayFragment;
import tech.swaghunt.app.utility.Functions;

import static tech.swaghunt.app.fragments.CreateTaskFragment.MY_PERMISSIONS_REQUEST_CAMERA;

public class PlayActivity extends AppCompatActivity {

    public static int CAMERA_SCAN_ACTIVITY_REQUEST = 1000, CAMERA_SCAN_ACTIVITY_RESULT = 1001;

    String sampleJSON = "{\n" +
            "\"qr_code\":\"pankaj\",\n" +
            "\"hunt_task\":[\n" +
            "{\n" +
            "\"clue\": \"Is it Monday?\",\n" +
            "\"answer\": \"No\",\n" +
            "\"taskType\": \"text\"\n" +
            "},\n" +
            "{\n" +
            "\"clue\": \"Grizzhacks sponspor who provides app monitoring solution.\",\n" +
            "\"answer\": \"Dynatrace\",\n" +
            "\"taskType\": \"image\"\n" +
            "},\n" +
            "{\n" +
            "\"clue\": \"QR code on Grizzhacks 3 id.\",\n" +
            "\"answer\": \"http://l.ead.me/baxKDS\",\n" +
            "\"taskType\": \"qr\"\n" +
            "}\n" +
            "]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        requestCameraAccess();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERA_SCAN_ACTIVITY_REQUEST &&
                resultCode == CAMERA_SCAN_ACTIVITY_RESULT)  {


            if (PlayFragment.isPlayFragment)    {

                String stringData = data.getStringExtra("BARCODE");

                Toast.makeText(this, stringData, Toast.LENGTH_SHORT).show();

            } else {

                try {

                    JSONObject jsonObject = new JSONObject(sampleJSON);

                    JSONArray jsonArray = jsonObject.getJSONArray("hunt_task");

                    PlayFragment[] playFragments = new PlayFragment[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++)
                        playFragments[i] = PlayFragment.newInstance(jsonArray.getString(i), i, jsonArray.length());

                    for (int i = jsonArray.length() - 1; i >= 0; i--)
                        replaceFragment(playFragments[i]);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void replaceFragment(Fragment fragment) {
        Functions.fragment_replacement(
                getSupportFragmentManager(),
                R.id.relative,
                fragment,
                true,
                false
        );
    }


    public void requestCameraAccess()   {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                MY_PERMISSIONS_REQUEST_CAMERA
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startActivityForResult(
                            new Intent(this, CameraScanActvity.class),
                            CAMERA_SCAN_ACTIVITY_REQUEST
                    );

                } else {

                    Toast.makeText(this, "Camera Access Denied", Toast.LENGTH_LONG).show();
                }
                // other 'case' lines to check for other
                // permissions this app might request
        }
    }
}