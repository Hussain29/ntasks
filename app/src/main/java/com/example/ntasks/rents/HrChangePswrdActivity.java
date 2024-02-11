package com.example.ntasks.rents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ntasks.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HrChangePswrdActivity extends AppCompatActivity {

    EditText etcnewpswrd, etnewpswrd;
    Button setpswrd;
    DatabaseReference hrPwdRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_changepswrd);

        etcnewpswrd = findViewById(R.id.etcnewpswrd);
        etnewpswrd = findViewById(R.id.etnewpswrd);
        setpswrd = findViewById(R.id.setpswrd);

        // Get reference to the HrPwd node in the database
        hrPwdRef = FirebaseDatabase.getInstance().getReference("Psswrds").child("HrPwd");

        setpswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        String newPswrd = etnewpswrd.getText().toString().trim();
        String confirmNewPswrd = etcnewpswrd.getText().toString().trim();

        // Check if new password and confirm password match
        if (newPswrd.equals(confirmNewPswrd)) {
            // Update the password in the database
            hrPwdRef.setValue(newPswrd);
            Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity after password change
        } else {
            // Passwords don't match, display error message
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
    }
}
