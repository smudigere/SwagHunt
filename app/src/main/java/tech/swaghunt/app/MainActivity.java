package tech.swaghunt.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mPlay, mCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_ui();
    }


    private void init_ui()  {

        mPlay = findViewById(R.id.play_button);
        mCreate = findViewById(R.id.create_button);
    }
}
