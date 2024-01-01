package com.example.ntasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Master extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        Button thingstd=findViewById(R.id.thingstd);
        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();

        }


        thingstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Master.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}