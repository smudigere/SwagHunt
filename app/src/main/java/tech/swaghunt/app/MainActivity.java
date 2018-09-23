package tech.swaghunt.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import tech.swaghunt.app.models.Player;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        storageRef = storage.getReference();

        mDatabase = database.getReference();

        List<String> images = new ArrayList<>();
        images.add("img1Ref");
        images.add("img2Ref");

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
                images , texts, qrCodes, tasksIDsCompleted);

    }

    private void writeNewPlayer(String name, String huntPlaying,
                                Boolean hasWon, List<String> images, List<String> text,
                                List<String> qrCodes, List<String> tasksIDsCompleted) {
        Player player = new Player(name, huntPlaying, hasWon, images, text, qrCodes, tasksIDsCompleted);
        String playerID = mDatabase.push().getKey();
        mDatabase.child("player").child(playerID).setValue(player);
    }
}
