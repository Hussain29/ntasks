package com.example.ntasks;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompletedTasksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference dbref;
    MyAdapter myAdapter;
    ArrayList<Userlist> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("Completed Tasks");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        recyclerView = findViewById(R.id.completedTasksRecycler);
        dbref = FirebaseDatabase.getInstance().getReference("Taskdata");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        // Set up item click listener
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Userlist userlist) {
                openTaskDetailsActivity(userlist);
            }
        });

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                // Get the currently logged-in user's information
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String currentUserName = currentUser.getDisplayName();

                    // Iterate through the tasks and filter based on the completed status
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        String statusdb = dataSnapshot.child("statusdb").getValue(String.class);
                        String assignedUserdb = dataSnapshot.child("assignedUserdb").getValue(String.class);
                        String assignerdb = dataSnapshot.child("assignerdb").getValue(String.class);

                        // Check if the task is completed and user conditions are met
                        if (statusdb != null && statusdb.equals("COMPLETED")
                                && ((assignerdb.equals(currentUserName) && assignedUserdb.equals(currentUserName))
                                || !assignerdb.equals(assignedUserdb))) {

                            // Task is completed and meets user conditions, retrieve task details
                            String taskID = dataSnapshot.getKey();
                            String taskName = dataSnapshot.child("taskNamedb").getValue(String.class);
                            String taskDesc = dataSnapshot.child("taskDescriptiondb").getValue(String.class);
                            String priority = dataSnapshot.child("prioritydb").getValue(String.class);
                            String deadline = dataSnapshot.child("deadlinedb").getValue(String.class);
                            String clientdb = dataSnapshot.child("clientdb").getValue(String.class);

                            Userlist userlist = new Userlist();
                            userlist.setTaskID(taskID);
                            userlist.setTaskName(taskName);
                            userlist.setTaskDesc(taskDesc);
                            userlist.setTaskprio(priority);
                            userlist.setTaskdeadl(deadline);
                            userlist.setStatusdb(statusdb);
                            userlist.setAssignedUserdb(assignedUserdb);
                            userlist.setAssignerdb(assignerdb);
                            userlist.setClientdb(clientdb);
                            list.add(0, userlist);
                        }
                    }

                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CompletedTasksActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openTaskDetailsActivity(Userlist userlist) {
        // Create an intent to open TaskDetailsActivity
        Intent intent = new Intent(CompletedTasksActivity.this, AllTasksDetailsActivity.class);

        // Pass the task details to the intent
        intent.putExtra("task", new TaskModel(userlist.getTaskID(), userlist.getTaskName(), userlist.getTaskDesc(), userlist.getTaskprio(), userlist.getTaskdeadl(), userlist.getStatusdb(), userlist.getAssignedUserdb(), userlist.getAssignerdb(), userlist.getClientdb()));

        // Start the TaskDetailsActivity
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the home button click
                onBackPressed();
                // Optional: Close the current activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


/*
package com.example.ntasks;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompletedTasksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference dbref;
    MyAdapter myAdapter;
    ArrayList<Userlist> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("Completed Tasks");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        recyclerView = findViewById(R.id.completedTasksRecycler);
        dbref = FirebaseDatabase.getInstance().getReference("Taskdata");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                // Get the currently logged-in user's information
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String currentUserName = currentUser.getDisplayName();

                    // Iterate through the tasks and filter based on the completed status
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        String statusdb = dataSnapshot.child("statusdb").getValue(String.class);
                        String assignedUserdb = dataSnapshot.child("assignedUserdb").getValue(String.class);
                        String assignerdb = dataSnapshot.child("assignerdb").getValue(String.class);

                        // Check if the task is completed and user conditions are met
                        if (statusdb != null && statusdb.equals("COMPLETED") &&
                                (assignedUserdb.equals(currentUserName) || assignerdb.equals(currentUserName))) {

                            // Task is completed and meets user conditions, retrieve task details
                            String taskID = dataSnapshot.getKey();
                            String taskName = dataSnapshot.child("taskNamedb").getValue(String.class);
                            String taskDesc = dataSnapshot.child("taskDescriptiondb").getValue(String.class);
                            String priority = dataSnapshot.child("prioritydb").getValue(String.class);
                            String deadline = dataSnapshot.child("deadlinedb").getValue(String.class);
                            String clientdb = dataSnapshot.child("clientdb").getValue(String.class);

                            Userlist userlist = new Userlist();
                            userlist.setTaskID(taskID);
                            userlist.setTaskName(taskName);
                            userlist.setTaskDesc(taskDesc);
                            userlist.setTaskprio(priority);
                            userlist.setTaskdeadl(deadline);
                            userlist.setStatusdb(statusdb);
                            userlist.setAssignedUserdb(assignedUserdb);
                            userlist.setAssignerdb(assignerdb);
                            userlist.setClientdb(clientdb);
                            list.add(0, userlist);
                        }
                    }

                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CompletedTasksActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the home button click
                onBackPressed();
                // Optional: Close the current activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
*/
