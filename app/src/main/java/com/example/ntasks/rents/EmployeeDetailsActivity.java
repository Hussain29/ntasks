package com.example.ntasks.rents;

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

import com.example.ntasks.R;  // Update with your actual package name
import com.squareup.picasso.Picasso;

public class EmployeeDetailsActivity extends AppCompatActivity {

    private ImageView imgEmployeePic;
    private TextView tvEmployeeName, tvEmployeeOccupation, tvEmployeeNationality,
            tvEmployeeIqamaNo, tvEmpIqamaExpiryG, tvEmpIqamaExpiryH,
            tvEmpPermanentAddress, tvEmpEmergencyNo1, tvEmpEmergencyNo2,
            tvEmpBirthCountry, tvEmpBdayG, tvEmpBdayH, tvEmpMarital, tvEmpReligion,
             tvEmpHealthInsExpiryG, tvEmpHealthInsExpiryH, tvEmpIqamaDetExpiryG, tvEmpIqamaDetExpiryH,
            tvEmpPassport, tvEmpPasExpiryG, tvEmpPasExpiryH,
            tvEmpPasDetails, tvEmpPassportNo, tvEmpPassportCity,
            tvEmpPassportIssueDate, tvEmpPassportExpiryDate,
            tvEmpDL, tvEmpDrivingLicenseNo, tvEmpDLIssueDate, tvEmpDLExpiryDate,
            tvEmpVisa, tvEmpDependent, tvEmpNotes, tvEmpDocument;

    private Employee employee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        // Retrieve Employee object from Intent
        employee = getIntent().getParcelableExtra("employee");

