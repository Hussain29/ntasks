package com.example.ntasks.rents;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.ntasks.PasswordsActivity;
import com.example.ntasks.R;

public class AdminConsoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminconsole);

        // Find the buttons by their IDs
        Button usersButton = findViewById(R.id.usersButton);
        Button passwordsButton = findViewById(R.id.passwordsButton);

        // Set click listeners for the buttons
        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the UsersActivity
                Intent intent = new Intent(AdminConsoleActivity.this, UsersActivity.class);
                startActivity(intent);
            }
        });

        passwordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the PasswordsActivity
                Intent intent = new Intent(AdminConsoleActivity.this, PasswordsActivity.class);
                startActivity(intent);
            }
        });
    }
}
