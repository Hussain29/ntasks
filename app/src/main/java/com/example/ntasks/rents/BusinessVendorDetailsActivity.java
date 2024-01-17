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

}