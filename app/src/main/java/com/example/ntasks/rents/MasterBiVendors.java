package com.example.ntasks.rents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ntasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MasterBiVendors extends AppCompatActivity {

    private RecyclerView recyclerViewVendors;
    private BVendorsAdapter bVendorsAdapter;
    private ArrayList<BusinessVendor> vendorList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_bi_vendors);

        Button btnAddVendor = findViewById(R.id.btnaddbven);

        btnAddVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterBiVendors.this, AddBusinessVendorsActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerViewVendors = findViewById(R.id.recyclerViewbven);
        recyclerViewVendors.setLayoutManager(new LinearLayoutManager(this));

        vendorList = new ArrayList<>();

        // Retrieve data from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vendorList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BusinessVendor vendor = snapshot.getValue(BusinessVendor.class);
                    vendorList.add(vendor);
                }
                bVendorsAdapter.notifyDataSetChanged();
                progressDialog.dismiss(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        bVendorsAdapter = new BVendorsAdapter(this, vendorList);
        recyclerViewVendors.setAdapter(bVendorsAdapter);

        // Set up item click listener
        bVendorsAdapter.setOnItemClickListener(new BVendorsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BusinessVendor vendor) {
                // Handle item click here
                // You can open BusinessVendorDetailsActivity and pass relevant data through Intent
                openBusinessVendorDetailsActivity(vendor);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("BUSINESS VENDORS");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void openBusinessVendorDetailsActivity(BusinessVendor vendor) {
        Intent intent = new Intent(MasterBiVendors.this, BusinessVendorDetailsActivity.class);
        intent.putExtra("business_vendor", new BusinessVendor(
                vendor.getVendorId(),
                vendor.getVendorName(),
                vendor.getContactPerson(),
                vendor.getMobileNumber(),
                vendor.getLandlineNumber(),
                vendor.getAddress(),
                vendor.getEmailAddress(),
                vendor.getAlternateContact1(),
                vendor.getAlternateContact2(),
                vendor.getProducts(),
                vendor.getCoordinates(),
                vendor.getNotes(),
                vendor.getDocUrl(),
                vendor.getUserId(),
                vendor.getPhotoUrl()
        ));
        startActivity(intent);
        // Add more data if needed
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
