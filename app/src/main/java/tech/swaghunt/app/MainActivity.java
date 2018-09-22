package tech.swaghunt.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener{

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


    private void init_controller()  {
        mPlay.setOnClickListener(this);
        mCreate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())   {

            case R.id.play_button:

                break;
            case R.id.create_button:

                break;
        }

    }
}
