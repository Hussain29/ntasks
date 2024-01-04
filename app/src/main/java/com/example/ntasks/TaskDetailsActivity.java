/*
package com.example.ntasks;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TaskDetailsActivity extends AppCompatActivity {

    private TextView taskNameTextView;
    private TextView taskDescriptionTextView;
    private TextView priorityTextView;
    private TextView deadlineTextView;
    private Spinner spinnerStatus;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Log.d("TaskDetailsActiviy", "onCreate started");
        // Retrieve task details from Intent
        TaskModel task = getIntent().getParcelableExtra("task");

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
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set TextViews with task details
        taskNameTextView.setText("Task Name: " + task.getTaskName());
        taskDescriptionTextView.setText("Task Description: " + task.getTaskDescription());
        priorityTextView.setText("Priority: " + task.getPriority());
        deadlineTextView.setText("Deadline: " + task.getDeadline());

        // Add log statements to check values
        Log.d("CheckTDA", "TaskID: " + task.getTaskID());
        Log.d("CheckTDA", "Current Status: " + task.getStatus());

        // Set up the Spinner with status options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.status_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        // Set the current status in the Spinner
        int position = adapter.getPosition(task.getStatus());
        spinnerStatus.setSelection(position);

        // Set click listener for the Submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the selected status here and update it in the database
                String selectedStatus = spinnerStatus.getSelectedItem().toString();
                String taskID = task.getTaskID();
                updateStatusInDatabase(taskID, selectedStatus);
            }
        });
    }

    private void updateStatusInDatabase(String taskID, String selectedStatus) {
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("Taskdata").child(taskID);

        // Update the 'status' field in the database
        taskRef.child("status").setValue(selectedStatus);

        // You can also show a Toast message or handle UI updates to indicate success
        Toast.makeText(TaskDetailsActivity.this, "Status updated successfully", Toast.LENGTH_SHORT).show();
    }
}*/

/*
package com.example.ntasks;

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

public class TaskDetailsActivity extends AppCompatActivity {

    private TextView taskNameTextView;
    private TextView taskDescriptionTextView;
    private TextView priorityTextView;
    private TextView deadlineTextView;
    private Spinner spinnerStatus;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        // Retrieve task details from Intent
        TaskModel task = getIntent().getParcelableExtra("task");

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
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set TextViews with task details
        taskNameTextView.setText("Task Name: " + task.getTaskName());
        taskDescriptionTextView.setText("Task Description: " + task.getTaskDescription());
        priorityTextView.setText("Priority: " + task.getPriority());
        deadlineTextView.setText("Deadline: " + task.getDeadline());

        // Set up the Spinner with status options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.status_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        // Set the current status in the Spinner
        int position = adapter.getPosition(task.getStatus());
        spinnerStatus.setSelection(position);

        // Set click listener for the Submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the selected status here and update it in the database
                String selectedStatus = spinnerStatus.getSelectedItem().toString();
                String taskID = task.getTaskID();
                updateStatusInDatabase(taskID, selectedStatus);
            }
        });
    }

    private void updateStatusInDatabase(String taskID, String selectedStatus) {
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("Taskdata").child(taskID);

        // Update the 'status' field in the database
        taskRef.child("status").setValue(selectedStatus);

        // You can also show a Toast message or handle UI updates to indicate success
        Toast.makeText(TaskDetailsActivity.this, "Status updated successfully", Toast.LENGTH_SHORT).show();
    }
}
*/

