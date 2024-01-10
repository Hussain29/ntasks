package com.example.ntasks.rents;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;

public class ApartmentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details);

        TextView tvidap, tvaptName, tvaddap, tvflatsapt, tvareapt, tvsptshops, tvfloorapt, tvownerapt, tvvendorapt, tvaptnotes, tvdocapt;
        tvidap = findViewById(R.id.tvidap);
        tvaptName = findViewById(R.id.tvaptName);
        tvaddap = findViewById(R.id.tvaddap);
        tvflatsapt = findViewById(R.id.tvflatsapt);
        tvareapt = findViewById(R.id.tvareapt);
        tvsptshops = findViewById(R.id.tvsptshops);
        tvfloorapt = findViewById(R.id.tvfloorapt);
        tvownerapt = findViewById(R.id.tvownerapt);
        tvvendorapt = findViewById(R.id.tvvendorapt);
        tvaptnotes = findViewById(R.id.tvaptnotes);
        tvdocapt = findViewById(R.id.tvdocapt);


        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("APARTMENT DETAILS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the home button click
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }}

