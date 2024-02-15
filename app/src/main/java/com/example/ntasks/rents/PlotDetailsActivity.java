package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.squareup.picasso.Picasso;

public class PlotDetailsActivity extends AppCompatActivity {

    private ImageView imageView2;
    private TextView tvPlotName;
    private TextView tvPlotId;
    private TextView tvAddPlot;
    private TextView tvAreaPlot;
    private TextView tvShops;
    private TextView tvOwnerPlot;
    private TextView tvVendorPlot;
    private TextView tvPlotNotes;
    private TextView tvDocPlot;
    private TextView linkTextView;
    private TextView tvCoords;
    private Button Editbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_details);
        ImageView imgloc=findViewById(R.id.imgloc);

        // Retrieve Plot object from Intent
        Plot plot = getIntent().getParcelableExtra("plot");
        String latlong= plot.getCoordinates();


        Editbtn = findViewById(R.id.btneditdetails);
        Editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlotDetailsActivity.this, EditPlotDetailsActivity.class);
                intent.putExtra("plot_details", plot);
                startActivity(intent);
            }
        });

        imgloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // Create a Uri with the specified latitude and longitude
                Uri gmmIntentUri = Uri.parse("geo:" +latlong + "?z=15&q=" +latlong);

                // Create an Intent with the action "ACTION_VIEW"
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Set the package to the Google Maps app (you can also use "com.google.android.apps.maps")
                mapIntent.setPackage("com.google.android.apps.maps");

                // Check if there is an app to handle the Intent before starting it
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    // Handle the case where Google Maps is not installed
                    // You can open the browser or notify the user to install Google Maps
                }
                ///////////////////////////////////////////////////////////////
                    /*// Create a Uri with the specified latitude and longitude
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" +latlong);

                    // Create an Intent with the action "ACTION_VIEW"
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                    // Set the package to the Google Maps app (you can also use "com.google.android.apps.maps")
                    mapIntent.setPackage("com.google.android.apps.maps");

                    // Check if there is an app to handle the Intent before starting it
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    } else {
                        // Handle the case where Google Maps is not installed
                        // You can open the browser or notify the user to install Google Maps
                    }*/
            }
        });


        // Check if the plot object is not null
        if (plot != null) {
            // Initialize Views
            imageView2 = findViewById(R.id.imageView2);
            tvPlotName = findViewById(R.id.tvpltName);
            tvPlotId = findViewById(R.id.tvidplt);
            tvAddPlot = findViewById(R.id.tvaddplt);
            tvAreaPlot = findViewById(R.id.tvareplt);
            tvShops = findViewById(R.id.tvsptshops);
            tvOwnerPlot = findViewById(R.id.tvownerplt);
            tvVendorPlot = findViewById(R.id.tvvendorplt);
            tvPlotNotes = findViewById(R.id.tvpltnotes);
            tvCoords = findViewById(R.id.tvCoords);
            tvDocPlot = findViewById(R.id.tvdocplt);
            linkTextView = findViewById(R.id.tvdoclinkplt);

            // Set values to views
            imageView2.setImageResource(R.drawable.plot); // You can change this image based on your requirements
            tvPlotName.setText(plot.getPltName());
            tvPlotId.setText("\t\tPlot Id: " + plot.getPltId());
            tvAddPlot.setText("\t\tAddress: " + plot.getPltAddress());
            tvAreaPlot.setText("\t\tArea: " + plot.getPltArea());
            tvShops.setText("\t\tShops: " + plot.getPltShops());
            tvOwnerPlot.setText("\t\tOwner: " + plot.getOwnerName());
            tvVendorPlot.setText("\t\tVendor: " + plot.getVendorName());
            tvPlotNotes.setText("\t\tPlot Notes: " + plot.getPltNotes());
            tvCoords.setText("\t\tCoordinates: " + plot.getCoordinates());
            tvDocPlot.setText("\t\tDOC TYPE:" + plot.getDocType());

            // Load plot image using Picasso
            if (plot.getImgUrl() != null && !plot.getImgUrl().isEmpty()) {
                Picasso.get().load(plot.getImgUrl()).into(imageView2);
            }

            // Get the ActionBar
            ActionBar actionBar = getSupportActionBar();

            // Set the title
            actionBar.setTitle("PLOT DETAILS");

            // Enable the back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
            // Display clickable link for document URL
            displayAttachmentLink(plot.getDocUrl());

        } else {
            // Log an error or show a message indicating that the plot data is null
            Log.e("PlotDetailsActivity", "Plot data is null");
            // You may also finish the activity or handle it accordingly
            finish();
        }
    }

    private void displayAttachmentLink(String url) {
        // Use a TextView to display the clickable link
        linkTextView.setVisibility(View.VISIBLE);

        // Set the autoLink property to web for automatic linking of URLs
        linkTextView.setAutoLinkMask(Linkify.WEB_URLS);

        // Set the text to the URL
        linkTextView.setText("Click To View Document: " + url);

        // Set an onClickListener to perform some action when clicked
        linkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Modify this block based on your desired action
                // For example, show a toast or perform some other action
                Toast.makeText(PlotDetailsActivity.this, "Document URL Clicked", Toast.LENGTH_SHORT).show();
            }
        });
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
