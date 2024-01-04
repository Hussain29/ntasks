// ClientTasksActivity.java
package com.example.ntasks;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

import java.util.ArrayList;

public class ClientTasksActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Userlist> list;

    String selectedClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_tasks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("CLIENT");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedClient")) {
            selectedClient = intent.getStringExtra("selectedClient");
            getSupportActionBar().setTitle(selectedClient + "'s Tasks");
        }

        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(this);

        loadTasksForSelectedClient();
    }

    private void loadTasksForSelectedClient() {
        DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference("Taskdata");
        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String currentUserName = currentUser.getDisplayName();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String assignedUserdb = dataSnapshot.child("assignedUserdb").getValue(String.class);
                        String clientdb = dataSnapshot.child("clientdb").getValue(String.class);
                        if (clientdb != null && clientdb.equals(selectedClient)) {
                            String taskID = dataSnapshot.getKey();
                            String taskName = dataSnapshot.child("taskNamedb").getValue(String.class);
                            String taskDesc = dataSnapshot.child("taskDescriptiondb").getValue(String.class);
                            String priority = dataSnapshot.child("prioritydb").getValue(String.class);
                            String deadline = dataSnapshot.child("deadlinedb").getValue(String.class);
                            String statusdb = dataSnapshot.child("statusdb").getValue(String.class);
                            String assignerdb = dataSnapshot.child("assignerdb").getValue(String.class);

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
                Log.e("Firebase", "Error fetching data", error.toException());
                Toast.makeText(ClientTasksActivity.this, "Failed to load tasks", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(Userlist userlist) {
        openTaskDetailsActivity(userlist);
    }

    private void openTaskDetailsActivity(Userlist userlist) {
        Intent intent = new Intent(ClientTasksActivity.this, TaskDetailsActivity.class);
        intent.putExtra("task", new TaskModel(
                userlist.getTaskID(),
                userlist.getTaskName(),
                userlist.getTaskDesc(),
                userlist.getTaskprio(),
                userlist.getTaskdeadl(),
                userlist.getStatusdb(),
                userlist.getAssignedUserdb(),
                userlist.getAssignerdb(),
                userlist.getClientdb()));
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
