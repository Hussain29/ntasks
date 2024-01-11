package com.example.ntasks.rents;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;

import java.util.List;

public class ApartmentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details);

        // Retrieve Apartment object from Intent
        Apartment apartment = getIntent().getParcelableExtra("apartment");
        List<Apartment> apartmentList = getIntent().getParcelableArrayListExtra("apartmentList");

        // Find views
        /*CardView cardView = findViewById(R.id.cvview);*/
        ImageView imageView2 = findViewById(R.id.imageView2);
        TextView tvAptName = findViewById(R.id.tvaptName);
        TextView tvAptId = findViewById(R.id.tvidap);
        TextView tvAddApt = findViewById(R.id.tvaddap);
        TextView tvAreaApt = findViewById(R.id.tvareapt);
        TextView tvFlatsApt = findViewById(R.id.tvflatsapt);
        TextView tvFloorApt = findViewById(R.id.tvfloorapt);
        TextView tvShops = findViewById(R.id.tvsptshops);
        TextView tvOwnerApt = findViewById(R.id.tvownerapt);
        TextView tvVendorApt = findViewById(R.id.tvvendorapt);
        TextView tvAptNotes = findViewById(R.id.tvaptnotes);
        TextView tvDocApt = findViewById(R.id.tvdocapt);
        /*Button btnDownloadDoc = findViewById(R.id.buttonDownloadDoc);*/

        // Check if the apartment object is not null
        if (apartment != null) {
            // Set values to views
            imageView2.setImageResource(R.drawable.apartment); // You can change this image based on your requirements
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
            tvDocApt.setText("\t\tDOC TYPE:");

            // Set onClickListener for the download button
            /*btnDownloadDoc.setOnClickListener(v -> {
                // Add your logic for downloading the document
            });*/

            // You can customize this based on your specific requirements
            // For example, changing background colors, text colors, etc.
         /*   cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardBackgroundColor));
            tvAptName.setTextColor(ContextCompat.getColor(this, R.color.textColor));*/
        } else {
            // Handle the case where the apartment object is null
        }
    }
}
