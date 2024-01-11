package com.example.ntasks.rents;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddFlatsActivity extends AppCompatActivity {

    private Button saveButton;
    private EditText etFlatId, etArea, etFlatNo, etFlatNotes;
    private Spinner spinApart, spinFType;

    private DatabaseReference apartmentRef, flatsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addflats);

        apartmentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");
        flatsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");

        saveButton = findViewById(R.id.btnaddflats);

        etFlatId = findViewById(R.id.etflatid);
        etArea = findViewById(R.id.etarflat);
        etFlatNo = findViewById(R.id.etflatno);
        etFlatNotes = findViewById(R.id.etflatNotes);

        spinApart = findViewById(R.id.spinapart);
        spinFType = findViewById(R.id.spinftype);

        setupSpinnerWithApartments();
        setupSpinnerForFType(); // Added this line to initialize the FType spinner

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

    private void setupSpinnerForFType() {
        // Remove this line: Spinner spinFType = findViewById(R.id.spinftype);

        // Get the string array from resources
        String[] flatTypes = getResources().getStringArray(R.array.flattypes);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, flatTypes);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinFType.setAdapter(adapter);
    }

    public interface Callback<T> {
        void onSuccess(T result);
        void onFailure(String error);
    }

    private void getOwnerNameAndVendorNameForApartment(String apartmentName, Callback<Pair<String, String>> callback) {
        apartmentRef.orderByChild("aptName").equalTo(apartmentName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot apartmentSnapshot = snapshot.getChildren().iterator().next();
                    String ownerName = apartmentSnapshot.child("ownerName").getValue(String.class);
                    String vendorName = apartmentSnapshot.child("vendorName").getValue(String.class);

                    callback.onSuccess(new Pair<>(ownerName, vendorName));
                } else {
                    callback.onFailure("Apartment not found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
                callback.onFailure(error.getMessage());
            }
        });
    }

    private void validateAndSaveFlatDetails() {
        try {
            String flatId = etFlatId.getText().toString().trim();
            String area = etArea.getText().toString().trim();
            String flatNo = etFlatNo.getText().toString().trim();
            String flatNotes = etFlatNotes.getText().toString().trim();
            String apartmentName = getSelectedItemString(spinApart);
            String fType = getSelectedItemString(spinFType);

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
        String flatId = etFlatId.getText().toString().trim();
        String area = etArea.getText().toString().trim();
        String flatNo = etFlatNo.getText().toString().trim();
        String flatNotes = etFlatNotes.getText().toString().trim();
        String apartmentName = spinApart.getSelectedItem().toString();
        String fType = spinFType.getSelectedItem().toString();

        // Retrieve ownerName and vendorName from the selected apartment
        getOwnerNameAndVendorNameForApartment(apartmentName, new Callback<Pair<String, String>>() {
            @Override
            public void onSuccess(Pair<String, String> result) {
                // Now, you have the ownerName and vendorName, proceed to save the flat details
                String userId = getCurrentUserId();

                if (!TextUtils.isEmpty(flatId) && !TextUtils.isEmpty(area) && !TextUtils.isEmpty(flatNo)
                        && !TextUtils.isEmpty(apartmentName) && !TextUtils.isEmpty(fType)
                        && !TextUtils.isEmpty(result.first) && !TextUtils.isEmpty(result.second)) {

                    Flats flat = new Flats(flatId, area, flatNo, flatNotes, apartmentName, fType, result.first, result.second, userId);

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
