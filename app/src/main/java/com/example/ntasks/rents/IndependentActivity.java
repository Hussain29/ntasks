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

public class IndependentActivity extends AppCompatActivity {

    private RecyclerView recyclerViewIndependents;
    private IndependentAdapter independentAdapter;
    private ArrayList<Independent> independentList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_independent);

        Button btnAddIndependent = findViewById(R.id.btnaddindp);

        btnAddIndependent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndependentActivity.this, AddIndependentActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerViewIndependents = findViewById(R.id.recindp);
        recyclerViewIndependents.setLayoutManager(new LinearLayoutManager(this));

        independentList = new ArrayList<>();

        // Retrieve data from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                independentList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Independent independent = snapshot.getValue(Independent.class);
                    independentList.add(independent);
                }
                independentAdapter.notifyDataSetChanged();
                progressDialog.dismiss(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        independentAdapter = new IndependentAdapter(this, independentList);
        recyclerViewIndependents.setAdapter(independentAdapter);

        // Set up item click listener
        independentAdapter.setOnItemClickListener(new IndependentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Independent independent) {
                // Handle item click here
                // You can open IndependentDetailsActivity and pass relevant data through Intent
                openIndependentDetailsActivity(independent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("INDEPENDENTS");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void openIndependentDetailsActivity(Independent independent) {
        Intent intent = new Intent(IndependentActivity.this, IndependentDetailsActivity.class);
        intent.putExtra("independent", new Independent(
                independent.getIndpId(),
                independent.getIndpName(),
                independent.getIndpAddress(),
                independent.getIndpArea(),
                independent.getIndpUnits(),
                independent.getIndpFloor(),
                independent.getIndpShops(),
                independent.getIndpNotes(),
                independent.getUserId(),
                independent.getVendorName(),
                independent.getOwnerName(),
                independent.getDocuUrl(),
                independent.getImgUrl(),
                independent.getDocuType(),
                independent.getCoordinates()
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
