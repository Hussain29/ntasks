package com.example.ntasks.rents;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;


public class CollectionsActivity extends AppCompatActivity {
    private Spinner propertySpinner, tenantSpinner;
    private TextInputEditText editTextRentAmt;
    private DatabaseReference flatsRef, apartmentsRef, independentsRef, tenantsRef, plotsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        propertySpinner = findViewById(R.id.propertyspinner);
        tenantSpinner = findViewById(R.id.tenantspinner);
        editTextRentAmt = findViewById(R.id.editTextrentamt);

        flatsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");
        apartmentsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");
        independentsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");
        tenantsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Tenants");
        plotsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Plots");

        setupPropertySpinner();
        setupTenantSpinner();

        Button btnAddPayment = findViewById(R.id.Btnaddpay);
        btnAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPayment();
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
                            propertyName = propertySnapshot.child("flatNo").getValue(String.class);
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

    private void setupTenantSpinner() {
        tenantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> tenantNames = new ArrayList<>();
                tenantNames.add("Select Tenant");

                for (DataSnapshot tenantSnapshot : snapshot.getChildren()) {
                    String tenantName = tenantSnapshot.child("tenantName").getValue(String.class);

                    if (!TextUtils.isEmpty(tenantName)) {
                        tenantNames.add(tenantName);
                    }
                }

                ArrayAdapter<String> tenantAdapter = new ArrayAdapter<>(CollectionsActivity.this, android.R.layout.simple_spinner_item, tenantNames);
                tenantAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tenantSpinner.setAdapter(tenantAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("CollectionsActivity", "Error retrieving tenant data", error.toException());
            }
        });
    }

    private void updatePropertySpinner(List<String> propertyList) {
        ArrayAdapter<String> propertyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, propertyList);
        propertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertySpinner.setAdapter(propertyAdapter);
    }

    private void addPayment() {
        String selectedProperty = propertySpinner.getSelectedItem().toString();
        String selectedTenant = tenantSpinner.getSelectedItem().toString();
        String rentAmount = editTextRentAmt.getText().toString().trim();

        if (TextUtils.isEmpty(selectedProperty) || TextUtils.isEmpty(selectedTenant) || TextUtils.isEmpty(rentAmount)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Create a Payment object (adjust this based on your data model)
        Collection collection = new Collection(selectedProperty, selectedTenant, rentAmount, currentDate);

        // Push the payment data to the database
        DatabaseReference paymentsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Payments");
        paymentsRef.push().setValue(collection)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CollectionsActivity.this, "Payment added successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CollectionsActivity.this, "Failed to add payment", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        propertySpinner.setSelection(0);
        tenantSpinner.setSelection(0);
        editTextRentAmt.getText().clear();
    }
}
