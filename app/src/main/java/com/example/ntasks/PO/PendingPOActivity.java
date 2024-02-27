package com.example.ntasks.PO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PendingPOActivity extends AppCompatActivity implements POAdapter.OnItemClickListener {

    private RecyclerView recyclerViewPOs;
    private POAdapter poAdapter;
    private ArrayList<PurchaseOrder> completedPOList;
    private ProgressDialog progressDialog;
    private SearchView svcppo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_po);

        // Initialize views and variables
        recyclerViewPOs = findViewById(R.id.recyclerViewPendingPOs);
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
        /*svcppo.performClick();

        svcppo.setQuery("", false); // The second parameter is to submit the query, set to false if you don't want to submit

        // Optionally, you can also clear the focus to dismiss the keyboard
        svcppo.clearFocus();*/
   /*     svcppo.postDelayed(new Runnable() {
            @Override
            public void run() {
                svcppo.getText().clear();

                String searchText = yourSearchView.getQuery().toString();
            }
        }, 2000); // 2000 milliseconds (2 seconds) delay, adjust as needed
    }*/
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("PO");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
/*
        svcppo.setQuery("Default Keyword", true);
*/



        svcppo.clearFocus();
        svcppo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // This method is called when the user submits the query (e.g., presses the magnifier button)
                // Clear the text in the SearchView


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                poAdapter.filter(newText);
                return true;
            }
        });

    }

    /*
                @Override
                public void onBindViewHolder(@NonNull POAdapter.POViewHolder holder, int position) {
                    PurchaseOrder po = completedPOList.get(position); // Use filteredList instead of poList
                    String statuspo = po.getStatus();
                    holder.tvPOSubject.setText(po.getPoSubject());
                    holder.tvAssignedBy.setText("A.By: " + po.getAssigner());
                    holder.tvAssignedTo.setText("A.To.: " + po.getAssignedUser());
                    if (statuspo != null) {
                        switch (statuspo.toLowerCase()) {
                            case "completed":
                                holder.itemView.setBackgroundResource(R.drawable.done);
                                break;
                            case "pending":
                                holder.itemView.setBackgroundResource(R.drawable.assigned);
                                break;
                            case "done":
                                holder.itemView.setBackgroundResource(R.drawable.done);
                                break;
                            default:
                                holder.itemView.setBackgroundResource(R.drawable.assigned);
                                break;
                        }
                    } else {
                        holder.itemView.setBackgroundResource(R.drawable.assigned);
                    }


                }*/
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
                completedPOList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PurchaseOrder purchaseOrder = snapshot.getValue(PurchaseOrder.class);
                    if (purchaseOrder != null && purchaseOrder.getStatus().equals("PENDING")) {
                        completedPOList.add(purchaseOrder);
                    }
                }
                poAdapter.setDataList(completedPOList);
                poAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                Log.d("Debug", "Completed PO List Size: " + completedPOList.size());

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
        Intent intent = new Intent(PendingPOActivity.this, PODetailsActivity.class);
        intent.putExtra("PURCHASE_ORDER", po);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}