package com.example.ntasks.rents;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ntasks.R;

public class VendorDetailsActivity extends AppCompatActivity {

    private ImageView vendorImageView;
    private TextView vendorNameTextView;
    private TextView vendorAddressTextView;
    private TextView vendorEmailTextView;
    private TextView vendorPhone1TextView;
    private TextView vendorPhone2TextView;
    private TextView vendorNotesTextView;
    private TextView idTypeTextView;
    private Button downloadIdButton;

    private Vendor vendor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendordetails);

        vendor = getIntent().getParcelableExtra("vendor");

        // Initialize Views
        vendorImageView = findViewById(R.id.ivimagev);
        vendorNameTextView = findViewById(R.id.tvName);
        vendorAddressTextView = findViewById(R.id.tvaddv);
        vendorEmailTextView = findViewById(R.id.tvemailv);
        vendorPhone1TextView = findViewById(R.id.ph1v);
        vendorPhone2TextView = findViewById(R.id.ph2v);
        vendorNotesTextView = findViewById(R.id.tvnotesv);
        idTypeTextView = findViewById(R.id.tvidv);
        downloadIdButton = findViewById(R.id.btndownidv);

        // Set TextViews with vendor details
        vendorImageView.setImageResource(R.drawable.no_profile_pic); // Set the image resource
        vendorNameTextView.setText("Name: " + vendor.getVendorName());
        vendorAddressTextView.setText("Address: " + vendor.getVendorAddress());
        vendorEmailTextView.setText("Email: " + vendor.getVendorEmail());
        vendorPhone1TextView.setText("Phone 1: " + vendor.getVendorPhone1());
        vendorPhone2TextView.setText("Phone 2: " + vendor.getVendorPhone2());
        vendorNotesTextView.setText("Vendor Notes: " + vendor.getVendorNotes());
        idTypeTextView.setText("ID Type: " + vendor.getUserId());

        // Add onClickListener for the downloadIdButton if needed
    }
}
