/*
package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddVendorsActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 2;
    static final int PICK_FILE_REQUEST_IMG = 4;
    private Button add1, add2, saveButton;
    CardView cvv2;
    CardView cvaddattach;
    LinearLayout ll2;
    private Spinner spinven;
    private EditText etVendorId, etVendorName, etVendorAddress, etVendorEmail, etVendorPhoneNumber, etVendorPhoneNumber2, etVendorNotes;
    private DatabaseReference vendorsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvendors);

        setupSpinnerForFType();
        spinven=findViewById(R.id.spinnervendor);

        vendorsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");

        etVendorPhoneNumber2 = findViewById(R.id.etvendorPhonenumber2);
        etVendorNotes = findViewById(R.id.etvendorNotes);
        add1 = findViewById(R.id.add1);
        saveButton = findViewById(R.id.btnaddvendordet);
        cvv2 = findViewById(R.id.cvv2);
        cvaddattach=findViewById(R.id.cvaddattach);

        ImageView imaddperson = findViewById(R.id.addvendorpic);

        etVendorId = findViewById(R.id.etvendorid);
        etVendorName = findViewById(R.id.etvendorName);
        etVendorAddress = findViewById(R.id.etvendoradd);
        etVendorEmail = findViewById(R.id.etvendoremail);
        etVendorPhoneNumber = findViewById(R.id.etvendorPhonenumber);
        etVendorPhoneNumber2 = findViewById(R.id.etvendorPhonenumber2);
        etVendorNotes = findViewById(R.id.etvendorNotes);


        imaddperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddVendorsActivity.this, "Select Your IMAGE", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent, PICK_FILE_REQUEST_IMG);
            }
        });
        cvaddattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddVendorsActivity.this, "Select Your Document", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent, PICK_FILE_REQUEST_IMG);
            }
        });
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvv2.setVisibility(View.VISIBLE);
            }
        });
        */
/*add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvph2.setVisibility(View.VISIBLE);
            }
        });*//*



        */
/*cvaddattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSaveVendorDetails();
            }
        });*//*


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveVendorDetails();
            }
        });

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD VENDORS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }
    private void setupSpinnerForFType() {
        // Remove this line:
        Spinner spinven = findViewById(R.id.spinnervendor);

        // Get the string array from resources
        String[] idtypes = getResources().getStringArray(R.array.IDtypes);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idtypes);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinven.setAdapter(adapter);
    }
    private void validateAndSaveVendorDetails() {
        String vendorId = etVendorId.getText().toString().trim();
        String vendorName = etVendorName.getText().toString().trim();
        String vendorAddress = etVendorAddress.getText().toString().trim();
        String vendorEmail = etVendorEmail.getText().toString().trim();
        String vendorPhone1 = etVendorPhoneNumber.getText().toString().trim();
        String vendorPhone2 = etVendorPhoneNumber2.getText().toString().trim();
        String vendorNotes = etVendorNotes.getText().toString().trim();

        if (isValidVendorId(vendorId) && isValidEmail(vendorEmail) && isValidPhoneNumber(vendorPhone1) && isValidPhoneNumber(vendorPhone2)) {
            saveVendorDetails();
        } else {
            Toast.makeText(AddVendorsActivity.this, "Please fill all the necessary fields with valid data", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveVendorDetails() {
        String vendorId = etVendorId.getText().toString().trim();
        String vendorName = etVendorName.getText().toString().trim();
        String vendorAddress = etVendorAddress.getText().toString().trim();
        String vendorEmail = etVendorEmail.getText().toString().trim();
        String vendorPhone1 = etVendorPhoneNumber.getText().toString().trim();
        String vendorPhone2 = etVendorPhoneNumber2.getText().toString().trim();
        String vendorNotes = etVendorNotes.getText().toString().trim();

        String userId = getCurrentUserId();

        if (!TextUtils.isEmpty(vendorId) && !TextUtils.isEmpty(vendorName) && !TextUtils.isEmpty(vendorAddress)
                && !TextUtils.isEmpty(vendorEmail) && !TextUtils.isEmpty(vendorPhone1) && !TextUtils.isEmpty(vendorPhone2)) {

            Vendor vendor = new Vendor(vendorId, vendorName, vendorAddress, vendorEmail, vendorPhone1, vendorPhone2, vendorNotes, userId);



            vendorsRef.child(vendorId).setValue(vendor);

            Toast.makeText(AddVendorsActivity.this, "Vendor details saved successfully!", Toast.LENGTH_SHORT).show();
            Log.d("AddVendorsActivity", "Vendor details saved - ID: " + vendorId + ", Name: " + vendorName);

            finish();
        } else {
            Toast.makeText(AddVendorsActivity.this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidVendorId(String vendorId) {
        return !TextUtils.isEmpty(vendorId) && vendorId.length() >= 4;
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
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



*/


