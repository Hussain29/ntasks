package com.example.ntasks.rents;/*
package com.example.ntasks.rents;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ntasks.R;

public class BusinessVendorDetailsActivity extends AppCompatActivity {
LinearLayout callalt, emailButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bi_vendor_details);

        callalt=findViewById(R.id.callalt);
        emailButton=findViewById(R.id.emailButton);


        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("BUSINESS VENDORS DETAILS ");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
        Toast.makeText(this, "Under Development, Coming Soon!", Toast.LENGTH_LONG).show();

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace "recipient@example.com" with the actual email address
                String emailAddress = "mohdsuleman2628@gmail.com";

                // Create the intent with the ACTION_SENDTO action
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

                // Set the email address
                emailIntent.setData(Uri.parse("mailto:" + emailAddress));

                // You can also add subject and body if needed
                // emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "Body text");

                // Start the email composer activity
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });



        callalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace "1234567890" with the actual phone number you want to dial
                String phoneNumber = "1234567890";

                // Create the intent with the ACTION_DIAL action
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);

                // Set the phone number to dial
                dialIntent.setData(Uri.parse("tel:" + phoneNumber));

                // Start the dialer activity
                startActivity(dialIntent);
            }
        });




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

}*/


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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

public class BusinessVendorDetailsActivity extends AppCompatActivity {

    private ImageView imageView2;
    private TextView tvBivenName;
    private TextView tvBivenId;
    /*private TextView tvAddBiven;*/
    private TextView tvProducts;
    private TextView tvAddress;
    private TextView tvWebsite;
    private TextView tvCity;
    private TextView tvCountry;
    private TextView tvFax;
    private TextView tvTelephone;
    private TextView tvEmail;
    private TextView tvContactName;
    private TextView tvContactEmail;
    private TextView tvLandline;
    private TextView tvAlternateContact;
    private TextView tvCRNo;
    private TextView tvVATNo;
    private TextView tvAdditionalInfo;
    private TextView tvGoogleLink;
    private TextView tvBankName;
    private TextView tvBeneficiaryName;
    private TextView tvAccountNo;
    private TextView tvBankAddress;
    private TextView tvIBANNo;
    private TextView tvNotes;

