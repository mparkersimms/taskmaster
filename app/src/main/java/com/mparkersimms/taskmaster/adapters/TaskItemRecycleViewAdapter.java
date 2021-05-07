package com.mparkersimms.taskmaster.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.TaskItem;
import com.mparkersimms.taskmaster.R;

import java.util.List;

public class TaskItemRecycleViewAdapter extends RecyclerView.Adapter<TaskItemRecycleViewAdapter.TaskItemViewHolder> {
    static String TAG = "msimms_adapter";


    List<TaskItem> taskItems;

    public ClickOnTaskAble clickOnTaskAble;

    public TaskItemRecycleViewAdapter(ClickOnTaskAble clickOnTaskAble, List<TaskItem> taskItems){
        this.taskItems = taskItems;
        this.clickOnTaskAble = clickOnTaskAble;
    }

    @NonNull
    @Override
    public TaskItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fragment = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task_item2, parent, false);

        TaskItemViewHolder tivh = new TaskItemViewHolder(fragment);

        tivh.itemView.setOnClickListener(v -> {
            clickOnTaskAble.handleClickOnTask(tivh);
        });
        return tivh;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskItemViewHolder holder, int position) {
        holder.taskItem = taskItems.get(position);
        ((TextView)holder.itemView.findViewById(R.id.taskItemFragmentTextView))
                .setText(holder.taskItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    public class TaskItemViewHolder extends RecyclerView.ViewHolder{
        public TaskItem taskItem;
        public TaskItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface ClickOnTaskAble {
        public void handleClickOnTask(TaskItemViewHolder tivh);
    }
}
