package com.example.ntasks.PO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.graphics.drawable.ColorDrawable;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.ntasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllPOActivity extends AppCompatActivity implements POAdapter.OnItemClickListener {

    private RecyclerView recyclerViewPOs;
    private POAdapter poAdapter;
    private ArrayList<PurchaseOrder> POList;
    private ProgressDialog progressDialog;
    private SearchView searchView;

    private TextView PendingCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_po);

        // Initialize views and variables
        PendingCount = findViewById(R.id.nopenpo);
        recyclerViewPOs = findViewById(R.id.recyclerViewPOs);
        progressDialog = new ProgressDialog(this);
        POList = new ArrayList<>();

        // Set up RecyclerView
        recyclerViewPOs.setLayoutManager(new LinearLayoutManager(this));
        poAdapter = new POAdapter(this, POList);
        recyclerViewPOs.setAdapter(poAdapter);
        poAdapter.setOnItemClickListener(this);

        // Load completed POs from Firebase Realtime Database
        loadCompletedPOs();
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ALL PO");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        // Set up SearchView
        searchView = findViewById(R.id.svcppo);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                poAdapter.filter(newText);
                return true;
            }
        });
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

    // Load completed POs from Firebase Realtime Database
    private void loadCompletedPOs() {
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("POs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int pendingCount = 0; // Initialize the count of pending POs

                POList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PurchaseOrder purchaseOrder = snapshot.getValue(PurchaseOrder.class);
                    if (purchaseOrder.getStatus().equals("PENDING")) { // Check if the PO is pending
                        pendingCount++; // Increment the count for pending POs
                    }
                    POList.add(purchaseOrder);
                }
                poAdapter.setDataList(POList);
                poAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

                // Update the PendingCount TextView with the number of pending POs
                PendingCount.setText("Pending POs: " + pendingCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }


    @Override
    public void onBindViewHolder(@NonNull POAdapter.POViewHolder holder, int position) {

    }

    // Handle item click on completed PO item
    @Override
    public void onItemClick(PurchaseOrder po) {
        openPODetailsActivity(po);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void openPODetailsActivity(PurchaseOrder po) {
        Intent intent = new Intent(AllPOActivity.this, PODetailsActivity.class);
        intent.putExtra("PURCHASE_ORDER", po);
        startActivity(intent);
    }
}
