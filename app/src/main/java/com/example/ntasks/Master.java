package com.example.ntasks;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ntasks.rents.MasterBiVendors;
import com.example.ntasks.rents.rentsmaster;

public class Master extends AppCompatActivity {
    Button Testify;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        ImageView thingstdi = findViewById(R.id.thingstdi);
        ImageView clientbtni = findViewById(R.id.clientbtni);
        ImageView rentsbtni = findViewById(R.id.rentsbtni);
        ImageView hrbtni = findViewById(R.id.hrbtni);
        ImageView vendorsbtni = findViewById(R.id.vendorbtni);
        // Get the ActionBar

        int permissionState = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);
        // If the permission is not granted, request it.
        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }



/*

        Testify = findViewById(R.id.testify);
*/


        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();

        }
        /*Testify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 =new Intent(Master.this, testify.class);
                 startActivity(intent2);
            }
        });*/

     /*   clientbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(Master.this, AddClientActivity.class);
                startActivity(intent2);
            }
        });*/
        clientbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(Master.this, AddClientActivity.class);
                startActivity(intent2);
            }
        });
        rentsbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(Master.this, rentsmaster.class);
                startActivity(intent3);
            }
        });

        thingstdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Master.this, MainActivity.class);
                startActivity(intent);
            }
        });   hrbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent4 = new Intent(Master.this, BusinessVendorDetailsActivity.class);
                startActivity(intent4);*/
            }
        });vendorsbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Master.this, MasterBiVendors.class);
                startActivity(intent4);
            }
        });


    }
}