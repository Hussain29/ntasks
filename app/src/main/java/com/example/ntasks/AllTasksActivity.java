package com.example.ntasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class AllTasksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference dbref;
    MyAdapter myAdapter;
    ArrayList<Userlist> list;

    ArrayAdapter<String> spinnerAdapter;
    List<String> registeredUserList;

    Button btnsort;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ALL TASKS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading tasks...");
        progressDialog.setCancelable(false);
        progressDialog.show(); // Show loading screen

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Registered Users");

        // Assuming you have a Spinner reference in your layout file with the ID "spinnerUserSort"
        Spinner spinnerUserSort = findViewById(R.id.spinnerUserSort);

        registeredUserList = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, registeredUserList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserSort.setAdapter(spinnerAdapter);

        retrieveRegisteredUsers();

        recyclerView = findViewById(R.id.allTasksRecycler);
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

        spinnerUserSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUser = registeredUserList.get(position);
                filterTasksByUser(selectedUser);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
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
                    String assignerdb = dataSnapshot.child("assignerdb").getValue(String.class);
                    String clientdb = dataSnapshot.child("clientdb").getValue(String.class);
                    String lastchangeddb = dataSnapshot.child("lastchangeddb").getValue(String.class);

                    if (assignerdb != null && assignedUserdb != null && !assignerdb.equals(assignedUserdb) && !statusdb.equals("COMPLETED")) {
                        Userlist userlist = new Userlist();
                        userlist.setTaskID(taskID);
                        userlist.setTaskName(taskName);
                        userlist.setTaskDesc(taskDesc);
                        userlist.setTaskprio(priority);
                        userlist.setTaskdeadl(deadline);
                        userlist.setStatusdb(statusdb);
                        userlist.setAssignerdb(assignerdb);
                        userlist.setAssignedUserdb(assignedUserdb);
                        userlist.setClientdb(clientdb);
                        userlist.setLastchangeddb(lastchangeddb);
                        list.add(0, userlist);
                    }
                }

                Collections.sort(list, new Comparator<Userlist>() {
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                    @Override
                    public int compare(Userlist task1, Userlist task2) {
                        try {
                            Date lastChanged1 = dateFormat.parse(task1.getLastchangeddb());
                            Date lastChanged2 = dateFormat.parse(task2.getLastchangeddb());
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching data", error.toException());
                Toast.makeText(AllTasksActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrieveRegisteredUsers() {
        DatabaseReference dbrefRegisteredUsers = FirebaseDatabase.getInstance().getReference().child("Registered Users");

        dbrefRegisteredUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                registeredUserList.clear();
                registeredUserList.add("Select User");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String user = dataSnapshot.child("name").getValue(String.class);
                    registeredUserList.add(user);
                }
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching registered users", error.toException());
            }
        });
    }

    private void openTaskDetailsActivity(Userlist userlist) {
        Intent intent = new Intent(AllTasksActivity.this, AllTasksDetailsActivity.class);
        intent.putExtra("task", new TaskModel(userlist.getTaskID(), userlist.getTaskName(), userlist.getTaskDesc(), userlist.getTaskprio(), userlist.getTaskdeadl(), userlist.getStatusdb(), userlist.getAssignedUserdb(), userlist.getAssignerdb(), userlist.getClientdb()));
        startActivity(intent);
    }

    private void filterTasksByUser(String selectedUser) {
        list.clear();

        if (selectedUser.equals("Select User")) {
            // Display the full list without filtering
            retrieveAllTasks();
        } else {

            dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String assignedUser = dataSnapshot.child("assignedUserdb").getValue(String.class);
                        if (assignedUser != null && assignedUser.equals(selectedUser)) {
                            String taskID = dataSnapshot.getKey();
                            String taskName = dataSnapshot.child("taskNamedb").getValue(String.class);
                            String taskDesc = dataSnapshot.child("taskDescriptiondb").getValue(String.class);
                            String priority = dataSnapshot.child("prioritydb").getValue(String.class);
                            String deadline = dataSnapshot.child("deadlinedb").getValue(String.class);
                            String statusdb = dataSnapshot.child("statusdb").getValue(String.class);
                            String assignedUserdb = dataSnapshot.child("assignedUserdb").getValue(String.class);
                            String assignerdb = dataSnapshot.child("assignerdb").getValue(String.class);
                            String clientdb = dataSnapshot.child("clientdb").getValue(String.class);
                            String lastchangeddb = dataSnapshot.child("lastchangeddb").getValue(String.class);


                            if (assignerdb != null && assignedUserdb != null && !assignerdb.equals(assignedUserdb) && !statusdb.equals("COMPLETED")) {
                                Userlist userlist = new Userlist();
                                userlist.setTaskID(taskID);
                                userlist.setTaskName(taskName);
                                userlist.setTaskDesc(taskDesc);
                                userlist.setTaskprio(priority);
                                userlist.setTaskdeadl(deadline);
                                userlist.setStatusdb(statusdb);
                                userlist.setAssignerdb(assignerdb);
                                userlist.setAssignedUserdb(assignedUser);
                                userlist.setClientdb(clientdb);
                                userlist.setLastchangeddb(lastchangeddb);
                                list.add(0, userlist);
                            }
                        }
                    }
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Error fetching data", error.toException());
                    Toast.makeText(AllTasksActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void retrieveAllTasks() {
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String taskID = dataSnapshot.getKey();
                    String taskName = dataSnapshot.child("taskNamedb").getValue(String.class);
                    String taskDesc = dataSnapshot.child("taskDescriptiondb").getValue(String.class);
                    String priority = dataSnapshot.child("prioritydb").getValue(String.class);
                    String deadline = dataSnapshot.child("deadlinedb").getValue(String.class);
                    String statusdb = dataSnapshot.child("statusdb").getValue(String.class);
                    String assignedUserdb = dataSnapshot.child("assignedUserdb").getValue(String.class);
                    String assignerdb = dataSnapshot.child("assignerdb").getValue(String.class);
                    String clientdb = dataSnapshot.child("clientdb").getValue(String.class);
                    String lastchangeddb = dataSnapshot.child("lastchangeddb").getValue(String.class);

                    if (assignerdb != null && assignedUserdb != null && !assignerdb.equals(assignedUserdb) && !statusdb.equals("COMPLETED")) {
                        Userlist userlist = new Userlist();
                        userlist.setTaskID(taskID);
                        userlist.setTaskName(taskName);
                        userlist.setTaskDesc(taskDesc);
                        userlist.setTaskprio(priority);
                        userlist.setTaskdeadl(deadline);
                        userlist.setStatusdb(statusdb);
                        userlist.setAssignerdb(assignerdb);
                        userlist.setAssignedUserdb(assignedUserdb);
                        userlist.setClientdb(clientdb);
                        userlist.setLastchangeddb(lastchangeddb);
                        list.add(0, userlist);
                    }
                }

                Collections.sort(list, new Comparator<Userlist>() {
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                    @Override
                    public int compare(Userlist task1, Userlist task2) {
                        try {
                            Date lastChanged1 = dateFormat.parse(task1.getLastchangeddb());
                            Date lastChanged2 = dateFormat.parse(task2.getLastchangeddb());
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching data", error.toException());
                Toast.makeText(AllTasksActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
