package com.example.ntasks;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Master extends AppCompatActivity {
Button Testify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        Button thingstd=findViewById(R.id.thingstd);

        // Get the ActionBar


        Testify=findViewById(R.id.testify);




        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();

        }
Testify.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ////Intent intent2 =new Intent(Master.this, testify.class);
      ///  startActivity(intent2);
    }
});

        thingstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Master.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}