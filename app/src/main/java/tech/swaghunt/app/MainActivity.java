package tech.swaghunt.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import tech.swaghunt.app.models.Hunt;
import tech.swaghunt.app.models.HuntTask;
import tech.swaghunt.app.models.Image;
import tech.swaghunt.app.models.Player;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener{
    private static final String TAG = "MAIN";
    private DatabaseReference mDatabase;
    private StorageReference storageReference;
    private Button mPlay, mCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        List<Image> images = new ArrayList<>();
        images.add(new Image("img1Ref", "url"));
        images.add(new Image("img2Ref", "url"));

        List<String> texts = new ArrayList<>();
        texts.add("txt1");
        texts.add("txt2");

        List<String> qrCodes = new ArrayList<>();
        qrCodes.add("qrCode1");
        qrCodes.add("qrCode2");

        List<String> tasksIDsCompleted = new ArrayList<>();
        tasksIDsCompleted.add("taskId1");
        tasksIDsCompleted.add("taskID2");

        List<HuntTask> tasks = new ArrayList<HuntTask>();
        tasks.add(new HuntTask("clue", "clueAnswer", new Image("imgName", "imgUrl"), "text", "qrCodes", "taskType", "huntId"));

        createPlayer("playerName 2", "huntIDBeingPlayed", true,
                images, texts, qrCodes, tasksIDsCompleted);

        createHunt("grizzhunt 2", tasks, "qrCode");
        init_ui();
    }

    void imageDetection(){
        FirebaseVisionLabelDetectorOptions options =
                new FirebaseVisionLabelDetectorOptions.Builder()
                        .setConfidenceThreshold(0.8f)
                        .build();
        android.media.Image mediaImage = null;
        try {
            FirebaseVisionImage image = FirebaseVisionImage.fromMediaImage(mediaImage, getRotationCompensation("cameraID", MainActivity.this, this));
            FirebaseVisionLabelDetector detector = FirebaseVision.getInstance()
                    .getVisionLabelDetector();

            Task<List<FirebaseVisionLabel>> result =
                    detector.detectInImage(image)
                            .addOnSuccessListener(
                                    new OnSuccessListener<List<FirebaseVisionLabel>>() {
                                        @Override
                                        public void onSuccess(List<FirebaseVisionLabel> labels) {
                                            // HuntTask completed successfully
                                            for (FirebaseVisionLabel label: labels) {
                                                String text = label.getLabel();
                                                String entityId = label.getEntityId();
                                                float confidence = label.getConfidence();
                                            }
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // HuntTask failed with an exception
                                            // ...
                                        }
                                    });

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    /**
     * Get the angle by which an image must be rotated given the device's current
     * orientation.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, Context context)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        // On most devices, the sensor orientation is 90 degrees, but for some
        // devices it is 270 degrees. For devices with a sensor orientation of
        // 270, rotate the image an additional 180 ((270 + 270) % 360) degrees.
        CameraManager cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);
        rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360;

        // Return the corresponding FirebaseVisionImageMetadata rotation value.
        int result;
        switch (rotationCompensation) {
            case 0:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                break;
            case 90:
                result = FirebaseVisionImageMetadata.ROTATION_90;
                break;
            case 180:
                result = FirebaseVisionImageMetadata.ROTATION_180;
                break;
            case 270:
                result = FirebaseVisionImageMetadata.ROTATION_270;
                break;
            default:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                Log.e(TAG, "Bad rotation value: " + rotationCompensation);
        }
        return result;
    }

    private void createPlayer(String name, String huntPlaying,
                              Boolean hasWon, List<Image> images, List<String> text,
                                List<String> qrCodes, List<String> tasksIDsCompleted) {
        Player player = new Player(name, huntPlaying, hasWon, images, text, qrCodes, tasksIDsCompleted);
        String playerID = mDatabase.push().getKey();
        mDatabase.child("player").child(playerID).setValue(player);
    }

    private void createHunt(String name, List<HuntTask> tasks, String qrCode) {
        Hunt hunt = new Hunt(name, tasks, qrCode);
        String huntID = mDatabase.push().getKey();
        mDatabase.child("hunt").child(huntID).setValue(hunt);
    }

    private void uploadImage(Uri filePath) {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            //creating the upload object to store uploaded image details
                            Image upload = new Image("editTextName.getText().toString().trim()", taskSnapshot.getUploadSessionUri().toString());

                            //adding an upload to firebase database
                            String uploadId = mDatabase.push().getKey();
                            mDatabase.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void init_ui()  {

        mPlay = findViewById(R.id.play_button);
        mCreate = findViewById(R.id.create_button);

        init_controller();
    }


    private void init_controller()  {
        mPlay.setOnClickListener(this);
        mCreate.setOnClickListener(this);
    }

    private void startActivity(Class<?> cls)    {
        startActivity(new Intent(this, cls));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())   {

            case R.id.play_button:
                startActivity(PlayActivity.class);
                break;
            case R.id.create_button:
                startActivity(CreateActivity.class);
                break;
        }
    }
}
