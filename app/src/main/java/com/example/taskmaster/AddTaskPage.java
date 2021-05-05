package com.example.taskmaster;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
public class AddTaskPage extends AppCompatActivity {
    String TAG = "mPSimms.addTaskPage.";

    TaskDatabase taskDatabase;



    static int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "msimms_tasks")
//                .allowMainThreadQueries()
//                .build();

        Task newTask = Task.builder()
                .title("test task")
                .body("This is just a test")
                .state("New")
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

                Task newTask = Task.builder()
                        .title(titleText)
                        .body(descriptionText)
                        .state("New")
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(newTask),
                        response -> Log.i(TAG, "successfull addition of task"),
                        response -> Log.i(TAG, "failed addition of task" + response)
                );
//                Task task = new Task(titleText, descriptionText, "new");

//                taskDatabase.taskDao().insert(task);

            }
        });

        findViewById(R.id.addTaskWholePage).setOnClickListener( v -> {
            TextView submittedTextView = findViewById(R.id.submittedTextView);
            submittedTextView.setText("");
            Log.i(TAG, "onCreate: clicked anywhere");
        });



    }
}