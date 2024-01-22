package com.example.ntasks.rents;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ntasks.R;

public class ExpensesActivity extends AppCompatActivity {
    EditText editTextpart,editTextexpamt;
    Button addexpns;
    Spinner flatspinner;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
    }
}