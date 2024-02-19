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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EditTenantDetailsActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;

    private EditText etTenantName, etTenantFatherName, etTenantPerAddress, etTenantPrevAddress,
            etTenantOccupation, etAge, etTenantWorkAddress, etTenantPhoneNumber, etTenantPhoneNumber2,
            etTenantPhoneNumber3, etTenantRent, etAdvanceAmount, etAdmissionDate, etTenantNotes;
    private Button btnSave;
    private ProgressDialog progressDialog;
    private Spinner docTypeSpinner;
    private ConstraintLayout tenantImageView;

    private Spinner spinnerpay, spinnernoof, propertySpinner;

    private String photoUrl;
    private String docUrl;
    private Tenant tenant;
    private String selectedDocType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittenant_details);

        // Retrieve tenant from Intent
        tenant = getIntent().getParcelableExtra("tenant_details");

        // Check if tenant is null before accessing its properties
        if (tenant != null) {
            // Initialize Views
            etTenantName = findViewById(R.id.ettenantName);
            etTenantFatherName = findViewById(R.id.ettenantfathername);
            etTenantPerAddress = findViewById(R.id.ettenantperaddress);
            etTenantPrevAddress = findViewById(R.id.ettenantprevaddress);
            etTenantOccupation = findViewById(R.id.ettenantoccupation);
            etAge = findViewById(R.id.ettenantAge);
            etTenantWorkAddress = findViewById(R.id.ettenantworkaddress);
            etTenantPhoneNumber = findViewById(R.id.ettenantphonenumber);
            etTenantPhoneNumber2 = findViewById(R.id.ettenantphonenumber2);
            etTenantPhoneNumber3 = findViewById(R.id.ettenantphonenumber3);
            etTenantRent = findViewById(R.id.ettentantrent);
            etAdvanceAmount = findViewById(R.id.etadvanceamount);
            etAdmissionDate = findViewById(R.id.etadmissiondate);
            etTenantNotes = findViewById(R.id.ettenantnotes);
            btnSave = findViewById(R.id.btntenantsave);

            spinnerpay = findViewById(R.id.spinpayday);
            spinnernoof = findViewById(R.id.etnoofpeople);
            propertySpinner = findViewById(R.id.spinproperty);
            docTypeSpinner = findViewById(R.id.spindoctype);
            tenantImageView = findViewById(R.id.addimg);

            tenantImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFileChooser(PICK_FILE_REQUEST_IMG);
                }
            });

            LinearLayout llAttach = findViewById(R.id.attachfile);
            llAttach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFileChooser(PICK_FILE_REQUEST_DOC);
                }
            });

            // Set EditTexts with tenant details
            etTenantName.setText(tenant.getTenantName());
            etTenantFatherName.setText(tenant.getTenantFatherName());
            etTenantPerAddress.setText(tenant.getTenantPerAddress());
            etTenantPrevAddress.setText(tenant.getTenantPrevAddress());
            etTenantOccupation.setText(tenant.getTenantOccupation());
            etAge.setText(tenant.getAge());
            etTenantWorkAddress.setText(tenant.getTenantWorkAddress());
            etTenantPhoneNumber.setText(tenant.getTenantPhoneNumber());
            etTenantPhoneNumber2.setText(tenant.getTenantPhoneNumber2());
            etTenantPhoneNumber3.setText(tenant.getTenantPhoneNumber3());
            etTenantRent.setText(tenant.getTenantRent());
            etAdvanceAmount.setText(tenant.getAdvanceAmount());
            etAdmissionDate.setText(tenant.getAdmissionDate());
            etTenantNotes.setText(tenant.getTenantNotes());
            docUrl = tenant.getDocUrl();
            photoUrl = tenant.getImgUrl();

            setupSpinnerForDocType();

            // Setup payment day, no of people, and ID type spinners
            setupSpinner(R.array.Paymentday, spinnerpay);
            setupSpinner(R.array.noof, spinnernoof);

            // Setup properties spinner
            setupPropertiesSpinner();

            // Get the ActionBar
            ActionBar actionBar = getSupportActionBar();

            // Set the title
            actionBar.setTitle("EDIT TENANT DETAILS");

            // Enable the back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

            // Set onClickListener for Save button
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTenantDetails();
                }
            });
        } else {
            // Log an error or show a message indicating that the tenant data is null
            Log.e("EditTenantDetailsActivity", "Tenant data is null");
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


    private void setupSpinner(int arrayId, Spinner spinner) {
        String[] items = getResources().getStringArray(arrayId);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (tenant != null) {
            String selectedItem = ""; // Set default value
            // Logic to get the default item based on tenant details
            // For example:
            if (arrayId == R.array.Paymentday) {
                selectedItem = tenant.getPayday();
            } else if (arrayId == R.array.noof) {
                selectedItem = tenant.getNoOfPeople();
            }
            int position = adapter.getPosition(selectedItem);
            if (position != -1) {
                spinner.setSelection(position);
            }
        }
    }

    private void setupPropertiesSpinner() {
        DatabaseReference flatRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");
        DatabaseReference independentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");

        List<String> propertyList = new ArrayList<>();
        AtomicInteger requestsCompleted = new AtomicInteger(0);
        propertyList.add("Select Property");

        ValueEventListener propertyListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot propertySnapshot : dataSnapshot.getChildren()) {
                    String propertyName;
                    if (dataSnapshot.getKey().equals("Flats")) {
                        propertyName = propertySnapshot.child("flatNo").getValue(String.class)  + ", " + propertySnapshot.child("apartmentName").getValue(String.class);
                    } else {
                        propertyName = propertySnapshot.child("indpName").getValue(String.class);
                    }
                    if (!TextUtils.isEmpty(propertyName)) {
                        propertyList.add(propertyName);
                    }
                }

                // Increment the counter and check if both requests are completed
                if (requestsCompleted.incrementAndGet() == 2) {
                    updatePropertiesSpinner(propertyList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("AddTenantsActivity", "Error retrieving property data", databaseError.toException());
            }
        };

        // Attach the listeners to the respective references
        flatRef.addListenerForSingleValueEvent(propertyListener);
        independentRef.addListenerForSingleValueEvent(propertyListener);
    }


    private void updatePropertiesSpinner(List<String> propertyList) {
        ArrayAdapter<String> propertyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, propertyList);
        propertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertySpinner.setAdapter(propertyAdapter);

        if (tenant != null && !TextUtils.isEmpty(tenant.getPropertyName())) {
            int position = propertyAdapter.getPosition(tenant.getPropertyName());
            if (position != -1) {
                propertySpinner.setSelection(position);
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
                        Log.d("EditTenantActivity", "Document URL: " + docUrl);
                        Toast.makeText(EditTenantDetailsActivity.this, "Document upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditTenantDetailsActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Log.e("EditTenantActivity", "Document upload failed", e);
                });
    }

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
                        Log.d("EditTenantActivity", "Image URL: " + photoUrl);
                        Toast.makeText(EditTenantDetailsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditTenantDetailsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Log.e("EditTenantActivity", "Image upload failed", e);
                });
    }

    private void updateTenantDetails() {
        if (tenant != null) {
            // Update tenant details with EditText values
            tenant.setTenantName(etTenantName.getText().toString().trim());
            tenant.setTenantFatherName(etTenantFatherName.getText().toString().trim());
            tenant.setTenantPerAddress(etTenantPerAddress.getText().toString().trim());
            tenant.setTenantPrevAddress(etTenantPrevAddress.getText().toString().trim());
            tenant.setTenantOccupation(etTenantOccupation.getText().toString().trim());
            tenant.setAge(etAge.getText().toString().trim());
            tenant.setTenantWorkAddress(etTenantWorkAddress.getText().toString().trim());
            tenant.setTenantPhoneNumber(etTenantPhoneNumber.getText().toString().trim());
            tenant.setTenantPhoneNumber2(etTenantPhoneNumber2.getText().toString().trim());
            tenant.setTenantPhoneNumber3(etTenantPhoneNumber3.getText().toString().trim());
            tenant.setTenantRent(etTenantRent.getText().toString().trim());
            tenant.setAdvanceAmount(etAdvanceAmount.getText().toString().trim());
            tenant.setAdmissionDate(etAdmissionDate.getText().toString().trim());
            tenant.setTenantNotes(etTenantNotes.getText().toString().trim());
            tenant.setDocType(docTypeSpinner.getSelectedItem().toString());
            tenant.setDocUrl(docUrl);
            tenant.setImgUrl(photoUrl);

            // Save the updated tenant details to the database
            DatabaseReference tenantRef = FirebaseDatabase.getInstance().getReference().child("Rents/Tenants").child(tenant.getTenantId());
            tenantRef.setValue(tenant)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditTenantDetailsActivity.this, "Tenant details updated successfully", Toast.LENGTH_SHORT).show();
                                finish(); // Finish the activity after updating the details
                            } else {
                                Toast.makeText(EditTenantDetailsActivity.this, "Failed to update tenant details", Toast.LENGTH_SHORT).show();
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

        if (tenant != null && !TextUtils.isEmpty(tenant.getDocType())) {
            int position = adapter.getPosition(tenant.getDocType());
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