        // Check if employee is null before accessing its properties
        if (employee != null) {
            // Initialize Views
            imgEmployeePic = findViewById(R.id.imgemployeepic);
            tvEmployeeName = findViewById(R.id.tvEmployeeName);
            tvEmployeeOccupation = findViewById(R.id.tvEmployeeOccupation);
            tvEmployeeNationality = findViewById(R.id.tvEmployeeNationality);
            tvEmployeeIqamaNo = findViewById(R.id.tvEmployeeIqamaNo);
            tvEmpIqamaExpiryG = findViewById(R.id.tvEmpIqamaExpiryG);
            tvEmpIqamaExpiryH = findViewById(R.id.tvEmpIqamaExpiryH);
            tvEmpPermanentAddress = findViewById(R.id.tvEmpPermanentAddress);
            tvEmpEmergencyNo1 = findViewById(R.id.tvEmpEmergencyNo1);
            tvEmpEmergencyNo2 = findViewById(R.id.tvEmpEmergencyNo2);
            tvEmpBirthCountry = findViewById(R.id.tvEmpBirthCountry);
            tvEmpBdayG = findViewById(R.id.tvEmpBdayG);
            tvEmpBdayH = findViewById(R.id.tvEmpBdayH);
            tvEmpMarital = findViewById(R.id.tvEmpMarital);
            tvEmpReligion = findViewById(R.id.tvEmpReligion);
            /*tvEmpHealthInsurance = findViewById(R.id.tvEmpHealthInsurance);*/
            tvEmpHealthInsExpiryG = findViewById(R.id.tvEmpHealthInsExpiryG);
            tvEmpHealthInsExpiryH = findViewById(R.id.tvEmpHealthInsExpiryH);
            /*tvEmpIqamaDetails = findViewById(R.id.tvEmpIqamaDetails);*/
            tvEmpIqamaDetExpiryG = findViewById(R.id.tvEmpIqamaDetExpiryG);
            tvEmpIqamaDetExpiryH = findViewById(R.id.tvEmpIqamaDetExpiryH);
            tvEmpPassport = findViewById(R.id.tvEmpPassport);
            tvEmpPasExpiryG = findViewById(R.id.tvEmpPasExpiryG);
            tvEmpPasExpiryH = findViewById(R.id.tvEmpPasExpiryH);
            tvEmpPasDetails = findViewById(R.id.tvEmpPasDetails);
            tvEmpPassportNo = findViewById(R.id.tvEmpPassportNo);
            tvEmpPassportCity = findViewById(R.id.tvEmpPassportCity);
            tvEmpPassportIssueDate = findViewById(R.id.tvEmpPassportIssueDate);
            tvEmpPassportExpiryDate = findViewById(R.id.tvEmpPassportExpiryDate);
            tvEmpDL = findViewById(R.id.tvEmpDL);
            tvEmpDrivingLicenseNo = findViewById(R.id.tvEmpDrivingLicenseNo);
            tvEmpDLIssueDate = findViewById(R.id.tvEmpDLIssueDate);
            tvEmpDLExpiryDate = findViewById(R.id.tvEmpDLExpiryDate);
            tvEmpVisa = findViewById(R.id.tvEmpVisa);
            tvEmpDependent = findViewById(R.id.tvEmpDependent);
            tvEmpNotes = findViewById(R.id.tvEmpNotes);
            tvEmpDocument = findViewById(R.id.tvEmpDocument);

            // Set values to views
            // Use Picasso to load the employee image
            tvEmployeeName.setText(employee.getEmpName());
            tvEmployeeOccupation.setText("Occupation: " + employee.getEmpOcc());
            tvEmployeeNationality.setText("Nationality: " + employee.getEmpNat());
            tvEmployeeIqamaNo.setText("Iqama No: " + employee.getEmpIqNo());
            tvEmpIqamaExpiryG.setText("Iqama Expiry (Gregorian): " + employee.getEmpIqExp1());
            tvEmpIqamaExpiryH.setText("Iqama Expiry (Hijri): " + employee.getEmpIqExp2());
            tvEmpPermanentAddress.setText("Permanent Address: " + employee.getEmpAddPer());
            tvEmpEmergencyNo1.setText("Emergency No 1: " + employee.getEmpEmCon1());
            tvEmpEmergencyNo2.setText("Emergency No 2: " + employee.getEmpEmCon2());
            tvEmpBirthCountry.setText("Birth Country: " + employee.getEmpCountry());
            tvEmpBdayG.setText("Birthday (Gregorian): " + employee.getEmpBtDt1());
            tvEmpBdayH.setText("Birthday (Hijri): " + employee.getEmpBtDt1());
            tvEmpMarital.setText("Marital Status: " + employee.getEmpMStat());
            tvEmpReligion.setText("Religion: " + employee.getEmpRel());
            tvEmpHealthInsExpiryG.setText("Health Ins. Expiry (Gregorian): " + employee.getEmpHInsStartDt());
            tvEmpHealthInsExpiryH.setText("Health Ins. Expiry (Hijri): " + employee.getEmpHInsEndDt());
            tvEmpIqamaDetExpiryG.setText("Iqama Det. Expiry (Gregorian): " + employee.getEmpIqExp1());
            tvEmpIqamaDetExpiryH.setText("Iqama Det. Expiry (Hijri): " + employee.getEmpIqExp2());
/*
            tvEmpPassport.setText("Passport: " + employee.getPassport());
*/
            tvEmpPasExpiryG.setText("Passport Expiry (Gregorian): " + employee.getEmpPp1());
            tvEmpPasExpiryH.setText("Passport Expiry (Hijri): " + employee.getEmpPp2());
            /*tvEmpPasDetails.setText("Passport Details: " + employee.getPasDetails());*/
            tvEmpPassportNo.setText("Passport No: " + employee.getEmpPpNo());
            tvEmpPassportCity.setText("Passport City: " + employee.getEmpPpCity());
            tvEmpPassportIssueDate.setText("Passport Issue Date: " + employee.getEmpPpDt());
            tvEmpPassportExpiryDate.setText("Passport Expiry Date: " + employee.getEmpPpExpDt());
            /*tvEmpDL.setText("Driving License: " + employee.getDL());*/
            tvEmpDrivingLicenseNo.setText("DL No: " + employee.getEmpDlNo());
            tvEmpDLIssueDate.setText("DL Issue Date: " + employee.getEmpDlExpDt());
            tvEmpDLExpiryDate.setText("DL Expiry Date: " + employee.getEmpDlExpDt());
            tvEmpVisa.setText("Visa: " + employee.getEmpVisaInfo());
            tvEmpDependent.setText("Dependent: " + employee.getEmpDep());
            tvEmpNotes.setText("Notes: " + employee.getEmpNotes());

            // Get the ActionBar
            ActionBar actionBar = getSupportActionBar();

            // Set the title
            actionBar.setTitle("EMPLOYEE DETAILS");

            // Enable the back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            int actionBarColor = ContextCompat.getColor(this, R.color.blueeee);
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

            if (employee.getPhotoUrl() != null && !employee.getPhotoUrl().isEmpty()) {
                Picasso.get().load(employee.getPhotoUrl()).into(imgEmployeePic);
            }

            // Display clickable link for document URL
            displayAttachmentLink(employee.getDocUrl());

            // You can customize this based on your specific requirements
            // For example, changing background colors, text colors, etc.
        } else {
            // Log an error or show a message indicating that the employee data is null
            Log.e("EmployeeDetailsActivity", "Employee data is null");
            // You may also finish the activity or handle it accordingly
            finish();
        }
    }

    private void displayAttachmentLink(String url) {
        // Use a TextView to display the clickable link
        tvEmpDocument.setVisibility(View.VISIBLE);

        // Set the autoLink property to web for automatic linking of URLs
        tvEmpDocument.setAutoLinkMask(Linkify.WEB_URLS);

        // Set the text to the URL
        tvEmpDocument.setText("Click To View Document: " + url);

        // Set an onClickListener to perform some action when clicked
        tvEmpDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Modify this block based on your desired action
                // For example, show a toast or perform some other action
                Toast.makeText(EmployeeDetailsActivity.this, "Document URL Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openUrlInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
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
