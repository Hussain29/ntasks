// TaskAdapter.java
package com.example.ntasks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<TaskModel> taskList;

    private Context context;

    public TaskAdapter(Context context, ArrayList<TaskModel> taskList) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);

        // Bind data to views
        holder.taskNameTextView.setText(task.getTaskName());
        holder.taskDescriptionTextView.setText(task.getTaskDescription());
        holder.priorityTextView.setText(task.getPriority());
        holder.deadlineTextView.setText(task.getDeadline());
        /*"Task ID: " + */
        holder.priorityTextView.setText(task.getPriority());

        // Set the background color based on priority
        String priority = task.getPriority();
        int color;
        switch (priority.toLowerCase()) {
            case "high":
                color = Color.RED;
                break;
            case "medium":
                color = Color.YELLOW;
                break;
            case "low":
                color = Color.BLUE;
                break;
            default:
                color = Color.WHITE; // Default color if priority is not recognized
                break;
        }
        holder.priorityTextView.setBackgroundColor(color);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event, e.g., start a new activity with task details
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    TaskModel clickedTask = taskList.get(position);
                    openTaskDetailsActivity(clickedTask);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    private void openTaskDetailsActivity(TaskModel task) {
        Intent intent = new Intent(context, TaskDetailsActivity.class);
        intent.putExtra("task", task);
        context.startActivity(intent);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView taskName,taskDesc,priority,deadline;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            taskName=itemView.findViewById(R.id.textViewTaskName);
            taskDesc=itemView.findViewById(R.id.textViewTaskDescription);
            priority=itemView.findViewById(R.id.textViewPriority);
            deadline=itemView.findViewById(R.id.textViewDeadline);
        }
    }
}
