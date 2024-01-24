package com.example.ntasks;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddTaskActivity extends AppCompatActivity {

    private RadioGroup radioGroupPriority;
    private RadioButton radioButtonHigh;
    private RadioButton radioButtonMedium;
    private RadioButton radioButtonLow;
    private TextInputEditText editTextDeadline;
    private Button buttonAddTask;
    private TextInputEditText editTextTaskName;
    private TextInputEditText editTextTaskDescription;
    private Spinner spinnerAssignedUser1;
    private Spinner spinnerAssignedUser2;
    private Spinner spinnerAssignedUser3;
    private Spinner spinnerClients;
    private CheckBox checkBoxSendToAll;
    private Button buttonSendToAll;
    private ProgressDialog progressDialog;



    DatabaseReference taskRef;
    DatabaseReference userRef;

    private DatabaseReference clientsRef;
    private Button addbtn;

    ArrayList<String> userList = new ArrayList<>();

    ////initializations for attachments
    private static final int PICK_FILE_REQUEST = 1; // You can use any integer value
    private static final String TAG = "Main";
    private StorageReference storageRef;
    private DatabaseReference databaseRef;

    // Variable to store the file URI
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD TASK");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


        taskRef = FirebaseDatabase.getInstance().getReference().child("Taskdata");
        userRef = FirebaseDatabase.getInstance().getReference().child("Registered Users");
        clientsRef = FirebaseDatabase.getInstance().getReference().child("Clients");

        // Initialize views
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        editTextTaskName = findViewById(R.id.editTextTaskName);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        radioButtonHigh = findViewById(R.id.radioButtonHigh);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        editTextDeadline = findViewById(R.id.editTextDeadline);
        buttonAddTask = findViewById(R.id.addTaskButton);
        spinnerAssignedUser1 = findViewById(R.id.spinnerAssignedUser1);
        spinnerAssignedUser2 = findViewById(R.id.spinnerAssignedUser2);
        spinnerAssignedUser3 = findViewById(R.id.spinnerAssignedUser3);
        checkBoxSendToAll = findViewById(R.id.checkBoxSendToAll);
        buttonSendToAll = findViewById(R.id.buttonSendToAll);
        addbtn = findViewById(R.id.btn_attach);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding Task...");
        progressDialog.setCancelable(false);
        spinnerClients = findViewById(R.id.spinnerClients);


        setCurrentDate();
        setupSpinnerWithUserNames();
        setupSpinnerWithClients();

        spinnerAssignedUser1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    spinnerAssignedUser2.setVisibility(View.VISIBLE);
                    spinnerAssignedUser2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            if (position > 0) {
                                spinnerAssignedUser3.setVisibility(View.VISIBLE);
                            } else {
                                spinnerAssignedUser3.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                        }
                    });
                } else {
                    spinnerAssignedUser2.setVisibility(View.GONE);
                    spinnerAssignedUser3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTaskActivity.this, "Select any type of file", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST);
            }
        });

        FirebaseApp.initializeApp(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("Attachments");

        /*editTextDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });*/

        checkBoxSendToAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonSendToAll.setVisibility(View.VISIBLE);
                } else {
                    buttonSendToAll.setVisibility(View.GONE);
                }
            }
        });

        buttonSendToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTaskToAllUsers();
            }
        });

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();


                if (fileUri != null) {
                    uploadFileToStorage(fileUri);
                } else {
                    insertTaskData();
                }


                progressDialog.hide();
            }
        });
    }

    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());
        editTextDeadline.setText(formattedDate);
    }

    private void setupSpinnerWithClients() {
        clientsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> clientNames = new ArrayList<>();

                clientNames.add("Select Client");

                for (DataSnapshot clientSnapshot : snapshot.getChildren()) {
                    String clientId = clientSnapshot.getKey(); // Get the client ID
                    String clientName = clientSnapshot.getValue(String.class); // Get the client name

                    // Assuming each client node has a 'name' field
                    if (clientName != null) {
                        clientNames.add(clientName);
                    }
                }

                // Sort the clientNames list alphabetically
                Collections.sort(clientNames.subList(1, clientNames.size()));

                // Update the client spinner adapter with client names
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddTaskActivity.this, android.R.layout.simple_spinner_item, clientNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerClients.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }


