package com.example.ntasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddClientActivity extends AppCompatActivity {

    private EditText editTextClientName;
    private Button buttonAddClient;
    private GridView gridViewClients;

    private DatabaseReference clientsRef;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        // Show loading screen
        progressDialog.show();
        editTextClientName = findViewById(R.id.editTextClientName);
        buttonAddClient = findViewById(R.id.buttonAddClient);
        gridViewClients = findViewById(R.id.gridViewClients);

        // Initialize Firebase Realtime Database reference
        clientsRef = FirebaseDatabase.getInstance().getReference().child("Clients");

        buttonAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClientToDatabase();
            }
        });

        // Load existing clients and update GridView
        loadClientsAndUpdateGridView();
    }

    private void addClientToDatabase() {
        // Get the client name from the EditText
        String clientName = editTextClientName.getText().toString().trim();

        if (!TextUtils.isEmpty(clientName)) {
            // Add clientName to the database under the "Clients" node
            String clientId = clientsRef.push().getKey();
            clientsRef.child(clientId).setValue(clientName);

            // Show a success message
            Toast.makeText(this, "New Client Added!", Toast.LENGTH_SHORT).show();

            // Clear the EditText
            editTextClientName.setText("");

            // Update GridView with the new client
            loadClientsAndUpdateGridView();
        } else {
            Toast.makeText(this, "Please Enter A Client Name", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadClientsAndUpdateGridView() {
        // Read client names from the database and update the GridView
        clientsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> clientNames = new ArrayList<>();
                for (DataSnapshot clientSnapshot : dataSnapshot.getChildren()) {
                    String clientName = clientSnapshot.getValue(String.class);
                    if (clientName != null) {
                        clientNames.add(clientName);
                    }
                }

                // Update the GridView with client names
                updateGridView(clientNames);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(AddClientActivity.this, "Failed to load clients", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateGridView(ArrayList<String> clientNames) {
        // Use the custom adapter for GridView
        CustomGridAdapter customGridAdapter = new CustomGridAdapter(this, clientNames);
        gridViewClients.setAdapter(customGridAdapter);

        // Set item click listener for GridView
        gridViewClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Handle the click on a client name
                String selectedClient = clientNames.get(position);

                // Start TaskViewListActivity with the selected client's tasks
                Intent intent = new Intent(AddClientActivity.this, ClientTasksActivity.class);
                intent.putExtra("selectedClient", selectedClient);
                startActivity(intent);
            }
        });
    }
}


/*
package com.example.ntasks;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddClientActivity extends AppCompatActivity {

    private EditText editTextClientName;
    private Button buttonAddClient;
    private GridView gridViewClients;

    private DatabaseReference clientsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        editTextClientName = findViewById(R.id.editTextClientName);
        buttonAddClient = findViewById(R.id.buttonAddClient);
        gridViewClients = findViewById(R.id.gridViewClients);

        // Initialize Firebase Realtime Database reference
        clientsRef = FirebaseDatabase.getInstance().getReference().child("Clients");

        buttonAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClientToDatabase();
            }
        });

        // Load existing clients and update GridView
        loadClientsAndUpdateGridView();
    }

    private void addClientToDatabase() {
        // Get the client name from the EditText
        String clientName = editTextClientName.getText().toString().trim();

        if (!TextUtils.isEmpty(clientName)) {
            // Add clientName to the database under the "Clients" node
            String clientId = clientsRef.push().getKey();
            clientsRef.child(clientId).setValue(clientName);

            // Show a success message
            Toast.makeText(this, "New Client Added!", Toast.LENGTH_SHORT).show();

            // Clear the EditText
            editTextClientName.setText("");

            // Update GridView with the new client
            loadClientsAndUpdateGridView();
        } else {
            Toast.makeText(this, "Please Enter A Client Name", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadClientsAndUpdateGridView() {
        // Read client names from the database and update the GridView
        clientsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> clientNames = new ArrayList<>();
                for (DataSnapshot clientSnapshot : dataSnapshot.getChildren()) {
                    String clientName = clientSnapshot.getValue(String.class);
                    if (clientName != null) {
                        clientNames.add(clientName);
                    }
                }

                // Update the GridView with client names
                updateGridView(clientNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(AddClientActivity.this, "Failed to load clients", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateGridView(List<String> clientNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clientNames);
        gridViewClients.setAdapter(adapter);

        // Set item click listener for GridView
        gridViewClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Handle the click on a client name
                String selectedClient = clientNames.get(position);

                // Start TaskViewListActivity with the selected client's tasks
                Intent intent = new Intent(AddClientActivity.this, ClientTasksActivity.class);
                intent.putExtra("selectedClient", selectedClient);
                startActivity(intent);
            }
        });
    }
}
*/
