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
import com.example.ntasks.TaskModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VendorsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VendorAdapter vendorAdapter;
    private ArrayList<Vendor> vendorList;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors);

        Button btnAddVendor = findViewById(R.id.btnAddVendor);

        btnAddVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VendorsActivity.this, AddVendorsActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerViewVendors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vendorList = new ArrayList<>();

        // Retrieve data from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vendorList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Vendor vendor = snapshot.getValue(Vendor.class);
                    vendorList.add(vendor);
                }
                vendorAdapter.notifyDataSetChanged();
                progressDialog.dismiss(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        vendorAdapter = new VendorAdapter(this, vendorList);
        recyclerView.setAdapter(vendorAdapter);

        // Set up item click listener
        vendorAdapter.setOnItemClickListener(new VendorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Vendor vendor) {
                // Handle item click here
                // You can open VendorDetailsActivity and pass relevant data through Intent
                openVendorDetailsActivity(vendor);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("VENDORS");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void openVendorDetailsActivity(Vendor vendor) {
        Intent intent = new Intent(VendorsActivity.this, VendorDetailsActivity.class);
        intent.putExtra("vendor", new Vendor(vendor.getVendorId(), vendor.getVendorName(), vendor.getVendorAddress(), vendor.getVendorEmail(), vendor.getVendorPhone1(), vendor.getVendorPhone2(), vendor.getVendorNotes(), vendor.getUserId()));
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


/*
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

public class VendorsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VendorAdapter vendorAdapter;
    private ArrayList<Vendor> vendorList;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors);

        Button btnAddVendor = findViewById(R.id.btnAddVendor);

        btnAddVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VendorsActivity.this, AddVendorsActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerViewVendors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vendorList = new ArrayList<>();

        // Retrieve data from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents/Vendors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vendorList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Vendor vendor = snapshot.getValue(Vendor.class);
                    vendorList.add(vendor);
                }
                vendorAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        vendorAdapter = new VendorAdapter(this, vendorList);
        recyclerView.setAdapter(vendorAdapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("VENDORS");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
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
