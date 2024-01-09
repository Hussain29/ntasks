package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;

public class PropertiesActivity extends AppCompatActivity {
    Button btnplots, btnapart, btnflats, btnindepen, btnothers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);
        btnplots = findViewById(R.id.btnplots);
        btnapart = findViewById(R.id.btnapartments);
        btnflats = findViewById(R.id.btnflats);
        btnindepen = findViewById(R.id.btnindependent);
        btnothers = findViewById(R.id.btn_otherbuildings);

        btnplots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropertiesActivity.this, PlotsActivity.class);
                startActivity(intent);
            }
        });

        btnapart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PropertiesActivity.this, ApartmentsActivity.class);
                startActivity(intent1);
            }
        });

        btnflats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(PropertiesActivity.this, FlatsActivity.class);
                startActivity(intent3);
            }
        });

        btnindepen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(PropertiesActivity.this, IndependentActivity.class);
                startActivity(intent4);
            }
        });

        btnothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(PropertiesActivity.this, OtherPropActivity.class);
                startActivity(intent5);
            }
        });


        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("BUILDINGS");

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
    }
}