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

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.concurrent.atomic.AtomicInteger;

public class AddTenantActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;

    private ConstraintLayout tenantImageView;
    private Uri imageUri;
    private Uri documentUri;
    private Spinner docTypeSpinner;
    private Spinner spinnerpay, spinnernoof, spinid, propertySpinner;
    private ProgressDialog progressDialog;
    private EditText etTenantId, etTenantName, etTenantFatherName, etTenantPerAddress, etTenantPrevAddress,
            etTenantOccupation, etTenantWorkAddress, etTenantPhoneNumber, etTenantPhoneNumber2,
            etTenantPhoneNumber3, etTenantRent, etAdvanceAmount, etAdmissionDate, etTenantNotes, etAge;

    private Button saveButton;
    private DatabaseReference tenantsRef;

    private String photoUrl;
    private String docUrl;
    private String selectedDocType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);



        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD TENANT");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));






        etTenantId = findViewById(R.id.ettenantid);
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
        saveButton = findViewById(R.id.btntenantsave);

        tenantImageView = findViewById(R.id.addimg);
        docTypeSpinner = findViewById(R.id.spindoctype);
        spinnerpay = findViewById(R.id.spinpayday);
        spinnernoof = findViewById(R.id.etnoofpeople);
        propertySpinner = findViewById(R.id.spinproperty);

        tenantsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Tenants");

        // Inside your onCreate method or wherever you initialize your views
        LinearLayout llAttach = findViewById(R.id.attachfile);
        llAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_DOC);
            }
        });

        tenantImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_IMG);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSaveTenantDetails();
            }
        });

        // Setup document type spinner
        setupSpinnerForDocType();

        // Setup payment day, no of people, and ID type spinners
        setupSpinner(R.array.Paymentday, spinnerpay);
        setupSpinner(R.array.noof, spinnernoof);
        setupSpinner(R.array.IDtypes, docTypeSpinner);

        // Setup properties spinner
        setupPropertiesSpinner();
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

    private void setupSpinnerForDocType() {
        String[] docTypes = getResources().getStringArray(R.array.DocIDtypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, docTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        docTypeSpinner.setAdapter(adapter);
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

    private void setupSpinner(int arrayId, Spinner spinner) {
        String[] items = getResources().getStringArray(arrayId);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setupPropertiesSpinner() {
        DatabaseReference flatRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");
        DatabaseReference independentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");

        List<String> propertyList = new ArrayList<>();
        AtomicInteger requestsCompleted = new AtomicInteger(0);

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
                        Log.d("AddTenantsActivity", "Document URL: " + docUrl);
                        Toast.makeText(AddTenantActivity.this, "Document upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddTenantActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddTenantsActivity", "Document upload failed", e);
                    progressDialog.dismiss();// Notify the adapter that the data has changed
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
                        Log.d("AddTenantsActivity", "Image URL: " + photoUrl);
                        Toast.makeText(AddTenantActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddTenantActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddTenantsActivity", "Image upload failed", e);
                    progressDialog.dismiss();// Notify the adapter that the data has changed
                });
    }

    private void validateAndSaveTenantDetails() {
        Tenant tenant = extractTenantDetailsFromUI();
        if (isValidTenantId(tenant.getTenantId())) {
            saveTenantDetails(tenant);
        } else {
            Toast.makeText(AddTenantActivity.this, "Please fill all the necessary fields with valid data", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveTenantDetails(Tenant tenant) {
        String userId = getCurrentUserId();

        if (!TextUtils.isEmpty(tenant.getTenantId()) && !TextUtils.isEmpty(tenant.getTenantName())

                && !TextUtils.isEmpty(tenant.getTenantPhoneNumber()) && !TextUtils.isEmpty(tenant.getPropertyName())
                && !TextUtils.isEmpty(tenant.getTenantRent()) && !TextUtils.isEmpty(tenant.getAdvanceAmount())
                && !TextUtils.isEmpty(tenant.getAdmissionDate())) {

            tenantsRef.child(tenant.getTenantId()).setValue(tenant, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if (error == null) {
                        Toast.makeText(AddTenantActivity.this, "Tenant details saved successfully!", Toast.LENGTH_SHORT).show();
                        Log.d("AddTenantsActivity", "Tenant details saved - ID: " + tenant.getTenantId() + ", Name: " + tenant.getTenantName());
                        finish();
                    } else {
                        Toast.makeText(AddTenantActivity.this, "Failed to save tenant details. Please try again.", Toast.LENGTH_SHORT).show();
                        Log.e("AddTenantsActivity", "Failed to save tenant details", error.toException());
                    }
                }
            });
        }
    }

    private Tenant extractTenantDetailsFromUI() {
        String tenantId = tenantsRef.push().getKey();
        String tenantName = etTenantName.getText().toString().trim();
        String tenantFatherName = etTenantFatherName.getText().toString().trim();
        String tenantPerAddress = etTenantPerAddress.getText().toString().trim();
        String tenantPrevAddress = etTenantPrevAddress.getText().toString().trim();
        String tenantOccupation = etTenantOccupation.getText().toString().trim();
        String tenantWorkAddress = etTenantWorkAddress.getText().toString().trim();
        String noOfPeople = spinnernoof.getSelectedItem().toString();
        String age = etAge.getText().toString().trim();
        String tenantPhoneNumber = etTenantPhoneNumber.getText().toString().trim();
        String tenantPhoneNumber2 = etTenantPhoneNumber2.getText().toString().trim();
        String tenantPhoneNumber3 = etTenantPhoneNumber3.getText().toString().trim();
        String propertyName = propertySpinner.getSelectedItem().toString();
        String tenantRent = etTenantRent.getText().toString().trim();
        String advanceAmount = etAdvanceAmount.getText().toString().trim();
        String admissionDate = etAdmissionDate.getText().toString().trim();
        String docType = docTypeSpinner.getSelectedItem().toString();
        String tenantNotes = etTenantNotes.getText().toString().trim();
        String payday = spinnerpay.getSelectedItem().toString();
        String status = "ACTIVE" ;

        return new Tenant(tenantId, tenantName, tenantFatherName, tenantPerAddress, tenantPrevAddress,
                tenantOccupation, tenantWorkAddress, noOfPeople, tenantPhoneNumber, tenantPhoneNumber2, tenantPhoneNumber3,
                propertyName, tenantRent, advanceAmount, admissionDate, docType, tenantNotes, photoUrl, docUrl, payday, age, status);
    }

    private boolean isValidTenantId(String tenantId) {
        return !TextUtils.isEmpty(tenantId) && tenantId.length() >= 4;
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
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the back button click
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
