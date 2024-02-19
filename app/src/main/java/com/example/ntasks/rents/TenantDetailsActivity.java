package com.example.ntasks.rents;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import com.example.ntasks.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class TenantDetailsActivity extends AppCompatActivity {

    private ImageView ivTenantPic;
    private TextView tvTenName, tvTenantID, tvTenantFatherName, tvTenantPermAddress, tvTenantPrevAddress,
            tvTenantPayday, tvTenantWorkAddress, tvTenantRentAmt, tvTenantAdvanceAmt,
            tvTenantDateOfAdmission, tvTenantPhone, tvTenantProperty, tvTenantNoOfPpl, tvTenantNotes,
            tvTenantDocType, tvTenantDocumentLink;
    private Button btnFlatDetails;
    private CardView cvView;
    private NestedScrollView scrollView;

    private Button btnEndTenant;
    private Button btnReTenant;
    private Button Editbtn;

    private Tenant tenant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_details);

        tenant = getIntent().getParcelableExtra("tenant");

        // Initialize your views
        ivTenantPic = findViewById(R.id.ivtenantpic);
        tvTenName = findViewById(R.id.tvtenName);
        tvTenantID = findViewById(R.id.tvtenantID);
        tvTenantFatherName = findViewById(R.id.tvtenantfathername);
        tvTenantPermAddress = findViewById(R.id.tvtenantpermaddress);
        tvTenantPrevAddress = findViewById(R.id.tvtenantprevaddress);
        tvTenantPayday = findViewById(R.id.tvtenantpayday);
        tvTenantWorkAddress = findViewById(R.id.tvtenantworkaddress);
        tvTenantRentAmt = findViewById(R.id.tvtenantrentamt);
        tvTenantAdvanceAmt = findViewById(R.id.tvtenantadvanceamt);
        tvTenantDateOfAdmission = findViewById(R.id.tvtenantdateofadmission);
        tvTenantPhone = findViewById(R.id.tvtenantphone);
        tvTenantProperty = findViewById(R.id.tvtenantproperty);
        tvTenantNoOfPpl = findViewById(R.id.tvtenantnoofppl);
        tvTenantNotes = findViewById(R.id.tvtenantnotes);
        tvTenantDocType = findViewById(R.id.tvtenantdoctype);
        tvTenantDocumentLink = findViewById(R.id.tvtenantdocumentlink);
        btnFlatDetails = findViewById(R.id.btnFlatDetails);
        cvView = findViewById(R.id.cvview);

        btnEndTenant = findViewById(R.id.btnEndTenant);
        btnReTenant = findViewById(R.id.btnReTenant);
        Editbtn = findViewById(R.id.btneditdetails);

        Editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TenantDetailsActivity.this, EditTenantDetailsActivity.class);
                intent.putExtra("tenant_details", tenant);
                startActivity(intent);
            }
        });

        if(tenant.getStatus().equals("PAST")){
            btnEndTenant.setVisibility(View.GONE);// Show the logout button
            btnReTenant.setVisibility(View.VISIBLE);// Show the logout button

        }

        // Display Tenant data
        displayTenantData(tenant);

        // Set an onClickListener for the document link
        displayAttachmentLink(tenant.getDocUrl());
    }

    private void displayTenantData(Tenant tenant) {
        // Use Picasso to load image into ImageView
        Picasso.get().load(tenant.getImgUrl()).into(ivTenantPic);

        // Set other TextViews with Tenant data
        tvTenName.setText(tenant.getTenantName());
        tvTenantID.setText("Tenant ID: " + tenant.getTenantId());
        tvTenantFatherName.setText("Tenant Father Name: " + tenant.getTenantFatherName());
        tvTenantPermAddress.setText("Permanent Address: " + tenant.getTenantPerAddress());
        tvTenantPrevAddress.setText("Previous Address: " + tenant.getTenantPrevAddress());
        tvTenantPayday.setText("Payment Day: " + tenant.getPayday());
        tvTenantWorkAddress.setText("Work Address: " + tenant.getTenantWorkAddress());
        tvTenantRentAmt.setText("Rent Amount: " + tenant.getTenantRent());
        tvTenantAdvanceAmt.setText("Deposit/Advance Amount: " + tenant.getAdvanceAmount());
        tvTenantDateOfAdmission.setText("Date of Admission: " + tenant.getAdmissionDate());
        tvTenantPhone.setText("Phone Numbers: " + tenant.getTenantPhoneNumber() + ", " + tenant.getTenantPhoneNumber2() + ", " + tenant.getTenantPhoneNumber3());
        tvTenantProperty.setText("Property: " + tenant.getPropertyName());
        tvTenantNoOfPpl.setText("Number of People: " + tenant.getNoOfPeople());
        tvTenantNotes.setText("Tenant Notes: " + tenant.getTenantNotes());
        tvTenantDocType.setText("Document Type: " + tenant.getDocType());
        tvTenantDocumentLink.setText("Click To View ID: " + tenant.getDocUrl());

        btnEndTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });

        btnReTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUndoConfirmationDialog();
            }
        });
    }

    private void displayAttachmentLink(String url) {
        // Use a TextView to display the clickable link
        tvTenantDocumentLink.setVisibility(View.VISIBLE);

        // Set the autoLink property to web for automatic linking of URLs
        tvTenantDocumentLink.setAutoLinkMask(Linkify.WEB_URLS);

        // Set the text to the URL
        tvTenantDocumentLink.setText("Click To View ID: " + url);

        // Set an onClickListener to perform some action when clicked
        tvTenantDocumentLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Modify this block based on your desired action
                // For example, you can show a toast or perform some other action
                Toast.makeText(TenantDetailsActivity.this, "Attachment URL Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to remove this tenant?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Yes," so delete the task
                        deleteTenant(tenant.getTenantId());
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
    private void showUndoConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to reinstate this tenant?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Yes," so delete the task
                        undoTenant(tenant.getTenantId());
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

    private void deleteTenant(String tenantID) {

        // Update the status of the task to "COMPLETED" in the database
        DatabaseReference tenantRef = FirebaseDatabase.getInstance().getReference().child("Rents/Tenants").child(tenantID);
        tenantRef.child("status").setValue("PAST");

        // You can also show a Toast message or handle UI updates to indicate success
        Toast.makeText(TenantDetailsActivity.this, "Tenant Removed", Toast.LENGTH_SHORT).show();

        // Close the current activity after updating the task status
        finish();
    }
    private void undoTenant(String tenantID) {

        // Update the status of the task to "COMPLETED" in the database
        DatabaseReference tenantRef = FirebaseDatabase.getInstance().getReference().child("Rents/Tenants").child(tenantID);
        tenantRef.child("status").setValue("ACTIVE");

        // You can also show a Toast message or handle UI updates to indicate success
        Toast.makeText(TenantDetailsActivity.this, "Tenant Added Again", Toast.LENGTH_SHORT).show();

        // Close the current activity after updating the task status
        finish();
    }
}
