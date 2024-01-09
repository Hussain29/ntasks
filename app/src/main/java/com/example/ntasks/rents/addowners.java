package com.example.ntasks.rents;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.ntasks.AddTaskActivity;
import com.example.ntasks.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class addowners extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 2; // You can use any integer value
    private static final int PICK_FILE_REQUEST_IMG = 4; // You can use any integer value
    private Button add1,add2;
    CardView cv3;
    LinearLayout ll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addowners);


        CardView cvaddattach = findViewById(R.id.cvaddattach);
        cv3 = findViewById(R.id.cv3);
        ll2=findViewById(R.id.ll2);
        add1=findViewById(R.id.add1);
        add2=findViewById(R.id.add2);


        ImageView imaddperson=findViewById(R.id.imaddperson);


        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll2.setVisibility(View.VISIBLE);
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv3.setVisibility(View.VISIBLE);
            }
        });







        imaddperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(addowners.this, "Select Your Image", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_IMG);
            }
        });

        cvaddattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(addowners.this, "Select any type of file", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST);
            }
        });


        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD OWNERS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


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