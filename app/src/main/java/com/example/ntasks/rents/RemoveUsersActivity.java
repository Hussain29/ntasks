package com.example.ntasks.rents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ntasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RemoveUsersActivity extends AppCompatActivity {

    private DatabaseReference usersRef, passwordRef;
    private Spinner userSpinner;
    private Button removeUserButton;

    private static final String PASSWORD_PATH = "Psswrds/RemoveUsersPwd"; // Change this to the correct path


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_users);

        usersRef = FirebaseDatabase.getInstance().getReference().child("Registered Users");
        passwordRef = FirebaseDatabase.getInstance().getReference().child(PASSWORD_PATH);
        userSpinner = findViewById(R.id.userSpinner);
        removeUserButton = findViewById(R.id.removeUserButton);

        populateUserSpinner();

        removeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("REMOVE USERS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the home button click
                onBackPressed();
                // Optional: Close the current activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void populateUserSpinner() {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> userList = new ArrayList<>();
                userList.add("Select User");
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String name = userSnapshot.child("name").getValue(String.class);
                    if (name != null) {
                        userList.add(name);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(RemoveUsersActivity.this,
                        android.R.layout.simple_spinner_item, userList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                userSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RemoveUsersActivity.this, "Failed to retrieve users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showConfirmationDialog() {
        final String selectedUserName = userSpinner.getSelectedItem().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to remove " + selectedUserName + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showPasswordDialog(selectedUserName);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, just close the dialog
            }
        });
        builder.show();
    }

    private void showPasswordDialog(final String selectedUserName) {
        final EditText inputPassword = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Password");
        builder.setView(inputPassword);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredPassword = inputPassword.getText().toString().trim();
                passwordRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String storedPassword = dataSnapshot.getValue(String.class);
                        if (storedPassword != null && storedPassword.equals(enteredPassword)) {
                            deleteUser(selectedUserName);
                        } else {
                            Toast.makeText(RemoveUsersActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(RemoveUsersActivity.this, "Failed to retrieve password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deleteUser(final String userName) {
        usersRef.orderByChild("name").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    userSnapshot.getRef().removeValue();
                }
                Toast.makeText(RemoveUsersActivity.this, "User removed successfully", Toast.LENGTH_SHORT).show();
                populateUserSpinner();
                finish();// Refresh the spinner after removing the user
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RemoveUsersActivity.this, "Failed to remove user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
