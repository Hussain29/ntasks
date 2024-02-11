package com.example.ntasks.rents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.ntasks.R;

public class HrChangepswrd extends AppCompatActivity {
    EditText etcnewpswrd,    etnewpswrd,etcurpswrd;
    Button setpswrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_changepswrd);

        etcnewpswrd=findViewById(R.id.etcnewpswrd);
        etnewpswrd=findViewById(R.id.etnewpswrd);
        etcurpswrd=findViewById(R.id.etcurpswrd);

        setpswrd=findViewById(R.id.setpswrd);




    }
}