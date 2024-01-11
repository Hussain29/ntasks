package com.example.ntasks.rents;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddPlotsActivity extends AppCompatActivity {

    private Button saveButton;
    CardView cvaddattach;
    LinearLayout ll2;

    private EditText etPltId, etPltName, etPltAdd, etPltArea, etPltFloor, etPltShops, etPltNotes;
    private Spinner spinOwner, spinVendors, spinDocs;
    private DatabaseReference plotsRef, ownersRef, vendorsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plots);

        plotsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Plots");
        ownersRef = FirebaseDatabase.getInstance().getReference().child("Rents/Owners");
        vendorsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");

        cvaddattach = findViewById(R.id.cvaddattach);
        ll2 = findViewById(R.id.llattach);
        saveButton = findViewById(R.id.btnpltadd);

        etPltId = findViewById(R.id.etpltid);
        etPltName = findViewById(R.id.etpltName);
        etPltAdd = findViewById(R.id.etpltadd);
        etPltArea = findViewById(R.id.etpltarea);
        etPltFloor = findViewById(R.id.etpltfloor);
        etPltShops = findViewById(R.id.etpltshops);
        etPltNotes = findViewById(R.id.etpltNotes);

        spinOwner = findViewById(R.id.spinowner);
        spinVendors = findViewById(R.id.spinvendors);
        spinDocs = findViewById(R.id.spinnerdoc);

        setupSpinnerWithOwners();
        setupSpinnerWithVendors();

        cvaddattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add logic for handling attachments if needed
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlotDetails();
            }
        });

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD PLOT DETAILS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void setupSpinnerWithOwners() {
        ownersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> ownerNames = new ArrayList<>();

                ownerNames.add("Select Owner");

                for (DataSnapshot ownerSnapshot : snapshot.getChildren()) {
                    String ownerName = ownerSnapshot.child("ownerName").getValue(String.class);

                    if (!TextUtils.isEmpty(ownerName)) {
                        ownerNames.add(ownerName);
                    }
                }

                ArrayAdapter<String> ownerAdapter = new ArrayAdapter<>(AddPlotsActivity.this, android.R.layout.simple_spinner_item, ownerNames);
                ownerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinOwner.setAdapter(ownerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    private void setupSpinnerWithVendors() {
        vendorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> vendorNames = new ArrayList<>();

                vendorNames.add("Select Vendor");

                for (DataSnapshot vendorSnapshot : snapshot.getChildren()) {
                    String vendorName = vendorSnapshot.child("vendorName").getValue(String.class);

                    if (!TextUtils.isEmpty(vendorName)) {
                        vendorNames.add(vendorName);
                    }
                }

                ArrayAdapter<String> vendorAdapter = new ArrayAdapter<>(AddPlotsActivity.this, android.R.layout.simple_spinner_item, vendorNames);
                vendorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinVendors.setAdapter(vendorAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    private void savePlotDetails() {
        String pltId = etPltId.getText().toString().trim();
        String pltName = etPltName.getText().toString().trim();
        String pltAdd = etPltAdd.getText().toString().trim();
        String pltArea = etPltArea.getText().toString().trim();
        String pltFloor = etPltFloor.getText().toString().trim();
        String pltShops = etPltShops.getText().toString().trim();
        String pltNotes = etPltNotes.getText().toString().trim();
        String ownerName = spinOwner.getSelectedItem().toString();
        String vendorName = spinVendors.getSelectedItem().toString();

        String userId = getCurrentUserId();

        if (!TextUtils.isEmpty(pltId) && !TextUtils.isEmpty(pltName) && !TextUtils.isEmpty(pltAdd)
                && !TextUtils.isEmpty(pltArea) && !TextUtils.isEmpty(pltFloor) && !TextUtils.isEmpty(pltShops)
                && !TextUtils.isEmpty(ownerName) && !TextUtils.isEmpty(vendorName)) {

            // Assuming you have a Plot class, replace it with your actual Plot class
            Plot plot = new Plot(pltId, pltName, pltAdd, pltArea, pltFloor, pltShops, pltNotes, userId, vendorName, ownerName);

            // Save plot details to Firebase
            plotsRef.child(pltId).setValue(plot);

            // Additional actions, if needed
            Toast.makeText(AddPlotsActivity.this, "Plot details saved successfully!", Toast.LENGTH_SHORT).show();

            // You can add further actions or redirection here
            // For example, redirect the user to another activity
            // Intent intent = new Intent(AddPlotsActivity.this, AnotherActivity.class);
            // startActivity(intent);

            // Finish the current activity
            finish();
        } else {
            Toast.makeText(AddPlotsActivity.this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            // Handle the case when the user is not authenticated
            return "Unknown User";
        }
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
