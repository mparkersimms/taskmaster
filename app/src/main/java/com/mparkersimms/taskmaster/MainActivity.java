package com.mparkersimms.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskItem;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mparkersimms.taskmaster.adapters.TaskItemRecycleViewAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class MainActivity extends AppCompatActivity {
    String TAG = "msimms_main";

    static int REQUEST_CODE = 123;

//    TaskDatabase taskDatabase;
    List<TaskItem> taskItems = new ArrayList<>();


    Handler mainThreadHandler;


    void signupCognito(){
        Amplify.Auth.signUp(
                "m.parker.simms@gmail.com",
                "password",
                AuthSignUpOptions.builder().build(),
                r -> {
                    Log.i(TAG, "signupCognito: signup successfull" + r.toString());
                },
                r-> {
                    Log.i(TAG, "signupCognito: signup unsuccessfull" + r.toString());
                }
        );
    }

    void confirmSignupCognito(){
        Amplify.Auth.confirmSignUp(
                "m.parker.simms@gmail.com",
                "694095",
                r -> {
                    Log.i(TAG, "confirmsignupCognito: signup successfull" + r.toString());
                },
                r-> {
                    Log.i(TAG, "confirmsignupCognito: signup unsuccessfull" + r.toString());
                }
        );
    }

    void loginCognito(){
        Amplify.Auth.signIn(
                "m.parker.simms@gmail.com",
                "password",
                r -> {
                    Log.i(TAG, "loginCognito: login successfull" + r.toString());
                },
                r-> {
                    Log.i(TAG, "loginCognito: login unsuccessfull" + r.toString());
                }
        );
    }

    void uploadTestToS3(){
        File file = new File(getApplicationContext().getFilesDir(), "ExampleFile");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.append("Example file contents");
            writer.close();
        } catch (Exception exception) {
            Log.e("MyAmplifyApp", "Upload failed", exception);
        }
    Amplify.Storage.uploadFile(
            "ExampleFile",
            file,
            r -> {
                Log.i(TAG, "uploadToS3: successsfull");
            },
            r ->{
                Log.i(TAG, "uploadToS3: unsuccessfull");
            }
    );
    }

    void uploadToS3(File file, String filename){
        Amplify.Storage.uploadFile(
                filename,
                file,
                r -> {},
                r -> {}
        );
    }

    void getFileFromPhone(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE);
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            File file = new File(getApplicationContext().getFilesDir(), "new file");
            try{
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                FileUtils.copy(inputStream, new FileOutputStream(file));
                uploadToS3(file, "test file");

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void downloadFileFromS3(String key){
        Amplify.Storage.downloadFile(
                key,
                new File(getApplicationContext().getFilesDir(), key),
                r ->{
                    ImageView i = findViewById(R.id.ImageViewMainActivity);
                    i.setImageBitmap(BitmapFactory.decodeFile(r.getFile().getPath()));
                },
                r -> {});
    }


    void logoutCognito(){
        Amplify.Auth.signOut(
                () -> Log.i(TAG, "Signed out successfully"),
                error -> Log.e(TAG, error.toString())
        );
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.mainActivityView).setOnClickListener(v -> {
            Log.i(TAG, "onClick: toast why arent you showing up?" + getApplicationContext().toString());
//        Context context = getApplicationContext();
//        CharSequence text = "Test toast";
//        int duration = Toast.LENGTH_LONG;

        Toast.makeText(MainActivity.this, "test toast", Toast.LENGTH_LONG).show();
//        toast.show();
        });

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
                     Log.i(TAG, "configured amplify");
        } catch (AmplifyException e) {
            e.printStackTrace();
        }

        registerWithFirebaseAndPinpoint();



//  ====== cognito signup =====

//        signupCognito();

//  ===== cognito confirm signup =====

//        confirmSignupCognito();

//  ===== cognito login =====

//        loginCognito();

//  ===== cognito logout =====

//        logoutCognito();

//   ===== upload to s3 =====

//        uploadTestToS3();
//         getFileFromPhone();
        downloadFileFromS3("test file");


