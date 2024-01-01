// AllTasksActivity.java

package com.example.ntasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class PersonalTasksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference dbref;
    MyAdapter myAdapter;
    ArrayList<Userlist> list;
    private TextView Header;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading tasks...");
        progressDialog.setCancelable(false);
        progressDialog.show(); // Show loading screen

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.allTasksRecycler);
        Header = findViewById(R.id.LabelTitle);
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

                            if (assignerdb != null && assignedUserdb != null && assignerdb.equals(assignedUserdb)) {
                                Header.setVisibility(View.GONE);
                                Userlist userlist = new Userlist();
                                userlist.setTaskID(taskID);
                                userlist.setTaskName(taskName);
                                userlist.setTaskDesc(taskDesc);
                                userlist.setTaskprio(priority);
                                userlist.setTaskdeadl(deadline);
                                userlist.setStatusdb(statusdb);
                                userlist.setAssignerdb(assignerdb);
                                userlist.setAssignedUserdb(assignedUserdb);
                                list.add(0, userlist);
                            }
                        }
                    }

                    myAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching data", error.toException());
                Toast.makeText(PersonalTasksActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openTaskDetailsActivity(Userlist userlist) {
        Intent intent = new Intent(PersonalTasksActivity.this, AllTasksDetailsActivity.class);
        intent.putExtra("task", new TaskModel(userlist.getTaskID(), userlist.getTaskName(), userlist.getTaskDesc(), userlist.getTaskprio(), userlist.getTaskdeadl(), userlist.getStatusdb(), userlist.getAssignedUserdb(), userlist.getAssignerdb(), userlist.getClientdb()));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the home button click
                finish(); // Close the current activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
