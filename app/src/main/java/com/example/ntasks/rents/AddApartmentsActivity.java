package com.example.ntasks.rents;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

public class AddApartmentsActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;

    private ImageView apartmentImageView;
    private ConstraintLayout addapartment;
    private Uri imageUri;
    private Uri documentUri;
    private Spinner docTypeSpinner;
    private EditText etCoordinates;  // Add this line to your existing declarations

    private EditText etAptId, etAptName, etAptAddress, etAptArea, etAptUnits, etAptFloor, etAptShops, etAptNotes;
    private Button saveButton;
    private DatabaseReference apartmentsRef, ownersRef, vendorsRef;
    private Spinner spinOwner, spinVendors;
    private String photoUrl;
    private String docUrl;
    private String selectedDocType;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addapartments);

        etAptId = findViewById(R.id.etaptid);
        etAptName = findViewById(R.id.etaptName);
        etAptAddress = findViewById(R.id.etaptadd);
        etAptArea = findViewById(R.id.etaptarea);
        etAptUnits = findViewById(R.id.etaptunits);
        etAptFloor = findViewById(R.id.etaptfloor);
        etAptShops = findViewById(R.id.etaptshops);
        etAptNotes = findViewById(R.id.etaptNotes);
        saveButton = findViewById(R.id.btnaptadd);


        addapartment=findViewById(R.id.addapartment);
        apartmentImageView = findViewById(R.id.ivaddapt);
        docTypeSpinner = findViewById(R.id.spinnerdoc);
        spinOwner = findViewById(R.id.spinowner);  // Add this line to your existing declarations
        spinVendors = findViewById(R.id.spinvendors);  // Add this line to your existing declarations
        etCoordinates = findViewById(R.id.etsplit);  // Add this line to your existing declarations

        apartmentsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");
        ownersRef = FirebaseDatabase.getInstance().getReference().child("Rents/Owners");
        vendorsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");

        // Inside your onCreate method or wherever you initialize your views
        LinearLayout llAttach = findViewById(R.id.llattach);
        llAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_DOC);
            }
        });

        addapartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_IMG);
            }
        });

        apartmentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_IMG);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSaveApartmentDetails();
            }
        });

        // Setup document type spinner
        setupSpinnerForDocType();

        // Setup owner and vendor spinners
        setupSpinnerWithOwners();
        setupSpinnerWithVendors();
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
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                List<String> vendorNames = new ArrayList<>();

                vendorNames.add("Select Vendor");

                for (com.google.firebase.database.DataSnapshot vendorSnapshot : snapshot.getChildren()) {
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
                        Toast.makeText(AddApartmentsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddApartmentsActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddApartmentsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddApartmentsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();// Notify the adapter that the data has changed

                    Log.e("AddVendorsActivity", "Image upload failed", e);
                });
    }


    private void validateAndSaveApartmentDetails() {
        String aptId = apartmentsRef.push().getKey();
        String aptName = etAptName.getText().toString().trim();
        String aptAddress = etAptAddress.getText().toString().trim();
        String aptArea = etAptArea.getText().toString().trim();
        String aptUnits = etAptUnits.getText().toString().trim();
        String aptFloor = etAptFloor.getText().toString().trim();
        String aptShops = etAptShops.getText().toString().trim();
        String aptNotes = etAptNotes.getText().toString().trim();
        String coordinates = etCoordinates.getText().toString().trim();  // Add this line to your existing validation

        if (isValidApartmentId(aptId)) {
            saveApartmentDetails();
        } else {
            Toast.makeText(AddApartmentsActivity.this, "Please fill all the necessary fields with valid data", Toast.LENGTH_SHORT).show();
        }
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
        String docType = docTypeSpinner.getSelectedItem().toString();
        String ownerName = spinOwner.getSelectedItem().toString();  // Add this line to your existing save logic
        String vendorName = spinVendors.getSelectedItem().toString();  // Add this line to your existing save logic
        String coordinates = etCoordinates.getText().toString().trim();  // Add this line to your existing save logic

        String userId = getCurrentUserId();

        if (!TextUtils.isEmpty(aptId) && !TextUtils.isEmpty(aptName) && !TextUtils.isEmpty(aptAddress)
                && !TextUtils.isEmpty(aptArea) && !TextUtils.isEmpty(aptUnits) && !TextUtils.isEmpty(aptFloor)
                && !TextUtils.isEmpty(aptShops) && !TextUtils.isEmpty(ownerName) && !TextUtils.isEmpty(vendorName)) {

            Apartment apartment = new Apartment(aptId, aptName, aptAddress, aptArea, aptUnits, aptFloor,
                    aptShops, aptNotes, userId, vendorName, ownerName, coordinates, docType, photoUrl, docUrl);

            apartmentsRef.child(aptId).setValue(apartment);

            Toast.makeText(AddApartmentsActivity.this, "Plot details saved successfully!", Toast.LENGTH_SHORT).show();
            finish();

        } else {
            Toast.makeText(AddApartmentsActivity.this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidApartmentId(String aptId) {
        return !TextUtils.isEmpty(aptId) && aptId.length() >= 4;
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            return "Unknown User";
        }
    }
}
