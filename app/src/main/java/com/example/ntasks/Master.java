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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ntasks.PO.POmaster;
import com.example.ntasks.rents.AdminConsoleActivity;
import com.example.ntasks.rents.MasterBiVendors;
import com.example.ntasks.rents.hrmaster;
import com.example.ntasks.rents.rentsmaster;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Master extends AppCompatActivity {
    Button Testify;
    private DatabaseReference passwordRef;
    private static final String PASSWORDS_PATH = "Psswrds";
    private static final String HR_PASSWORD_PATH = "HrPwd";
    private static final String RENTS_PASSWORD_PATH = "RentsPwd";

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        passwordRef = FirebaseDatabase.getInstance().getReference().child(PASSWORDS_PATH);

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
                showPasswordDialog(RENTS_PASSWORD_PATH);
            }
        });

        postbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent22 = new Intent(Master.this, POmaster.class);
                startActivity(intent22);
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
                showPasswordDialog(HR_PASSWORD_PATH);
            }
        });

        vendorsbtni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Master.this, MasterBiVendors.class);
                startActivity(intent4);
            }
        });
    }

    private void showPasswordDialog(final String passwordPath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Master.this);
        builder.setTitle("Enter Password");

        final EditText inputPassword = new EditText(Master.this);
        inputPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(inputPassword);

        builder.setPositiveButton("ENTER PASSWORD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String enteredPassword = inputPassword.getText().toString().trim();
                DatabaseReference featurePasswordRef = passwordRef.child(passwordPath);
                featurePasswordRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String password = dataSnapshot.getValue(String.class);
                        if (password != null && password.equals(enteredPassword)) {
                            Toast.makeText(Master.this, "Password Correct!", Toast.LENGTH_SHORT).show();
                            startFeatureActivity(passwordPath);
                        } else {
                            Toast.makeText(Master.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Master.this, "Failed to retrieve password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(Master.this, "Dismiss", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void startFeatureActivity(String feature) {
        Intent intent;
        switch (feature) {
            case HR_PASSWORD_PATH:
                intent = new Intent(Master.this, hrmaster.class);
                break;
            case RENTS_PASSWORD_PATH:
                intent = new Intent(Master.this, rentsmaster.class);
                break;
            default:
                return;
        }
        startActivity(intent);
    }
}
