package com.example.ntasks;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllTasksDetailsActivity extends AppCompatActivity {

    private TextView taskNameTextView;
    private TextView taskDescriptionTextView;
    private TextView priorityTextView;
    private TextView deadlineTextView;
    private Spinner spinnerStatus;
    private TextView assignedByTextView;

    private TextView statusTextView;
    private TextView assignedToTextView; // Added TextView for Assigned To
    private TextView attachmentTextView;
    private Button btnDownloadAttachment;
    private Button buttonSubmit;
    private TextView clientTextView;

    private ImageView clientimg;


    private TaskModel task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks_details);
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("All Tasks");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        // Retrieve task details from Intent
        task = getIntent().getParcelableExtra("task");



        if (task == null || task.getTaskID() == null) {
            Toast.makeText(this, "Error: Task details not available", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize Views
        taskNameTextView = findViewById(R.id.textViewTaskName);
        statusTextView = findViewById(R.id.textViewStatusLabel);
        taskDescriptionTextView = findViewById(R.id.textViewTaskDescription);
        priorityTextView = findViewById(R.id.textViewPriority);
        deadlineTextView = findViewById(R.id.textViewDeadline);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        assignedByTextView = findViewById(R.id.textViewAssignedBy);
        assignedToTextView = findViewById(R.id.textViewAssignedTo); // Initialize the Assigned To TextView
        buttonSubmit = findViewById(R.id.buttonSubmit);
        attachmentTextView = findViewById(R.id.linkTextView);
        btnDownloadAttachment = findViewById(R.id.btnDownloadAttachment);
        clientTextView = findViewById(R.id.textViewClientLabel);
        clientimg = findViewById(R.id.imgClient);

        Log.d("HUSH", "clients: " + task.getClientdb());
        if(task.getClientdb() != null && task.getClientdb().equals("Select Client") ){
            clientTextView.setVisibility(View.GONE);
            clientimg.setVisibility(View.GONE);
        }
        else{
            clientTextView.setText("Client:" + task.getClientdb());

        }

        // Set TextViews with task details
        taskNameTextView.setText("Task Name: " + task.getTaskName());
        taskDescriptionTextView.setText("Task Description: " + task.getTaskDescription());
        priorityTextView.setText("Priority: " + task.getPriority());
        deadlineTextView.setText("Assigned On: " + task.getDeadline());
        statusTextView.setText("Current Status: " + task.getStatus());
        assignedByTextView.setText("Assigned By: " + task.getAssignerdb());
        assignedToTextView.setText("Assigned To: " + task.getAssignedUser());
        // (Assuming the assigned user is available in the task model)

        // Set up Spinner
        setupSpinner();

        // Add click listener for the "Download Attachment" button
        btnDownloadAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAttachment();
            }
        });

        // Add click listener for the "Submit Changes" button
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
    private void setupSpinner() {
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
            Toast.makeText(AllTasksDetailsActivity.this, "No app found to handle the URL", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStatusInDatabase(String taskID, String selectedStatus) {
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("Taskdata").child(taskID);

        // Update the 'status' field in the database
        taskRef.child("statusdb").setValue(selectedStatus);

        // Update the status in the current TaskModel
        task.setStatus(selectedStatus);

        // You can also show a Toast message or handle UI updates to indicate success
        Toast.makeText(AllTasksDetailsActivity.this, "Status updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
