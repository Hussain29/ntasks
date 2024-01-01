
// TaskViewHolder.java
package com.example.ntasks;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTaskID;
    TextView taskNameTextView;
    TextView taskDescriptionTextView;
    TextView priorityTextView;
    TextView deadlineTextView;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);

        // Initialize views from the layout
/*
        textViewTaskID = itemView.findViewById(R.id.textViewTaskID);
*/
        taskNameTextView = itemView.findViewById(R.id.textViewTaskName);
        taskDescriptionTextView = itemView.findViewById(R.id.textViewTaskDescription);
        priorityTextView = itemView.findViewById(R.id.textViewPriority);
        deadlineTextView = itemView.findViewById(R.id.textViewDeadline);
    }
}

