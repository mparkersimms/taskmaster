package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button addTasksButton = findViewById(R.id.addTasksButton);
        addTasksButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent goToAddTasksPageIntent = new Intent(MainActivity.this, AddTaskPage.class);
                startActivity(goToAddTasksPageIntent);

            }
        });

        Button allTasksButton = findViewById(R.id.allTasksButton);
        allTasksButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent gotToAllTasksPageIntent = new Intent(MainActivity.this, AllTasksPage.class);
                startActivity(gotToAllTasksPageIntent);
            }
        });
    }
}

