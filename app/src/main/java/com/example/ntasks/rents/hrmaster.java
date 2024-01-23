package com.example.ntasks.rents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ntasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class hrmaster extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private ArrayList<Employee> employeeList;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrmaster);

        Button btnAddEmployee = findViewById(R.id.btnaddemp);

        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hrmaster.this, hrAddEmployeeActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerViewEmployeeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        employeeList = new ArrayList<>();

        // Retrieve data from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("HR/Employees");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employeeList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Employee employee = snapshot.getValue(Employee.class);
                    employeeList.add(employee);
                }
                employeeAdapter.notifyDataSetChanged();
                progressDialog.dismiss();// Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        employeeAdapter = new EmployeeAdapter(this, employeeList);
        recyclerView.setAdapter(employeeAdapter);

        // Set up item click listener
        employeeAdapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Employee employee) {
                // Handle item click here
                openEmployeeDetailsActivity(employee);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("EMPLOYEES");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void openEmployeeDetailsActivity(Employee employee) {
        Intent intent = new Intent(hrmaster.this, EmployeeDetailsActivity.class);
        // Pass relevant data through Intent
        intent.putExtra("employee", employee);
        startActivity(intent);
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
}
