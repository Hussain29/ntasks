package com.example.ntasks;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.ntasks.rents.AddFlatsActivity;

import java.util.Calendar;

public class AddTenant extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST_IMG = 90;
    EditText ettendate;
    CardView cvdep222;
    ImageView imgcalen;
    LinearLayout attachfile;
    ConstraintLayout addimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);


        ettendate = findViewById(R.id.ettendate);
        cvdep222 = findViewById(R.id.cvid222);
        imgcalen = findViewById(R.id.imgcalender);
        attachfile=findViewById(R.id.attachfile);
        addimg=findViewById(R.id.addimg);


        Spinner spinnerpay = findViewById(R.id.spinpayday);
        Spinner spinnernoof = findViewById(R.id.spinnooff);
        Spinner spinid = findViewById(R.id.spinid);



        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD TENANT DETAILS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTenant.this, "Select Image", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_IMG);
            }
        });


        attachfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTenant.this, "Select Image", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_IMG);
            }
        });



        String[] items = getResources().getStringArray(R.array.Paymentday);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpay.setAdapter(adapter);


        String[] items1 = getResources().getStringArray(R.array.noof);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnernoof.setAdapter(adapter1);

        String[] items2 = getResources().getStringArray(R.array.IDtypes);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinid.setAdapter(adapter2);


        ettendate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        // on below line we are adding click listener for our pick date button
        imgcalen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        AddTenant.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                ettendate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
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