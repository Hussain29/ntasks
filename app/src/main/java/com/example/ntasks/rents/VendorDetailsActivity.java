package com.example.ntasks.rents;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.squareup.picasso.Picasso;

public class VendorDetailsActivity extends AppCompatActivity {

    private ImageView vendorImageView;
    private TextView vendorNameTextView;
    private TextView vendorAddressTextView;
    private TextView vendorEmailTextView;
    private TextView vendorPhone1TextView;
    private TextView vendorPhone2TextView;
    private TextView vendorNotesTextView;
    private TextView docIdTypeTextView;
    private TextView linkTextView;

    private Vendor vendor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendordetails);

        vendor = getIntent().getParcelableExtra("vendor");

        // Check if vendor is null before accessing its properties
        if (vendor != null) {
            // Initialize Views
            vendorImageView = findViewById(R.id.ivimagev);
            vendorNameTextView = findViewById(R.id.tvName);
            vendorAddressTextView = findViewById(R.id.tvaddv);
            vendorEmailTextView = findViewById(R.id.tvemailv);
            vendorPhone1TextView = findViewById(R.id.ph1v);
            vendorPhone2TextView = findViewById(R.id.ph2v);
            vendorNotesTextView = findViewById(R.id.tvnotesv);
            docIdTypeTextView = findViewById(R.id.tvidv);
            linkTextView = findViewById(R.id.linkTextView);

            // Set TextViews with vendor details
            vendorNameTextView.setText("\t\tName: " + vendor.getVendorName());
            vendorAddressTextView.setText("\t\tAddress: " + vendor.getVendorAddress());
            vendorEmailTextView.setText("\t\tEmail: " + vendor.getVendorEmail());
            vendorPhone1TextView.setText("\t\tPhone 1: " + vendor.getVendorPhone1());
            vendorPhone2TextView.setText("\t\tPhone 2: " + vendor.getVendorPhone2());
            vendorNotesTextView.setText("\t\tVendor Notes: " + vendor.getVendorNotes());
            docIdTypeTextView.setText("\t\tID Type: " + vendor.getDocType());

            // Load vendor image using Picasso
            if (vendor.getImageUri() != null && !vendor.getImageUri().isEmpty()) {
                Picasso.get().load(vendor.getImageUri()).into(vendorImageView);
            }

            // Get the ActionBar
            ActionBar actionBar = getSupportActionBar();

            // Set the title
            actionBar.setTitle("VENDOR DETAILS");

            // Enable the back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

            // Display clickable link
            displayAttachmentLink(vendor.getDocumentUri());
        } else {
            // Log an error or show a message indicating that the vendor data is null
            Log.e("VendorDetailsActivity", "Vendor data is null");
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
        linkTextView.setText("Click To View ID: " + url);

        // Set an onClickListener to perform some action when clicked
        linkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Modify this block based on your desired action
                // For example, you can show a toast or perform some other action
                Toast.makeText(VendorDetailsActivity.this, "Attachment URL Clicked", Toast.LENGTH_SHORT).show();
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
