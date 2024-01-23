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
import android.os.Parcelable;
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

public class TenantsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TenantAdapter tenantAdapter;
    private ArrayList<Tenant> tenantList;

    private ArrayList<Flats> flatArrayList;
    private ArrayList<Independent> independentList;

    private ProgressDialog progressDialog;
    private Button btnpastTenants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants);

        Button btnAddTenant = findViewById(R.id.addTenants);
        btnpastTenants=findViewById(R.id.btnpastTenants);



        btnpastTenants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TenantsActivity.this,PastTenantsActivity.class);
                startActivity(intent);
            }
        });
        btnAddTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TenantsActivity.this, AddTenantActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerViewTenants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        flatArrayList = new ArrayList<>();

        DatabaseReference databaseReferenceApts = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");

        databaseReferenceApts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                flatArrayList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Flats flats = snapshot.getValue(Flats.class);
                    flatArrayList.add(flats);
                }
                // Here you might want to notify your adapter or update UI with the new data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });


        independentList = new ArrayList<>();

        DatabaseReference databaseReferenceIndps = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");

        databaseReferenceIndps.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                independentList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Independent independent = snapshot.getValue(Independent.class);
                    independentList.add(independent);
                }
                // Here you might want to notify your adapter or update UI with the new data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });



        tenantList = new ArrayList<>();
        // Retrieve data from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents/Tenants");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenantList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tenant tenant = snapshot.getValue(Tenant.class);
                    tenantList.add(tenant);
                }
                tenantAdapter.notifyDataSetChanged();
                progressDialog.dismiss();// Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        tenantAdapter = new TenantAdapter(this, tenantList);
        recyclerView.setAdapter(tenantAdapter);

        // Set up item click listener
        tenantAdapter.setOnItemClickListener(new TenantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tenant tenant) {
                // Handle item click here
                // You can open TenantDetailsActivity and pass relevant data through Intent
                openTenantDetailsActivity(tenant);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TENANTS");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    /*private void openTenantDetailsActivity(Tenant tenant) {
        Intent intent = new Intent(TenantsActivity.this, TenantDetailsActivity.class);
        intent.putExtra("tenant", tenant);
        intent.putParcelableArrayListExtra("flatList", flatArrayList);
        startActivity(intent);
    }*/

    private void openTenantDetailsActivity(Tenant tenant) {
        Intent intent = new Intent(TenantsActivity.this, TenantDetailsActivity.class);
        intent.putExtra("tenant", tenant);
        intent.putParcelableArrayListExtra("flatList", flatArrayList);
        intent.putParcelableArrayListExtra("independentList", independentList); // Pass the entire list
        startActivity(intent);
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
