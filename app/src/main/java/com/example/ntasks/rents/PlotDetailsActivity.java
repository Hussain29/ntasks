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

public class PlotDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_details);
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("PLOT DETAILS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        // Retrieve Plot object from Intent
        Plot plot = getIntent().getParcelableExtra("plot");

        // Find views
        ImageView imageView2 = findViewById(R.id.imageView2);
        TextView tvPlotName = findViewById(R.id.tvpltName);
        TextView tvPlotId = findViewById(R.id.tvidplt);
        TextView tvAddPlot = findViewById(R.id.tvaddplt);
        TextView tvAreaPlot = findViewById(R.id.tvareplt);
        /*TextView tvUnitsPlot = findViewById(R.id.tvflatsplt);*/
        TextView tvShops = findViewById(R.id.tvsptshops);
        TextView tvOwnerPlot = findViewById(R.id.tvownerplt);
        TextView tvVendorPlot = findViewById(R.id.tvvendorplt);
        TextView tvPlotNotes = findViewById(R.id.tvpltnotes);
        TextView tvDocPlot = findViewById(R.id.tvdocplt);
       /* Button btnDownloadDoc = findViewById(R.id.btnDownloadDoc);*/

        // Check if the plot object is not null
        if (plot != null) {
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
            tvDocPlot.setText("\t\tDOC TYPE:");

            // Set onClickListener for the download button
            /*btnDownloadDoc.setOnClickListener(v -> {
                // Add your logic for downloading the document
            });
*/
            // You can customize this based on your specific requirements
            // For example, changing background colors, text colors, etc.
        } else {
            // Handle the case where the plot object is null
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
