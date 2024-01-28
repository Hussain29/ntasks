package com.example.ntasks.rents;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RentAlertsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RentAlertsAdapter adapter;
    private List<Tenant> tenantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentalerts);

        recyclerView = findViewById(R.id.recyclerViewRentAlerts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tenantList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents/Tenants");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenantList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tenant tenant = snapshot.getValue(Tenant.class);
                    tenantList.add(tenant);
                }
                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(RentAlertsActivity.this, "Failed to load tenants: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new RentAlertsAdapter(this, tenantList);
        recyclerView.setAdapter(adapter);
    }
}
