package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddTaskPage extends AppCompatActivity {
    String TAG = "mPSimms.addTaskPage.";
    static int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                title.setText("");
                TextView description = findViewById(R.id.taskDescriptionTextView);
                description.setText("");
                TextView totalTaskCounter = findViewById(R.id.taskTotalTextView);
                totalTaskCounter.setText("Total Tasks: " + counter);

            }
        });

        findViewById(R.id.addTaskWholePage).setOnClickListener( v -> {
            TextView submittedTextView = findViewById(R.id.submittedTextView);
            submittedTextView.setText("");
            Log.i(TAG, "onCreate: clicked anywhere");
        });



    }
}