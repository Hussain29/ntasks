package com.example.ntasks.rents;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import kotlin.Triple;

public class AddFlatsActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;

    private EditText etFlatId, etArea, etFlatNo, etFlatNotes;
    private Button saveButton;
    private Spinner spinApart, spinFType;
    private ImageView flatImageView;
    private ProgressDialog progressDialog;
    private Spinner docTypeSpinner;
    private String photoUrl;
    private String docUrl;


    private DatabaseReference apartmentRef, flatsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addflats);

        flatImageView = findViewById(R.id.addimg);
        docTypeSpinner = findViewById(R.id.spinid);

        apartmentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");
        flatsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");

        // Inside your onCreate method or wherever you initialize your views
        ConstraintLayout addimg;
        CardView cvattach = findViewById(R.id.cvaddattach);


        flatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddFlatsActivity.this, "Select Image", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_IMG);
            }
        });

        cvattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddFlatsActivity.this, "Select Your Document", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_DOC);
            }
        });

        setupSpinnerForDocType(); // Setup document type spinner

        etFlatId = findViewById(R.id.etflatid);
        etArea = findViewById(R.id.etarflat);
        etFlatNo = findViewById(R.id.etflatno);
        etFlatNotes = findViewById(R.id.etflatNotes);

        spinApart = findViewById(R.id.spinapart);
        spinFType = findViewById(R.id.spinftype);

        setupSpinnerWithApartments();
        setupSpinnerForFType(); // Added this line to initialize the FType spinner

        saveButton = findViewById(R.id.btnaddflats);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSaveFlatDetails();
            }
        });

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD FLAT DETAILS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void setupSpinnerWithApartments() {
        apartmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> apartmentNames = new ArrayList<>();

                apartmentNames.add("Select Apartment");

                for (DataSnapshot apartmentSnapshot : snapshot.getChildren()) {
                    String apartmentName = apartmentSnapshot.child("aptName").getValue(String.class);

                    if (apartmentName != null && !apartmentName.isEmpty()) {
                        apartmentNames.add(apartmentName);
                    }
                }

                ArrayAdapter<String> apartmentAdapter = new ArrayAdapter<>(AddFlatsActivity.this, android.R.layout.simple_spinner_item, apartmentNames);
                apartmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinApart.setAdapter(apartmentAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
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

    // Method to handle document upload
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
                        Log.d("AddFlatsActivity", "Document URL: " + docUrl);
                        Toast.makeText(AddFlatsActivity.this, "Document upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddFlatsActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddFlatsActivity", "Document upload failed", e);
                    progressDialog.dismiss();// Notify the adapter that the data has changed
                });
    }

    // Method to handle image upload
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
                        Log.d("AddFlatsActivity", "Image URL: " + photoUrl);
                        Toast.makeText(AddFlatsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddFlatsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddFlatsActivity", "Image upload failed", e);
                    progressDialog.dismiss();// Notify the adapter that the data has changed
                });
    }


    private void setupSpinnerForFType() {
        String[] flatTypes = getResources().getStringArray(R.array.flattypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, flatTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinFType.setAdapter(adapter);
    }

    private void setupSpinnerForDocType() {
        String[] docTypes = getResources().getStringArray(R.array.DocIDtypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, docTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        docTypeSpinner.setAdapter(adapter);
    }

    private void validateAndSaveFlatDetails() {
        try {
            String flatId = flatsRef.push().getKey();
            String area = etArea.getText().toString().trim();
            String flatNo = etFlatNo.getText().toString().trim();
            String flatNotes = etFlatNotes.getText().toString().trim();
            String apartmentName = getSelectedItemString(spinApart);
            String fType = getSelectedItemString(spinFType);
            String docType = docTypeSpinner.getSelectedItem().toString();

            if (!TextUtils.isEmpty(flatId) && !TextUtils.isEmpty(area) && !TextUtils.isEmpty(flatNo)
                    && !TextUtils.isEmpty(apartmentName) && !TextUtils.isEmpty(fType)) {
                saveFlatDetails();
            } else {
                Toast.makeText(AddFlatsActivity.this, "Please fill all the necessary fields with valid data", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(AddFlatsActivity.this, "NullPointerException occurred. Check logs for details.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getSelectedItemString(Spinner spinner) {
        return spinner.getSelectedItem() != null ? spinner.getSelectedItem().toString() : "";
    }

    private void saveFlatDetails() {
        String flatId = flatsRef.push().getKey();
        String area = etArea.getText().toString().trim();
        String flatNo = etFlatNo.getText().toString().trim();
        String flatNotes = etFlatNotes.getText().toString().trim();
        String apartmentName = spinApart.getSelectedItem().toString();
        String fType = spinFType.getSelectedItem().toString();
        String docType = docTypeSpinner.getSelectedItem().toString();


        // Retrieve ownerName and vendorName from the selected apartment
        getOwnerNameVendorNameAndApartmentAddress(apartmentName, new Callback<Triple<String, String, String>>() {
            @Override
            public void onSuccess(Triple<String, String, String> result) {
                // Now, you have the ownerName, vendorName, and apartmentAddress, proceed to save the flat details
                String userId = getCurrentUserId();

                if (!TextUtils.isEmpty(flatId) && !TextUtils.isEmpty(area) && !TextUtils.isEmpty(flatNo)
                        && !TextUtils.isEmpty(apartmentName) && !TextUtils.isEmpty(fType)
                        && !TextUtils.isEmpty(result.first) && !TextUtils.isEmpty(result.second)
                       /* && !TextUtils.isEmpty(result.third)*/) {

                    Flats flat = new Flats(flatId, area, flatNo, flatNotes, apartmentName, fType, result.first, result.second, userId, docType, photoUrl, docUrl, result.third);

                    flatsRef.child(flatId).setValue(flat);

                    Toast.makeText(AddFlatsActivity.this, "Flat details saved successfully!", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(AddFlatsActivity.this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String error) {
                // Handle the error
                Toast.makeText(AddFlatsActivity.this, "Failed to retrieve ownerName and vendorName: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            return "Unknown User";
        }
    }

    private void getOwnerNameVendorNameAndApartmentAddress(String apartmentName, Callback<Triple<String, String, String>> callback) {
        apartmentRef.orderByChild("aptName").equalTo(apartmentName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot aptSnapshot = dataSnapshot.getChildren().iterator().next();
                    String ownerName = aptSnapshot.child("ownerName").getValue(String.class);
                    String vendorName = aptSnapshot.child("vendorName").getValue(String.class);
                    String apartmentAddress = aptSnapshot.child("aptAddress").getValue(String.class);
                    callback.onSuccess(new Triple<>(ownerName, vendorName, apartmentAddress));
                } else {
                    callback.onFailure("Apartment not found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public class Triple<A, B, C> {
        private final A first;
        private final B second;
        private final C third;

        public Triple(A first, B second, C third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public A getFirst() {
            return first;
        }

        public B getSecond() {
            return second;
        }

        public C getThird() {
            return third;
        }
    }



    private interface Callback<T> {
        void onSuccess(T result);

        void onFailure(String error);
    }
}
