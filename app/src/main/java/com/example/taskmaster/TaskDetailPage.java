package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TaskDetailPage extends AppCompatActivity {
    public static String TAG = "msimms_taskDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_page);

        Intent taskDetailPageIntent = getIntent();
        if(taskDetailPageIntent.getStringExtra("Title") == null){
            Log.i(TAG, "no title for task exists");
        }else{
            String title = taskDetailPageIntent.getStringExtra("Title");
            ((TextView) findViewById(R.id.detailTitle)).setText(title);
        }
    }
}