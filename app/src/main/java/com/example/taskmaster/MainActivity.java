package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.example.taskmaster.adapters.TaskItemRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskItemRecycleViewAdapter.ClickOnTaskAble {
    String TAG = "msimms_main";

    TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "msimms_tasks")
//                .allowMainThreadQueries()
//                .build();

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "configured amplify");
        } catch (AmplifyException e) {
            e.printStackTrace();
        }

        Task newTask = Task.builder()
                .title("test task")
                .body("This is just a test")
                .state("New")
                .build();

        Amplify.API.mutate(
                ModelMutation.create(newTask),
                response -> Log.i(TAG, "successfull addition of task"),
                response -> Log.i(TAG, "failed addition of task" + response)
        );

        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    List<Task> tasks = new ArrayList<>();
                    for(Task task : response.getData()) {
                        tasks.add(task);
                        Log.i(TAG, "task:" + task);
                    }},
                response -> Log.i(TAG, "onCreate: failed to retrieve task" + response.toString())
                );

//        List<Task> taskItems = taskDatabase.taskDao().findAll();
//        taskItems.add(new Task("Go climbing", "With all your ropes, and gear", "New"));
//        taskItems.add(new Task("Walk the dog", "With your feet, and leash", "New"));
//        taskItems.add(new Task("Ride Dirt Bike", "With all your gear, and helmet", "New"));

        RecyclerView homePageRecyclerView = findViewById(R.id.taskListRecyclerView);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        homePageRecyclerView.setLayoutManager(lm);
//        homePageRecyclerView.setAdapter(new TaskItemRecycleViewAdapter(this, taskItems));



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
            Log.i(TAG, "onCreate: hit the button");
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

    @Override
    public void handleClickOnTask(RecyclerView.ViewHolder recyclerViewHolder) {
        Intent goToDetailPageIntent = new Intent(MainActivity.this, TaskDetailPage.class);
        String title = (String)((TextView)recyclerViewHolder.itemView.findViewById(R.id.taskItemFragmentTextView)).getText();
        int itemId = recyclerViewHolder.itemView.getId();
//        goToDetailPageIntent.putExtra("Title", title);
        goToDetailPageIntent.putExtra("id", itemId);
        Log.i(TAG, "handleClickOnTask: " + title);
        startActivity(goToDetailPageIntent);

    }
}

