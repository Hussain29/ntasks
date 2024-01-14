package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
public class AddIndependentActivity extends AppCompatActivity {

    private Button saveButton,saveloc;
    CardView cvaddattach;
    LinearLayout ll2;

    private EditText etIndpId, etIndpName, etIndpAdd, etIndpArea, etIndpUnits, etIndpFloor, etIndpShops, etIndpNotes;
    private Spinner spinOwner, spinVendors, spinDocs;
    private DatabaseReference independentRef, ownersRef, vendorsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_independent);

        independentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");
        ownersRef = FirebaseDatabase.getInstance().getReference().child("Rents/Owners");
        vendorsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");

        cvaddattach = findViewById(R.id.cvaddattach);
        ll2 = findViewById(R.id.llattach);
        saveButton = findViewById(R.id.btnindpadd);
        saveloc=findViewById(R.id.btnsaveloc);
        etIndpId = findViewById(R.id.etindpid);
        etIndpName = findViewById(R.id.etindpName);
        etIndpAdd = findViewById(R.id.etindpadd);
        etIndpArea = findViewById(R.id.etindparea);
        etIndpUnits = findViewById(R.id.etindpunits);
        etIndpFloor = findViewById(R.id.etindpfloor);
        etIndpShops = findViewById(R.id.etindpshops);
        etIndpNotes = findViewById(R.id.etindpNotes);

        spinOwner = findViewById(R.id.spinowner);
        spinVendors = findViewById(R.id.spinvendors);
        spinDocs = findViewById(R.id.spinnerdoc);

        setupSpinnerWithOwners();
        setupSpinnerWithVendors();


        Spinner spinnerdoc=findViewById(R.id.spinnerdoc);

        String[] items = getResources().getStringArray(R.array.DocIDtypes);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,items );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerdoc.setAdapter(adapter);

        cvaddattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSaveIndependentDetails();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIndependentDetails();
            }
        });

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD INDEPENDENT HOUSE");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


        saveloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splitText();
                EditText etsplit=findViewById(R.id.etsplit);

                // Get the latitude and longitude from user input
                String enteredText = etsplit.getText().toString().trim();

                // Check if the text contains a comma
                if (enteredText.contains(",")) {
                    // Split the text into parts based on the comma
                    String[] parts = enteredText.split(",");

                    if (parts.length == 2) {
                        // Extract latitude and longitude
                        double latitude = Double.parseDouble(parts[0].trim());
                        double longitude = Double.parseDouble(parts[1].trim());

                        // Create a Uri with the coordinates
                        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?z=15&q=" + latitude + "," + longitude);

                        // Create an Intent to open Google Maps
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                        // Check if there's an activity to handle the Intent
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                    } else {
                        // Invalid input
                        Toast.makeText(AddIndependentActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // No comma found
                    Toast.makeText(AddIndependentActivity.this, "No comma found in input", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

                ArrayAdapter<String> ownerAdapter = new ArrayAdapter<>(AddIndependentActivity.this, android.R.layout.simple_spinner_item, ownerNames);
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

                ArrayAdapter<String> vendorAdapter = new ArrayAdapter<>(AddIndependentActivity.this, android.R.layout.simple_spinner_item, vendorNames);
                vendorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinVendors.setAdapter(vendorAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    private void validateAndSaveIndependentDetails() {
        // Validate and save logic if needed for attachments
    }

    private void saveIndependentDetails() {
        String indpId = etIndpId.getText().toString().trim();
        String indpName = etIndpName.getText().toString().trim();
        String indpAdd = etIndpAdd.getText().toString().trim();
        String indpArea = etIndpArea.getText().toString().trim();
        String indpUnits = etIndpUnits.getText().toString().trim();
        String indpFloor = etIndpFloor.getText().toString().trim();
        String indpShops = etIndpShops.getText().toString().trim();
        String indpNotes = etIndpNotes.getText().toString().trim();
        String ownerName = spinOwner.getSelectedItem().toString();
        String vendorName = spinVendors.getSelectedItem().toString();

        String userId = getCurrentUserId();

        if (!TextUtils.isEmpty(indpId) && !TextUtils.isEmpty(indpName) && !TextUtils.isEmpty(indpAdd)
                && !TextUtils.isEmpty(indpArea) && !TextUtils.isEmpty(indpUnits) && !TextUtils.isEmpty(indpFloor)
                && !TextUtils.isEmpty(indpShops) && !TextUtils.isEmpty(ownerName) && !TextUtils.isEmpty(vendorName)) {

            // Assuming you have an Independent class, replace it with your actual Independent class
            Independent independent = new Independent(indpId, indpName, indpAdd, indpArea, indpUnits, indpFloor, indpShops, indpNotes, userId, vendorName, ownerName);

            // Save independent details to Firebase
            independentRef.child(indpId).setValue(independent);

            // Additional actions, if needed
            Toast.makeText(AddIndependentActivity.this, "Independent house details saved successfully!", Toast.LENGTH_SHORT).show();

            // You can add further actions or redirection here
            // For example, redirect the user to another activity
            // Intent intent = new Intent(AddIndependentActivity.this, AnotherActivity.class);
            // startActivity(intent);

            // Finish the current activity
            finish();
        } else {
            Toast.makeText(AddIndependentActivity.this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
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
    private void splitText() {
        EditText etsplit=findViewById(R.id.etsplit);

        String enteredText = etsplit.getText().toString().trim();
        TextView lonTextView=findViewById(R.id.tvlon);
        TextView latTextView=findViewById(R.id.tvlat);
        // Check if the text contains a comma
        if (enteredText.contains(",")) {
            // Split the text into parts based on the comma
            String[] parts = enteredText.split(",");

            if (parts.length == 2) {
                // Extract latitude and longitude
                String lat = parts[0].trim();
                String lon = parts[1].trim();

                // Display the results
                latTextView.setText("Lat = " + lat);
                lonTextView.setText("Lon = " + lon);
            } else {
                // Invalid input
                latTextView.setText("Invalid input");
                lonTextView.setText("");
            }
        } else {
            // No comma found
            latTextView.setText("No comma found");
            lonTextView.setText("");
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