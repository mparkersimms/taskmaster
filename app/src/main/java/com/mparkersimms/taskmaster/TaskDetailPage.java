package com.mparkersimms.taskmaster;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TaskDetailPage extends AppCompatActivity {
    public static String TAG = "msimms_taskDetail";

    TaskDatabase taskDatabase;
    List<TaskItem> taskItems = new ArrayList<>();
    Handler mainThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_page);

//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "msimms_tasks")
//                .allowMainThreadQueries()
//                .build();

        Intent taskDetailPageIntent = getIntent();
            Log.i(TAG, "onCreate: task with id");
            ((TextView) findViewById(R.id.detailTitle)).setText(taskDetailPageIntent.getStringExtra("title"));
            ((TextView) findViewById(R.id.detailDescription)).setText(taskDetailPageIntent.getStringExtra("body"));
            ((TextView) findViewById(R.id.detailState)).setText(taskDetailPageIntent.getStringExtra("state"));

            String id = taskDetailPageIntent.getStringExtra("id");
        Log.i(TAG, "onCreate: task id" + id);
            Amplify.Storage.downloadFile(
                   id,
                new File(getApplicationContext().getFilesDir(), id),
                r ->{
                    ImageView i = findViewById(R.id.imageViewTaskDetail);
                    i.setImageBitmap(BitmapFactory.decodeFile(r.getFile().getPath()));
                },
                r -> {});


    }
        }

