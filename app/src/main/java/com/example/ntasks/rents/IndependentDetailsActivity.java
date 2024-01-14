package com.example.ntasks.rents;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;

public class IndependentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_independent_details);
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("INDEPENDENT DETAILS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        // Retrieve Independent object from Intent
        Independent independent = getIntent().getParcelableExtra("independent");

        // Find views
        ImageView imageView2 = findViewById(R.id.imageView2);
        TextView tvIndpName = findViewById(R.id.tvindpName);
        TextView tvIndpId = findViewById(R.id.tvidindp);
        TextView tvAddIndp = findViewById(R.id.tvaddindp);
        TextView tvAreaIndp = findViewById(R.id.tvareindp);
        TextView tvUnitsIndp = findViewById(R.id.tvflatsindp);
        TextView tvFloorIndp = findViewById(R.id.tvfloorindp);
        TextView tvShops = findViewById(R.id.tvsptshops);
        TextView tvOwnerIndp = findViewById(R.id.tvownerindp);
        TextView tvVendorIndp = findViewById(R.id.tvvendorindp);
        TextView tvIndpNotes = findViewById(R.id.tvindpnotes);
        TextView tvDocIndp = findViewById(R.id.tvdocindp);
        /*Button btnDownloadDoc = findViewById(R.id.downloadDocButton);*/

        // Check if the independent object is not null
        if (independent != null) {
            // Set values to views
            imageView2.setImageResource(R.drawable.independent); // You can change this image based on your requirements
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
            tvDocIndp.setText("\t\tDOC TYPE:");

            // Set onClickListener for the download button
            /*btnDownloadDoc.setOnClickListener(v -> {
                // Add your logic for downloading the document
            });*/

            // You can customize this based on your specific requirements
            // For example, changing background colors, text colors, etc.
        } else {
            // Handle the case where the independent object is null
        }
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
