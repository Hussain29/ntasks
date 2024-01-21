package com.example.ntasks;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ntasks.rents.hrAddEmployeeActivity;

public class hrmaster extends AppCompatActivity {
    private EditText etsplit;
    Button btnaddemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrmaster);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnaddemp=findViewById(R.id.btnaddemp);


        btnaddemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(hrmaster.this, hrAddEmployeeActivity.class);
                startActivity(intent);
            }
        });
















        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("HR ");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.orangeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
        Toast.makeText(this, "Under Development, Coming Soon!", Toast.LENGTH_LONG).show();



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