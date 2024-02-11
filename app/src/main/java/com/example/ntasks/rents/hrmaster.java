package com.example.ntasks.rents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ntasks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrmaster);

        Button btnAddEmployee = findViewById(R.id.btnaddemp);
        Button btnchangepswrd=findViewById(R.id.btnchangepswrd);

        btnchangepswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent67=new Intent(hrmaster.this, HrChangepswrd.class);
                startActivity(intent67);
            }
        });


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

/*

    private void showPasswordDialog() {
        // Create an alert dialog with an EditText for password input
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Password");

        final EditText inputPassword = new EditText(this);
        inputPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(inputPassword);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Check if the entered password is correct
                String enteredPassword = inputPassword.getText().toString().trim();
                if (enteredPassword.equals(CORRECT_PASSWORD)) {
                    // Password is correct, continue with the activity
                    Toast.makeText(hrmaster.this, "Password Correct!", Toast.LENGTH_SHORT).show();
                    // Add your activity logic here
                } else {
                    // Password is incorrect, show a message or take appropriate action
                    Toast.makeText(hrmaster.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    // Close the activity or take other actions
                    finish();
                }
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // Handle cancellation (e.g., pressing the back button)
                finish();
            }
        });

        builder.show();
    }*/
}
