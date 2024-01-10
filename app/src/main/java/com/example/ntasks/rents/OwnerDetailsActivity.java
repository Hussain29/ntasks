package com.example.ntasks.rents;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ntasks.R;

public class OwnerDetailsActivity extends AppCompatActivity {

    private ImageView ownerImageView;
    private TextView ownerNameTextView;
    private TextView ownerAddressTextView;
    private TextView ownerEmailTextView;
    private TextView ownerPhone1TextView;
    private TextView ownerPhone2TextView;
    private TextView ownerPhone3TextView;
    private TextView ownerNotesTextView;
    private TextView idTypeTextView;

    private Owner owner;
    private Button downloadIdButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerdetails);

        owner = getIntent().getParcelableExtra("owner");

        // Initialize Views
        ownerImageView = findViewById(R.id.imageView2);
        ownerNameTextView = findViewById(R.id.tvName);
        ownerAddressTextView = findViewById(R.id.textViewDeadline); // Assuming Address is displayed in the same view
        ownerEmailTextView = findViewById(R.id.textViewAssignedBy);
        ownerPhone1TextView = findViewById(R.id.textViewOwnerPhone1);
        ownerPhone2TextView = findViewById(R.id.textViewOwnerPhone2); // Assuming Phone 2 is displayed in the same view
        ownerPhone3TextView = findViewById(R.id.textViewOwnerPhone3); // Assuming Phone 3 is displayed in the same view
        ownerNotesTextView = findViewById(R.id.textViewClientLabel);
        idTypeTextView = findViewById(R.id.textViewStatusLabel);


        // Set TextViews with owner details
        ownerNameTextView.setText("Name:" + owner.getOwnerName());
        ownerAddressTextView.setText("Address:" + owner.getOwnerAddress());
        ownerEmailTextView.setText("Email:" + owner.getOwnerEmail());
        ownerPhone1TextView.setText("Phone Number:" + owner.getOwnerPhone1());
        ownerPhone2TextView.setText("Phone Number 2:" + owner.getOwnerPhone2());
        ownerPhone3TextView.setText("Phone Number 3:" + owner.getOwnerPhone3());
        ownerNotesTextView.setText("Notes:" + owner.getOwnerNotes());
        idTypeTextView.setText("ID Type: " + owner.getUserId());

    }
}
