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


public class CollectionsActivity extends AppCompatActivity {
    private Spinner propertySpinner, tenantSpinner;
    private TextInputEditText editTextRentAmt;
    private DatabaseReference flatsRef, apartmentsRef, independentsRef, tenantsRef, plotsRef;
    private TextView myTextView;
    private DatePicker datePicker;
    private TextView mytvdate;
    private Button enterdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        propertySpinner = findViewById(R.id.propertyspinner);
        tenantSpinner = findViewById(R.id.tenantspinner);
        editTextRentAmt = findViewById(R.id.editTextrentamt);
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
        DatabaseReference tenantsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Tenants");
        DatabaseReference independentsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");
        DatabaseReference apartmentsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");
        DatabaseReference plotsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Plots");

        List<String> propertyList = new ArrayList<>();
        AtomicInteger requestsCompleted = new AtomicInteger(0);

        ValueEventListener flatListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot flatDataSnapshot) {
                // Increment the counter
                requestsCompleted.incrementAndGet();
                Log.d("CAA", "Flats data fetched successfully. Total Requests Completed: " + requestsCompleted.get());

                // Fetch data from Tenants module once Flats data is fetched
                tenantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot tenantsDataSnapshot) {
                        // Increment the counter
                        requestsCompleted.incrementAndGet();
                        Log.d("CAA", "Tenants data fetched successfully. Total Requests Completed: " + requestsCompleted.get());

                        for (DataSnapshot tenantSnapshot : tenantsDataSnapshot.getChildren()) {
                            String tenantName = tenantSnapshot.child("tenantName").getValue(String.class);
                            String propertyName = tenantSnapshot.child("propertyName").getValue(String.class);

                            // Find the corresponding Flat entry based on FlatNo and PropertyName
                            DataSnapshot flatEntry = findEntryByFlatNoAndProperty(flatDataSnapshot, propertyName);

                            // Format the spinner entry
                            String aptName = (flatEntry != null && flatEntry.child("apartmentName").exists()) ? flatEntry.child("apartmentName").getValue(String.class) : "";
                            String spinnerEntry = tenantName + " / " + propertyName + " (" + aptName + ")";
                            propertyList.add(spinnerEntry);
                        }

                        // Check if all requests are completed
                        if (requestsCompleted.get() == 5) {
                            Log.d("CAA", "Updating spinner with data. Total Requests Completed: " + requestsCompleted.get());
                            updatePropertySpinner(propertyList);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("CAA", "Error retrieving tenants data", databaseError.toException());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CAA", "Error retrieving flats data", databaseError.toException());
            }
        };

        // Fetch data from Independents module once Flats data is fetched
        flatRef.addListenerForSingleValueEvent(flatListener);

        // Fetch data from Independents module
        independentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot independentsDataSnapshot) {
                // Increment the counter
                requestsCompleted.incrementAndGet();
                Log.d("CAA", "Independents data fetched successfully. Total Requests Completed: " + requestsCompleted.get());

                for (DataSnapshot independentSnapshot : independentsDataSnapshot.getChildren()) {
                    String independentName = independentSnapshot.child("indpName").getValue(String.class);
                    String propertyName = independentSnapshot.child("propertyName").getValue(String.class);

                    // Format the spinner entry for Independents
                    String spinnerEntry = " / " +independentName;
                    propertyList.add(spinnerEntry);
                }

                // Check if all requests are completed
                if (requestsCompleted.get() == 5) {
                    Log.d("CAA", "Updating spinner with data. Total Requests Completed: " + requestsCompleted.get());
                    updatePropertySpinner(propertyList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CAA", "Error retrieving independents data", databaseError.toException());
            }
        });

        // Fetch data from Apartments module
        apartmentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot apartmentsDataSnapshot) {
                // Increment the counter
                requestsCompleted.incrementAndGet();
                Log.d("CAA", "Apartments data fetched successfully. Total Requests Completed: " + requestsCompleted.get());

                for (DataSnapshot apartmentSnapshot : apartmentsDataSnapshot.getChildren()) {
                    String apartmentName = apartmentSnapshot.child("aptName").getValue(String.class);

                    // Format the spinner entry for Apartments
                    String spinnerEntry = " / " + apartmentName;
                    propertyList.add(spinnerEntry);
                }

                // Check if all requests are completed
                if (requestsCompleted.get() == 5) {
                    Log.d("CollectionsActivity", "Updating spinner with data. Total Requests Completed: " + requestsCompleted.get());
                    updatePropertySpinner(propertyList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CAA", "Error retrieving apartments data", databaseError.toException());
            }
        });

        // Fetch data from Plots module
        plotsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot plotsDataSnapshot) {
                // Increment the counter
                requestsCompleted.incrementAndGet();
                Log.d("CAA", "Plots data fetched successfully. Total Requests Completed: " + requestsCompleted.get());

                for (DataSnapshot plotSnapshot : plotsDataSnapshot.getChildren()) {
                    String plotName = plotSnapshot.child("pltName").getValue(String.class);

                    // Format the spinner entry for Plots
                    String spinnerEntry = " / " + plotName;
                    propertyList.add(spinnerEntry);
                }

                // Check if all requests are completed
                if (requestsCompleted.get() == 5) {
                    Log.d("CAA", "Updating spinner with data. Total Requests Completed: " + requestsCompleted.get());
                    updatePropertySpinner(propertyList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CAA", "Error retrieving plots data", databaseError.toException());
            }
        });
    }



    /*private DataSnapshot findEntryByFlatNoAndProperty(DataSnapshot flatDataSnapshot, String propertyName) {
        for (DataSnapshot flatSnapshot : flatDataSnapshot.getChildren()) {
            // Adjust the conditions to match your data structure
            String flatNoFromSnapshot = flatSnapshot.child("flatNo").getValue(String.class);
            String propertyNameFromSnapshot = flatSnapshot.child("propertyName").getValue(String.class);

            if (propertyName.equals(propertyNameFromSnapshot)) {
                // Assuming you want to find the flat entry based on property name
                return flatSnapshot;
            }
        }
        return null;
    }*/


    private DataSnapshot findEntryByFlatNoAndProperty(DataSnapshot flatDataSnapshot, String propertyName) {
        for (DataSnapshot flatSnapshot : flatDataSnapshot.getChildren()) {
            // Adjust the conditions to match your data structure
            String flatNoFromSnapshot = flatSnapshot.child("flatNo").getValue(String.class);

            Log.d("CAA", "Checking flat: " + flatNoFromSnapshot + " for property: " + propertyName);

            if (flatNoFromSnapshot.equals(propertyName)) {
                // Assuming you want to find the flat entry based on property name

                // Fetch aptName from the flat entry
                String aptName = flatSnapshot.child("apartmentName").getValue(String.class);

                // Log the aptName for debugging
                Log.d("CAA", "aptName found: " + aptName);

                return flatSnapshot;
            }
        }
        return null;
    }


    /*private void setupPropertySpinner() {
        DatabaseReference flatRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");
        DatabaseReference apartmentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");
        DatabaseReference independentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");
        DatabaseReference plotRef = FirebaseDatabase.getInstance().getReference().child("Rents/Plots");
        DatabaseReference tenantsRef = FirebaseDatabase.getInstance().getReference().child("Rents/  Tenants");

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
                            propertyName = propertySnapshot.child("flatNo").getValue(String.class) + ", " + propertySnapshot.child("apartmentName").getValue(String.class);
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
    }*/

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
        /*String selectedTenant = tenantSpinner.getSelectedItem().toString();*/
        String rentAmount = editTextRentAmt.getText().toString().trim();

        String[] parts = selectedProperty.split(" / ");
        String selectedTenant = parts[0];
        String selectedPropertyName = parts[1];

        if (TextUtils.isEmpty(selectedProperty) || TextUtils.isEmpty(rentAmount)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, day);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedSelectedDate = dateFormat.format(selectedDate.getTime());

        // Get the current date
        SimpleDateFormat dateFormatcur = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String currentDate = dateFormatcur.format(new Date());

        // Create a Payment object (adjust this based on your data model)
        Collection collection = new Collection(selectedPropertyName, selectedTenant, rentAmount, currentDate, formattedSelectedDate);

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
