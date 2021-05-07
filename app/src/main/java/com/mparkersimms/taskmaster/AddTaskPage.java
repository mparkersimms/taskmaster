package com.mparkersimms.taskmaster;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskItem;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class AddTaskPage extends AppCompatActivity {
    static int counter = 0;
    String TAG = "msimms.addTaskPage.";
    TaskDatabase taskDatabase;
    List<Team> teams = new ArrayList<>();
    Handler mainThreadHandler;
    Spinner spinner;
    ArrayAdapter<Team> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainThreadHandler = new Handler(this.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.i(TAG, "------------------------------handleMessages: hit the handler");
                if (msg.what == 2) {
//                    StringJoiner sj = new StringJoiner(" , ");
//                    for(Team team : teams){
//                        sj.add(team.getName());
//                    }
                    spinnerAdapter.notifyDataSetChanged();
                }
            }
        };

        spinner = findViewById(R.id.spinnerTeams);
        spinnerAdapter = new ArrayAdapter<>(AddTaskPage.this, android.R.layout.simple_spinner_dropdown_item, teams);
        spinner.setAdapter(spinnerAdapter);


//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "msimms_tasks")
//                .allowMainThreadQueries()
//                .build();

//        TaskItem newTaskItem = TaskItem.builder()
//                .title("test task")
//                .body("This is just a test")
//                .state("New")
//                .build();


        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    Log.i(TAG, "onCreate: " + response);
                    for (Team team : response.getData()) {
                        teams.add(team);
                        Log.i(TAG, "team:" + team);
                    }

                    mainThreadHandler.sendEmptyMessage(2);
                    Log.i(TAG, "onCreate++++++++++++++++ now what?");
                },
                response -> Log.i(TAG, "onCreate: failed to retrieve task" + response.toString())
        );


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
                Team clickedOnTeam = (Team) spinner.getSelectedItem();
                TextView totalTaskCounter = findViewById(R.id.taskTotalTextView);
                totalTaskCounter.setText("Total Tasks: " + counter);


                TaskItem newTaskItem = TaskItem.builder()
                        .title(titleText)
                        .body(descriptionText)
                        .state("New")
                        .team(clickedOnTeam)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(newTaskItem),
                        response -> Log.i(TAG, "successfull addition of task"),
                        response -> Log.i(TAG, "failed addition of task" + response)
                );
//                Task task = new Task(titleText, descriptionText, "new");

//                taskDatabase.taskDao().insert(task);

            }
        });

        findViewById(R.id.addTaskWholePage).setOnClickListener(v -> {
            TextView submittedTextView = findViewById(R.id.submittedTextView);
            submittedTextView.setText("");
//            Log.i(TAG, "onCreate: clicked anywhere");
        });


    }
}