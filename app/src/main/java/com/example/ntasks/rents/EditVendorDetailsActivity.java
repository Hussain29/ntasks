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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class EditVendorDetailsActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;
    private Vendor vendor;
    private String photoUrl;
    private String docUrl;
    private ImageView vendorImageView;
    private EditText etVendorId, etVendorName, etVendorAddress, etVendorEmail, etVendorPhoneNumber, etVendorPhoneNumber2, etVendorNotes;
    private Button btnSave;
    private ProgressDialog progressDialog;
    private Spinner docTypeSpinner;
    private String selectedDocType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vendor_details);

        vendor = getIntent().getParcelableExtra("vendor_details");

        // Initialize Views
        etVendorId = findViewById(R.id.etvendorid);
        etVendorName = findViewById(R.id.etvendorName);
        etVendorAddress = findViewById(R.id.etvendoradd);
        etVendorEmail = findViewById(R.id.etvendoremail);
        etVendorPhoneNumber = findViewById(R.id.etvendorPhonenumber);
        etVendorPhoneNumber2 = findViewById(R.id.etvendorPhonenumber2);
        etVendorNotes = findViewById(R.id.etvendorNotes);
        btnSave = findViewById(R.id.btnaddvendordet);
        docTypeSpinner = findViewById(R.id.spinnerdoctype);
        vendorImageView = findViewById(R.id.addvendorpic);

        LinearLayout llAttach = findViewById(R.id.llattach);
        llAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_DOC);
            }
        });// Add this line to your existing declarations

        // Set onClickListener for ImageView

        vendorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_IMG);
            }
        });

        // Set onClickListener for Save button
        btnSave.setOnClickListener(v -> updateVendorDetails());

        // Setup Spinner for Document Type
        setupSpinnerForDocType();

        etVendorName.setText(vendor.getVendorName());
        etVendorAddress.setText(vendor.getVendorAddress());
        etVendorEmail.setText(vendor.getVendorEmail());
        etVendorPhoneNumber.setText(vendor.getVendorPhone1());
        etVendorPhoneNumber2.setText(vendor.getVendorPhone2());
        etVendorNotes.setText(vendor.getVendorNotes());
        docUrl = vendor.getDocumentUri();
        photoUrl = vendor.getImageUri();

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("EDIT VENDOR DETAILS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
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
                        Toast.makeText(EditVendorDetailsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditVendorDetailsActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditVendorDetailsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditVendorDetailsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();// Notify the adapter that the data has changed

                    Log.e("AddVendorsActivity", "Image upload failed", e);
                });
    }

    private void updateVendorDetails() {
        if (vendor != null) {

            vendor.setVendorName(etVendorName.getText().toString().trim());
            vendor.setVendorAddress(etVendorAddress.getText().toString().trim());
            vendor.setVendorEmail(etVendorEmail.getText().toString().trim());
            vendor.setVendorPhone1(etVendorPhoneNumber.getText().toString().trim());
            vendor.setVendorPhone2(etVendorPhoneNumber2.getText().toString().trim());
            vendor.setVendorNotes(etVendorNotes.getText().toString().trim());
            vendor.setDocType(docTypeSpinner.getSelectedItem().toString());
            vendor.setDocumentUri(docUrl);
            vendor.setImageUri(photoUrl);

            // Save the updated vendor details to the database
            DatabaseReference vendorRef = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors").child(vendor.getVendorId());
            vendorRef.setValue(vendor)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditVendorDetailsActivity.this, "Vendor details updated successfully", Toast.LENGTH_SHORT).show();
                                finish(); // Finish the activity after updating the details
                            } else {
                                Toast.makeText(EditVendorDetailsActivity.this, "Failed to update vendor details", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }



    private void setupSpinnerForDocType() {
        String[] docTypes = getResources().getStringArray(R.array.DocIDtypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, docTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        docTypeSpinner.setAdapter(adapter);

        if (vendor != null && !TextUtils.isEmpty(vendor.getDocType())) {
            int position = adapter.getPosition(vendor.getDocType());
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
