package com.example.ntasks.rents;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ntasks.R;

public class addapartments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addapartments);

        // Assuming 'your_spinner' is the ID of your Spinner in the layout file
        Spinner spinner = findViewById(R.id.spinowner);
        Spinner spinner1 = findViewById(R.id.spinvendors);

// Get the string array from resources
        String[] items = getResources().getStringArray(R.array.Owners);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter);


        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD APARTMENTS");

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