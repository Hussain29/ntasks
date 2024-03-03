package com.example.ntasks.PO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.PO.POClientAdapter;
import com.example.ntasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class POmaster extends AppCompatActivity {
    private GridView gridViewClients;
    private DatabaseReference clientsRef;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomaster);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("PO ACTIVITY");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        // Show loading screen
        progressDialog.show();

        gridViewClients = findViewById(R.id.gridViewClients);

        // Initialize Firebase Realtime Database reference
        clientsRef = FirebaseDatabase.getInstance().getReference().child("Clients");

        // Load clients and update GridView
        loadClientsAndUpdateGridView();
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

    private void loadClientsAndUpdateGridView() {
        clientsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> clientNames = new ArrayList<>();

                for (DataSnapshot clientSnapshot : dataSnapshot.getChildren()) {
                    String clientName = clientSnapshot.getValue(String.class);

                    // Check if client has pending POs
                    checkPendingPOsForClient(clientName, new PendingPOCallback() {
                        @Override
                        public void onResult(String clientName, boolean hasPendingPO) {
                            // Modify client name if there are pending POs
                            if (hasPendingPO) {
                                clientName += " *"; // Append '*' symbol
                            }

                            // Add modified client name to the list
                            clientNames.add(clientName);

                            // Update the GridView with client names
                            updateGridView(clientNames);
                            progressDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(POmaster.this, "Failed to load clients", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPendingPOsForClient(String clientName, PendingPOCallback callback) {
        DatabaseReference poRef = FirebaseDatabase.getInstance().getReference().child("POs");
        Query query = poRef.orderByChild("client").equalTo(clientName);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean hasPending = false;
                for (DataSnapshot poSnapshot : dataSnapshot.getChildren()) {
                    PurchaseOrder po = poSnapshot.getValue(PurchaseOrder.class);
                    if (po != null && "PENDING".equals(po.getStatus())) {
                        Log.d("AAJJAAJJ", "Found pending PO for client: " + clientName);
                        hasPending = true;
                        break;
                    }
                }
                // Use the callback to pass the result
                if (callback != null) {
                    callback.onResult(clientName, hasPending);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Log.e("AAJJAAJJ", "Database error: " + databaseError.getMessage());
                // Use the callback to pass the result (false due to error)
                if (callback != null) {
                    callback.onResult(clientName, false);
                }
            }
        });
    }

    interface PendingPOCallback {
        void onResult(String clientName, boolean hasPendingPO);
    }

    private void updateGridView(ArrayList<String> clientNames) {
        // Use the custom adapter for GridView
        POClientAdapter poClientAdapter = new POClientAdapter(this, clientNames);
        gridViewClients.setAdapter(poClientAdapter);

        // Set item click listener for GridView
        gridViewClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected client name
                String selectedClientName = clientNames.get(position);

                selectedClientName = selectedClientName.replaceAll("\\s*\\*", ""); // Remove '*' symbol and any surrounding spaces

                // Open PO List Activity and pass the selected client name
                Intent intent = new Intent(POmaster.this, POListActivity.class);
                intent.putExtra("CLIENT_NAME", selectedClientName);
                startActivity(intent);
            }
        });
    }
}