    private BusinessVendor businessVendor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bi_vendor_details);

        // Retrieve BusinessVendor object from Intent
        businessVendor = getIntent().getParcelableExtra("business_vendor");

        // Check if businessVendor is null before accessing its properties
        if (businessVendor != null) {
            // Initialize Views
            imageView2 = findViewById(R.id.imageView2);
            tvBivenName = findViewById(R.id.tvbvendorName);
            tvBivenId = findViewById(R.id.tvbvenodrID);
            /*tvAddBiven = findViewById(R.id.tvidbiven);*/
            tvProducts = findViewById(R.id.tvproducts);
            tvAddress = findViewById(R.id.tvaddap); // Assuming the address TextView ID is the same
            tvWebsite = findViewById(R.id.tvbvendorwebsite);
            tvCity = findViewById(R.id.tvbvendorcity); // Assuming the city TextView ID is the same
            tvCountry = findViewById(R.id.tvbvendorscountry); // Assuming the country TextView ID is the same
            tvFax = findViewById(R.id.tvbvendorfax); // Assuming the fax TextView ID is the same
            tvTelephone = findViewById(R.id.tvbvendortelephone); // Assuming the telephone TextView ID is the same
            tvEmail = findViewById(R.id.tvbvendoremail); // Assuming the email TextView ID is the same
            tvContactName = findViewById(R.id.tvbvendorpocname); // Assuming the contact name TextView ID is the same
            tvContactEmail = findViewById(R.id.tvbvendorpocemail); // Assuming the contact email TextView ID is the same
            tvLandline = findViewById(R.id.tvbvendorpocphone1); // Assuming the landline TextView ID is the same
            tvAlternateContact = findViewById(R.id.tvbvendorpocphone2); // Assuming the alternate contact TextView ID is the same
            tvCRNo = findViewById(R.id.tvbvendorcrno);
            tvVATNo = findViewById(R.id.tvbvendorvatno);
            tvAdditionalInfo = findViewById(R.id.tvbvendoraddinfo);
            tvGoogleLink = findViewById(R.id.tvbvendorglink);
            tvBankName = findViewById(R.id.tvbvendorbankname);
            tvBeneficiaryName = findViewById(R.id.tvbvendorbenefname);
            tvAccountNo = findViewById(R.id.tvbvendoraccno);
            tvBankAddress = findViewById(R.id.tvbvendorbankadd);
            tvIBANNo = findViewById(R.id.tvbvendoribanno);
            tvNotes = findViewById(R.id.tvbvendornotes);

            // Set values to views
            // Use Picasso to load the vendor image
            Picasso.get().load(R.drawable.bven1).into(imageView2);
            tvBivenName.setText(businessVendor.getCompanyName());
            tvBivenId.setText("\t\tVendor Id: " + businessVendor.getCompanyId());
            /*tvAddBiven.setText("\t\tAddress: " + businessVendor.getCompanyMailingAddress());*/
            tvProducts.setText("\t\tProducts: " + businessVendor.getCompanyProducts());
            tvAddress.setText("\t\tAddress: " + businessVendor.getCompanyMailingAddress());
            tvWebsite.setText("\t\tWebsite: " + businessVendor.getCompanyEmail());
            tvCity.setText("\t\tCity: " + businessVendor.getCompanyCity());
            tvCountry.setText("\t\tCountry: " + businessVendor.getCompanyCountry());
            tvFax.setText("\t\tFAX: " + businessVendor.getCompanyFax());
            tvTelephone.setText("\t\tTelephone No.: " + businessVendor.getCompanyTelephone());
            tvEmail.setText("\t\tEmail Address: " + businessVendor.getCompanyEmail());
            tvContactName.setText("\t\tPoint of Contact Name: " + businessVendor.getCompanyPocName());
            tvContactEmail.setText("\t\tContact Email: " + businessVendor.getCompanyEmail());
            tvLandline.setText("\t\tLandline No.: " + businessVendor.getCompanyAltContact1());
            tvAlternateContact.setText("\t\tALTERNATE CONTACT: " + businessVendor.getCompanyAltContact2());
            tvCRNo.setText("\tCR No: " + businessVendor.getCompanyCrNumber());
            tvVATNo.setText("\tVAT No: " + businessVendor.getCompanyVatNumber());
            tvAdditionalInfo.setText("\tAdditional Info: " + businessVendor.getAdditionalInfo());
            tvGoogleLink.setText("\tGoogle Link: " + businessVendor.getGoogleLocationLink());
            tvBankName.setText("\tBANK NAME: " + businessVendor.getBankName());
            tvBeneficiaryName.setText("\tBENEFICIARY NAME: " + businessVendor.getBeneficiaryName());
            tvAccountNo.setText("\tA/C NO: " + businessVendor.getAccountNumber());
            tvBankAddress.setText("\tBANK ADDRESS: " + businessVendor.getBankAddress());
            tvIBANNo.setText("\tIBAN NO.: " + businessVendor.getIbanNumber());
            tvNotes.setText("\tNOTES / REMARKS: " + businessVendor.getNotes());

            // Load vendor image using Picasso
            if (businessVendor.getShopPicURL() != null && !businessVendor.getShopPicURL().isEmpty()) {
                Picasso.get().load(businessVendor.getShopPicURL()).into(imageView2);
            }

            // Get the ActionBar
            ActionBar actionBar = getSupportActionBar();

            // Set the title
            actionBar.setTitle("VENDOR DETAILS");

            // Enable the back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk);
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

            // You can customize this based on your specific requirements
            // For example, changing background colors, text colors, etc.

            // Display clickable link for website URL
            displayLink(businessVendor.getCompanyWebsite());
        } else {
            // Log an error or show a message indicating that the vendor data is null
            Log.e("BusinessVendorDetailsActivity", "Vendor data is null");
            // You may also finish the activity or handle it accordingly
            finish();
        }
    }

    private void displayLink(String url) {
        // Use a TextView to display the clickable link
        tvWebsite.setVisibility(View.VISIBLE);

        // Set the autoLink property to web for automatic linking of URLs
        tvWebsite.setAutoLinkMask(Linkify.WEB_URLS);

        // Set the text to the URL
        tvWebsite.setText("Click To Visit Website: " + url);

        // Set an onClickListener to perform some action when clicked
        tvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Modify this block based on your desired action
                // For example, open the website in a browser or perform some other action
                Toast.makeText(BusinessVendorDetailsActivity.this, "Website URL Clicked", Toast.LENGTH_SHORT).show();
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