private void setupSpinnerWithUserNames() {
    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            List<String> userNames = new ArrayList<>();

            userNames.add("Select User");

            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                // Assuming each user node has a 'name' field
                String userName = userSnapshot.child("name").getValue(String.class);
                if (userName != null) {
                    userNames.add(userName);
                }
            }

            // Sort the userNames list alphabetically
            Collections.sort(userNames.subList(1, userNames.size()));

            // Update the spinner adapter with user names
            ArrayAdapter<String> adapter = new ArrayAdapter<>(AddTaskActivity.this, android.R.layout.simple_spinner_item, userNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAssignedUser1.setAdapter(adapter);
            spinnerAssignedUser2.setAdapter(adapter);
            spinnerAssignedUser3.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            // Handle error
        }
    });
}

    private List<String> insertTaskData() {
        // Insert task data to Firebase
        String taskNamedb = editTextTaskName.getText().toString();
        String taskDescriptiondb = editTextTaskDescription.getText().toString();
        String prioritydb = getSelectedPriority();
        String deadlinedb = editTextDeadline.getText().toString();
        String assignedUserdb1 = spinnerAssignedUser1.getSelectedItem().toString();
        String assignedUserdb2 = spinnerAssignedUser2.getSelectedItem().toString();
        String assignedUserdb3 = spinnerAssignedUser3.getSelectedItem().toString();
        String clientdb = spinnerClients.getSelectedItem().toString();
        String assignerdb = getCurrentUser();
        String defaultStatus = "ASSIGNED";

        if (TextUtils.isEmpty(taskNamedb) || TextUtils.isEmpty(taskDescriptiondb) || TextUtils.isEmpty(prioritydb) || TextUtils.isEmpty(deadlinedb) || assignedUserdb1.equals("Select User")) {
            Toast.makeText(this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
            return Collections.emptyList();
        }

        List<String> taskIds = new ArrayList<>();

        // Add a new task for each selected user
        if (!assignedUserdb1.equals("Select User")) {
            taskIds.add(addTaskForUser(taskNamedb, taskDescriptiondb, prioritydb, deadlinedb, defaultStatus, assignedUserdb1, clientdb));

            // Schedule notification for the first user
            long notificationTimeMillis = convertDeadlineToMillis(deadlinedb);
            scheduleNotification(taskIds.get(0), taskNamedb, assignedUserdb1, notificationTimeMillis);
        }
        if (!assignedUserdb2.equals("Select User")) {
            taskIds.add(addTaskForUser(taskNamedb, taskDescriptiondb, prioritydb, deadlinedb, defaultStatus, assignedUserdb2, clientdb));

            // Schedule notification for the second user
            long notificationTimeMillis = convertDeadlineToMillis(deadlinedb);
            scheduleNotification(taskIds.get(1), taskNamedb, assignedUserdb2, notificationTimeMillis);
        }
        if (!assignedUserdb3.equals("Select User")) {
            taskIds.add(addTaskForUser(taskNamedb, taskDescriptiondb, prioritydb, deadlinedb, defaultStatus, assignedUserdb3, clientdb));

            // Schedule notification for the third user
            long notificationTimeMillis = convertDeadlineToMillis(deadlinedb);
            scheduleNotification(taskIds.get(2), taskNamedb, assignedUserdb3, notificationTimeMillis);
        }

        Toast.makeText(AddTaskActivity.this, "Task sent!", Toast.LENGTH_SHORT).show();
        Log.d("TaskData", "Name: " + assignerdb);
        finish();

        return taskIds;
    }


    private String getSelectedPriority() {
        int selectedId = radioGroupPriority.getCheckedRadioButtonId();
        if (selectedId == R.id.radioButtonHigh) {
            return "HIGH";
        } else if (selectedId == R.id.radioButtonMedium) {
            return "MEDIUM";
        } else if (selectedId == R.id.radioButtonLow) {
            return "LOW";
        }
        return "";
    }

    private void sendTaskToAllUsers() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String userName = userSnapshot.child("name").getValue(String.class);
                    if (userName != null) {
                        userList.add(userName);
                    }
                }

                // Now userList contains all user names, including "Select User"

                for (int i = 0; i < userList.size(); i++) {
                    String userName = userList.get(i);
                    if (!userName.equals("Select User")) {
                        // Get task details
                        String taskName = editTextTaskName.getText().toString();
                        String taskDescription = editTextTaskDescription.getText().toString();
                        String priority = getSelectedPriority();
                        String deadline = editTextDeadline.getText().toString();
                        String assignedUser = userName;
                        String clientdb = spinnerClients.getSelectedItem().toString();
                        String assignerdb = getCurrentUser();

                        if (TextUtils.isEmpty(taskName) || TextUtils.isEmpty(taskDescription) || TextUtils.isEmpty(priority) || TextUtils.isEmpty(deadline)) {
                            Toast.makeText(AddTaskActivity.this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Add task for the user
                        addTaskForUser(taskName, taskDescription, priority, deadline, "ASSIGNED", assignedUser, clientdb);
                    }
                }
                Toast.makeText(AddTaskActivity.this, "Tasks sent to all users", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(AddTaskActivity.this, "Failed to fetch users", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String addTaskForUser(String taskNamedb, String taskDescriptiondb, String prioritydb,
                                  String deadlinedb, String defaultStatus, String assignedUserdb, String clientdb) {
        String assignerdb = getCurrentUser();
        Log.d("TaskData", "Assigner: " + assignerdb);

        // Get the current timestamp
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String lastChangedTimestamp = dateFormat.format(currentTimeMillis);

        DatabaseReference newTaskRef = taskRef.push();
        newTaskRef.setValue(new taskdb(taskNamedb, taskDescriptiondb, prioritydb, deadlinedb, defaultStatus, assignedUserdb, assignerdb, clientdb, lastChangedTimestamp));
        return newTaskRef.getKey();
    }


    private String getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getDisplayName(); // Assuming you have set the display name during authentication
        } else {
            // Handle the case when the user is not authenticated
            return "Unknown User";
        }
    }

    // ... (Your existing methods remain unchanged)

    private long convertDeadlineToMillis(String deadline) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date date = dateFormat.parse(deadline);
            if (date != null) {
                return date.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        return 0L;
    }

    private void scheduleNotification(String taskId, String taskName, String assignedUser, long notificationTimeMillis) {
        Intent notificationIntent = new Intent(this, TaskNotificationReceiver.class);
        notificationIntent.putExtra("taskId", taskId);
        notificationIntent.putExtra("taskName", taskName);
        notificationIntent.putExtra("assignedUser", assignedUser);

        // Use FLAG_IMMUTABLE for PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        Log.d(TAG, "Scheduling notification for Task ID: " + taskId);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC, notificationTimeMillis, pendingIntent);
        } else {
            Log.e(TAG, "AlarmManager is null");
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get the selected file URI
            fileUri = data.getData();
            Toast.makeText(this, "File selected: " + fileUri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
        }
    }


    private void uploadFileToStorage(Uri fileUri) {
        StorageReference fileRef = storageRef.child("uploads/" + fileUri.getLastPathSegment());

        UploadTask uploadTask = fileRef.putFile(fileUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                List<String> taskIds = insertTaskData();
                storeFileMetadata(uri.toString(), taskIds);
                progressDialog.dismiss();
            });
        }).addOnFailureListener(exception -> {
            Log.e(TAG, "Upload failed: " + exception.getMessage());
            progressDialog.dismiss();
        });
    }

    private void storeFileMetadata(String downloadUrl, List<String> taskIds) {
        for (String taskId : taskIds) {
            String fileId = databaseRef.push().getKey();
            Map<String, Object> fileMetadata = new HashMap<>();
            fileMetadata.put("url", downloadUrl);
            fileMetadata.put("timestamp", System.currentTimeMillis());
            fileMetadata.put("taskId", taskId);
            databaseRef.child(fileId).setValue(fileMetadata);
        }
    }
}