package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AddVendorsActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 2;
    private static final int PICK_FILE_REQUEST_IMG = 4;
    private Button add1, saveButton;
    CardView cvv2;
    CardView cvaddattach;
    private Spinner spinven;
    private EditText etVendorId, etVendorName, etVendorAddress, etVendorEmail, etVendorPhoneNumber, etVendorPhoneNumber2, etVendorNotes;
    private DatabaseReference vendorsRef;

    // Add these variables
    private ImageView vendorImageView;
    private Uri imageUri;
    private Uri documentUri;
    private Spinner docTypeSpinner;

    private String photoUrl;
    private String docUrl;
    private String selectedDocType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvendors);

        setupSpinnerForFType();

        vendorsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");

        etVendorPhoneNumber2 = findViewById(R.id.etvendorPhonenumber2);
        etVendorNotes = findViewById(R.id.etvendorNotes);
        add1 = findViewById(R.id.add1);
        saveButton = findViewById(R.id.btnaddvendordet);
        cvv2 = findViewById(R.id.cvv2);
        cvaddattach=findViewById(R.id.cvaddattach);

        vendorImageView = findViewById(R.id.addvendorpic);
        docTypeSpinner = findViewById(R.id.spinnervendor);

        etVendorId = findViewById(R.id.etvendorid);
        etVendorName = findViewById(R.id.etvendorName);
        etVendorAddress = findViewById(R.id.etvendoradd);
        etVendorEmail = findViewById(R.id.etvendoremail);
        etVendorPhoneNumber = findViewById(R.id.etvendorPhonenumber);

        cvaddattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST);
            }
        });

        vendorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_IMG);
            }
        });

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvv2.setVisibility(View.VISIBLE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSaveVendorDetails();
            }
        });

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD VENDORS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void setupSpinnerForFType() {
        spinven = findViewById(R.id.spinnervendor);
        String[] ownertypes = getResources().getStringArray(R.array.IDtypes);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ownertypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinven.setAdapter(adapter);
        spinven.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedDocType = ownertypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    private void showFileChooser(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(requestCode == PICK_FILE_REQUEST ? "*/*" : "image/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data != null && data.getData() != null) {
                    Uri selectedDocUri = data.getData();
                    uploadDocumentToFirebaseStorage(selectedDocUri);
                }
            } else if (requestCode == PICK_FILE_REQUEST_IMG) {
                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    uploadImageToFirebaseStorage(selectedImageUri);
                }
            }
        }
    }

    // Add this method to handle document upload
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
                    Toast.makeText(AddVendorsActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
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
                        photoUrl = uri.toString();
                        Log.d("AddVendorsActivity", "Image URL: " + photoUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddVendorsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddVendorsActivity", "Image upload failed", e);
                });
    }

    private void validateAndSaveVendorDetails() {
        String vendorId = etVendorId.getText().toString().trim();
        String vendorName = etVendorName.getText().toString().trim();
        String vendorAddress = etVendorAddress.getText().toString().trim();
        String vendorEmail = etVendorEmail.getText().toString().trim();
        String vendorPhone1 = etVendorPhoneNumber.getText().toString().trim();

        if (isValidVendorId(vendorId) && isValidEmail(vendorEmail) && isValidPhoneNumber(vendorPhone1)) {
            saveVendorDetails();
        } else {
            Toast.makeText(AddVendorsActivity.this, "Please fill all the necessary fields with valid data", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveVendorDetails() {
        String vendorId = etVendorId.getText().toString().trim();
        String vendorName = etVendorName.getText().toString().trim();
        String vendorAddress = etVendorAddress.getText().toString().trim();
        String vendorEmail = etVendorEmail.getText().toString().trim();
        String vendorPhone1 = etVendorPhoneNumber.getText().toString().trim();
        String vendorPhone2 = etVendorPhoneNumber2.getText().toString().trim();
        String vendorNotes = etVendorNotes.getText().toString().trim();
        String docType = docTypeSpinner.getSelectedItem().toString();

        String userId = getCurrentUserId();

        if (!TextUtils.isEmpty(vendorId) && !TextUtils.isEmpty(vendorName) && !TextUtils.isEmpty(vendorAddress)
                && !TextUtils.isEmpty(vendorEmail) && !TextUtils.isEmpty(vendorPhone1)) {

            Vendor vendor = new Vendor(vendorId, vendorName, vendorAddress, vendorEmail, vendorPhone1,
                    vendorPhone2, vendorNotes, userId, selectedDocType, photoUrl, docUrl);

            vendorsRef.child(vendorId).setValue(vendor);

            Toast.makeText(AddVendorsActivity.this, "Vendor details saved successfully!", Toast.LENGTH_SHORT).show();
            Log.d("AddVendorsActivity", "Vendor details saved - ID: " + vendorId + ", Name: " + vendorName);

            finish();
        } else {
            Toast.makeText(AddVendorsActivity.this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidVendorId(String vendorId) {
        return !TextUtils.isEmpty(vendorId) && vendorId.length() >= 4;
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
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
