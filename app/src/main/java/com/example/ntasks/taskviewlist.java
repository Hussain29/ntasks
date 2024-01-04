/*
package com.example.ntasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class taskviewlist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference dbref;
    MyAdapter myAdapter;
    ArrayList<Userlist> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskviewlist);

        recyclerView = findViewById(R.id.taskrecy);
        dbref = FirebaseDatabase.getInstance().getReference("Taskdata");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

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
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String taskID = dataSnapshot.getKey();
                    String taskName = dataSnapshot.child("taskNamedb").getValue(String.class);
                    String taskDesc = dataSnapshot.child("taskDescriptiondb").getValue(String.class);
                    String priority = dataSnapshot.child("prioritydb").getValue(String.class);
                    String deadline = dataSnapshot.child("deadlinedb").getValue(String.class);
                    String statusdb = dataSnapshot.child("statusdb").getValue(String.class);
                    String assignedUserdb = dataSnapshot.child("assignedUserdb").getValue(String.class);

                    Log.d("FirebaseData", "Task ID: " + taskID);

                    Userlist userlist = new Userlist();
                    userlist.setTaskID(taskID);
                    userlist.setTaskName(taskName);
                    userlist.setTaskDesc(taskDesc);
                    userlist.setTaskprio(priority);
                    userlist.setTaskdeadl(deadline);
                    userlist.setStatusdb(statusdb);
                    userlist.setAssignedUserdb(assignedUserdb);
                    list.add(userlist);
                }

                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching data", error.toException());
                Toast.makeText(taskviewlist.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openTaskDetailsActivity(Userlist userlist) {
        Intent intent = new Intent(taskviewlist.this, TaskDetailsActivity.class);
        intent.putExtra("task", new TaskModel(userlist.getTaskID(), userlist.getTaskName(), userlist.getTaskDesc(), userlist.getTaskprio(), userlist.getTaskdeadl(), userlist.getStatusdb(), userlist.getAssignedUserdb()));
        startActivity(intent);
    }
}*/
package com.example.ntasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class taskviewlist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference dbref;
    MyAdapter myAdapter;
    ArrayList<Userlist> list;

    private ProgressDialog progressDialog;
    String assignerdbf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading tasks...");
        progressDialog.setCancelable(false);
        progressDialog.show(); // Show loading screen

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskviewlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("Tasks");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        assignerdbf = getCurrentUser();
        Log.d("TaskData", "Name: " + assignerdbf);
        recyclerView = findViewById(R.id.taskrecy);
        dbref = FirebaseDatabase.getInstance().getReference("Taskdata");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);


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

                    // Iterate through the tasks and filter based on the assigned user
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        String assignedUserdb = dataSnapshot.child("assignedUserdb").getValue(String.class);

                        // Check if the task is assigned to the currently logged-in user
                        if (assignedUserdb != null && assignedUserdb.equals(currentUserName)) {
                            // Task is assigned to the current user, retrieve task details

                            String taskID = dataSnapshot.getKey();
                            String taskName = dataSnapshot.child("taskNamedb").getValue(String.class);
                            String taskDesc = dataSnapshot.child("taskDescriptiondb").getValue(String.class);
                            String priority = dataSnapshot.child("prioritydb").getValue(String.class);
                            String deadline = dataSnapshot.child("deadlinedb").getValue(String.class);
                            String statusdb = dataSnapshot.child("statusdb").getValue(String.class);
                            // Add the missing assignedUserdb statement
                            assignedUserdb = dataSnapshot.child("assignedUserdb").getValue(String.class);
                            String assignerdb = dataSnapshot.child("assignerdb").getValue(String.class);
                            String clientdb = dataSnapshot.child("clientdb").getValue(String.class);
                            String lastchangeddb = dataSnapshot.child("lastchangeddb").getValue(String.class);

                            if (!statusdb.equals("COMPLETED")) {
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
                                userlist.setLastchangeddb(lastchangeddb);
                                list.add(0, userlist);
                                Log.d("LastCh", "Data- " + lastchangeddb);
                            }
                        }
                    }

                    // Sort the list based on the deadline
                    Collections.sort(list, new Comparator<Userlist>() {
                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                        @Override
                        public int compare(Userlist task1, Userlist task2) {
                            try {
                                Date deadline1 = dateFormat.parse(task1.getTaskdeadl());
                                Date deadline2 = dateFormat.parse(task2.getTaskdeadl());
                                return deadline1.compareTo(deadline2);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return 0;
                            } catch (java.text.ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });


                    myAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching data", error.toException());
                Toast.makeText(taskviewlist.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void openTaskDetailsActivity(Userlist userlist) {
        Intent intent = new Intent(taskviewlist.this, TaskDetailsActivity.class);
        intent.putExtra("task", new TaskModel(userlist.getTaskID(), userlist.getTaskName(), userlist.getTaskDesc(), userlist.getTaskprio(), userlist.getTaskdeadl(), userlist.getStatusdb(), userlist.getAssignedUserdb(), userlist.getAssignerdb(), userlist.getClientdb()));
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