package com.example.ntasks;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskDetailsActivity extends AppCompatActivity {

    private TextView taskNameTextView;
    private TextView taskDescriptionTextView;
    private TextView priorityTextView;
    private TextView deadlineTextView;
    private TextView statusTextView;
    private TextView assignedByTextView;
    private TextView attachmentTextView;

    private TextView clientTextView;
    private Spinner spinnerStatus;
    private Button buttonSubmit;
    private Button btnDownloadAttachment; // Add the Download Attachment button

    private TaskModel task;

    private ImageView clientimg,ivattach;// Add a member variable to store the TaskModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

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
        assignedByTextView = findViewById(R.id.textViewAssignedBy);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        attachmentTextView = findViewById(R.id.linkTextView);
        btnDownloadAttachment = findViewById(R.id.btnDownloadAttachment);
        clientTextView = findViewById(R.id.textViewClientLabel);
        clientimg = findViewById(R.id.imgClient);// Initialize the Download Attachment button
        ivattach = findViewById(R.id.ivattach);// Initialize the Download Attachment button

        // Set TextViews with task details
        taskNameTextView.setText("Task Name: " + task.getTaskName());
        taskDescriptionTextView.setText("Task Description: " + task.getTaskDescription());
        priorityTextView.setText("Priority: " + task.getPriority());
        deadlineTextView.setText("Assigned On: " + task.getDeadline());
        statusTextView.setText("Current Status: " + task.getStatus());
        assignedByTextView.setText("Assigned By: " + task.getAssignerdb());
        downloadAttachment();


        if(task.getClientdb() == null || task.getClientdb().equals("Select Client")){
            clientTextView.setVisibility(View.GONE);
            clientimg.setVisibility(View.GONE);
        }
        else{
            clientTextView.setText("Client:" + task.getClientdb());
        }

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

        // Add click listener for the "Download Attachment" button
        /*btnDownloadAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAttachment();
            }
        });*/


        String[] statusOptionsWithDefault = new String[]{
                getString(R.string.change_status_prompt),
                "ASSIGNED",
                "READ",
                "IN PROGRESS",
                "ON HOLD",
                "NEED DISCUSSION",
                "REVIEW",
                "DONE"
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the back button click
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void downloadAttachment() {
        // Get the original task ID associated with the displayed task
        String originalTaskId = task.getTaskID();

        // Query the "Attachments" node using the original task ID
        DatabaseReference attachmentsRef = FirebaseDatabase.getInstance().getReference("Attachments");

        attachmentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // Iterate through the children of Attachments node
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String taskId = snapshot.child("taskId").getValue(String.class);

                    // Check if the taskId matches the originalTaskId
                    if (taskId != null && taskId.equals(originalTaskId)) {
                        Log.d("AttachmentDebug", "Attachment entry found for Task ID: " + originalTaskId);

                        Toast.makeText(TaskDetailsActivity.this, "Attachment Available", Toast.LENGTH_LONG).show();
                        ivattach.setVisibility(View.VISIBLE);
                        btnDownloadAttachment.setVisibility(View.VISIBLE);

                        // If the entry exists, log the URL
                        String downloadUrl = snapshot.child("url").getValue(String.class);

                        // Display the URL as clickable link text
                        displayClickableLink(downloadUrl);

                        return;
                    }
                }

                // If no matching entry is found, log a message
                Log.d("AttachmentDebug", "Attachments entry does not exist for Task ID: " + originalTaskId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Log.e("AttachmentDebug", "Error retrieving attachment data: " + databaseError.getMessage());
            }
        });
    }

    private void displayClickableLink(String url) {
        // Use a TextView to display the clickable link
        TextView linkTextView = findViewById(R.id.linkTextView);

        // Set the autoLink property to web for automatic linking of URLs
        linkTextView.setAutoLinkMask(Linkify.WEB_URLS);

        // Set the text to the URL
        linkTextView.setText("Click To Download: " + url);

        // Set an onClickListener to open the URL when clicked
        linkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openURLInBrowser(url);
            }
        });
    }

    private void openURLInBrowser(String url) {
        // Create an Intent to open the URL in a web browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // Check if there is an app available to handle the Intent
        if (browserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(browserIntent);
        } else {
            // If no app is available, show a toast or handle it accordingly
            Toast.makeText(TaskDetailsActivity.this, "No app found to handle the URL", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStatusInDatabase(String taskID, String selectedStatus) {
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("Taskdata").child(taskID);

        // Update the 'statusdb' field in the database
        taskRef.child("statusdb").setValue(selectedStatus);

        // Update the 'lastchangeddb' field with the current timestamp
        taskRef.child("lastchangeddb").setValue(getCurrentTimestamp());

        // Update the status in the current TaskModel
        task.setStatus(selectedStatus);

        // You can also show a Toast message or handle UI updates to indicate success
        Toast.makeText(TaskDetailsActivity.this, "Status updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    // Method to get the current timestamp in the desired format
    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}



/*
package com.example.ntasks;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TaskDetailsActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            for (String key : extras.keySet()) {
                Log.d("Intent Extra", key + ": " + extras.get(key));
            }
        }
        // Retrieve task details from Intent
        TaskModel task = getIntent().getParcelableExtra("task");

        if (task != null) {
            Log.d("TaskModel", "TaskID: " + task.getTaskID());
            Log.d("TaskModel", "TaskName: " + task.getTaskName());
            Log.d("TaskModel", "TaskDescription: " + task.getTaskDescription());
            Log.d("TaskModel", "Priority: " + task.getPriority());
            Log.d("TaskModel", "Deadline: " + task.getDeadline());
            Log.d("TaskModel", "Status: " + task.getStatus());
        } else {
            Log.e("TaskModel", "Task is null");
        }

        if (task == null || task.getTaskID() == null) {
            // Handle the case where the task or taskID is null, perhaps show an error message
            Toast.makeText(this, "Error: Task details not available", Toast.LENGTH_SHORT).show();
        }
        Log.d("CheckTDA", "TaskID: " + task.getTaskID());
        // Update TextViews with task details
        TextView taskNameTextView = findViewById(R.id.textViewTaskName);
        TextView taskDescriptionTextView = findViewById(R.id.textViewTaskDescription);
        TextView priorityTextView = findViewById(R.id.textViewPriority);
        TextView deadlineTextView = findViewById(R.id.textViewDeadline);
        TextView taskStatusTextView = findViewById(R.id.textViewStatusLabel);

        taskNameTextView.setText("Task Name: " + task.getTaskName());
        taskDescriptionTextView.setText("Task Description: " + task.getTaskDescription());
        priorityTextView.setText("Priority: " + task.getPriority());
        deadlineTextView.setText("Deadline: " + task.getDeadline());
        taskStatusTextView.setText("Current Status: " + task.getStatus());

    }
}*/
