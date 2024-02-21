package com.example.ntasks.PO;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ntasks.AddTaskActivity;
import com.example.ntasks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class AddPOActivity extends AppCompatActivity {

    private EditText etPOsubject, etPOremarks;
    private Spinner spinnerAssignedUser1, spinnerAssignedUser2, spinnerAssignedUser3, spinnerClients;
    private Button choseDatePickerButton, submitPOButton;
    private TextView selectedDateTextView;
    private LinearLayout llAttach;
    private DatabaseReference poRef;
    private String PODocUrl;
    private ProgressDialog progressDialog;
    private DatabaseReference clientsRef;
    private DatabaseReference userRef;

    private static final int PICK_FILE_REQUEST_DOC = 77;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_po);

        userRef = FirebaseDatabase.getInstance().getReference().child("Registered Users");
        clientsRef = FirebaseDatabase.getInstance().getReference().child("Clients");

        etPOsubject = findViewById(R.id.etPOsubject);
        etPOremarks = findViewById(R.id.etPOremarks);

        spinnerAssignedUser1 = findViewById(R.id.spinnerAssignedUser1);
        spinnerAssignedUser2 = findViewById(R.id.spinnerAssignedUser2);
        spinnerAssignedUser3 = findViewById(R.id.spinnerAssignedUser3);
        spinnerClients = findViewById(R.id.spinnerClients);

        choseDatePickerButton = findViewById(R.id.choseDatePickerButton);
        submitPOButton = findViewById(R.id.submitPOButton);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        llAttach = findViewById(R.id.POllattach);

        setupSpinners();
        spinnerAssignedUser1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    spinnerAssignedUser2.setVisibility(View.VISIBLE);
                    spinnerAssignedUser2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            if (position > 0) {
                                spinnerAssignedUser3.setVisibility(View.VISIBLE);
                            } else {
                                spinnerAssignedUser3.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                        }
                    });
                } else {
                    spinnerAssignedUser2.setVisibility(View.GONE);
                    spinnerAssignedUser3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        choseDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        llAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddPOActivity.this, "Select Your Document", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST_DOC);
            }
        });

        poRef = FirebaseDatabase.getInstance().getReference().child("POs");

        submitPOButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPO();
            }
        });
    }

    private void setupSpinners() {
        setupSpinnerWithUserNames();
        setupSpinnerWithClients();
    }

    private void setupSpinnerWithUserNames() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> userNames = new ArrayList<>();

                userNames.add("Select User");

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    // Assuming each user node has a 'name' field
                    String userName = userSnapshot.child("name").getValue(String.class);
                    if (userName != null) {
                        userNames.add(userName);
                    }
                }

                // Sort the userNames list alphabetically
                Collections.sort(userNames.subList(1, userNames.size()));

                // Update the spinner adapter with user names
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddPOActivity.this, android.R.layout.simple_spinner_item, userNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAssignedUser1.setAdapter(adapter);
                spinnerAssignedUser2.setAdapter(adapter);
                spinnerAssignedUser3.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void setupSpinnerWithClients() {
        clientsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> clientNames = new ArrayList<>();
                clientNames.add("Select Client");

                for (DataSnapshot clientSnapshot : snapshot.getChildren()) {
                    String clientName = clientSnapshot.getValue(String.class);
                    if (clientName != null) {
                        clientNames.add(clientName);
                    }
                }

                Collections.sort(clientNames.subList(1, clientNames.size()));

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddPOActivity.this, android.R.layout.simple_spinner_item, clientNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerClients.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        // Format the selected date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String formattedDate = dateFormat.format(selectedDate.getTime());
                        selectedDateTextView.setText(formattedDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_FILE_REQUEST_DOC && data != null && data.getData() != null) {
            Uri selectedDocUri = data.getData();
            uploadDocumentToFirebaseStorage(selectedDocUri);
        }
    }

    private void uploadDocumentToFirebaseStorage(Uri docUri) {
        String fileName = "document:" + System.currentTimeMillis();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("uploads").child(fileName);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        storageRef.putFile(docUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        PODocUrl = uri.toString();
                        Log.d("AddPOActivity", "Document URL: " + PODocUrl);
                        Toast.makeText(AddPOActivity.this, "Document upload successful.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddPOActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                });
    }

    private String getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getDisplayName(); // Assuming you have set the display name during authentication
        } else {
            // Handle the case when the user is not authenticated
            return "Unknown User";
        }
    }

    private void submitPO() {
        String subject = etPOsubject.getText().toString().trim();
        String remarks = etPOremarks.getText().toString().trim();
        String assigner = getCurrentUser();
        String client = spinnerClients.getSelectedItem().toString();
        String selectedDate = selectedDateTextView.getText().toString().trim();

        if (TextUtils.isEmpty(subject) || TextUtils.isEmpty(remarks) || TextUtils.isEmpty(client) || selectedDate.equals("Select Date: ")) {
            Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        String assignedUser1 = spinnerAssignedUser1.getSelectedItem().toString();
        String assignedUser2 = spinnerAssignedUser2.getSelectedItem().toString();
        String assignedUser3 = spinnerAssignedUser3.getSelectedItem().toString();

        // Push PO data for each assigned user separately
        if (!assignedUser1.equals("Select User")) {
            pushPOData(subject, remarks, client, selectedDate, assignedUser1, assigner);
        }

        if (!assignedUser2.equals("Select User")) {
            pushPOData(subject, remarks, client, selectedDate, assignedUser2, assigner);
        }

        if (!assignedUser3.equals("Select User")) {
            pushPOData(subject, remarks, client, selectedDate, assignedUser3, assigner);
        }
    }

    private void pushPOData(String subject, String remarks, String client, String selectedDate, String assignedUser, String assigner) {
        String poId = poRef.push().getKey();
        PurchaseOrder po = new PurchaseOrder(poId, subject, remarks, client, selectedDate, assignedUser, assigner, PODocUrl);
        poRef.child(poId).setValue(po)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddPOActivity.this, "Purchase Order submitted successfully for " + assignedUser, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddPOActivity.this, "Failed to submit Purchase Order for " + assignedUser, Toast.LENGTH_SHORT).show();
                });
        finish();
    }

}
