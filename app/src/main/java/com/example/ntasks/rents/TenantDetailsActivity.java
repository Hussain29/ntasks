package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class TenantDetailsActivity extends AppCompatActivity {

    private List<Flats> flatArrayList;


    private List<Independent> independentList;


    private ImageView tenantImageView;
    private TextView tenantNameTextView;
    private TextView tenantFatherNameTextView;
    private TextView tenantPerAddressTextView;
    private TextView tenantPrevAddressTextView;
    private TextView tenantOccupationTextView;
    private TextView tenantWorkAddressTextView;
    private TextView tenantNoOfPeopleTextView;
    private TextView tenantPhoneNumbersTextView; // Combine all phone numbers into one field
    private TextView tenantPropertyTypeTextView;
    private TextView tenantRentTextView;
    private TextView tenantAdvanceAmountTextView;
    private TextView tenantAdmissionDateTextView;
    private TextView tenantDocTypeTextView;
    private TextView tenantNotesTextView;
    private TextView tenantPaydayTextView;
    private TextView tenantDocumentLinkTextView;
    private TextView tenantIDTextView;

    private Button btnApartmentDetails;

    private Tenant tenant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_details);

        // Retrieve tenant from Intent
        tenant = getIntent().getParcelableExtra("tenant");
        flatArrayList = getIntent().getParcelableArrayListExtra("flatList");
        independentList = getIntent().getParcelableArrayListExtra("independentList"); // Initialize the list


        if (flatArrayList != null) {
            Log.d("TDAA", "flatsList size: " + flatArrayList.size());
        } else {
            Log.e("TDAA", "flatsList is null");
        }

        if (independentList != null) {
            Log.d("TDAA", "independetList size: " + independentList.size());
        } else {
            Log.e("TDAA", "flatsList is null");
        }


        // Check if tenant is null before accessing its properties
        if (tenant != null) {
            // Initialize Views
            tenantIDTextView = findViewById(R.id.tvidtenant);
            tenantImageView = findViewById(R.id.imageView2);
            tenantNameTextView = findViewById(R.id.tvtenName);
            tenantFatherNameTextView = findViewById(R.id.tvtenantfathername);
            tenantPerAddressTextView = findViewById(R.id.tvtenantaddress);
            tenantPrevAddressTextView = findViewById(R.id.tvtenantprevaddress);
            tenantOccupationTextView = findViewById(R.id.tvtenantworkaddress);
            tenantWorkAddressTextView = findViewById(R.id.tvtenantworkaddress);
            tenantNoOfPeopleTextView = findViewById(R.id.tvtenantnoofppl);
            tenantPhoneNumbersTextView = findViewById(R.id.tvtenantphone); // Combine all phone numbers into one field
            tenantPropertyTypeTextView = findViewById(R.id.tvtenantproperty);
            tenantRentTextView = findViewById(R.id.tvtenantrentamt);
            tenantAdvanceAmountTextView = findViewById(R.id.tvtenantadvamt);
            tenantAdmissionDateTextView = findViewById(R.id.tvtenantdateofadmsn);
            tenantDocTypeTextView = findViewById(R.id.tvtenantdoctype);
            tenantNotesTextView = findViewById(R.id.tvtenantnotes);
            tenantPaydayTextView = findViewById(R.id.tvtenantpayday);
            tenantDocumentLinkTextView = findViewById(R.id.tvtenantdocumentlink);

            // Set TextViews with tenant details

            tenantIDTextView.setText("\t\tTenant ID: " + tenant.getTenantId());
            tenantNameTextView.setText("\t\tName: " + tenant.getTenantName());
            tenantFatherNameTextView.setText("\t\tTenant Father Name: " + tenant.getTenantFatherName());
            tenantPerAddressTextView.setText("\t\tPermanent Address: " + tenant.getTenantPerAddress());
            tenantPrevAddressTextView.setText("\t\tPrevious Address: " + tenant.getTenantPrevAddress());
            tenantOccupationTextView.setText("\t\tOccupation: " + tenant.getTenantOccupation());
            tenantWorkAddressTextView.setText("\t\tWork Address: " + tenant.getTenantWorkAddress());
            tenantNoOfPeopleTextView.setText("\t\tNumber of People: " + tenant.getNoOfPeople());

            // Combine all phone numbers into one field
            String phoneNumbers = "Phone Numbers: " +
                    tenant.getTenantPhoneNumber() + ", " +
                    tenant.getTenantPhoneNumber2() + ", " +
                    tenant.getTenantPhoneNumber3();
            tenantPhoneNumbersTextView.setText(phoneNumbers);

            tenantPropertyTypeTextView.setText("\t\tProperty: " + tenant.getPropertyName());
            tenantRentTextView.setText("\t\tRent Amount: " + tenant.getTenantRent());
            tenantAdvanceAmountTextView.setText("\t\tDeposit/Advance Amount: " + tenant.getAdvanceAmount());
            tenantAdmissionDateTextView.setText("\t\tAdmission Date: " + tenant.getAdmissionDate());
            tenantDocTypeTextView.setText("\t\tDocument Type: " + tenant.getDocType());
            tenantNotesTextView.setText("\t\tTenant Notes: " + tenant.getTenantNotes());
            tenantPaydayTextView.setText("\t\tPayment Day: " + tenant.getPayday());
            tenantDocumentLinkTextView.setText("\t\tDocument Link: " + tenant.getDocUrl());
            btnApartmentDetails = findViewById(R.id.btnFlatDetails);

            btnApartmentDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Log statement to check if the button click is registered
                    Log.d("TDAA", "Button clicked");

                    // Create an Intent to navigate to FlatDetailsActivity or IndependentDetailsActivity
                    Intent intent;

                    // Assuming you have a method to get Flat object from flat name
                    Flats flat = getFlatByName(tenant.getPropertyName());

                    // Assuming you have a method to get Independent object from independent name
                    Independent independent = getIndependentByName(tenant.getPropertyName());

                    if (flat != null) {
                        // Log statement to check if flat is not null
                        Log.d("TDAA", "Flat found: " + flat.getFlatNo());
                        intent = new Intent(TenantDetailsActivity.this, FlatDetailsActivity.class);
                        intent.putExtra("flat", flat);
                        intent.putExtra("flatsList", new ArrayList<>(flatArrayList)); // Pass the entire list
                    } else if (independent != null) {
                        // Log statement to check if independent is not null
                        Log.d("TDAA", "Independent found: " + independent.getIndpName());
                        intent = new Intent(TenantDetailsActivity.this, IndependentDetailsActivity.class);
                        intent.putExtra("independent", independent);
                        intent.putExtra("independentList", new ArrayList<>(independentList));
                    } else {
                        // Log the property type when it's neither "Flat" nor "Independent"
                        Log.e("TDAA", "Unknown property type: " + tenant.getPropertyName());
                        // You can show a toast or handle it based on your requirements
                        Toast.makeText(TenantDetailsActivity.this, "Unknown property type: " + tenant.getPropertyName(), Toast.LENGTH_SHORT).show();
                        return;
                    }


                    // Start the activity
                    startActivity(intent);
                }
            });

            // Load tenant image using Picasso
            if (tenant.getImgUrl() != null && !tenant.getImgUrl().isEmpty()) {
                Picasso.get().load(tenant.getImgUrl()).into(tenantImageView);
            }

            // Get the ActionBar
            ActionBar actionBar = getSupportActionBar();

            // Set the title
            actionBar.setTitle("TENANT DETAILS");

            // Enable the back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

            displayAttachmentLink(tenant.getDocUrl());
        } else {
            // Log an error or show a message indicating that the tenant data is null
            Log.e("TenantDetailsActivity", "Tenant data is null");
            // You may also finish the activity or handle it accordingly
            finish();
        }
    }

    private Flats getFlatByName(String flatname) {
        if (flatArrayList != null) {
            for (Flats flats : flatArrayList) {
                if (flats.getFlatNo().equals(flatname)) {
                    return flats;
                }
            }
        }
        return null;
    }

    private Independent getIndependentByName(String independentName) {
        Log.d("TDAA", "Checking Independent for: " + independentName);

        if (independentList != null) {
            for (Independent independent : independentList) {
                if (independent.getIndpName().equals(independentName)) {
                    Log.d("TDAA", "Independent found: " + independent.getIndpName());
                    return independent;
                }
            }
        }

        Log.e("TDAA", "Independent not found or independent is null");
        return null;
    }






    private void displayAttachmentLink(String url) {
        // Use a TextView to display the clickable link
        tenantDocumentLinkTextView.setVisibility(View.VISIBLE);

        // Set the autoLink property to web for automatic linking of URLs
        tenantDocumentLinkTextView.setAutoLinkMask(Linkify.WEB_URLS);

        // Set the text to the URL
        tenantDocumentLinkTextView.setText("Click To View ID: " + url);

        // Set an onClickListener to perform some action when clicked
        tenantDocumentLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Modify this block based on your desired action
                // For example, you can show a toast or perform some other action
                Toast.makeText(TenantDetailsActivity.this, "Attachment URL Clicked", Toast.LENGTH_SHORT).show();
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
