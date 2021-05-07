package com.mparkersimms.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskItem;
import com.mparkersimms.taskmaster.adapters.TaskItemRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class MainActivity extends AppCompatActivity {
    String TAG = "msimms_main";

//    TaskDatabase taskDatabase;
    List<TaskItem> taskItems = new ArrayList<>();


    Handler mainThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "msimms_tasks")
//                .allowMainThreadQueries()
//                .build();
    RecyclerView homePageRecyclerView = findViewById(R.id.taskListRecyclerView);
    homePageRecyclerView.setAdapter(
            new TaskItemRecycleViewAdapter(
                    tivh -> {
                        Intent goToDetailPageIntent = new Intent(MainActivity.this, TaskDetailPage.class);
                        String title = (String)((TextView)tivh.itemView.findViewById(R.id.taskItemFragmentTextView)).getText();
                        Log.i(TAG, "handleClickOnTask: "+ tivh.taskItem.getId());
                        String itemId = tivh.taskItem.getId();

                        goToDetailPageIntent.putExtra("id", itemId);
                        goToDetailPageIntent.putExtra("title", tivh.taskItem.getTitle());
                        goToDetailPageIntent.putExtra("body", tivh.taskItem.getBody());
                        goToDetailPageIntent.putExtra("state", tivh.taskItem.getState());
                        Log.i(TAG, "handleClickOnTask: " + title);
                        startActivity(goToDetailPageIntent);
                    },
                    taskItems));
        Log.i(TAG, "onCreat==========: tasks"+ taskItems);
    RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
    homePageRecyclerView.setLayoutManager(lm);


        mainThreadHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                Log.i(TAG, "------------------------------handleMessages: hit the handler");
                if(msg.what == 1){
                    StringJoiner sj = new StringJoiner(" , ");
                    for(TaskItem taskItem : taskItems){
                        sj.add(taskItem.getTitle());
                    }
                    homePageRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        };

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "configured amplify");
        } catch (AmplifyException e) {
            e.printStackTrace();
        }
//============= build new teams===============
//        Team newTeam1 = Team.builder()
//                .name("team1")
//                .build();
//        Team newTeam2 = Team.builder()
//                .name("team2")
//                .build();
//        Team newTeam3 = Team.builder()
//                .name("team3")
//                .build();

//        ====== add new teams to the database ======

//        Amplify.API.mutate(
//                ModelMutation.create(newTeam1),
//                response -> Log.i(TAG, "successfull addition of team"),
//                response -> Log.i(TAG, "failed addition of team" + response)
//        );
//        Amplify.API.mutate(
//                ModelMutation.create(newTeam2),
//                response -> Log.i(TAG, "successfull addition of team"),
//                response -> Log.i(TAG, "failed addition of team" + response)
//        );
//        Amplify.API.mutate(
//                ModelMutation.create(newTeam3),
//                response -> Log.i(TAG, "successfull addition of team"),
//                response -> Log.i(TAG, "failed addition of team" + response)
//        );

//        TaskItem newTaskItem = TaskItem.builder()
//                .title("test task")
//                .body("This is just a test")
//                .state("New")
//                .team(newTeam)
//                .build();


        Amplify.API.query(
        ModelQuery.list(TaskItem.class),
                response -> {
                    for(TaskItem taskItem : response.getData()) {
                        taskItems.add(taskItem);
                        Log.i(TAG, "task:" + taskItem);
                    }

                    mainThreadHandler.sendEmptyMessage(1);
                    Log.i(TAG, "onCreate++++++++++++++++ now what?");
                    },
                response -> Log.i(TAG, "onCreate: failed to retrieve task" + response.toString())
        );


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
        Amplify.API.query(
                ModelQuery.list(TaskItem.class),
                response -> {
                    for(TaskItem taskItem : response.getData()) {
                        taskItems.add(taskItem);
                        Log.i(TAG, "task:" + taskItem);
                    }
                    mainThreadHandler.sendEmptyMessage(1);
                },
                response -> Log.i(TAG, "onCreate: failed to retrieve task" + response.toString())
        );
        RecyclerView homePageRecyclerView = findViewById(R.id.taskListRecyclerView);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        homePageRecyclerView.setLayoutManager(lm);
    }

//    @Override
//    public void handleClickOnTask(TaskItemRecycleViewAdapter.TaskItemViewHolder tivh) {
//        Intent goToDetailPageIntent = new Intent(MainActivity.this, TaskDetailPage.class);
//        String title = (String)((TextView)tivh.itemView.findViewById(R.id.taskItemFragmentTextView)).getText();
//        Log.i(TAG, "handleClickOnTask: "+ tivh.itemView.getId());
//        String itemId = tivh.taskItem.getId();
////        goToDetailPageIntent.putExtra("Title", title);
//        goToDetailPageIntent.putExtra("id", itemId);
//        Log.i(TAG, "handleClickOnTask: " + title);
//        startActivity(goToDetailPageIntent);
//
//    }


}

