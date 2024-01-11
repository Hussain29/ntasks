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

public class FlatsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFlats;
    private FlatAdapter flatAdapter;
    private ArrayList<Flats> flatList;
    private ArrayList<Apartment> apartmentList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flats);

        Button btnAddFlats = findViewById(R.id.btnaddflats);

        btnAddFlats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlatsActivity.this, AddFlatsActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerViewFlats = findViewById(R.id.recyclerViewFlats);
        recyclerViewFlats.setLayoutManager(new LinearLayoutManager(this));

        flatList = new ArrayList<>();

        apartmentList = new ArrayList<>();

        // Populate apartmentList from Firebase
        DatabaseReference databaseReferenceApts = FirebaseDatabase.getInstance().getReference().child("Rents/Apartments");
        databaseReferenceApts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apartmentList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Apartment apartment = snapshot.getValue(Apartment.class);
                    apartmentList.add(apartment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        // Retrieve data from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                flatList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Flats flat = snapshot.getValue(Flats.class);
                    flatList.add(flat);
                }
                flatAdapter.notifyDataSetChanged();
                progressDialog.dismiss(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        flatAdapter = new FlatAdapter(this, flatList);
        recyclerViewFlats.setAdapter(flatAdapter);

        // Set up item click listener
        flatAdapter.setOnItemClickListener(new FlatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Flats flat) {
                // Handle item click here
                openFlatDetailsActivity(flat);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("FLATS");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void openFlatDetailsActivity(Flats flat) {
        Intent intent = new Intent(FlatsActivity.this, FlatDetailsActivity.class);
        intent.putExtra("flat", flat);
        intent.putParcelableArrayListExtra("apartmentList", (ArrayList<? extends Parcelable>) apartmentList);
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
