package com.example.ntasks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AssgnTaskDetailsActivity extends AppCompatActivity {

    private TextView taskNameTextView;
    private TextView taskDescriptionTextView;
    private TextView priorityTextView;
    private TextView deadlineTextView;
    private TextView statusTextView;
    private TextView assignedToTextView;
    private Spinner spinnerStatus;
    private TextView clientTextView;
    private Button buttonSubmit;
    private Button buttonEndTask; // Add the End Task button
    private TaskModel task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myassigned_tasks_details);

        // Retrieve task details from Intent
        task = getIntent().getParcelableExtra("task");

        if (task == null || task.getTaskID() == null) {
            // Handle the case where the task or taskID is null, perhaps show an error message
            Toast.makeText(this, "Error: Task details not available", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize Views
        taskNameTextView = findViewById(R.id.textViewTaskName);
        taskDescriptionTextView = findViewById(R.id.textViewTaskDescription);
        priorityTextView = findViewById(R.id.textViewPriority);
        deadlineTextView = findViewById(R.id.textViewDeadline);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        statusTextView = findViewById(R.id.textViewStatusLabel);
        assignedToTextView = findViewById(R.id.textViewAssignedTo);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonEndTask = findViewById(R.id.buttonEndTask);
        clientTextView = findViewById(R.id.textViewClientLabel);// Initialize the End Task button

        if(task.getClientdb() == null && task.getClientdb().equals("Select Client")){
            clientTextView.setVisibility(View.GONE);
        }
        else{
            clientTextView.setText("Client:" + task.getClientdb());
        }

        // Set TextViews with task details
        taskNameTextView.setText("Task Name: " + task.getTaskName());
        taskDescriptionTextView.setText("Task Description: " + task.getTaskDescription());
        priorityTextView.setText("Priority: " + task.getPriority());
        deadlineTextView.setText("Deadline: " + task.getDeadline());
        statusTextView.setText("Current Status: " + task.getStatus());
        assignedToTextView.setText("Assigned To: " + task.getAssignedUser());

        String priority = task.getPriority();
        switch (priority.toLowerCase()) {
            case "high":
                priorityTextView.setBackgroundResource(R.drawable.rounded_bg_priohigh);
                break;
            case "medium":
                priorityTextView.setBackgroundResource(R.drawable.rounded_bg_priomed);
                break;
            case "low":
                priorityTextView.setBackgroundResource(R.drawable.rounded_bg_priolow);
                break;
            default:
                priorityTextView.setBackgroundResource(R.drawable.rounded_bg);
                break;
        }

        String[] statusOptionsWithDefault = new String[]{
                getString(R.string.change_status_prompt),
                "ASSIGNED",
                "READ",
                "IN PROGRESS",
                "ON HOLD",
                "NEED DISCUSSION",
                "REVIEW"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, statusOptionsWithDefault);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the selected status here and update it in the database
                String selectedStatus = spinnerStatus.getSelectedItem().toString();
                updateStatusInDatabase(task.getTaskID(), selectedStatus);
            }
        });

        // Set a click listener for the End Task button
        buttonEndTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void updateStatusInDatabase(String taskID, String selectedStatus) {
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("Taskdata").child(taskID);

        // Update the 'status' field in the database
        taskRef.child("statusdb").setValue(selectedStatus);

        // Update the status in the current TaskModel
        task.setStatus(selectedStatus);

        // You can also show a Toast message or handle UI updates to indicate success
        Toast.makeText(AssgnTaskDetailsActivity.this, "Status updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Yes," so delete the task
                        deleteTask(task.getTaskID());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "No," so dismiss the dialog
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void deleteTask(String taskID) {
        // Delete the task from the database
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("Taskdata").child(taskID);
        taskRef.removeValue();

        // You can also show a Toast message or handle UI updates to indicate success
        Toast.makeText(AssgnTaskDetailsActivity.this, "Task deleted successfully", Toast.LENGTH_SHORT).show();

        // Close the current activity after deleting the task
        finish();
    }
}


/*
package com.example.ntasks;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AssgnTaskDetailsActivity extends AppCompatActivity {

    private TextView taskNameTextView;
    private TextView taskDescriptionTextView;
    private TextView priorityTextView;
    private TextView deadlineTextView;
    private TextView statusTextView;
    private TextView assignedToTextView;
    private Spinner spinnerStatus;
    private Button buttonSubmit;
    private TaskModel task; // Add a member variable to store the TaskModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myassigned_tasks_details);

        // Retrieve task details from Intent
        task = getIntent().getParcelableExtra("task");

        if (task == null || task.getTaskID() == null) {
            // Handle the case where the task or taskID is null, perhaps show an error message
            Toast.makeText(this, "Error: Task details not available", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize Views
        taskNameTextView = findViewById(R.id.textViewTaskName);
        taskDescriptionTextView = findViewById(R.id.textViewTaskDescription);
        priorityTextView = findViewById(R.id.textViewPriority);
        deadlineTextView = findViewById(R.id.textViewDeadline);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        statusTextView = findViewById(R.id.textViewStatusLabel);
        assignedToTextView = findViewById(R.id.textViewAssignedTo);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set TextViews with task details
        taskNameTextView.setText("Task Name: " + task.getTaskName());
        taskDescriptionTextView.setText("Task Description: " + task.getTaskDescription());
        priorityTextView.setText("Priority: " + task.getPriority());
        deadlineTextView.setText("Deadline: " + task.getDeadline());
        statusTextView.setText("Current Status: " + task.getStatus());
        assignedToTextView.setText("Assigned To: " + task.getAssignedUser());

        String priority = task.getPriority();
        switch (priority.toLowerCase()) {
            case "high":
                priorityTextView.setBackgroundResource(R.drawable.rounded_bg_priohigh);
                break;
            case "medium":
                priorityTextView.setBackgroundResource(R.drawable.rounded_bg_priomed);
                break;
            case "low":
                priorityTextView.setBackgroundResource(R.drawable.rounded_bg_priolow);
                break;
            default:
                priorityTextView.setBackgroundResource(R.drawable.rounded_bg);
                break;
        }

        String[] statusOptionsWithDefault = new String[]{
                getString(R.string.change_status_prompt),
                "ASSIGNED",
                "READ",
                "IN PROGRESS",
                "ON HOLD",
                "NEED DISCUSSION",
                "REVIEW"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, statusOptionsWithDefault);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the selected status here and update it in the database
                String selectedStatus = spinnerStatus.getSelectedItem().toString();
                updateStatusInDatabase(task.getTaskID(), selectedStatus);
            }
        });
    }

    private void updateStatusInDatabase(String taskID, String selectedStatus) {
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("Taskdata").child(taskID);

        // Update the 'status' field in the database
        taskRef.child("statusdb").setValue(selectedStatus);

        // Update the status in the current TaskModel
        task.setStatus(selectedStatus);

        // You can also show a Toast message or handle UI updates to indicate success
        Toast.makeText(AssgnTaskDetailsActivity.this, "Status updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
*/
