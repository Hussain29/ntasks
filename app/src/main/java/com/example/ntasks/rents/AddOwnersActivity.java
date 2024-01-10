package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
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

public class AddOwnersActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 2; // You can use any integer value
    private static final int PICK_FILE_REQUEST_IMG = 4; // You can use any integer value
    private Button add1, add2, saveButton;
    CardView cv3;
    LinearLayout ll2;

    private EditText etOwnerId, etOwnerName, etOwnerAddress, etOwnerEmail, etOwnerPhone1, etOwnerPhone2, etOwnerPhone3, etOwnerNotes;
    private DatabaseReference ownersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addowners);

        ownersRef = FirebaseDatabase.getInstance().getReference().child("Rents/Owners");

        CardView cvaddattach = findViewById(R.id.cvaddattach);
        cv3 = findViewById(R.id.cv3);
        ll2 = findViewById(R.id.ll2);
        add1 = findViewById(R.id.add1);
        add2 = findViewById(R.id.add2);
        saveButton = findViewById(R.id.btnsowner);

        ImageView imaddperson = findViewById(R.id.imaddperson);

        etOwnerId = findViewById(R.id.etownerid);
        etOwnerName = findViewById(R.id.etownerName);
        etOwnerAddress = findViewById(R.id.etowneradd);
        etOwnerEmail = findViewById(R.id.etowneremail);
        etOwnerPhone1 = findViewById(R.id.etownerPhonenumber);
        etOwnerPhone2 = findViewById(R.id.etownerPhonenumber2);
        etOwnerPhone3 = findViewById(R.id.etownerPhonenumber3);
        etOwnerNotes = findViewById(R.id.etownerNotes);

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll2.setVisibility(View.VISIBLE);
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv3.setVisibility(View.VISIBLE);
            }
        });

        imaddperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddOwnersActivity.this, "Select Your Image", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_IMG);
            }
        });

        cvaddattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSaveOwnerDetails();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOwnerDetails();
            }
        });

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD OWNERS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void validateAndSaveOwnerDetails() {
        String ownerId = etOwnerId.getText().toString().trim();
        String ownerName = etOwnerName.getText().toString().trim();
        String ownerAddress = etOwnerAddress.getText().toString().trim();
        String ownerEmail = etOwnerEmail.getText().toString().trim();
        String ownerPhone1 = etOwnerPhone1.getText().toString().trim();
        String ownerPhone2 = etOwnerPhone2.getText().toString().trim();
        String ownerPhone3 = etOwnerPhone3.getText().toString().trim();
        String ownerNotes = etOwnerNotes.getText().toString().trim();

        if (isValidOwnerId(ownerId) && isValidEmail(ownerEmail) && isValidPhoneNumber(ownerPhone1) && isValidPhoneNumber(ownerPhone2) && isValidPhoneNumber(ownerPhone3)) {
            saveOwnerDetails();
        } else {
            Toast.makeText(AddOwnersActivity.this, "Please fill all the necessary fields with valid data", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveOwnerDetails() {
        String ownerId = etOwnerId.getText().toString().trim();
        String ownerName = etOwnerName.getText().toString().trim();
        String ownerAddress = etOwnerAddress.getText().toString().trim();
        String ownerEmail = etOwnerEmail.getText().toString().trim();
        String ownerPhone1 = etOwnerPhone1.getText().toString().trim();
        String ownerPhone2 = etOwnerPhone2.getText().toString().trim();
        String ownerPhone3 = etOwnerPhone3.getText().toString().trim();
        String ownerNotes = etOwnerNotes.getText().toString().trim();

        String userId = getCurrentUserId();

        if (!TextUtils.isEmpty(ownerId) && !TextUtils.isEmpty(ownerName) && !TextUtils.isEmpty(ownerAddress)
                && !TextUtils.isEmpty(ownerEmail) && !TextUtils.isEmpty(ownerPhone1) && !TextUtils.isEmpty(ownerPhone2)) {

            // Assuming you have an Owner class, replace it with your actual Owner class
            Owner owner = new Owner(ownerId, ownerName, ownerAddress, ownerEmail, ownerPhone1, ownerPhone2, ownerPhone3, ownerNotes, userId);

            // Save owner details to Firebase
            ownersRef.child(ownerId).setValue(owner);

            // Additional actions, if needed
            Toast.makeText(AddOwnersActivity.this, "Owner details saved successfully!", Toast.LENGTH_SHORT).show();
            Log.d("AddOwnersActivity", "Owner details saved - ID: " + ownerId + ", Name: " + ownerName);

            // You can add further actions or redirection here
            // For example, redirect the user to another activity
            // Intent intent = new Intent(AddOwnersActivity.this, AnotherActivity.class);
            // startActivity(intent);

            // Finish the current activity
            finish();
        } else {
            Toast.makeText(AddOwnersActivity.this, "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}"); // Assumes a 10-digit phone number
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidOwnerId(String ownerId) {
        return !TextUtils.isEmpty(ownerId) && ownerId.length() >= 4;
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            // Handle the case when the user is not authenticated
            return "Unknown User";
        }
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