//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "msimms_tasks")
//                .allowMainThreadQueries()
//                .build();

        AuthUser authUser = Amplify.Auth.getCurrentUser();
        if(authUser != null) {
            ((TextView) findViewById(R.id.textViewMainUsername)).setText(authUser.getUsername());
        }

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
//                        Log.i(TAG, "task:" + taskItem);
                    }

                    mainThreadHandler.sendEmptyMessage(1);
                    Log.i(TAG, "onCreate++++++++++++++++ now what?");
                    },
                response -> Log.i(TAG, "onCreate: failed to retrieve task" + response.toString())
        );

//  ========== buttons ==============

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

//   --------- Signup Button --------

        findViewById(R.id.signUpButton).setOnClickListener(v -> {
            Intent goToSettingsPageIntent = new Intent(MainActivity.this, SignupPage.class);
            startActivity(goToSettingsPageIntent);
        });
//   --------- ConfirmSignup Button --------

        findViewById(R.id.ConfirmSignUpButton).setOnClickListener(v -> {
            Intent goToSettingsPageIntent = new Intent(MainActivity.this, ConfirmSignupPage.class);
            startActivity(goToSettingsPageIntent);
        });

//   --------- Login Button --------

        findViewById(R.id.LoginButton).setOnClickListener(v -> {
            Intent goToSettingsPageIntent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(goToSettingsPageIntent);
        });

        //   --------- Logout Button --------

        findViewById(R.id.logoutButton).setOnClickListener(v -> {
            logoutCognito();
            Intent goToSettingsPageIntent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(goToSettingsPageIntent);

        });


        //   ---------Task Detail buttons --------

//        String firstTaskString = "Go Climbing";
//        String secondTaskString = "Ride Dirt Bike";
//        String thirdTaskString = "Walk the Dog";
//
//
//        Button firstTaskButton = findViewById(R.id.firstTaskButton);
//        Button secondTaskButton = findViewById(R.id.secondTaskButton);
//        Button thirdTaskButton = findViewById(R.id.thirdTaskButton);
//
//        firstTaskButton.setText(firstTaskString);
//        secondTaskButton.setText(secondTaskString);
//        thirdTaskButton.setText(thirdTaskString);
//
//        firstTaskButton.setOnClickListener(v -> {
//            Intent taskDetailIntent1 = new Intent(MainActivity.this, TaskDetailPage.class);
//            taskDetailIntent1.putExtra("Title", firstTaskString);
//            Log.i(TAG, "onCreate: hit the button");
//            startActivity(taskDetailIntent1);
//        });
//        secondTaskButton.setOnClickListener(v -> {
//            Intent taskDetailIntent2 = new Intent(MainActivity.this, TaskDetailPage.class);
//            taskDetailIntent2.putExtra("Title", secondTaskString);
//            startActivity(taskDetailIntent2);
//        });
//        thirdTaskButton.setOnClickListener(v -> {
//            Intent taskDetailIntent3 = new Intent(MainActivity.this, TaskDetailPage.class);
//            taskDetailIntent3.putExtra("Title", thirdTaskString);
//            startActivity(taskDetailIntent3);
//        });

    }
    void registerWithFirebaseAndPinpoint(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                    }
                });
    }





    @Override
    protected void onResume() {
        super.onResume();

        Context context = MainActivity.this;
        CharSequence text = "Test toast";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        Log.i(TAG, "onResume: toast should have appeared");
        toast.show();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString("username", null);
        String greeting = username + "'s Tasks";
        String userChosenTeamId = preferences.getString("teamId", null);
        Log.i(TAG, "=============onResume: userChosenTeamId "+ userChosenTeamId);
        Log.i(TAG, "=============onResume: username "+ username);
        if(username != null){
            ((TextView) findViewById(R.id.homeUsernameDisplay)).setText(greeting);
        }
        Amplify.API.query(
                ModelQuery.list(TaskItem.class),
                response -> {
                    for(TaskItem taskItem : response.getData()) {
                            if (taskItem.getTeam().getId().equals(userChosenTeamId)) {
                                taskItems.add(taskItem);
//                        Log.i(TAG, "task:" + taskItem);
                            }
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

