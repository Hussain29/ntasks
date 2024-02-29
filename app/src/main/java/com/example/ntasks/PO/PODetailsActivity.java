package com.example.ntasks.PO;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DownloadManager;
import android.content.Context;
import android.os.Environment;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.AssgnTaskDetailsActivity;
import com.example.ntasks.PO.PurchaseOrder;
import com.example.ntasks.R;
import com.example.ntasks.rents.ApartmentDetailsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PODetailsActivity extends AppCompatActivity {

    private TextView tvPOSubject, tvPOAssignedBy, tvPOAssignedTo, tvPODate, tvPORemarks, tvPOAttachmentLink;
    private Button btnPOAttachDownload,btnPODown;
    private String poAttachmentUrl;

    PurchaseOrder selectedPO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podetails);

        // Initialize views
        tvPOSubject = findViewById(R.id.tvPOsubject);
        tvPOAssignedBy = findViewById(R.id.tvPOAssignedBy);
        tvPOAssignedTo = findViewById(R.id.tvPOAssignedTo);
        tvPODate = findViewById(R.id.tvPODate);
        tvPORemarks = findViewById(R.id.tvPOremarks);
        tvPOAttachmentLink = findViewById(R.id.tvAttachmentLink);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("POs");

        Button btnPOComplete = findViewById(R.id.btnPOcomplete);
        btnPODown=findViewById(R.id.btnPODown);

        btnPOComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCompleteConfirmationDialog();
            }
        });

        Button btnPOUpdate = findViewById(R.id.btnPOUpdate);





        btnPOUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve updated remarks from the text field
                String updatedRemarks = tvPORemarks.getText().toString().trim();

                // Check if the updated remarks are not empty
                if (!updatedRemarks.isEmpty()) {
                    // Update the remarks in the database
                    databaseReference.child(selectedPO.getPoId()).child("poRemarks").setValue(updatedRemarks)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Update successful, show a toast message
                                    Toast.makeText(PODetailsActivity.this, "Remarks updated successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle the failure
                                    Toast.makeText(PODetailsActivity.this, "Failed to update remarks: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    // If the updated remarks are empty, show a message
                    Toast.makeText(PODetailsActivity.this, "Remarks cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Receive selected PO from intent

        selectedPO = getIntent().getParcelableExtra("PURCHASE_ORDER");

        if (selectedPO != null) {
            // Set PO details to views
            tvPOSubject.setText(selectedPO.getPoSubject());
            tvPOAssignedBy.setText("A.By: " + selectedPO.getAssigner());
            tvPOAssignedTo.setText("A.To.: " + selectedPO.getAssignedUser());
            tvPODate.setText("Date: " + selectedPO.getSelectedDate());
            tvPORemarks.setText(selectedPO.getPoRemarks());
            displayAttachmentLink(selectedPO.getPoAttachmentUrl());


        }
        else{
            Toast.makeText(this, "PO Null", Toast.LENGTH_SHORT).show();
        }
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("PO DETAILS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        // Handle attachment download button click
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
    private void showCompleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_complete_po_with_invoice, null);
        builder.setView(dialogView);

        TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
        EditText etInvoiceNo = dialogView.findViewById(R.id.etInvoiceNo);

        tvMessage.setText("Are you sure you want to change the status to complete? Enter the invoice number below:");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Yes," so complete the PO
                        String invoiceNo = etInvoiceNo.getText().toString().trim();
                        if (!invoiceNo.isEmpty()) {
                            completePO(selectedPO.getPoId(), invoiceNo);
                        } else {
                            Toast.makeText(PODetailsActivity.this, "Please enter the invoice number", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "No," so dismiss the dialog
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


    // Download PO attachment
    private void displayAttachmentLink(final String url) {

        // Set the autoLink property to web for automatic linking of URLs
        tvPOAttachmentLink.setAutoLinkMask(Linkify.WEB_URLS);

        // Set the text to the URL
        tvPOAttachmentLink.setText("Click To View PO Attachment: " + url);



        tvPOAttachmentLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Modify this block based on your desired action
                // For example, show a toast or perform some other action
                Toast.makeText(PODetailsActivity.this, "Document URL Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnPODown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace "https://www.example.com" with your actual link
                ///String link = "https://www.example.com";
                if(url == null){
                    Toast.makeText(PODetailsActivity.this, "NO FILE ATTACHED", Toast.LENGTH_SHORT).show();
                }
                else {
                // Create an Intent with ACTION_VIEW and the link URI
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Start the activity to open the link
                startActivity(intent);
            }
            }
        });



    }

    private void completePO(String poId, String invoiceNo) {
        // Update the status of the PO to "COMPLETED" and set the invoice number
        DatabaseReference poRef = FirebaseDatabase.getInstance().getReference().child("POs").child(poId);
        poRef.child("status").setValue("COMPLETED");
        poRef.child("invoiceNo").setValue(invoiceNo);

        // Show a message or perform any additional actions upon completion
        Toast.makeText(PODetailsActivity.this, "Purchase order marked as completed with invoice number: " + invoiceNo, Toast.LENGTH_SHORT).show();

        // Close the activity or perform any other necessary actions
        finish();
    }



}
