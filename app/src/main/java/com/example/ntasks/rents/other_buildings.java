package com.example.ntasks.rents;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ntasks.R;

public class other_buildings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_buildings);

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("OTHERS");

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