package com.example.ntasks.rents;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.ntasks.R;

public class hrAddEmployeeActivity extends AppCompatActivity {

    EditText etempid, etempname, etempiqno, etempiqexp1, etempiqexp2, etempaddper, etempemcon1, etempemcon2, etempnat, etempocc, etempbtdt, etempbtdt1, etempbtdt2, etempcountry, etempmstat, etemprel, etemphins1, etemphins2, etempiq1, etempiq2, etempdl1, etempdl2, etemppp1, etemppp2, etempdep, etempppno, etempppcity, etempppdt, etempppexpdt, etempvinfo, etempdlno, etempdldate, etempdlexpdt, etemphisd, etemphied, etempnotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_add_employee);


/*

        etempid = findViewById(R.id.etempid);
        etempname = findViewById(R.id.etempname);
        etempiqno = findViewById(R.id.etempiqno);
        etempiqexp1 = findViewById(R.id.etempiqexp1);
        etempiqexp2 = findViewById(R.id.etempiqexp2);
        etempaddper = findViewById(R.id.etempaddper);
        etempemcon1 = findViewById(R.id.etempemcon1);
        etempemcon2 = findViewById(R.id.etempemcon2);
        etempnat = findViewById(R.id.etempnat);
        etempocc = findViewById(R.id.etempocc);
        etempbtdt = findViewById(R.id.etempbtdt);
        etempcountry = findViewById(R.id.etempcountry);
        etempmstat = findViewById(R.id.etempmstat);
        etemprel = findViewById(R.id.etemprel);
        etemphins1 = findViewById(R.id.etemphins1);
        etemphins2 = findViewById(R.id.etemphins2);
        etempiq1 = findViewById(R.id.etempiq1);
        etempdl1 = findViewById(R.id.etempdl1);
        etempdl2 = findViewById(R.id.etempdl2);
        etemppp1 = findViewById(R.id.etemppp1);
        etemppp2 = findViewById(R.id.etemppp2);
        etempdep = findViewById(R.id.etempdep);
        etempppno = findViewById(R.id.etempppno);
        etempppcity = findViewById(R.id.etempppcity);
        etempppdt = findViewById(R.id.etempppdt);
        etempppexpdt = findViewById(R.id.etempppexpdt);
        etempvinfo = findViewById(R.id.etempvinfo);
        etempdlno = findViewById(R.id.etempdlno);
        etempdldate = findViewById(R.id.etempdldate);
        etempdlexpdt = findViewById(R.id.etempdlexpdt);
        etemphisd = findViewById(R.id.etemphisd);
        etemphied = findViewById(R.id.etemphied);
        etempnotes = findViewById(R.id.etempnotes);
*/


// Assigning IDs to EditText fields
        EditText etempid = findViewById(R.id.etempid);  // Employee ID
        EditText etempname = findViewById(R.id.etempname);  // Employee Name
        EditText etempiqno = findViewById(R.id.etempiqno);  // Employee IQ Number
        EditText etempiqexp1 = findViewById(R.id.etempiqexp1);  // Employee IQ Experience 1
        EditText etempiqexp2 = findViewById(R.id.etempiqexp2);  // Employee IQ Experience 2
        EditText etempaddper = findViewById(R.id.etempaddper);  // Employee Address Permanent
        EditText etempemcon1 = findViewById(R.id.etempemcon1);  // Employee Emergency Contact 1
        EditText etempemcon2 = findViewById(R.id.etempemcon2);  // Employee Emergency Contact 2
        EditText etempnat = findViewById(R.id.etempnat);  // Employee Nationality
        EditText etempocc = findViewById(R.id.etempocc);  // Employee Occupation
        EditText etempbtdt1 = findViewById(R.id.etempbtdt1);  // Employee Birthdate
        EditText etempbtdt2 = findViewById(R.id.etempbtdt2);  // Employee Birthdate
        EditText etempcountry = findViewById(R.id.etempcountry);  // Employee Country
        EditText etempmstat = findViewById(R.id.etempmstat);  // Employee Marital Status
        EditText etemprel = findViewById(R.id.etemprel);  // Employee Relationship
        EditText etemphins1 = findViewById(R.id.etemphins1);  // Employee Insurance 1
        EditText etemphins2 = findViewById(R.id.etemphins2);  // Employee Insurance 2
        EditText etempiq1 = findViewById(R.id.etempiq1);  // Employee IQ 1
        EditText etempiq2 = findViewById(R.id.etempiq2);  // Employee IQ 1
        EditText etempdl1 = findViewById(R.id.etempdl1);  // Employee Driving License 1
        EditText etempdl2 = findViewById(R.id.etempdl2);  // Employee Driving License 2
        EditText etemppp1 = findViewById(R.id.etemppp1);  // Employee Passport 1
        EditText etemppp2 = findViewById(R.id.etemppp2);  // Employee Passport 2
        EditText etempdep = findViewById(R.id.etempdep);  // Employee Department
        EditText etempppno = findViewById(R.id.etempppno);  // Employee Passport Number
        EditText etempppcity = findViewById(R.id.etempppcity);  // Employee Passport City
        EditText etempppdt = findViewById(R.id.etempppdt);  // Employee Passport Date
        EditText etempppexpdt = findViewById(R.id.etempppexpdt);  // Employee Passport Expiry Date
        EditText etempvinfo = findViewById(R.id.etempvinfo);  // Employee Visa Info
        EditText etempdlno = findViewById(R.id.etempdlno);  // Employee Driving License Number
        EditText etempdldate = findViewById(R.id.etempdldate);  // Employee Driving License Date
        EditText etempdlexpdt = findViewById(R.id.etempdlexpdt);  // Employee Driving License Expiry Date
        EditText etemphisd = findViewById(R.id.etemphisd);  // Employee Health Insurance Start Date
        EditText etemphied = findViewById(R.id.etemphied);  // Employee Health Insurance End Date
        EditText etempnotes = findViewById(R.id.etempnotes);  // Employee Notes

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD EMPLOYEE");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.orangeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


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
    }}