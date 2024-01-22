package com.example.ntasks.rents;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ntasks.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class ExpensesActivity extends AppCompatActivity {
    private Spinner propertySpinner;
    private TextInputEditText editTextParticular, editTextExpAmt;
    private DatePicker datePicker;
    private TextView mytvdate;
    private Button enterdate;
    private DatabaseReference flatsRef, apartmentsRef, independentsRef, expensesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        propertySpinner = findViewById(R.id.propertyspinner);
        editTextParticular = findViewById(R.id.editTextparticular);
        editTextExpAmt = findViewById(R.id.editTextexpamt);
        enterdate = findViewById(R.id.enterdate);


        mytvdate = findViewById(R.id.myTvdate);


        enterdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.VISIBLE);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, day);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(selectedDate.getTime());

                mytvdate.setText("Selected Date: " + formattedDate);
            }
        });
        mytvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.VISIBLE);
            }
        });


        datePicker = findViewById(R.id.datePicker);




        flatsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");
        apartmentsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");
        independentsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");
        expensesRef = FirebaseDatabase.getInstance().getReference().child("Rents/Expenses");

        setupPropertySpinner();

        Button btnAddExpense = findViewById(R.id.addexpns);
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense();
            }
        });
    }

    private void setupPropertySpinner() {
        DatabaseReference flatRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");
        DatabaseReference apartmentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");
        DatabaseReference independentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");
        DatabaseReference plotRef = FirebaseDatabase.getInstance().getReference().child("Rents/Plots");

        List<String> propertyList = new ArrayList<>();
        AtomicInteger requestsCompleted = new AtomicInteger(0);

        ValueEventListener propertyListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot propertySnapshot : dataSnapshot.getChildren()) {
                    String propertyName;

                    // Determine the type of property and fetch the corresponding field
                    switch (dataSnapshot.getKey()) {
                        case "Flats":
                            propertyName = propertySnapshot.child("flatNo").getValue(String.class)  + ", " + propertySnapshot.child("apartmentName").getValue(String.class);
                            break;
                        case "Apartments":
                            propertyName = propertySnapshot.child("aptName").getValue(String.class);
                            break;
                        case "Independents":
                            propertyName = propertySnapshot.child("indpName").getValue(String.class);
                            break;
                        case "Plots":
                            propertyName = propertySnapshot.child("pltName").getValue(String.class);
                            break;
                        default:
                            propertyName = null;
                    }

                    if (!TextUtils.isEmpty(propertyName)) {
                        propertyList.add(propertyName);
                    }
                }

                // Increment the counter and check if all requests are completed
                if (requestsCompleted.incrementAndGet() == 4) {
                    updatePropertySpinner(propertyList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CollectionsActivity", "Error retrieving property data", databaseError.toException());
            }
        };

        // Attach the listeners to the respective references
        flatRef.addListenerForSingleValueEvent(propertyListener);
        apartmentRef.addListenerForSingleValueEvent(propertyListener);
        independentRef.addListenerForSingleValueEvent(propertyListener);
        plotRef.addListenerForSingleValueEvent(propertyListener);
    }

    private void updatePropertySpinner(List<String> propertyList) {
        ArrayAdapter<String> propertyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, propertyList);
        propertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertySpinner.setAdapter(propertyAdapter);
    }

    private void addExpense() {
        String selectedProperty = propertySpinner.getSelectedItem().toString();
        String particular = editTextParticular.getText().toString().trim();
        String expenseAmount = editTextExpAmt.getText().toString().trim();

        if (TextUtils.isEmpty(selectedProperty) || TextUtils.isEmpty(particular) || TextUtils.isEmpty(expenseAmount)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Create an Expenses object (adjust this based on your data model)
        Expenses expenses = new Expenses(selectedProperty, particular, expenseAmount, currentDate);

        // Push the expense data to the database
        DatabaseReference expensesRef = FirebaseDatabase.getInstance().getReference().child("Rents/Expenses");
        expensesRef.push().setValue(expenses)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ExpensesActivity.this, "Expense added successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ExpensesActivity.this, "Failed to add expense", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void clearFields() {
        propertySpinner.setSelection(0);
        editTextParticular.getText().clear();
        editTextExpAmt.getText().clear();
    }
}
