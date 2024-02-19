package com.example.ntasks.rents;

import android.app.ProgressDialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EditIndependentDetailsActivity extends AppCompatActivity {


    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;
    private Spinner spinOwner, spinVendors, docTypeSpinner;

    private DatabaseReference vendorsRef, ownersRef;

    private ConstraintLayout independentImageView;

    private String photoUrl;
    private String docUrl;

    private EditText etIndpName, etIndpAddress, etIndpArea, etIndpUnits, etIndpFloor, etIndpShops, etIndpNotes, etIndpCoordinates;
    private Button btnSave;

    private ProgressDialog progressDialog;
    private String selectedDocType;

    private Independent independent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_independent_details);

        ownersRef = FirebaseDatabase.getInstance().getReference().child("Rents/Owners");
        vendorsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");

        independent = getIntent().getParcelableExtra("independent_details");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("EDIT INDEPENDENT");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        etIndpName = findViewById(R.id.etindpName);
        etIndpAddress = findViewById(R.id.etindpadd);
        etIndpArea = findViewById(R.id.etindparea);
        etIndpUnits = findViewById(R.id.etindpunits);
        etIndpFloor = findViewById(R.id.etindpfloor);
        etIndpShops = findViewById(R.id.etindpshops);
        etIndpNotes = findViewById(R.id.etindpNotes);
        etIndpCoordinates = findViewById(R.id.etsplit);
        btnSave = findViewById(R.id.btnindpadd);
        independentImageView = findViewById(R.id.ivaddindp);
        docTypeSpinner = findViewById(R.id.spinnerdoc);
        spinOwner = findViewById(R.id.spinowner);
        spinVendors = findViewById(R.id.spinvendors);

        independentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_IMG);
            }
        });

        LinearLayout llAttach = findViewById(R.id.llattach);
        llAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_DOC);
            }
        });// Add this line to your existing declarations

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIndependentDetails();
            }
        });

        setupSpinnerForDocType();
        setupSpinnerWithOwners();
        setupSpinnerWithVendors();

        if (independent != null) {
            etIndpName.setText(independent.getIndpName());
            etIndpAddress.setText(independent.getIndpAddress());
            etIndpArea.setText(independent.getIndpArea());
            etIndpUnits.setText(independent.getIndpUnits());
            etIndpFloor.setText(independent.getIndpFloor());
            etIndpShops.setText(independent.getIndpShops());
            etIndpNotes.setText(independent.getIndpNotes());
            etIndpCoordinates.setText(independent.getCoordinates());
            docUrl = independent.getDocuUrl();
            photoUrl = independent.getImgUrl();
        } else {
            Toast.makeText(this, "No independent data found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateIndependentDetails() {
        if (independent != null) {
            independent.setIndpName(etIndpName.getText().toString().trim());
            independent.setIndpAddress(etIndpAddress.getText().toString().trim());
            independent.setIndpArea(etIndpArea.getText().toString().trim());
            independent.setIndpUnits(etIndpUnits.getText().toString().trim());
            independent.setIndpFloor(etIndpFloor.getText().toString().trim());
            independent.setIndpShops(etIndpShops.getText().toString().trim());
            independent.setOwnerName(spinOwner.getSelectedItem().toString());
            independent.setVendorName(spinVendors.getSelectedItem().toString());
            independent.setIndpNotes(etIndpNotes.getText().toString().trim());
            independent.setCoordinates(etIndpCoordinates.getText().toString().trim());
            independent.setDocuUrl(docUrl);
            independent.setImgUrl(photoUrl);
            independent.setDocuType(docTypeSpinner.getSelectedItem().toString());

            DatabaseReference independentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Independents").child(independent.getIndpId());
            independentRef.setValue(independent)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditIndependentDetailsActivity.this, "Independent details updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EditIndependentDetailsActivity.this, "Failed to update independent details", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditIndependentDetailsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditIndependentDetailsActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditIndependentDetailsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditIndependentDetailsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();// Notify the adapter that the data has changed

                    Log.e("AddVendorsActivity", "Image upload failed", e);
                });
    }

    private void setupSpinnerForDocType() {
        String[] docTypes = getResources().getStringArray(R.array.DocIDtypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, docTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        docTypeSpinner.setAdapter(adapter);

        if (independent != null && !TextUtils.isEmpty(independent.getDocuType())) {
            int position = adapter.getPosition(independent.getDocuType());
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

                ArrayAdapter<String> ownerAdapter = new ArrayAdapter<>(EditIndependentDetailsActivity.this, android.R.layout.simple_spinner_item, ownerNames);
                ownerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinOwner.setAdapter(ownerAdapter);

                if (independent != null && !TextUtils.isEmpty(independent.getOwnerName())) {
                    int position = ownerAdapter.getPosition(independent.getOwnerName());
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

                ArrayAdapter<String> vendorAdapter = new ArrayAdapter<>(EditIndependentDetailsActivity.this, android.R.layout.simple_spinner_item, vendorNames);
                vendorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinVendors.setAdapter(vendorAdapter);

                if (independent != null && !TextUtils.isEmpty(independent.getVendorName())) {
                    int position = vendorAdapter.getPosition(independent.getVendorName());
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
