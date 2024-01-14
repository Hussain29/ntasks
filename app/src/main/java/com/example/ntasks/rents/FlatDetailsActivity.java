package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;

import java.util.ArrayList;
import java.util.List;

public class FlatDetailsActivity extends AppCompatActivity {

    private List<Apartment> apartmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_details);
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("FLAT DETAILS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        // Retrieve Flat object from Intent
        apartmentList = getIntent().getParcelableArrayListExtra("apartmentList");
        Flats flat = getIntent().getParcelableExtra("flat");


        // Find views
        ImageView imageView2 = findViewById(R.id.imageView2);
        TextView tvFlatNo = findViewById(R.id.tvfaptName);
        TextView tvArea = findViewById(R.id.tvareafapt);
        TextView tvFlatNotes = findViewById(R.id.tvfaptnotes);
        TextView tvApartmentName = findViewById(R.id.tvidap);
        TextView tvfType = findViewById(R.id.tvflattype);
        TextView tvOwnerName = findViewById(R.id.tvownerfapt);
        TextView tvVendorName = findViewById(R.id.tvvendorfapt);
        Button btnApartmentDetails = findViewById(R.id.btnApartmentDetails);

        // Check if the flat object is not null
        if (flat != null) {
            // Set values to views
            imageView2.setImageResource(R.drawable.flat); // You can change this image based on your requirements
            tvFlatNo.setText(flat.getFlatNo());
            tvArea.setText("Area: " + flat.getArea());
            tvFlatNotes.setText("Flat Notes: " + flat.getFlatNotes());
            tvApartmentName.setText("Apartment Name: " + flat.getApartmentName());
            tvfType.setText("Flat Type: " + flat.getfType());
            tvOwnerName.setText("Owner: " + flat.getOwnerName());
            tvVendorName.setText("Vendor: " + flat.getVendorName());

            // Set onClickListener for the Apartment Details button
            btnApartmentDetails.setOnClickListener(v -> {
                // Create an Intent to navigate to ApartmentDetailsActivity
                Intent intent = new Intent(FlatDetailsActivity.this, ApartmentDetailsActivity.class);

                // Assuming you have a method to get Apartment object from apartment name
                Apartment apartment = getApartmentByName(flat.getApartmentName());

                if (apartment != null) {
                    intent.putExtra("apartment", apartment);
                    intent.putExtra("apartmentList", new ArrayList<>(apartmentList)); // Pass the entire list

                    // Start the activity
                    startActivity(intent);
                } else {
                    // Handle the case where the apartment object is null
                }
            });
        }
    }

    // Method to get Apartment object by name (Replace with your actual implementation)
    private Apartment getApartmentByName(String apartmentName) {
        for (Apartment apartment : apartmentList) {
            if (apartment.getAptName().equals(apartmentName)) {
                return apartment;
            }
        }
        return null; // Return null if the apartment is not found
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
