package com.example.ntasks.rents;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.ntasks.R;

public class UsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        // Find the buttons by their IDs
        Button addUserButton = findViewById(R.id.addUserButton);
        Button removeUserButton = findViewById(R.id.removeUserButton);

        // Set click listeners for the buttons
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the AddUserActivity
                /*Intent intent = new Intent(UsersActivity.this, AddUserActivity.class);
                startActivity(intent);*/
            }
        });

        removeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the RemoveUserActivity
                Intent intent = new Intent(UsersActivity.this, RemoveUsersActivity.class);
                startActivity(intent);
            }
        });
    }
}
