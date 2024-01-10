package com.example.ntasks;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class hrmaster extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrmaster);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button openMapsButton = findViewById(R.id.openmaps);
        Button split = findViewById(R.id.split);
         TextView lonTextView=findViewById(R.id.tvlon);
        TextView latTextView=findViewById(R.id.tvlat);
        EditText etsplit=findViewById(R.id.etsplit);


        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splitText();



                String enteredText = etsplit.getText().toString().trim();

                // Check if the text contains a comma
                if (enteredText.contains(",")) {
                    // Split the text into parts based on the comma
                    String[] parts = enteredText.split(",");

                    if (parts.length == 2) {
                        // Extract latitude and longitude
                        String lat = parts[0].trim();
                        String lon = parts[1].trim();

                        // Display the results
                        latTextView.setText("Lat = " + lat);
                        lonTextView.setText("Lon = " + lon);
                    } else {
                        // Invalid input
                        latTextView.setText("Invalid input");
                        lonTextView.setText("");
                    }
                } else {
                    // No comma found
                    latTextView.setText("No comma found");
                    lonTextView.setText("");
                }






            }
        });

    /*    openMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the coordinates with your desired latitude and longitude
                double latitude = 37.7749;
                double longitude = -122.4194;

                // Create a Uri with the coordinates
                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?z=15&q=" + latitude + "," + longitude);

                // Create an Intent to open Google Maps
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Check if there's an activity to handle the Intent
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
*/
        openMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the latitude and longitude from user input
                String enteredText = etsplit.getText().toString().trim();

                // Check if the text contains a comma
                if (enteredText.contains(",")) {
                    // Split the text into parts based on the comma
                    String[] parts = enteredText.split(",");

                    if (parts.length == 2) {
                        // Extract latitude and longitude
                        double latitude = Double.parseDouble(parts[0].trim());
                        double longitude = Double.parseDouble(parts[1].trim());

                        // Create a Uri with the coordinates
                        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?z=15&q=" + latitude + "," + longitude);

                        // Create an Intent to open Google Maps
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                        // Check if there's an activity to handle the Intent
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                    } else {
                        // Invalid input
                        Toast.makeText(hrmaster.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // No comma found
                    Toast.makeText(hrmaster.this, "No comma found in input", Toast.LENGTH_SHORT).show();
                }
            }
        });




















     /*   Intent intent=new Intent(hrmaster.this, ApartmentDetails.class);
        startActivity(intent);
*/

        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("HR ");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
        Toast.makeText(this, "Under Development, Coming Soon!", Toast.LENGTH_LONG).show();



    }





    private void splitText() {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
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
    }}