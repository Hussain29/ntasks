package com.example.ntasks;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ntasks.rents.MasterBiVendors;
import com.example.ntasks.rents.hrmaster;
import com.example.ntasks.rents.rentsmaster;

public class Master extends AppCompatActivity {
    Button Testify;
    private static final String CORRECT_PASSWORD = "1729";


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        ImageView thingstdi = findViewById(R.id.thingstdi);
        ImageView clientbtni = findViewById(R.id.clientbtni);
        ImageView rentsbtni = findViewById(R.id.rentsbtni);
        ImageView hrbtni = findViewById(R.id.hrbtni);
        ImageView vendorsbtni = findViewById(R.id.vendorbtni);
        ImageView postbtni = findViewById(R.id.posbtni);

        // Get the ActionBar

        int permissionState = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);
        // If the permission is not granted, request it.
        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }



/*

        Testify = findViewById(R.id.testify);
*/


        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();

        }
        /*Testify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 =new Intent(Master.this, testify.class);
                 startActivity(intent2);
            }
        });*/

     /*   clientbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(Master.this, AddClientActivity.class);
                startActivity(intent2);
            }
        });*/
        clientbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(Master.this, AddClientActivity.class);
                startActivity(intent2);
            }
        });
        rentsbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                // Create an alert dialog with an EditText for password input
                AlertDialog.Builder builder = new AlertDialog.Builder(Master.this);
                builder.setTitle("Enter Password");

                final EditText inputPassword = new EditText(Master.this);
                inputPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(inputPassword);

                builder.setPositiveButton("ENTER PASSWORD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Check if the entered password is correct
                        String enteredPassword = inputPassword.getText().toString().trim();
                        if (enteredPassword.equals(CORRECT_PASSWORD)) {
                            // Password is correct, continue with the activity
                            Toast.makeText(Master.this, "Password Correct!", Toast.LENGTH_SHORT).show();
                            // Add your activity logic here



                            Intent intent3 = new Intent(Master.this, rentsmaster.class);
                            startActivity(intent3);


                        } else {
                            // Password is incorrect, show a message or take appropriate action
                            Toast.makeText(Master.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            // Close the activity or take other actions

                        }
                    }
                });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // Handle cancellation (e.g., pressing the back button)
                        Toast.makeText(Master.this, "Dissmiss", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();



















            }
        });
        postbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Master.this, "Under Development", Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(Master.this, EmployeeDetailsActivity.class);
                startActivity(intent);*/
            }
        });

        thingstdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Master.this, MainActivity.class);
                startActivity(intent);
            }
        });


        hrbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Create an alert dialog with an EditText for password input
                AlertDialog.Builder builder = new AlertDialog.Builder(Master.this);
                builder.setTitle("Enter Password");

                final EditText inputPassword = new EditText(Master.this);
                inputPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(inputPassword);

                builder.setPositiveButton("ENTER PASSWORD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Check if the entered password is correct
                        String enteredPassword = inputPassword.getText().toString().trim();
                        if (enteredPassword.equals(CORRECT_PASSWORD)) {
                            // Password is correct, continue with the activity
                            Toast.makeText(Master.this, "Password Correct!", Toast.LENGTH_SHORT).show();
                            // Add your activity logic here



                            Intent intent4 = new Intent(Master.this, hrmaster.class);
                            startActivity(intent4);


                        } else {
                            // Password is incorrect, show a message or take appropriate action
                            Toast.makeText(Master.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            // Close the activity or take other actions

                        }
                    }
                });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // Handle cancellation (e.g., pressing the back button)
                        Toast.makeText(Master.this, "Dissmiss", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();















            }
        });vendorsbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Master.this, MasterBiVendors.class);
                startActivity(intent4);
            }
        });


    }




    private void showPasswordDialog() {

    }


}