
package com.example.ntasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MyAssignedTasksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference dbref;
    MyAdapter myAdapter;
    ArrayList<Userlist> list;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading tasks...");
        progressDialog.setCancelable(false);
        progressDialog.show(); // Show loading screen
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("Assigned Tasks");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_assigned_tasks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.myAssignedTasksRecycler);
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

                    // Iterate through the tasks and filter based on the assigner
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String assignerdb = dataSnapshot.child("assignerdb").getValue(String.class);

                        // Check if the task was assigned by the currently logged-in user
                        if (assignerdb != null && assignerdb.equals(currentUserName)) {
                            // Task was assigned by the current user, retrieve task details
                            String taskID = dataSnapshot.getKey();
                            String taskName = dataSnapshot.child("taskNamedb").getValue(String.class);
                            String taskDesc = dataSnapshot.child("taskDescriptiondb").getValue(String.class);
                            String priority = dataSnapshot.child("prioritydb").getValue(String.class);
                            String deadline = dataSnapshot.child("deadlinedb").getValue(String.class);
                            String statusdb = dataSnapshot.child("statusdb").getValue(String.class);
                            String assignedUserdb = dataSnapshot.child("assignedUserdb").getValue(String.class);


                            if(!statusdb.equals("COMPLETED")) {
                                Userlist userlist = new Userlist();
                                userlist.setTaskID(taskID);
                                userlist.setTaskName(taskName);
                                userlist.setTaskDesc(taskDesc);
                                userlist.setTaskprio(priority);
                                userlist.setTaskdeadl(deadline);
                                userlist.setStatusdb(statusdb);
                                userlist.setAssignedUserdb(assignedUserdb);
                                userlist.setAssignerdb(assignerdb);
                                list.add(0, userlist);
                            }
                        }
                    }
                    Collections.sort(list, new Comparator<Userlist>() {
                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                        @Override
                        public int compare(Userlist task1, Userlist task2) {
                            try {
                                Date lastChanged1 = dateFormat.parse(task1.getLastchangeddb());
                                Date lastChanged2 = dateFormat.parse(task2.getLastchangeddb());

                                // Compare in reverse order for descending order
                                return lastChanged2.compareTo(lastChanged1);
                            } catch (ParseException | java.text.ParseException e) {
                                e.printStackTrace();
                                return 0;
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
                Toast.makeText(MyAssignedTasksActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openTaskDetailsActivity(Userlist userlist) {
        Intent intent = new Intent(MyAssignedTasksActivity.this, AssgnTaskDetailsActivity.class);
        intent.putExtra("task", new TaskModel(userlist.getTaskID(), userlist.getTaskName(), userlist.getTaskDesc(), userlist.getTaskprio(), userlist.getTaskdeadl(), userlist.getStatusdb(), userlist.getAssignedUserdb(), userlist.getAssignerdb(), userlist.getClientdb()));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the home button click
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

