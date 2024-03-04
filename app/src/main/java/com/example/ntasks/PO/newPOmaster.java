package com.example.ntasks.PO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.Master;
import com.example.ntasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class newPOmaster extends AppCompatActivity {

    private Button button_clipo, button_compo, button_penpo, button_addPo, button_allPo;
    private ImageView imageView_logo;
    private TextView tvPendingCount;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pomaster);

        button_clipo=findViewById(R.id.button_clipo);
        button_compo=findViewById(R.id.button_compo);
        button_penpo=findViewById(R.id.buttonpenpo);
        button_addPo=findViewById(R.id.button_addPo);
        button_allPo=findViewById(R.id.buttonShowAllPOs);
        tvPendingCount = findViewById(R.id.nopenpo);

        progressDialog = new ProgressDialog(this);

        imageView_logo=findViewById(R.id.imageView_logo);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("POs");

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int pendingCount = 0; // Initialize the count of pending POs

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PurchaseOrder purchaseOrder = snapshot.getValue(PurchaseOrder.class);
                    if (purchaseOrder.getStatus().equals("PENDING")) { // Check if the PO is pending
                        pendingCount++; // Increment the count for pending POs
                    }
                }
                // Update the PendingCount TextView with the number of pending POs
                tvPendingCount.setText("PENDING PO's- " + pendingCount);
                progressDialog.dismiss();
                if(pendingCount>0){
                    button_penpo.setBackgroundColor(getResources().getColor(R.color.pendingcolour));
                }
                else {
                    Toast.makeText(newPOmaster.this, "No pending POs", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });


        imageView_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent89=new Intent(newPOmaster.this, Master.class);
                startActivity(intent89);
            }
        });


        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("PO");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        button_clipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(newPOmaster.this, POmaster.class);
                startActivity(intent1);
            }
        });

        button_addPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(newPOmaster.this, AddPOActivity.class);
                startActivity(intent2);
            }
        });
        button_compo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(newPOmaster.this, CompletedPOActivity.class);
                startActivity(intent5);
            }
        });
        button_penpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent9 = new Intent(newPOmaster.this, PendingPOActivity.class);
                startActivity(intent9);
            }
        });
        button_allPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent91 = new Intent(newPOmaster.this, AllPOActivity.class);
                startActivity(intent91);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the back button click
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}