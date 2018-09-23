package tech.swaghunt.app;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tech.swaghunt.app.models.Image;
import tech.swaghunt.app.models.Player;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private StorageReference storageReference;

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

        writeNewPlayer("playerName1", "huntIDBeingPlayed", false,
                images, texts, qrCodes, tasksIDsCompleted);

    }

    private void writeNewPlayer(String name, String huntPlaying,
                                Boolean hasWon, List<Image> images, List<String> text,
                                List<String> qrCodes, List<String> tasksIDsCompleted) {
        Player player = new Player(name, huntPlaying, hasWon, images, text, qrCodes, tasksIDsCompleted);
        String playerID = mDatabase.push().getKey();
        mDatabase.child("player").child(playerID).setValue(player);
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
                            Image upload = new Image("editTextName.getText().toString().trim()", taskSnapshot.getDownloadUrl().toString());

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
}
