package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class rentsmaster extends AppCompatActivity {


    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentsmaster);
        Button ownersbtn, propertybtn, vendorsbtn, tenantsbtn, paymentsbtn, expensesbtn, ralertsbtn, contractsbtn, reportsbtn, statementsbtn;
        ownersbtn = findViewById(R.id.ownersbtn);
        vendorsbtn = findViewById(R.id.vendorsbtn);
        propertybtn = findViewById(R.id.propertybtn);
        tenantsbtn = findViewById(R.id.tenantsbtn);
        paymentsbtn = findViewById(R.id.paymentsbtn);
        expensesbtn = findViewById(R.id.expensesbtn);
        ralertsbtn = findViewById(R.id.ralertsbtn);
        contractsbtn = findViewById(R.id.contractsbtn);
        reportsbtn = findViewById(R.id.reportsbtn);
        statementsbtn = findViewById(R.id.statementsbtn);


        Button btnchangepswrd=findViewById(R.id.btnchangepswrd);

        if (user != null && user.getDisplayName() != null && user.getDisplayName().equals("Hussain Shaik")) {
            btnchangepswrd.setVisibility(View.VISIBLE);
        } else {
            btnchangepswrd.setVisibility(View.GONE); // Hide the button if the condition doesn't match
        }

        btnchangepswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent67=new Intent(rentsmaster.this, RentsChangePswrdActivity.class);
                startActivity(intent67);
            }
        });


        propertybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent5 = new Intent(rentsmaster.this, PropertiesActivity.class);
                startActivity(intent5);
            }
        });
        vendorsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(rentsmaster.this, VendorsActivity.class);
                startActivity(intent2);
            }
        });

        tenantsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent = new Intent(rentsmaster.this, TenantsActivity.class);
                startActivity(intent);
            }
        });
        paymentsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent = new Intent(rentsmaster.this, CollectionsActivity.class);
                startActivity(intent);
            }
        });
        expensesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent = new Intent(rentsmaster.this, ExpensesActivity.class);
                startActivity(intent);
            }
        });
        ralertsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent = new Intent(rentsmaster.this, RentAlertsActivity.class);
                startActivity(intent);
            }
        });
        contractsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent = new Intent(rentsmaster.this,ContractsActivity.class);
                startActivity(intent);
            }
        });
        reportsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent = new Intent(rentsmaster.this,ReportsActivity.class);
                startActivity(intent);
            }
        });
        statementsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(rentsmaster.this, "Statements", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(rentsmaster.this,StatementsActivity.class);
                startActivity(intent);
            }
        });


        ownersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rentsmaster.this, OwnersActivity.class);
                startActivity(intent);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("RENTS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
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
    }


}