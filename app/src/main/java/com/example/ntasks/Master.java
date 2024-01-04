package com.example.ntasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Master extends AppCompatActivity {
    Button Testify;

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
                Intent intent4 = new Intent(Master.this, hrmaster.class);
                startActivity(intent4);
            }
        });vendorsbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Master.this, vendorsmaster.class);
                startActivity(intent4);
            }
        });


    }
}