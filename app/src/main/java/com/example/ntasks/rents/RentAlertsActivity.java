package com.example.ntasks.rents;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RentAlertsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RentAlertsAdapter adapter;
    private List<Tenant> tenantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentalerts);

        Toast.makeText(this, "Pending Rents In Next 4 days Will Be Shown Here", Toast.LENGTH_LONG).show();
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("RENT ALERTS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


        recyclerView = findViewById(R.id.recyclerViewRentAlerts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tenantList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents/Tenants");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenantList.clear(); // Clear the list before adding new data

                // Get today's date
                Calendar todayCalendar = Calendar.getInstance();
                todayCalendar.setTime(new Date());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tenant tenant = snapshot.getValue(Tenant.class);
                    if (tenant != null) {
                        String payday = tenant.getPayday();
                        int convertedDay = convertDayOfMonth(payday);

                        // Add logging to check values
                        Log.d("DataFromFirebase", "Payday: " + payday + ", Converted Day: " + convertedDay);

                        // Check if the converted day is valid
                        if (convertedDay != -1) {
                            int maxDayOfMonth = todayCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                            if (convertedDay <= maxDayOfMonth) {
                                // Valid day, proceed with further checks
                                int dayDifference = Math.abs(convertedDay - todayCalendar.get(Calendar.DAY_OF_MONTH));
                                if (dayDifference <= (maxDayOfMonth - todayCalendar.get(Calendar.DAY_OF_MONTH))) {
                                    tenantList.add(tenant);
                                }
                            }
                        }
                    }
                }
                // Notify the adapter that the data has changed
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
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

    public static int convertDayOfMonth(String originalDate) {
        // Split the original string to get the day part
        String[] parts = originalDate.split(" ");
        if (parts.length >= 1) {
            // Extract the numeric part
            String numericPart = parts[0].replaceAll("[^0-9]", "");
            // Parse the numeric part to an integer
            try {
                return Integer.parseInt(numericPart);
            } catch (NumberFormatException e) {
                // Handle parsing error
                e.printStackTrace();
            }
        }
        return -1; // Return -1 if conversion fails
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

}
