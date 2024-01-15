package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AddPlotsActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_IMG = 67;

    private static final int PICK_FILE_REQUEST_DOC = 70;

    private Button saveButton, saveloc;
    private CardView cvaddattach;
    private LinearLayout ll2;

    private EditText etPltId, etPltName, etPltAdd, etPltArea, etPltFloor, etPltShops, etPltNotes, etCoordinates;
    private Spinner spinOwner, spinVendors, spinDocs;

    private DatabaseReference plotsRef, ownersRef, vendorsRef;
    private String docUrl;
    private String imgUrl;
    private String docType;
    private String coordinates;

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
        saveloc = findViewById(R.id.btnsaveloc);
        etPltId = findViewById(R.id.etpltid);
        etPltName = findViewById(R.id.etpltName);
        etPltAdd = findViewById(R.id.etpltadd);
        etPltArea = findViewById(R.id.etpltarea);
        etPltFloor = findViewById(R.id.etpltfloor);
        etPltShops = findViewById(R.id.etpltshops);
        etPltNotes = findViewById(R.id.etpltNotes);
        etCoordinates=findViewById(R.id.etsplit);


        spinOwner = findViewById(R.id.spinowner);
        spinVendors = findViewById(R.id.spinvendors);
        spinDocs = findViewById(R.id.spinnerdoc);

        setupSpinnerWithOwners();
        setupSpinnerWithVendors();
        setupSpinnerForDocType();

        saveloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splitText();
                EditText etsplit = findViewById(R.id.etsplit);

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
                        Toast.makeText(AddPlotsActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // No comma found
                    Toast.makeText(AddPlotsActivity.this, "No comma found in input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ConstraintLayout ivaddplt = findViewById(R.id.ivaddplt);
        ivaddplt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddPlotsActivity.this, "Select Image", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_IMG);
            }
        });

        cvaddattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddPlotsActivity.this, "Select Your Document", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_DOC);
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

    private void setupSpinnerForDocType() {
        String[] docTypes = getResources().getStringArray(R.array.DocIDtypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, docTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDocs.setAdapter(adapter);
        spinDocs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                docType = docTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
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
        String coordinates = etCoordinates.getText().toString().trim();

        String userId = getCurrentUserId();

        if (!TextUtils.isEmpty(pltId) && !TextUtils.isEmpty(pltName) && !TextUtils.isEmpty(pltAdd)
                && !TextUtils.isEmpty(pltArea) && !TextUtils.isEmpty(pltFloor) && !TextUtils.isEmpty(pltShops)
                && !TextUtils.isEmpty(ownerName) && !TextUtils.isEmpty(vendorName)) {

            // Assuming you have a Plot class, replace it with your actual Plot class
            Plot plot = new Plot(pltId, pltName, pltAdd, pltArea, pltFloor, pltShops, pltNotes, userId, vendorName, ownerName,
                    docType, docUrl, imgUrl, coordinates);

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

    private void splitText() {
        EditText etsplit = findViewById(R.id.etsplit);

        String enteredText = etsplit.getText().toString().trim();
        TextView lonTextView = findViewById(R.id.tvlon);
        TextView latTextView = findViewById(R.id.tvlat);

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

                // Set coordinates in the Plot object or wherever needed
                coordinates = enteredText;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST_IMG) {
                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    uploadImageToFirebaseStorage(selectedImageUri);
                }
            } else if (requestCode == PICK_FILE_REQUEST_DOC) {
                if (data != null && data.getData() != null) {
                    Uri selectedDocUri = data.getData();
                    uploadDocumentToFirebaseStorage(selectedDocUri);
                }
            }
        }
    }

    private void uploadDocumentToFirebaseStorage(Uri docUri) {
        String fileName = "document:" + System.currentTimeMillis();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("uploads").child(fileName);

        storageRef.putFile(docUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        docUrl = uri.toString();
                        Log.d("AddPlotsActivity", "Document URL: " + docUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddPlotsActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddPlotsActivity", "Document upload failed", e);
                });
    }

    // Add this method to handle image upload
    private void uploadImageToFirebaseStorage(Uri imageUri) {
        String fileName = "image:" + System.currentTimeMillis();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("uploads").child(fileName);

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        imgUrl = uri.toString();
                        Log.d("AddPlotsActivity", "Image URL: " + imgUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddPlotsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddPlotsActivity", "Image upload failed", e);
                });
    }
}
