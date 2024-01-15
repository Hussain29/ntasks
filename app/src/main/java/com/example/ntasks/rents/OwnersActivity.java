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
import com.example.ntasks.rents.OwnerDetailsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OwnerAdapter ownerAdapter;
    private ArrayList<Owner> ownerList;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners);

        Button btnaddowner = findViewById(R.id.btnaddowner);

        btnaddowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnersActivity.this, AddOwnersActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ownerList = new ArrayList<>();

        // Retrieve data from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents/Owners");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ownerList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Owner owner = snapshot.getValue(Owner.class);
                    ownerList.add(owner);
                }
                ownerAdapter.notifyDataSetChanged();
                progressDialog.dismiss();// Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        ownerAdapter = new OwnerAdapter(this, ownerList);
        recyclerView.setAdapter(ownerAdapter);

        // Set up item click listener
        ownerAdapter.setOnItemClickListener(new OwnerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Owner owner) {
                // Handle item click here
                // You can open OwnerDetailsActivity and pass relevant data through Intent
                openOwnerDetailsActivity(owner);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("OWNERS");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void openOwnerDetailsActivity(Owner owner) {
        Intent intent = new Intent(OwnersActivity.this, OwnerDetailsActivity.class);
        intent.putExtra("owner", new Owner(owner.getOwnerId(), owner.getOwnerName(), owner.getOwnerAddress(), owner.getOwnerEmail(), owner.getOwnerPhone1(), owner.getOwnerPhone2(), owner.getOwnerPhone3(), owner.getOwnerNotes(), owner.getUserId(), owner.getPhotoUrl(), owner.getDocUrl(), owner.getDocType()));
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
