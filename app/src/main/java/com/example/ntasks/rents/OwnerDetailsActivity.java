package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import android.text.util.Linkify;

import com.example.ntasks.R;
import com.squareup.picasso.Picasso;

public class OwnerDetailsActivity extends AppCompatActivity {

    private ImageView ownerImageView;
    private TextView ownerNameTextView;
    private TextView ownerAddressTextView;
    private TextView ownerEmailTextView;
    private TextView ownerPhone1TextView;
    private TextView ownerPhone2TextView;
    private TextView ownerPhone3TextView;
    private TextView ownerNotesTextView;
    private TextView docIdTypeTextView;
    private TextView linkTextView;

    private Owner owner;
    private Button downloadIdButton;
    private Button Editbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerdetails);

        // Retrieve owner from Intent
        owner = getIntent().getParcelableExtra("owner");

        // Check if owner is null before accessing its properties
        if (owner != null) {
            // Initialize Views
            ownerImageView = findViewById(R.id.imageView2);
            ownerNameTextView = findViewById(R.id.tvName);
            ownerAddressTextView = findViewById(R.id.tvaddo);
            ownerEmailTextView = findViewById(R.id.tvemailo);
            ownerPhone1TextView = findViewById(R.id.textViewOwnerPhone1);
            ownerPhone2TextView = findViewById(R.id.textViewOwnerPhone2);
            ownerPhone3TextView = findViewById(R.id.textViewOwnerPhone3);
            ownerNotesTextView = findViewById(R.id.tvnoteso);
            docIdTypeTextView = findViewById(R.id.tvido);
            linkTextView = findViewById(R.id.linkTextView);
            Editbtn = findViewById(R.id.btneditdetails);

            Editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OwnerDetailsActivity.this, EditOwnerDetailsActivity.class);
                    intent.putExtra("owner_details", owner);
                    startActivity(intent);
                }
            });
            /*downloadIdButton = findViewById(R.id.downloadIdButton);*/

            // Set TextViews with owner details
            ownerNameTextView.setText("\t\tName: " + owner.getOwnerName());
            ownerAddressTextView.setText("\t\tAddress: " + owner.getOwnerAddress());
            ownerEmailTextView.setText("\t\tEmail: " + owner.getOwnerEmail());
            ownerPhone1TextView.setText("\t\tPhone Number: " + owner.getOwnerPhone1());
            ownerPhone2TextView.setText("\t\tPhone Number 2: " + owner.getOwnerPhone2());
            ownerPhone3TextView.setText("\t\tPhone Number 3: " + owner.getOwnerPhone3());
            ownerNotesTextView.setText("\t\tNotes: " + owner.getOwnerNotes());
            docIdTypeTextView.setText("\t\tID Type: " + owner.getDocType());

            // Load owner image using Picasso
            if (owner.getPhotoUrl() != null && !owner.getPhotoUrl().isEmpty()) {
                Picasso.get().load(owner.getPhotoUrl()).into(ownerImageView);
            }

            // Get the ActionBar
            ActionBar actionBar = getSupportActionBar();

            // Set the title
            actionBar.setTitle("OWNER DETAILS");

            // Enable the back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

            // Display clickable link
            displayAttachmentLink(owner.getDocUrl());
        } else {
            // Log an error or show a message indicating that the owner data is null
            Log.e("OwnerDetailsActivity", "Owner data is null");
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
                Toast.makeText(OwnerDetailsActivity.this, "Attachment URL Clicked", Toast.LENGTH_SHORT).show();
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
