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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlotsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPlots;
    private PlotAdapter plotAdapter;
    private ArrayList<Plot> plotList;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plots);

        Button btnAddPlot = findViewById(R.id.btnaddplot);

        btnAddPlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlotsActivity.this, AddPlotsActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerViewPlots = findViewById(R.id.recplt);
        recyclerViewPlots.setLayoutManager(new LinearLayoutManager(this));

        plotList = new ArrayList<>();

        // Retrieve data from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rents/Plots");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plotList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plot plot = snapshot.getValue(Plot.class);
                    plotList.add(plot);
                }
                plotAdapter.notifyDataSetChanged();
                progressDialog.dismiss(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        plotAdapter = new PlotAdapter(this, plotList);
        recyclerViewPlots.setAdapter(plotAdapter);

        // Set up item click listener
        plotAdapter.setOnItemClickListener(new PlotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Plot plot) {
                // Handle item click here
                // You can open PlotDetailsActivity and pass relevant data through Intent
                openPlotDetailsActivity(plot);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PLOTS");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
    }

    private void openPlotDetailsActivity(Plot plot) {
        Intent intent = new Intent(PlotsActivity.this, PlotDetailsActivity.class);
        intent.putExtra("plot", new Plot(
                plot.getPltId(),
                plot.getPltName(),
                plot.getPltAddress(),
                plot.getPltArea(),
                plot.getPltFloor(),
                plot.getPltShops(),
                plot.getPltNotes(),
                plot.getUserId(),
                plot.getVendorName(),
                plot.getOwnerName(),
                plot.getDocType(),
                plot.getDocUrl(),
                plot.getImgUrl(),
                plot.getCoordinates()
        ));
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
