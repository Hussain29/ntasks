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

public class ApartmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewApartments;
    private ApartmentAdapter apartmentAdapter;
    private ArrayList<Apartment> apartmentList;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments);

        Button btnAddApartment = findViewById(R.id.btnaddapartment);

        btnAddApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApartmentsActivity.this, AddApartmentsActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerViewApartments = findViewById(R.id.recyclerViewApartments);
        recyclerViewApartments.setLayoutManager(new LinearLayoutManager(this));

        apartmentList = new ArrayList<>();

        // Retrieve data from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apartmentList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Apartment apartment = snapshot.getValue(Apartment.class);
                    apartmentList.add(apartment);
                }
                apartmentAdapter.notifyDataSetChanged();
                progressDialog.dismiss(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        apartmentAdapter = new ApartmentAdapter(this, apartmentList);
        recyclerViewApartments.setAdapter(apartmentAdapter);

        // Set up item click listener
        apartmentAdapter.setOnItemClickListener(new ApartmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Apartment apartment) {
                // Handle item click here
                // You can open ApartmentDetailsActivity and pass relevant data through Intent
                openApartmentDetailsActivity(apartment);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("APARTMENTS");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void openApartmentDetailsActivity(Apartment apartment) {
        Intent intent = new Intent(ApartmentsActivity.this, ApartmentDetailsActivity.class);
        intent.putExtra("apartment", new Apartment(
                apartment.getAptId(),
                apartment.getAptName(),
                apartment.getAptAddress(),
                apartment.getAptArea(),
                apartment.getAptUnits(),
                apartment.getAptFloor(),
                apartment.getAptShops(),
                apartment.getAptNotes(),
                apartment.getUserId(),
                apartment.getVendorName(),
                apartment.getOwnerName()
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
