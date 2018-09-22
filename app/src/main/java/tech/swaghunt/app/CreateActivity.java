package tech.swaghunt.app;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tech.swaghunt.app.fragments.CreateTaskFragment;
import tech.swaghunt.app.fragments.TaskCountInputFragment;
import tech.swaghunt.app.utility.Functions;

public class CreateActivity extends AppCompatActivity {

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

    }
}