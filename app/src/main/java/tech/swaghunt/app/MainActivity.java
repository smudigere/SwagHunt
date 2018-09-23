package tech.swaghunt.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tech.swaghunt.app.models.Hunt;
import tech.swaghunt.app.models.HuntTask;
import tech.swaghunt.app.models.Image;
import tech.swaghunt.app.models.Player;

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

//        List<Image> images = new ArrayList<>();
//        images.add(new Image("img1Ref", "url"));
//        images.add(new Image("img2Ref", "url"));
//
//        List<String> texts = new ArrayList<>();
//        texts.add("txt1");
//        texts.add("txt2");
//
//        List<String> qrCodes = new ArrayList<>();
//        qrCodes.add("qrCode1");
//        qrCodes.add("qrCode2");
//
//        List<String> tasksIDsCompleted = new ArrayList<>();
//        tasksIDsCompleted.add("taskId1");
//        tasksIDsCompleted.add("taskID2");
//
//        List<HuntTask> tasks = new ArrayList<HuntTask>();
//        tasks.add(new HuntTask("clue", "clueAnswer", new Image("imgName", "imgUrl"), "text", "qrCodes", "taskType", "huntId"));

//        createPlayer("playerName 2", "huntIDBeingPlayed", true,
//                images, texts, qrCodes, tasksIDsCompleted);
//
//        createHunt("grizzhunt 2", tasks, "qrCode");
        init_ui();
    }

    private String createPlayer(String name, String huntPlaying,
                              Boolean hasWon, List<Image> images, List<String> text,
                                List<String> qrCodes, List<String> tasksIDsCompleted) {
        Player player = new Player(name, huntPlaying, hasWon, images, text, qrCodes, tasksIDsCompleted);
        String playerID = mDatabase.push().getKey();
        mDatabase.child("player").child(playerID).setValue(player);
        return playerID;
    }

    private String createHunt(String name, List<HuntTask> tasks, String qrCode) {
        Hunt hunt = new Hunt(name, tasks, qrCode);
        String huntID = mDatabase.push().getKey();
        mDatabase.child("hunt").child(huntID).setValue(hunt);
        return huntID;
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

    private void startActivity(Class<?> cls, Map<String, String> intentExtra)    {
        Intent intent = new Intent(this, cls);
        intent.putExtra(intentExtra.keySet().toArray()[0].toString(), intentExtra.get(intentExtra.keySet().toArray()[0]));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Map<String, String> intentMap = new HashMap<>();
        switch (view.getId())   {
            case R.id.play_button:
                String playerID = createPlayer("player name 1", "", false,
                        null, null, null, null);
                intentMap.put("playerID", playerID);
                startActivity(PlayActivity.class,intentMap);
                break;
            case R.id.create_button:
                String huntID = createHunt("GrizzHacks3", null, "");
                intentMap.put("huntID", huntID);
                startActivity(CreateActivity.class, intentMap);
                break;
        }
    }
}
