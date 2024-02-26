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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_po);

        // Initialize views and variables
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
                POList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PurchaseOrder purchaseOrder = snapshot.getValue(PurchaseOrder.class);
                    POList.add(purchaseOrder);
                }
                poAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    // Handle item click on completed PO item
    @Override
    public void onItemClick(PurchaseOrder po) {
        openPODetailsActivity(po);
    }

    private void openPODetailsActivity(PurchaseOrder po) {
        Intent intent = new Intent(AllPOActivity.this, PODetailsActivity.class);
        intent.putExtra("PURCHASE_ORDER", po);
        startActivity(intent);
    }
}
