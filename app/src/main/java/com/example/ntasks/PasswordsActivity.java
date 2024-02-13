package com.example.ntasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PasswordsActivity extends AppCompatActivity {

    private Spinner passwordSpinner;
    private EditText currentPasswordEditText, newPasswordEditText, reEnterNewPasswordEditText;
    private Button changePasswordButton;
    private DatabaseReference passwordsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwords);

        passwordSpinner = findViewById(R.id.passwordSpinner);
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        reEnterNewPasswordEditText = findViewById(R.id.reEnterNewPasswordEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        passwordsRef = FirebaseDatabase.getInstance().getReference().child("Psswrds");

        populatePasswordSpinner();

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void populatePasswordSpinner() {
        passwordsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> passwordList = new ArrayList<>();
                passwordList.add("Select Password");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    passwordList.add(snapshot.getKey());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(PasswordsActivity.this,
                        android.R.layout.simple_spinner_item, passwordList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                passwordSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PasswordsActivity.this, "Failed to retrieve passwords", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changePassword() {
        final String selectedPassword = passwordSpinner.getSelectedItem().toString();
        final String currentPassword = currentPasswordEditText.getText().toString().trim();
        final String newPassword = newPasswordEditText.getText().toString().trim();
        final String reEnteredNewPassword = reEnterNewPasswordEditText.getText().toString().trim();

        // Check if new password and confirm password match
        if (!newPassword.equals(reEnteredNewPassword)) {
            Toast.makeText(this, "New passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the current password matches the selected password
        passwordsRef.child(selectedPassword).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String storedPassword = dataSnapshot.getValue(String.class);
                if (storedPassword != null && storedPassword.equals(currentPassword)) {
                    // Current password is correct, update the password in the database
                    passwordsRef.child(selectedPassword).setValue(newPassword);
                    Toast.makeText(PasswordsActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    // Optionally, you may close the activity or perform any other actions here
                } else {
                    Toast.makeText(PasswordsActivity.this, "Incorrect current password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PasswordsActivity.this, "Failed to retrieve password", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
