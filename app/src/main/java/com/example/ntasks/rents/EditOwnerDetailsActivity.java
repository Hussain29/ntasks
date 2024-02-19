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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditOwnerDetailsActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;

    private ImageView OwnerImageView;
    private EditText etOwnerName, etOwnerOcc, etOwnerAge, etOwnerAddress, etOwnerEmail, etOwnerPhone1, etOwnerPhone2, etOwnerPhone3, etOwnerNotes, etDocIdType, etFOwnerName;
    private Button btnSave;
    private String photoUrl;
    private String docUrl;
    private ProgressDialog progressDialog;
    private Spinner docTypeSpinner;
    private Owner owner;

    private String selectedDocType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_owner_details);

        // Retrieve owner from Intent
        owner = getIntent().getParcelableExtra("owner_details");

        // Check if owner is null before accessing its properties
        if (owner != null) {
            // Initialize Views
            etOwnerName = findViewById(R.id.etownerName);
            etFOwnerName = findViewById(R.id.etownerFatherName);
            etOwnerAge = findViewById(R.id.etownerAge);
            etOwnerOcc = findViewById(R.id.etownerOcc);
            etOwnerAddress = findViewById(R.id.etowneradd);
            etOwnerEmail = findViewById(R.id.etowneremail);
            etOwnerPhone1 = findViewById(R.id.etownerPhonenumber);
            etOwnerPhone2 = findViewById(R.id.etownerPhonenumber2);
            etOwnerPhone3 = findViewById(R.id.etownerPhonenumber3);
            etOwnerNotes = findViewById(R.id.etownerNotes);
            btnSave = findViewById(R.id.btnsowner);
            docTypeSpinner = findViewById(R.id.spinnerdoctype);

            OwnerImageView = findViewById(R.id.imaddperson);

            OwnerImageView.setOnClickListener(new View.OnClickListener() {
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


            // Set EditTexts with owner details
            etOwnerName.setText(owner.getOwnerName());
            etFOwnerName.setText(owner.getFathername());
            etOwnerAge.setText(owner.getAge());
            etOwnerOcc.setText(owner.getOccupation());
            etOwnerAddress.setText(owner.getOwnerAddress());
            etOwnerEmail.setText(owner.getOwnerEmail());
            etOwnerPhone1.setText(owner.getOwnerPhone1());
            etOwnerPhone2.setText(owner.getOwnerPhone2());
            etOwnerPhone3.setText(owner.getOwnerPhone3());
            etOwnerNotes.setText(owner.getOwnerNotes());
            docUrl = owner.getDocUrl();
            photoUrl = owner.getPhotoUrl();

            setupSpinnerForDocType();

            // Get the ActionBar
            ActionBar actionBar = getSupportActionBar();

            // Set the title
            actionBar.setTitle("EDIT OWNER DETAILS");

            // Enable the back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

            // Set onClickListener for Save button
            btnSave.setOnClickListener(view -> updateOwnerDetails());
        } else {
            // Log an error or show a message indicating that the owner data is null
            Log.e("EditOwnerDetailsActivity", "Owner data is null");
            // You may also finish the activity or handle it accordingly
            finish();
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
                        Toast.makeText(EditOwnerDetailsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditOwnerDetailsActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditOwnerDetailsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditOwnerDetailsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();// Notify the adapter that the data has changed

                    Log.e("AddVendorsActivity", "Image upload failed", e);
                });
    }


    private void updateOwnerDetails() {
        if (owner != null) {
            // Update owner details with EditText values
            owner.setOwnerName(etOwnerName.getText().toString().trim());
            owner.setFathername(etFOwnerName.getText().toString().trim());
            owner.setAge(etOwnerAge.getText().toString().trim());
            owner.setOccupation(etOwnerOcc.getText().toString().trim());
            owner.setOwnerAddress(etOwnerAddress.getText().toString().trim());
            owner.setOwnerEmail(etOwnerEmail.getText().toString().trim());
            owner.setOwnerPhone1(etOwnerPhone1.getText().toString().trim());
            owner.setOwnerPhone2(etOwnerPhone2.getText().toString().trim());
            owner.setOwnerPhone3(etOwnerPhone3.getText().toString().trim());
            owner.setOwnerNotes(etOwnerNotes.getText().toString().trim());
            owner.setDocType(docTypeSpinner.getSelectedItem().toString());
            owner.setDocUrl(docUrl);
            owner.setPhotoUrl(photoUrl);

            // Save the updated owner details to the database
            DatabaseReference ownerRef = FirebaseDatabase.getInstance().getReference().child("Rents/Owners").child(owner.getOwnerId());
            ownerRef.setValue(owner)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditOwnerDetailsActivity.this, "Owner details updated successfully", Toast.LENGTH_SHORT).show();
                                finish(); // Finish the activity after updating the details
                            } else {
                                Toast.makeText(EditOwnerDetailsActivity.this, "Failed to update owner details", Toast.LENGTH_SHORT).show();
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

        if (owner != null && !TextUtils.isEmpty(owner.getDocType())) {
            int position = adapter.getPosition(owner.getDocType());
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
                // Handle the home button click
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
