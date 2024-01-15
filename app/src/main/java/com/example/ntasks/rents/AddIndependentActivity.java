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
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AddIndependentActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_IMG = 66;
    private static final int PICK_FILE_REQUEST_DOC = 77;

    private Button saveButton, saveloc;
    private LinearLayout ll2;

    private EditText etIndpId, etIndpName, etIndpAdd, etIndpArea, etIndpUnits, etIndpFloor, etIndpShops, etIndpNotes, etCoordinates;
    private Spinner spinOwner, spinVendors, spinDocs;
    private DatabaseReference independentRef, ownersRef, vendorsRef;
    private String docUrl;
    private String imgUrl;
    private String docType;
    private String coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_independent);

        independentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");
        ownersRef = FirebaseDatabase.getInstance().getReference().child("Rents/Owners");
        vendorsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");

        saveButton = findViewById(R.id.btnindpadd);
        etIndpId = findViewById(R.id.etindpid);
        etIndpName = findViewById(R.id.etindpName);
        etIndpAdd = findViewById(R.id.etindpadd);
        etIndpArea = findViewById(R.id.etindparea);
        etIndpUnits = findViewById(R.id.etindpunits);
        etIndpFloor = findViewById(R.id.etindpfloor);
        etIndpShops = findViewById(R.id.etindpshops);
        etIndpNotes = findViewById(R.id.etindpNotes);
        etCoordinates = findViewById(R.id.etsplit);

        spinOwner = findViewById(R.id.spinowner);
        spinVendors = findViewById(R.id.spinvendors);
        spinDocs = findViewById(R.id.spinnerdoc);

        setupSpinnerWithOwners();
        setupSpinnerWithVendors();
        setupSpinnerForDocType();

        ConstraintLayout addindpimg = findViewById(R.id.ivaddindp);
        addindpimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddIndependentActivity.this, "Select Image", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_IMG);
            }
        });



        CardView cvaddattach = findViewById(R.id.cvaddattach);
        cvaddattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddIndependentActivity.this, "Select Your Document", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_DOC);
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

        Button saveloc = findViewById(R.id.btnsaveloc);
        saveloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splitText();
                // Get the latitude and longitude from user input
                String enteredText = etCoordinates.getText().toString().trim();

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
        String coordinates = etCoordinates.getText().toString().trim();

        String userId = getCurrentUserId();

        if (!TextUtils.isEmpty(indpId) && !TextUtils.isEmpty(indpName) && !TextUtils.isEmpty(indpAdd)
                && !TextUtils.isEmpty(indpArea) && !TextUtils.isEmpty(indpUnits) && !TextUtils.isEmpty(indpFloor)
                && !TextUtils.isEmpty(indpShops) && !TextUtils.isEmpty(ownerName) && !TextUtils.isEmpty(vendorName)) {

            // Assuming you have an Independent class, replace it with your actual Independent class
            Independent independent = new Independent(indpId, indpName, indpAdd, indpArea, indpUnits, indpFloor,
                    indpShops, indpNotes, userId, vendorName, ownerName, docUrl, imgUrl, docType, coordinates);

            // Save independent details to Firebase
            independentRef.child(indpId).setValue(independent, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if (error == null) {
                        // Additional actions, if needed
                        Toast.makeText(AddIndependentActivity.this, "Independent house details saved successfully!", Toast.LENGTH_SHORT).show();

                        // You can add further actions or redirection here
                        // For example, redirect the user to another activity
                        // Intent intent = new Intent(AddIndependentActivity.this, AnotherActivity.class);
                        // startActivity(intent);

                        // Finish the current activity
                        finish();
                    } else {
                        Toast.makeText(AddIndependentActivity.this, "Failed to save independent house details. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(AddIndependentActivity.this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSpinnerWithOwners() {
        ownersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                List<String> ownerNames = new ArrayList<>();

                ownerNames.add("Select Owner");

                for (com.google.firebase.database.DataSnapshot ownerSnapshot : snapshot.getChildren()) {
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
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                List<String> vendorNames = new ArrayList<>();

                vendorNames.add("Select Vendor");

                for (com.google.firebase.database.DataSnapshot vendorSnapshot : snapshot.getChildren()) {
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

    private void splitText() {
        String enteredText = etCoordinates.getText().toString().trim();

        if (enteredText.contains(",")) {
            String[] parts = enteredText.split(",");
            if (parts.length == 2) {
                double lat = Double.parseDouble(parts[0].trim());
                double lon = Double.parseDouble(parts[1].trim());
                coordinates = lat + ", " + lon;
            }
        }
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            return "Unknown User";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // This is called when the Home (Up) button is pressed
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFileChooser(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(requestCode == PICK_FILE_REQUEST_IMG ? "image/*" : "*/*");
        startActivityForResult(intent, requestCode);
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
                        Log.d("AddVendorsActivity", "Document URL: " + docUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddIndependentActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddVendorsActivity", "Document upload failed", e);
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
                        Log.d("AddVendorsActivity", "Image URL: " + imgUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddIndependentActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddVendorsActivity", "Image upload failed", e);
                });
    }

}
