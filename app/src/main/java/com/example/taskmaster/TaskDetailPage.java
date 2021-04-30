package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TaskDetailPage extends AppCompatActivity {
    public static String TAG = "msimms_taskDetail";

    TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_page);

        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "msimms_tasks")
                .allowMainThreadQueries()
                .build();

        Intent taskDetailPageIntent = getIntent();
        if(taskDetailPageIntent.getIntExtra("id", 0) == 0){
            Log.i(TAG, "no title for task exists");
        }else{
            int id = taskDetailPageIntent.getIntExtra("id",0);
            Log.i(TAG, "onCreate: task with id" + id);
            Task taskFromDB = taskDatabase.taskDao().findById(id).get(0);
            Log.i(TAG, "onCreate: "+taskFromDB.body);
            ((TextView) findViewById(R.id.detailTitle)).setText(taskFromDB.title);
            ((TextView) findViewById(R.id.detailDescription)).setText(taskFromDB.body);
            ((TextView) findViewById(R.id.detailState)).setText(taskFromDB.state);
        }
    }
}