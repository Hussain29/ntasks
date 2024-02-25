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

import com.example.ntasks.ClientTasksActivity;
import com.example.ntasks.PO.PurchaseOrder;


import com.example.ntasks.R;
import com.example.ntasks.TaskDetailsActivity;
import com.example.ntasks.TaskModel;
import com.example.ntasks.Userlist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class POListActivity extends AppCompatActivity implements POAdapter.OnItemClickListener {

    private RecyclerView recyclerViewPOs;
    private POAdapter poAdapter;
    private ArrayList<PurchaseOrder> poList;
    private ProgressDialog progressDialog;
    private String selectedClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_list);

        // Initialize views and variables
        recyclerViewPOs = findViewById(R.id.recyclerViewPOs);
        progressDialog = new ProgressDialog(this);
        poList = new ArrayList<>();

        // Set up RecyclerView
        recyclerViewPOs.setLayoutManager(new LinearLayoutManager(this));
        poAdapter = new POAdapter(this, poList);
        recyclerViewPOs.setAdapter(poAdapter);
        poAdapter.setOnItemClickListener(this);

        // Retrieve selected client from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("CLIENT_NAME")) {
            selectedClient = intent.getStringExtra("CLIENT_NAME");
            getSupportActionBar().setTitle(selectedClient + "'s Tasks");
        }

        // Load POs for the selected client from Firebase Realtime Database
        loadPOsForSelectedClient();
    }

    // Load POs for the selected client from Firebase Realtime Database
    private void loadPOsForSelectedClient() {
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("POs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                poList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PurchaseOrder purchaseOrder = snapshot.getValue(PurchaseOrder.class);
                    if (purchaseOrder != null && purchaseOrder.getClient().equals(selectedClient)) {
                        poList.add(purchaseOrder);
                    }
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

    // Handle item click on PO item
    @Override
    public void onItemClick(PurchaseOrder po) {openPODetailsActivity(po);}

    private void openPODetailsActivity(PurchaseOrder po) {
        Intent intent = new Intent(POListActivity.this, PODetailsActivity.class);
        intent.putExtra("PURCHASE_ORDER", new PurchaseOrder(
                po.getPoId(),
                po.getPoSubject(),
                po.getPoRemarks(),
                po.getClient(),
                po.getSelectedDate(),
                po.getAssignedUser(),
                po.getAssigner(),
                po.getStatus(),
                po.getPoAttachmentUrl()));
        startActivity(intent);
    }
}
