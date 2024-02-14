package com.example.ntasks.rents;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ntasks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EditBiVendorsActivity extends AppCompatActivity {

    private EditText etcid, etcname, etcsname, etcmaddress, etcweb, etccity, etccountry, etcfax, etctel, etcemail, etcpocn, etcpoce, etcaltcontact1, etcaltcontact2, etcgoods, etccrno, etcvatno, etcaddinfo, etcglink, etcbankname, etcbenname, etcacno, etcbankadd, etcibanno, etcnotes;
    private Button btnSave,btnAddProduct;

    private DatabaseReference vendorsRef;
    private BusinessVendor currentVendor;
    private List<String> productsList;
    private TextView textViewProducts;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business_vendors);
        productsList = new ArrayList<>();

        vendorsRef = FirebaseDatabase.getInstance().getReference().child("BusinessVendors");

        currentVendor = getIntent().getParcelableExtra("business_vendor");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Edit Vendor Details");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        etcid = findViewById(R.id.etcid);
        etcname = findViewById(R.id.etcname);
        textViewProducts=findViewById(R.id.tvproducts);
        btnAddProduct=findViewById(R.id.btnaddproducts);
        etcsname = findViewById(R.id.etcsname);
        etcmaddress = findViewById(R.id.etcmaddress);
        etcweb = findViewById(R.id.etcweb);
        etccity = findViewById(R.id.etccity);
        etccountry = findViewById(R.id.etccountry);
        etcfax = findViewById(R.id.etcfax);
        etctel = findViewById(R.id.etctel);
        etcemail = findViewById(R.id.etcemail);
        etcpocn = findViewById(R.id.etcpocn);
        etcpoce = findViewById(R.id.etcpoce);
        etcaltcontact1 = findViewById(R.id.etcaltcontact1);
        etcaltcontact2 = findViewById(R.id.etcaltcontact2);
        etcgoods = findViewById(R.id.etcgoods);
        etccrno = findViewById(R.id.etccrno);
        etcvatno = findViewById(R.id.etcvatno);
        etcaddinfo = findViewById(R.id.etcaddinfo);
        etcglink = findViewById(R.id.etcglink);
        etcbankname = findViewById(R.id.etcbankname);
        etcbenname = findViewById(R.id.etcbenname);
        etcacno = findViewById(R.id.etcacno);
        etcbankadd = findViewById(R.id.etcbankadd);
        etcibanno = findViewById(R.id.etcibanno);
        etcnotes = findViewById(R.id.etcnotes);
        btnSave = findViewById(R.id.btnbadd);

        if (currentVendor != null) {
            etcid.setText(currentVendor.getCompanyId());
            etcname.setText(currentVendor.getCompanyName());
            etcsname.setText(currentVendor.getCompanyShortName());
            etcmaddress.setText(currentVendor.getCompanyMailingAddress());
            etcweb.setText(currentVendor.getCompanyWebsite());
            etccity.setText(currentVendor.getCompanyCity());
            etccountry.setText(currentVendor.getCompanyCountry());
            etcfax.setText(currentVendor.getCompanyFax());
            etctel.setText(currentVendor.getCompanyTelephone());
            etcemail.setText(currentVendor.getCompanyEmail());
            etcpocn.setText(currentVendor.getCompanyPocName());
            etcpoce.setText(currentVendor.getCompanyPocEmail());
            etcaltcontact1.setText(currentVendor.getCompanyAltContact1());
            etcaltcontact2.setText(currentVendor.getCompanyAltContact2());
            //textViewProducts.setText(currentVendor.getCompanyProducts());
            textViewProducts.append("\n" + currentVendor.getCompanyProducts());

           //// textViewProducts.append(currentVendor.getCompanyProducts());
            etccrno.setText(currentVendor.getCompanyCrNumber());
            etcvatno.setText(currentVendor.getCompanyVatNumber());
            etcaddinfo.setText(currentVendor.getAdditionalInfo());
            etcglink.setText(currentVendor.getGoogleLocationLink());
            etcbankname.setText(currentVendor.getBankName());
            etcbenname.setText(currentVendor.getBeneficiaryName());
            etcacno.setText(currentVendor.getAccountNumber());
            etcbankadd.setText(currentVendor.getBankAddress());
            etcibanno.setText(currentVendor.getIbanNumber());
            etcnotes.setText(currentVendor.getNotes());
        }

        btnSave.setOnClickListener(view -> validateAndSaveVendorDetails());

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });


    }

    private void validateAndSaveVendorDetails() {
        String companyId = etcid.getText().toString().trim();
        String companyName = etcname.getText().toString().trim();
        String companyShortName = etcsname.getText().toString().trim();
        String companyMailingAddress = etcmaddress.getText().toString().trim();
        String companyWebsite = etcweb.getText().toString().trim();
        String companyCity = etccity.getText().toString().trim();
        String companyCountry = etccountry.getText().toString().trim();
        String companyFax = etcfax.getText().toString().trim();
        String companyTelephone = etctel.getText().toString().trim();
        String companyEmail = etcemail.getText().toString().trim();
        String companyPocName = etcpocn.getText().toString().trim();
        String companyPocEmail = etcpoce.getText().toString().trim();
        String companyAltContact1 = etcaltcontact1.getText().toString().trim();
        String companyAltContact2 = etcaltcontact2.getText().toString().trim();
        String companyProducts = textViewProducts.getText().toString().trim();
        String companyCrNumber = etccrno.getText().toString().trim();
        String companyVatNumber = etcvatno.getText().toString().trim();
        String additionalInfo = etcaddinfo.getText().toString().trim();
        String googleLocationLink = etcglink.getText().toString().trim();
        String bankName = etcbankname.getText().toString().trim();
        String beneficiaryName = etcbenname.getText().toString().trim();
        String accountNumber = etcacno.getText().toString().trim();
        String bankAddress = etcbankadd.getText().toString().trim();
        String ibanNumber = etcibanno.getText().toString().trim();
        String notes = etcnotes.getText().toString().trim();

        if (currentVendor != null) {
            currentVendor.setCompanyId(companyId);
            currentVendor.setCompanyName(companyName);
            currentVendor.setCompanyShortName(companyShortName);
            currentVendor.setCompanyMailingAddress(companyMailingAddress);
            currentVendor.setCompanyWebsite(companyWebsite);
            currentVendor.setCompanyCity(companyCity);
            currentVendor.setCompanyCountry(companyCountry);
            currentVendor.setCompanyFax(companyFax);
            currentVendor.setCompanyTelephone(companyTelephone);
            currentVendor.setCompanyEmail(companyEmail);
            currentVendor.setCompanyPocName(companyPocName);
            currentVendor.setCompanyPocEmail(companyPocEmail);
            currentVendor.setCompanyAltContact1(companyAltContact1);
            currentVendor.setCompanyAltContact2(companyAltContact2);
            currentVendor.setCompanyProducts(companyProducts);
            currentVendor.setCompanyCrNumber(companyCrNumber);
            currentVendor.setCompanyVatNumber(companyVatNumber);
            currentVendor.setAdditionalInfo(additionalInfo);
            currentVendor.setGoogleLocationLink(googleLocationLink);
            currentVendor.setBankName(bankName);
            currentVendor.setBeneficiaryName(beneficiaryName);
            currentVendor.setAccountNumber(accountNumber);
            currentVendor.setBankAddress(bankAddress);
            currentVendor.setIbanNumber(ibanNumber);
            currentVendor.setNotes(notes);

            vendorsRef.child(currentVendor.getCompanyId()).setValue(currentVendor)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditBiVendorsActivity.this, "Vendor details updated successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EditBiVendorsActivity.this, "Failed to update vendor details. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void deleteVendorFromDatabase() {
        if (currentVendor != null) {
            DatabaseReference vendorRef = FirebaseDatabase.getInstance().getReference().child("BusinessVendors").child(currentVendor.getCompanyId());

            vendorRef.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditBiVendorsActivity.this, "Vendor deleted successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Finish the activity after successful deletion
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditBiVendorsActivity.this, "Failed to delete vendor: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(EditBiVendorsActivity.this, "No vendor data found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addProduct() {
        String product = etcgoods.getText().toString().trim();

        if (!product.isEmpty()) {
            // Add product to the list
            productsList.add(product);

            // Update the TextView to display the products
            updateProductsTextView();

            // Clear the EditText for the next input
            etcgoods.getText().clear();
        }
    }
   /* private void updateProductsTextView() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String product : productsList) {
            stringBuilder.append("(").append(product).append("), ");
        }

        // Remove the trailing comma and space
        String productsText = stringBuilder.toString().replaceAll(", $", "");

        // Set the updated products text to the TextView
        textViewProducts.setText(productsText);


    }*/
/*
    private void updateProductsTextView() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String product : productsList) {
            stringBuilder.append("(").append(product).append("), ");
        }

        // Remove the trailing comma and space
        String productsText = stringBuilder.toString().replaceAll(", $", "");

        // Append the updated products text to the TextView
        textViewProducts.append(productsText);
    }*/
    private void updateProductsTextView() {
        if (!productsList.isEmpty()) {
            String newProduct = productsList.get(productsList.size() - 1); // Get the latest product

            // Append the new product to the TextView
            textViewProducts.append(",(" + newProduct + ") ");
        }
    }

}
