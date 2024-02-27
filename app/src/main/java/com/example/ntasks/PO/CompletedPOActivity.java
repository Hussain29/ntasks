package com.example.ntasks.PO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.PO.POAdapter;
import com.example.ntasks.PO.PODetailsActivity;
import com.example.ntasks.PO.PurchaseOrder;
import com.example.ntasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompletedPOActivity extends AppCompatActivity implements POAdapter.OnItemClickListener {

    private RecyclerView recyclerViewPOs;
    private POAdapter poAdapter;
    private ArrayList<PurchaseOrder> completedPOList;
    private ProgressDialog progressDialog;
    private SearchView svcppo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_po);

        // Initialize views and variables
        recyclerViewPOs = findViewById(R.id.recyclerViewCompletedPOs);
        progressDialog = new ProgressDialog(this);
        completedPOList = new ArrayList<>();

        // Set up RecyclerView
        recyclerViewPOs.setLayoutManager(new LinearLayoutManager(this));
        poAdapter = new POAdapter(this, completedPOList);
        recyclerViewPOs.setAdapter(poAdapter);
        poAdapter.setOnItemClickListener(this);
        svcppo = findViewById(R.id.svcppo);

        // Load completed POs from Firebase Realtime Database
        loadCompletedPOs();

        svcppo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                poAdapter.getFilter().filter(newText);
                poAdapter.filter(newText);
                return true;
            }
        });

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("COMPLETED PO");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee);
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

    // Load completed POs from Firebase Realtime Database
    private void loadCompletedPOs() {
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("POs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                completedPOList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PurchaseOrder purchaseOrder = snapshot.getValue(PurchaseOrder.class);
                    if (purchaseOrder != null && purchaseOrder.getStatus().equals("COMPLETED")) {
                        completedPOList.add(purchaseOrder);
                    }
                }
                poAdapter.setDataList(completedPOList);
                poAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
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
        Intent intent = new Intent(CompletedPOActivity.this, PODetailsActivity.class);
        intent.putExtra("PURCHASE_ORDER", po);
        startActivity(intent);
    }
}
