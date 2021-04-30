package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddTaskPage extends AppCompatActivity {
    String TAG = "mPSimms.addTaskPage.";

    TaskDatabase taskDatabase;



    static int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "msimms_tasks")
                .allowMainThreadQueries()
                .build();

        TextView totalTaskCounter = findViewById(R.id.taskTotalTextView);
        totalTaskCounter.setText("Total Tasks: " + counter);

        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView submittedTextView = findViewById(R.id.submittedTextView);
                submittedTextView.setText("Submitted!");
                counter = counter + 1;
                TextView title = findViewById(R.id.taskTitleTextView);
                String titleText = title.getText().toString();
                title.setText("");
                TextView description = findViewById(R.id.taskDescriptionTextView);
                String descriptionText = description.getText().toString();
                description.setText("");
                TextView totalTaskCounter = findViewById(R.id.taskTotalTextView);
                totalTaskCounter.setText("Total Tasks: " + counter);
                Task task = new Task(titleText, descriptionText, "new");
                taskDatabase.taskDao().insert(task);

            }
        });

        findViewById(R.id.addTaskWholePage).setOnClickListener( v -> {
            TextView submittedTextView = findViewById(R.id.submittedTextView);
            submittedTextView.setText("");
            Log.i(TAG, "onCreate: clicked anywhere");
        });



    }
}