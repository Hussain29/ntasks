package com.example.ntasks.PO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.SearchView;

import com.example.ntasks.PO.POAdapter;
import com.example.ntasks.PO.PODetailsActivity;
import com.example.ntasks.PO.PurchaseOrder;
import com.example.ntasks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowMyPoActivity extends AppCompatActivity implements POAdapter.OnItemClickListener {

    private RecyclerView recyclerViewPOs;
    private POAdapter poAdapter;
    private ArrayList<PurchaseOrder> poList;
    private ProgressDialog progressDialog;
    private String selectedClient;
    private SearchView searchView;

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

        // Load POs for the selected client from Firebase Realtime Database
        loadPOsForSelectedClient();

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("PO ACTIVITY");

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
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PurchaseOrder purchaseOrder = snapshot.getValue(PurchaseOrder.class);

                        // Check if currentUser and assignedUser are the same
                        if (currentUser != null) {
                            String currentUserName = currentUser.getDisplayName();
                            Log.d("ALLSMPO", "Name: " + currentUserName + "Assigned:" + purchaseOrder.getAssignedUser());
                            if (currentUserName != null && purchaseOrder.getAssignedUser() != null && currentUserName.equals(purchaseOrder.getAssignedUser())) {
                                poList.add(purchaseOrder);

                            }
                        }

                }
                poAdapter.setDataList(poList);
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

    // Handle item click on PO item
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
        Intent intent = new Intent(ShowMyPoActivity.this, PODetailsActivity.class);
        intent.putExtra("PURCHASE_ORDER", new PurchaseOrder(
                po.getPoId(),
                po.getPoSubject(),
                po.getPoRemarks(),
                po.getClient(),
                po.getSelectedDate(),
                po.getAssignedUser(),
                po.getAssigner(),
                po.getStatus(),
                po.getInvoiceNo(),
                po.getPoAttachmentUrl()));
        startActivity(intent);
    }
}
