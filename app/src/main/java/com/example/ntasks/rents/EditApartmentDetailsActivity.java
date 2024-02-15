package com.example.ntasks.rents;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ntasks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Toast;

import com.example.ntasks.R;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class EditApartmentDetailsActivity extends AppCompatActivity {



    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;

    private Spinner spinOwner, spinVendors, docTypeSpinner;

    private DatabaseReference vendorsRef, ownersRef;

    private ConstraintLayout addapartment;

    private ImageView apartmentImageView;

    private String photoUrl;
    private String docUrl;

    private EditText etAptName, etAptAddress, etAptArea, etAptUnits, etAptFloor, etAptShops, etOwnerName, etVendorName, etAptNotes, etAptCoordinates;
    private Button btnSave;

    private ProgressDialog progressDialog;
    private String selectedDocType;


    private Apartment apartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_apartment_details);

        ownersRef = FirebaseDatabase.getInstance().getReference().child("Rents/Owners");
        vendorsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");

        apartment = getIntent().getParcelableExtra("apartment_details");

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("EDIT APARTMENT");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        etAptName = findViewById(R.id.etaptName);
        etAptAddress = findViewById(R.id.etaptadd);
        etAptArea = findViewById(R.id.etaptarea);
        etAptUnits = findViewById(R.id.etaptunits);
        etAptFloor = findViewById(R.id.etaptfloor);
        etAptShops = findViewById(R.id.etaptshops);
        etAptNotes = findViewById(R.id.etaptNotes);
        etAptCoordinates = findViewById(R.id.etsplit);
        btnSave = findViewById(R.id.btnaptadd);

        addapartment=findViewById(R.id.addapartment);
        apartmentImageView = findViewById(R.id.ivaddapt);
        docTypeSpinner = findViewById(R.id.spinnerdoc);
        spinOwner = findViewById(R.id.spinowner);  // Add this line to your existing declarations
        spinVendors = findViewById(R.id.spinvendors);


        LinearLayout llAttach = findViewById(R.id.llattach);
        llAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_DOC);
            }
        });// Add this line to your existing declarations

        addapartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_IMG);
            }
        });

        // Setup document type spinner
        setupSpinnerForDocType();

        // Setup owner and vendor spinners
        setupSpinnerWithOwners();
        setupSpinnerWithVendors();


        if (apartment != null) {
            etAptName.setText(apartment.getAptName());
            etAptAddress.setText(apartment.getAptAddress());
            etAptArea.setText(apartment.getAptArea());
            etAptUnits.setText(apartment.getAptUnits());
            etAptFloor.setText(apartment.getAptFloor());
            etAptShops.setText(apartment.getAptShops());
            etAptNotes.setText(apartment.getAptNotes());
            etAptCoordinates.setText(apartment.getCoordinates());
            docUrl = apartment.getDocUrl();
            photoUrl = apartment.getImgUrl();

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateApartmentDetails();
                }
            });
        } else {
            Toast.makeText(this, "No apartment data found", Toast.LENGTH_SHORT).show();
            finish();
        }

    }



    private void updateApartmentDetails() {
        if (apartment != null) {
            apartment.setAptName(etAptName.getText().toString().trim());
            apartment.setAptAddress(etAptAddress.getText().toString().trim());
            apartment.setAptArea(etAptArea.getText().toString().trim());
            apartment.setAptUnits(etAptUnits.getText().toString().trim());
            apartment.setAptFloor(etAptFloor.getText().toString().trim());
            apartment.setAptShops(etAptShops.getText().toString().trim());
            apartment.setOwnerName(spinOwner.getSelectedItem().toString());
            apartment.setVendorName(spinVendors.getSelectedItem().toString());
            apartment.setAptNotes(etAptNotes.getText().toString().trim());
            apartment.setCoordinates(etAptCoordinates.getText().toString().trim());
            apartment.setDocUrl(docUrl);
            apartment.setImgUrl(photoUrl);
            apartment.setDocType(docTypeSpinner.getSelectedItem().toString());

            DatabaseReference apartmentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments").child(apartment.getAptId());
            apartmentRef.setValue(apartment)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditApartmentDetailsActivity.this, "Apartment details updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EditApartmentDetailsActivity.this, "Failed to update apartment details", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);

        progressDialog.show();

        storageRef.putFile(docUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        docUrl = uri.toString();
                        Log.d("AddVendorsActivity", "Document URL: " + docUrl);
                        Toast.makeText(EditApartmentDetailsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditApartmentDetailsActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();// Notify the adapter that the data has changed
                    Log.e("AddVendorsActivity", "Document upload failed", e);
                });
    }

    // Add this method to handle image upload
    private void uploadImageToFirebaseStorage(Uri imageUri) {
        String fileName = "image:" + System.currentTimeMillis();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("uploads").child(fileName);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);

        progressDialog.show();

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        photoUrl = uri.toString();
                        Log.d("AddVendorsActivity", "Image URL: " + photoUrl);
                        Toast.makeText(EditApartmentDetailsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditApartmentDetailsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();// Notify the adapter that the data has changed

                    Log.e("AddVendorsActivity", "Image upload failed", e);
                });
    }

    private void setupSpinnerForDocType() {
        String[] docTypes = getResources().getStringArray(R.array.DocIDtypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, docTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        docTypeSpinner.setAdapter(adapter);

        if (apartment != null && !TextUtils.isEmpty(apartment.getDocType())) {
            int position = adapter.getPosition(apartment.getDocType());
            if (position != -1) {
                docTypeSpinner.setSelection(position);
            }
        }
        docTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedDocType = docTypes[position];
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
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                List<String> ownerNames = new ArrayList<>();

                ownerNames.add("Select Owner");

                for (com.google.firebase.database.DataSnapshot ownerSnapshot : snapshot.getChildren()) {
                    String ownerName = ownerSnapshot.child("ownerName").getValue(String.class);

                    if (!TextUtils.isEmpty(ownerName)) {
                        ownerNames.add(ownerName);
                    }
                }

                ArrayAdapter<String> ownerAdapter = new ArrayAdapter<>(EditApartmentDetailsActivity.this, android.R.layout.simple_spinner_item, ownerNames);
                ownerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinOwner.setAdapter(ownerAdapter);

                if (apartment != null && !TextUtils.isEmpty(apartment.getOwnerName())) {
                    int position = ownerAdapter.getPosition(apartment.getOwnerName());
                    if (position != -1) {
                        spinOwner.setSelection(position);
                    }
                }
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

                ArrayAdapter<String> vendorAdapter = new ArrayAdapter<>(EditApartmentDetailsActivity.this, android.R.layout.simple_spinner_item, vendorNames);
                vendorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinVendors.setAdapter(vendorAdapter);

                if (apartment != null && !TextUtils.isEmpty(apartment.getVendorName())) {
                    int position = vendorAdapter.getPosition(apartment.getVendorName());
                    if (position != -1) {
                        spinVendors.setSelection(position);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
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