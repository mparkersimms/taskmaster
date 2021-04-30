package com.example.taskmaster.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaster.MainActivity;
import com.example.taskmaster.R;
import com.example.taskmaster.Task;
import com.example.taskmaster.TaskDetailPage;
import com.example.taskmaster.TaskItemRecyclerView;

import java.util.List;

public class TaskItemRecycleViewAdapter extends RecyclerView.Adapter {
    static String TAG = "msimms_adapter";


    List<Task> taskItems;

    public ClickOnTaskAble clickOnTaskAble;

    public TaskItemRecycleViewAdapter(ClickOnTaskAble clickOnTaskAble, List<Task> taskItems){
        this.taskItems = taskItems;
        this.clickOnTaskAble = clickOnTaskAble;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fragment = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task_item2, parent, false);

        return new TaskItemViewHolder(fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextView)holder.itemView.findViewById(R.id.taskItemFragmentTextView))
                .setText(taskItems.get(position).getTitle() + taskItems.get(position).id);
        int itemId = (int)taskItems.get(position).id;
        holder.itemView.setId(itemId);

        holder.itemView.setOnClickListener(v -> {
            Log.i(TAG, "Clicked on a thing");
            Log.i(TAG, taskItems.get(position).getTitle());
//            clickOnTaskAble.handleClickOnTask(holder);
//            Intent goToTaskDetailPageIntent = new Intent(MainActivity.this, TaskDetailPage.class);
//            goToTaskDetailPageIntent.putExtra("Title", taskItems.get(position).getTitle());
//            startActivity(goToTaskDetailPageIntent);


            clickOnTaskAble.handleClickOnTask(holder);
        });

    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    class TaskItemViewHolder extends RecyclerView.ViewHolder{
        public TaskItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface ClickOnTaskAble {
        public void handleClickOnTask(RecyclerView.ViewHolder recyclerViewHolder);
    }
}
