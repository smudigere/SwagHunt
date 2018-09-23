package tech.swaghunt.app;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tech.swaghunt.app.fragments.CreateTaskFragment;
import tech.swaghunt.app.fragments.PhotoPreviewFragment;
import tech.swaghunt.app.fragments.TaskCountInputFragment;
import tech.swaghunt.app.utility.Functions;

import static tech.swaghunt.app.fragments.CreateTaskFragment.MY_PERMISSIONS_REQUEST_CAMERA;

public class CreateActivity extends AppCompatActivity {

    private final int RESULT_LOAD_IMAGE = 100;
    private final int PICK_FROM_GALLERY = 101;

    private int taskCount = 0;
    private CreateTaskFragment[] createTaskFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        replaceFragment(new TaskCountInputFragment());
    }


    private void replaceFragment(Fragment fragment) {
        Functions.fragment_replacement(
                getSupportFragmentManager(),
                R.id.relative,
                fragment,
                true,
                false
        );
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public void onNumberPicked()    {

        createTaskFragments = new CreateTaskFragment[taskCount];

        for (int i = 0; i < taskCount; i++)
            createTaskFragments[i] = CreateTaskFragment.newInstance(i, taskCount);

        for (int i = taskCount - 1; i >= 0; i--)
            replaceFragment(createTaskFragments[i]);
    }

    public void onTaskCreated() {
        Functions.popBackStack(getSupportFragmentManager());
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

                    startActivity(new Intent(this, CameraScanActvity.class));

                } else {

                    Toast.makeText(this, "Camera Access Denied", Toast.LENGTH_LONG).show();
                }
                // other 'case' lines to check for other
                // permissions this app might request
        }
    }


    /* *******************************************************

//Pick picture and preview it.
    public void selectPhotoFromLibrary()    {
        try {

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PICK_FROM_GALLERY);
            } else
                selectPhotoFunctionality();

        } catch (Exception e) {
            Log.v("E", "CC");
        }
    }

    private void selectPhotoFunctionality()  {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*"});
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(Intent.createChooser(intent,"Select Image"), RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
            getImages(data);
    }

    private void getImages(Intent data) {

        try {
            if (data.getClipData() != null) {

                ClipData mClipData = data.getClipData();

                for (int i = 0; i < mClipData.getItemCount(); i++) {

                    try {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();

                        replaceFragment(PhotoPreviewFragment.newInstance(uri));


                    } catch (Exception | Error e)   {
                        Log.v("E", "CC");
                    }
                }
            }

        } catch (Exception e) {
            Log.v("E", "CC");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {

            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectPhotoFunctionality();
                } else {

                    Toast.makeText(this, getString(R.string.Permission_Denied), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }*/
}