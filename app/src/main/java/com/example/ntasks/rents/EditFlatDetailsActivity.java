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

public class EditFlatDetailsActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;

    private Spinner spinApartment, spinFlatType, docTypeSpinner;

    private DatabaseReference apartmentRef;

    private EditText etFlatNo, etArea, etFlatNotes;
    private Button btnSave;

    private ProgressDialog progressDialog;
    private String photoUrl;
    private String docUrl;
    private String selectedDocType;

    ConstraintLayout AddPic;

    private Flats flat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flat_details);

        apartmentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");

        flat = getIntent().getParcelableExtra("flat_details");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("EDIT FLAT DETAILS");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        etFlatNo = findViewById(R.id.etflatno);
        etArea = findViewById(R.id.etarflat);
        etFlatNotes = findViewById(R.id.etflatNotes);
        btnSave = findViewById(R.id.btnaddflats);
        docTypeSpinner = findViewById(R.id.spindoctype);
        spinApartment = findViewById(R.id.spinapartment);
        spinFlatType = findViewById(R.id.spinflattype);

        AddPic = findViewById(R.id.ivaddflat);

        AddPic.setOnClickListener(new View.OnClickListener() {
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
        });// Add this line to your existing declarations

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFlatDetails();
            }
        });

        setupSpinnerWithApartments();
        setupSpinnerForFlatType();
        setupSpinnerForDocType();

        if (flat != null) {
            etFlatNo.setText(flat.getFlatNo());
            etArea.setText(flat.getArea());
            etFlatNotes.setText(flat.getFlatNotes());
            docUrl = flat.getDocUrl();
            photoUrl = flat.getPhotoUrl();
        } else {
            Toast.makeText(this, "No flat data found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateFlatDetails() {
        if (flat != null) {
            flat.setFlatNo(etFlatNo.getText().toString().trim());
            flat.setArea(etArea.getText().toString().trim());
            flat.setFlatNotes(etFlatNotes.getText().toString().trim());
            flat.setDocUrl(docUrl);
            flat.setPhotoUrl(photoUrl);
            flat.setDocType(docTypeSpinner.getSelectedItem().toString());
            flat.setfType(spinFlatType.getSelectedItem().toString());

            DatabaseReference flatRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats").child(flat.getFlatId());
            flatRef.setValue(flat)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditFlatDetailsActivity.this, "Flat details updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EditFlatDetailsActivity.this, "Failed to update flat details", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditFlatDetailsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditFlatDetailsActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditFlatDetailsActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();// Notify the adapter that the data has changed
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditFlatDetailsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();// Notify the adapter that the data has changed

                    Log.e("AddVendorsActivity", "Image upload failed", e);
                });
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

                ArrayAdapter<String> apartmentAdapter = new ArrayAdapter<>(EditFlatDetailsActivity.this, android.R.layout.simple_spinner_item, apartmentNames);
                apartmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinApartment.setAdapter(apartmentAdapter);

                if (flat != null && !TextUtils.isEmpty(flat.getApartmentName())) {
                    int position = apartmentAdapter.getPosition(flat.getApartmentName());
                    if (position != -1) {
                        spinApartment.setSelection(position);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    private void setupSpinnerForFlatType() {
        String[] flatTypes = getResources().getStringArray(R.array.flattypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, flatTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinFlatType.setAdapter(adapter);

        if (flat != null && !TextUtils.isEmpty(flat.getfType())) {
            int position = adapter.getPosition(flat.getfType());
            if (position != -1) {
                spinFlatType.setSelection(position);
            }
        }
    }

    private void setupSpinnerForDocType() {

        String[] docTypes = getResources().getStringArray(R.array.DocIDtypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, docTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        docTypeSpinner.setAdapter(adapter);

        if (flat != null && !TextUtils.isEmpty(flat.getDocType())) {
            int position = adapter.getPosition(flat.getDocType());
            if (position != -1) {
                docTypeSpinner.setSelection(position);
            }
        }
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
