package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//   ---------Add a Task button --------

        Button addTasksButton = findViewById(R.id.addTasksButton);
        addTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToAddTasksPageIntent = new Intent(MainActivity.this, AddTaskPage.class);
                startActivity(goToAddTasksPageIntent);
            }
        });

//   ---------All Tasks button --------

        Button allTasksButton = findViewById(R.id.allTasksButton);
        allTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotToAllTasksPageIntent = new Intent(MainActivity.this, AllTasksPage.class);
                startActivity(gotToAllTasksPageIntent);
            }

        });

//   ---------Settings Icon --------

        findViewById(R.id.settingsButton).setOnClickListener(v -> {
            Intent goToSettingsPageIntent = new Intent(MainActivity.this, SettingsPage.class);
            startActivity(goToSettingsPageIntent);
        });


        //   ---------Task Detail buttons --------

        String firstTaskString = "Go Climbing";
        String secondTaskString = "Ride Dirt Bike";
        String thirdTaskString = "Walk the Dog";


        Button firstTaskButton = findViewById(R.id.firstTaskButton);
        Button secondTaskButton = findViewById(R.id.secondTaskButton);
        Button thirdTaskButton = findViewById(R.id.thirdTaskButton);

        firstTaskButton.setText(firstTaskString);
        secondTaskButton.setText(secondTaskString);
        thirdTaskButton.setText(thirdTaskString);

        firstTaskButton.setOnClickListener(v -> {
            Intent taskDetailIntent1 = new Intent(MainActivity.this, TaskDetailPage.class);
            taskDetailIntent1.putExtra("Title", firstTaskString);
            startActivity(taskDetailIntent1);
        });
        secondTaskButton.setOnClickListener(v -> {
            Intent taskDetailIntent2 = new Intent(MainActivity.this, TaskDetailPage.class);
            taskDetailIntent2.putExtra("Title", secondTaskString);
            startActivity(taskDetailIntent2);
        });
        thirdTaskButton.setOnClickListener(v -> {
            Intent taskDetailIntent3 = new Intent(MainActivity.this, TaskDetailPage.class);
            taskDetailIntent3.putExtra("Title", thirdTaskString);
            startActivity(taskDetailIntent3);
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString("username", null);
        String greeting = username + "'s Tasks";
        if(username != null){
            ((TextView) findViewById(R.id.homeUsernameDisplay)).setText(greeting);
        }
    }
}

