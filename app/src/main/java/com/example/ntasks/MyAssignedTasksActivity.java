
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
import java.util.List;

public class MyAssignedTasksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference dbref;
    MyAdapter myAdapter;
    ArrayList<Userlist> list;
    Button btnsort;

    ArrayAdapter<String> spinnerAdapter;
    List<String> registeredUserList;

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
        actionBar.setTitle("ASSIGNED TASKS");

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

        Spinner spinnerUserFilter = findViewById(R.id.spinnerUserSort);

        registeredUserList = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, registeredUserList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserFilter.setAdapter(spinnerAdapter);

        retrieveRegisteredUsers();

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Userlist userlist) {
                openTaskDetailsActivity(userlist);
            }
        });

        spinnerUserFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                            String lastchangeddb = dataSnapshot.child("lastchangeddb").getValue(String.class);


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
                                userlist.setLastchangeddb(lastchangeddb);
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

    private void filterTasksByUser(String selectedUser) {
        list.clear();

        if (selectedUser.equals("Select User")) {
            // Display the full list without filtering
            retrieveAllTasks();
        } else {
            // Filter tasks by the selected user
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        String currentUserName = currentUser.getDisplayName();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String assignedUserdb = dataSnapshot.child("assignedUserdb").getValue(String.class);
                            String statusdb = dataSnapshot.child("statusdb").getValue(String.class);
                            String assignerdb = dataSnapshot.child("assignerdb").getValue(String.class);

                            if (assignedUserdb != null && assignedUserdb.equals(selectedUser) && !statusdb.equals("COMPLETED") && assignerdb != null && assignerdb.equals(currentUserName)) {
                                String taskID = dataSnapshot.getKey();
                                String taskName = dataSnapshot.child("taskNamedb").getValue(String.class);
                                String taskDesc = dataSnapshot.child("taskDescriptiondb").getValue(String.class);
                                String priority = dataSnapshot.child("prioritydb").getValue(String.class);
                                String deadline = dataSnapshot.child("deadlinedb").getValue(String.class);
                                String lastchangeddb = dataSnapshot.child("lastchangeddb").getValue(String.class);


                                Userlist userlist = new Userlist();
                                userlist.setTaskID(taskID);
                                userlist.setTaskName(taskName);
                                userlist.setTaskDesc(taskDesc);
                                userlist.setTaskprio(priority);
                                userlist.setTaskdeadl(deadline);
                                userlist.setStatusdb(statusdb);
                                userlist.setAssignedUserdb(assignedUserdb);
                                userlist.setAssignerdb(assignerdb);
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
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Error fetching data", error.toException());
                    Toast.makeText(MyAssignedTasksActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void retrieveAllTasks() {
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String currentUserName = currentUser.getDisplayName();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String assignerdb = dataSnapshot.child("assignerdb").getValue(String.class);
                        String statusdb = dataSnapshot.child("statusdb").getValue(String.class);

                        if (assignerdb != null && assignerdb.equals(currentUserName) && !statusdb.equals("COMPLETED")) {
                            String taskID = dataSnapshot.getKey();
                            String taskName = dataSnapshot.child("taskNamedb").getValue(String.class);
                            String taskDesc = dataSnapshot.child("taskDescriptiondb").getValue(String.class);
                            String priority = dataSnapshot.child("prioritydb").getValue(String.class);
                            String deadline = dataSnapshot.child("deadlinedb").getValue(String.class);
                            String assignedUserdb = dataSnapshot.child("assignedUserdb").getValue(String.class);
                            String lastchangeddb = dataSnapshot.child("lastchangeddb").getValue(String.class);


                            Userlist userlist = new Userlist();
                            userlist.setTaskID(taskID);
                            userlist.setTaskName(taskName);
                            userlist.setTaskDesc(taskDesc);
                            userlist.setTaskprio(priority);
                            userlist.setTaskdeadl(deadline);
                            userlist.setStatusdb(statusdb);
                            userlist.setAssignerdb(assignerdb);
                            userlist.setAssignedUserdb(assignedUserdb);
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

