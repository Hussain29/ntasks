package com.example.ntasks.rents;

import static com.example.ntasks.rents.AddVendorsActivity.PICK_FILE_REQUEST_IMG;

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
import androidx.constraintlayout.widget.ConstraintLayout;
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

public class AddApartmentsActivity extends AppCompatActivity {
    private EditText etaptloc;
    private static final int PICK_FILE_REQUEST = 22; // You can use any integer value
    private Button saveButton, saveloc;
    CardView cv3, cvaddattach;
    LinearLayout ll2;
    //private EditText etsplit;
    private EditText etAptId, etAptName, etAptAddress, etAptArea, etAptUnits, etAptFloor, etAptShops, etAptNotes;
    private Spinner spinOwner, spinVendors, spinDocs;
    private DatabaseReference apartmentsRef, ownersRef, vendorsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addapartments);
        ConstraintLayout ivaddapt=findViewById(R.id.ivaddapt);
        apartmentsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");
        ownersRef = FirebaseDatabase.getInstance().getReference().child("Rents/Owners");
        vendorsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");

        cvaddattach = findViewById(R.id.cvaddattach);
        ll2 = findViewById(R.id.llattach);
        saveButton = findViewById(R.id.btnaptadd);

        etAptId = findViewById(R.id.etaptid);
        etAptName = findViewById(R.id.etaptName);
        etAptAddress = findViewById(R.id.etaptadd);
        etAptArea = findViewById(R.id.etaptarea);
        etAptUnits = findViewById(R.id.etaptunits);
        etAptFloor = findViewById(R.id.etaptfloor);
        etAptShops = findViewById(R.id.etaptshops);
        etAptNotes = findViewById(R.id.etaptNotes);
        saveloc = findViewById(R.id.btnsaveloc);
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
                        Toast.makeText(AddApartmentsActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // No comma found
                    Toast.makeText(AddApartmentsActivity.this, "No comma found in input", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ivaddapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddApartmentsActivity.this, "Select Image", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_IMG);
            }
        });
        cvaddattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddApartmentsActivity.this, "Select Your Document", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_IMG);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveApartmentDetails();
            }
        });

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD APARTMENTS");

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

                ArrayAdapter<String> ownerAdapter = new ArrayAdapter<>(AddApartmentsActivity.this, android.R.layout.simple_spinner_item, ownerNames);
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

                ArrayAdapter<String> vendorAdapter = new ArrayAdapter<>(AddApartmentsActivity.this, android.R.layout.simple_spinner_item, vendorNames);
                vendorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinVendors.setAdapter(vendorAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    private void validateAndSaveApartmentDetails() {
        // Validate and save logic if needed for attachments
    }

    private void saveApartmentDetails() {
        String aptId = etAptId.getText().toString().trim();
        String aptName = etAptName.getText().toString().trim();
        String aptAddress = etAptAddress.getText().toString().trim();
        String aptArea = etAptArea.getText().toString().trim();
        String aptUnits = etAptUnits.getText().toString().trim();
        String aptFloor = etAptFloor.getText().toString().trim();
        String aptShops = etAptShops.getText().toString().trim();
        String aptNotes = etAptNotes.getText().toString().trim();
        String ownerName = spinOwner.getSelectedItem().toString();
        String vendorName = spinVendors.getSelectedItem().toString();

        String userId = getCurrentUserId();

        if (!TextUtils.isEmpty(aptId) && !TextUtils.isEmpty(aptName) && !TextUtils.isEmpty(aptAddress)
                && !TextUtils.isEmpty(aptArea) && !TextUtils.isEmpty(aptUnits) && !TextUtils.isEmpty(aptFloor)
                && !TextUtils.isEmpty(aptShops) && !TextUtils.isEmpty(ownerName) && !TextUtils.isEmpty(vendorName)) {

            // Assuming you have an Apartment class, replace it with your actual Apartment class
            Apartment apartment = new Apartment(aptId, aptName, aptAddress, aptArea, aptUnits, aptFloor, aptShops, aptNotes, userId, vendorName, ownerName);

            // Save apartment details to Firebase
            apartmentsRef.child(aptId).setValue(apartment);

            // Additional actions, if needed
            Toast.makeText(AddApartmentsActivity.this, "Apartment details saved successfully!", Toast.LENGTH_SHORT).show();

            // You can add further actions or redirection here
            // For example, redirect the user to another activity
            // Intent intent = new Intent(AddApartmentsActivity.this, AnotherActivity.class);
            // startActivity(intent);

            // Finish the current activity
            finish();
        } else {
            Toast.makeText(AddApartmentsActivity.this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
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



}
