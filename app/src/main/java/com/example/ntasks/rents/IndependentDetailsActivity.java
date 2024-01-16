package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.text.util.Linkify;

import com.example.ntasks.R;
import com.squareup.picasso.Picasso;

public class IndependentDetailsActivity extends AppCompatActivity {

    private ImageView imageView2;
    private TextView tvIndpName;
    private TextView tvIndpId;
    private TextView tvAddIndp;
    private TextView tvAreaIndp;
    private TextView tvUnitsIndp;
    private TextView tvFloorIndp;
    private TextView tvShops;
    private TextView tvOwnerIndp;
    private TextView tvVendorIndp;
    private TextView tvIndpNotes;
    private TextView tvDocIndp;
    private TextView tvDocumentsIndp;
    private TextView linkTextView;

    private TextView tvCoords;

    private Independent independent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_independent_details);

        // Retrieve Independent object from Intent
        independent = getIntent().getParcelableExtra("independent");

        ImageButton imgloc=findViewById(R.id.imgloc);


        String latlong= independent.getCoordinates();



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






        // Check if independent is null before accessing its properties
        if (independent != null) {
            // Initialize Views
            imageView2 = findViewById(R.id.imageView2);
            tvIndpName = findViewById(R.id.tvindpName);
            tvIndpId = findViewById(R.id.tvidindp);
            tvAddIndp = findViewById(R.id.tvaddindp);
            tvAreaIndp = findViewById(R.id.tvareindp);
            tvUnitsIndp = findViewById(R.id.tvflatsindp);
            tvFloorIndp = findViewById(R.id.tvfloorindp);
            tvShops = findViewById(R.id.tvsptshops);
            tvOwnerIndp = findViewById(R.id.tvownerindp);
            tvVendorIndp = findViewById(R.id.tvvendorindp);
            tvIndpNotes = findViewById(R.id.tvindpnotes);
            tvDocIndp = findViewById(R.id.tvdocindp);
            tvDocumentsIndp = findViewById(R.id.tvdocumentsindp);
            linkTextView = findViewById(R.id.linkTextView);
            tvCoords = findViewById(R.id.tvsptcoords);

            // Set values to views
            // Use Picasso to load the independent image
            Picasso.get().load(R.drawable.independent).into(imageView2);
            tvIndpName.setText(independent.getIndpName());
            tvIndpId.setText("\t\tIndependent Id: " + independent.getIndpId());
            tvAddIndp.setText("\t\tAddress: " + independent.getIndpAddress());
            tvAreaIndp.setText("\t\tArea: " + independent.getIndpArea());
            tvUnitsIndp.setText("\t\tUnits: " + independent.getIndpUnits());
            tvFloorIndp.setText("\t\tFloors: " + independent.getIndpFloor());
            tvShops.setText("\t\tShops: " + independent.getIndpShops());
            tvOwnerIndp.setText("\t\tOwner: " + independent.getOwnerName());
            tvVendorIndp.setText("\t\tVendor: " + independent.getVendorName());
            tvIndpNotes.setText("\t\tIndependent Notes: " + independent.getIndpNotes());
            tvDocIndp.setText("\t\tDOC TYPE:" + independent.getDocuType());
            tvCoords.setText("\t\tCoordinates:" + independent.getCoordinates());
            tvDocumentsIndp.setText("\t\tIndependent Doc");

            // Load owner image using Picasso
            if (independent.getImgUrl() != null && !independent.getImgUrl().isEmpty()) {
                Picasso.get().load(independent.getImgUrl()).into(imageView2);
            }

            // Get the ActionBar
            ActionBar actionBar = getSupportActionBar();

            // Set the title
            actionBar.setTitle("INDEPENDENT DETAILS");

            // Enable the back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            int actionBarColor = ContextCompat.getColor(this, R.color.blueeee);
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

            // Display clickable link for document URL
            displayAttachmentLink(independent.getDocuUrl());

            // You can customize this based on your specific requirements
            // For example, changing background colors, text colors, etc.
        } else {
            // Log an error or show a message indicating that the independent data is null
            Log.e("IndependentDetailsActivity", "Independent data is null");
            // You may also finish the activity or handle it accordingly
            finish();
        }
    }

    private void displayAttachmentLink(String url) {
        // Use a TextView to display the clickable link
        tvDocumentsIndp.setVisibility(View.VISIBLE);

        // Set the autoLink property to web for automatic linking of URLs
        tvDocumentsIndp.setAutoLinkMask(Linkify.WEB_URLS);

        // Set the text to the URL
        tvDocumentsIndp.setText("Click To View Document: " + url);

        // Set an onClickListener to perform some action when clicked
        tvDocumentsIndp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Modify this block based on your desired action
                // For example, show a toast or perform some other action
                Toast.makeText(IndependentDetailsActivity.this, "Document URL Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openUrlInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
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
