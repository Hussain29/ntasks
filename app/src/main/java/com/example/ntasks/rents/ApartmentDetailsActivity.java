
package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class ApartmentDetailsActivity extends AppCompatActivity {

    private ImageView imageView2;
    private TextView tvAptName;
    private TextView tvAptId;
    private TextView tvAddApt;
    private TextView tvAreaApt;
    private TextView tvFlatsApt;
    private TextView tvFloorApt;
    private TextView tvShops;
    private TextView tvOwnerApt;
    private TextView tvVendorApt;
    private TextView tvAptNotes;
    private TextView tvAptCoords;
    private TextView tvDocApt;
    private TextView tvDocumentsApt;
    private TextView linkTextView;
    private Button Editbtn;

    private Apartment apartment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details);

        Editbtn = findViewById(R.id.btneditdetails);

        // Retrieve Apartment object from Intent
        apartment = getIntent().getParcelableExtra("apartment");

        Editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApartmentDetailsActivity.this, EditApartmentDetailsActivity.class);
                intent.putExtra("apartment_details", apartment);
                startActivity(intent);
            }
        });

        // Check if apartment is null before accessing its properties
        if (apartment != null) {
            // Initialize Views
            imageView2 = findViewById(R.id.imageView2);
            tvAptName = findViewById(R.id.tvaptName);
            tvAptId = findViewById(R.id.tvidap);
            tvAddApt = findViewById(R.id.tvaddap);
            tvAreaApt = findViewById(R.id.tvareapt);
            tvFlatsApt = findViewById(R.id.tvflatsapt);
            tvFloorApt = findViewById(R.id.tvfloorapt);
            tvShops = findViewById(R.id.tvsptshops);
            tvOwnerApt = findViewById(R.id.tvownerapt);
            tvVendorApt = findViewById(R.id.tvvendorapt);
            tvAptNotes = findViewById(R.id.tvaptnotes);
            tvAptCoords = findViewById(R.id.tvaptcoords);
            tvDocApt = findViewById(R.id.tvdocapt);
            tvDocumentsApt = findViewById(R.id.tvdocumentsapt);
            linkTextView = findViewById(R.id.linkTextView);
            ImageButton imgloc=findViewById(R.id.imgloc);


            String latlong= apartment.getCoordinates();



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

                }
            });



            // Set values to views
            // Use Picasso to load the apartment image
            Picasso.get().load(R.drawable.apartment).into(imageView2);
            tvAptName.setText(apartment.getAptName());
            tvAptId.setText("\t\tApartment Id: " + apartment.getAptId());
            tvAddApt.setText("\t\tAddress: " + apartment.getAptAddress());
            tvAreaApt.setText("\t\tArea: " + apartment.getAptArea());
            tvFlatsApt.setText("\t\tFlats: " + apartment.getAptUnits());
            tvFloorApt.setText("\t\tFloors: " + apartment.getAptFloor());
            tvShops.setText("\t\tShops: " + apartment.getAptShops());
            tvOwnerApt.setText("\t\tOwner: " + apartment.getOwnerName());
            tvVendorApt.setText("\t\tVendor: " + apartment.getVendorName());
            tvAptNotes.setText("\t\tApartment Notes:" + apartment.getAptNotes());
            tvAptCoords.setText("\t\tCoordinates: " + apartment.getCoordinates());
            tvDocApt.setText("\t\tDOC TYPE:" + apartment.getDocType());
            tvDocumentsApt.setText("\t\tApartment Doc");


            // Load owner image using Picasso
            if (apartment.getImgUrl() != null && !apartment.getImgUrl().isEmpty()) {
                Picasso.get().load(apartment.getImgUrl()).into(imageView2);
            }

            // Get the ActionBar
            ActionBar actionBar = getSupportActionBar();

            // Set the title
            actionBar.setTitle("APARTMENT DETAILS");

            // Enable the back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
            // Display clickable link for document URL
            displayAttachmentLink(apartment.getDocUrl());

            // You can customize this based on your specific requirements
            // For example, changing background colors, text colors, etc.
        } else {
            // Log an error or show a message indicating that the apartment data is null
            Log.e("ApartmentDetailsActivity", "Apartment data is null");
            // You may also finish the activity or handle it accordingly
            finish();
        }
    }

    private void displayAttachmentLink(String url) {
        // Use a TextView to display the clickable link
        tvDocumentsApt.setVisibility(View.VISIBLE);

        // Set the autoLink property to web for automatic linking of URLs
        tvDocumentsApt.setAutoLinkMask(Linkify.WEB_URLS);

        // Set the text to the URL
        tvDocumentsApt.setText("Click To View Document: " + url);

        // Set an onClickListener to perform some action when clicked
        tvDocumentsApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Modify this block based on your desired action
                // For example, show a toast or perform some other action
                Toast.makeText(ApartmentDetailsActivity.this, "Document URL Clicked", Toast.LENGTH_SHORT).show();
            }
        });
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

