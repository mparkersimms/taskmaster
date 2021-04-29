package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.taskmaster.adapters.TaskItemRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class TaskItemRecyclerView extends AppCompatActivity {
    TaskItemRecycleViewAdapter taskItemRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        List<String> taskItems = new ArrayList<>();
        taskItems.add("go grocery shopping");
        taskItems.add("ride dirt bike");
        taskItems.add("go rock climbing");

//        taskItemRecyclerViewAdapter = new TaskItemRecycleViewAdapter(this, taskItems);

        RecyclerView recyclerView = findViewById(R.id.recyclerView100);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        recyclerView.setAdapter(taskItemRecyclerViewAdapter);

    }
}