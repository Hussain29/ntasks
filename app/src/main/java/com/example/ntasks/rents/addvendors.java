package com.example.ntasks.rents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ntasks.AddTaskActivity;
import com.example.ntasks.R;

public class addvendors extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 2; // You can use any integer value
    private Uri fileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvendors);
        CardView cvaddattach=findViewById(R.id.cvaddattach);






        cvaddattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(addvendors.this, "Select any type of file", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST);
            }
        });










        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD VENDORS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get the selected file URI
            fileUri = data.getData();
            Toast.makeText(this, "File selected: " + fileUri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
        }
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