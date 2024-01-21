package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AddBusinessVendorsActivity extends AppCompatActivity {
    private EditText etcid, etcname, etcsname, etcmaddress, etcweb, etccity, etccountry, etcfax, etctel, etcemail, etcpocn, etcpoce, etcaltcontact1, etcaltcontact2, etcgoods, etccrno, etcvatno, etcaddinfo, etcglink, etcbankname, etcbenname, etcacno, etcbankadd, etcibanno, etcnotes;

    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;

    TextView tvproductsss;
    private ConstraintLayout vendorImageView;
    private Uri imageUri;
    private Uri documentUri;
    Button btnAddProduct;
    private List<String> productsList;

    /*private EditText etVendorId, etVendorName, etContactPerson, etMobileNumber, etLandlineNumber,
            etAddress, etEmailAddress, etAlternateContact1, etAlternateContact2, etProducts,
            etCoordinates, etNotes;
*/
    private Button saveButton;
    private DatabaseReference vendorsRef;

    TextView textViewProducts;
    private Spinner spinDocType;

    private String shopPicURL;
    private String bCardURL;
    private String selectedDocType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business_vendors);

        etcid = findViewById(R.id.etcid); // Company ID
        etcname = findViewById(R.id.etcname);// Company Name
        etcsname = findViewById(R.id.etcsname);// Company Short Name
        etcmaddress = findViewById(R.id.etcmaddress);// Company Mailing Address
        etcweb = findViewById(R.id.etcweb);// Company Website
        etccity = findViewById(R.id.etccity);// Company City Name
        etccountry = findViewById(R.id.etccountry);// Company Country Name
        etcfax = findViewById(R.id.etcfax);// Company Fax
        etctel = findViewById(R.id.etctel);// Company Telephone
        etcemail = findViewById(R.id.etcemail);// Company Email
        etcpocn = findViewById(R.id.etcpocn);// Company Point Of Contact Name
        etcpoce = findViewById(R.id.etcpoce);// Company Point Of Contact Email
        etcaltcontact1 = findViewById(R.id.etcaltcontact1);// Company Point Of Contact Phone1
        etcaltcontact2 = findViewById(R.id.etcaltcontact2);// Company Point Of Contact Phone2

        etcgoods = findViewById(R.id.etcgoods);// Company Products
        etccrno = findViewById(R.id.etccrno);// Company CR Number
        etcvatno = findViewById(R.id.etcvatno);// Company VAT Number
        etcaddinfo = findViewById(R.id.etcaddinfo);// Additional Info (~notes)
        etcglink = findViewById(R.id.etcglink);// Google Location Link

        etcbankname = findViewById(R.id.etcbankname);// Bank Name
        etcbenname = findViewById(R.id.etcbenname);// Beneficary Name
        etcacno = findViewById(R.id.etcacno);// Account Number
        etcbankadd = findViewById(R.id.etcbankadd);//Bank Address
        etcibanno = findViewById(R.id.etcibanno);// IBAN Number

        tvproductsss = findViewById(R.id.tvproducts);

        etcnotes = findViewById(R.id.etcnotes);// Notes


        saveButton = findViewById(R.id.btnbadd);

        vendorImageView = findViewById(R.id.ivaddbvendor); //Add Image of Shop Pic

        btnAddProduct = findViewById(R.id.btnaddproducts);
        textViewProducts = findViewById(R.id.tvproducts);

        productsList = new ArrayList<>();
        // Add this line to your existing declarations

        vendorsRef = FirebaseDatabase.getInstance().getReference().child("BusinessVendors");




        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ADD BUSINESS VENDORS");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.greennnn);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


        // Inside your onCreate method or wherever you initialize your views
        LinearLayout llAttach = findViewById(R.id.llattach); // Attach Buiz Card
        llAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_DOC);
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        vendorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_IMG);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSaveVendorDetails();
            }
        });

        // Setup document type spinner
    }

    private void showFileChooser(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(requestCode == PICK_FILE_REQUEST_IMG ? "image/*" : "*/*");
        startActivityForResult(intent, requestCode);
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

    private void updateProductsTextView() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String product : productsList) {
            stringBuilder.append("(").append(product).append("), ");
        }

        // Remove the trailing comma and space
        String productsText = stringBuilder.toString().replaceAll(", $", "");

        // Set the updated products text to the TextView
        textViewProducts.setText(productsText);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST_IMG) {
                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    uploadImageToFirebaseStorage(selectedImageUri);
                }
            } else if (requestCode == PICK_FILE_REQUEST_DOC) {
                if (data != null && data.getData() != null) {
                    Uri selectedDocUri = data.getData();
                    uploadDocumentToFirebaseStorage(selectedDocUri);
                }
            }
        }
    }

    /*private void setupSpinnerForDocType() {
        String[] docTypes = getResources().getStringArray(R.array.DocIDtypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, docTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        docTypeSpinner.setAdapter(adapter);
        docTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedDocType = docTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }*/

    private void uploadDocumentToFirebaseStorage(Uri docUri) {
        String fileName = "document:" + System.currentTimeMillis();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("uploads").child(fileName);

        storageRef.putFile(docUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        bCardURL = uri.toString();
                        Log.d("AddBusinessVendors", "Document URL: " + bCardURL);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddBusinessVendorsActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddBusinessVendors", "Document upload failed", e);
                });
    }

    private void uploadImageToFirebaseStorage(Uri imageUri) {
        String fileName = "image:" + System.currentTimeMillis();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("uploads").child(fileName);

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        shopPicURL = uri.toString();
                        Log.d("AddBusinessVendors", "Image URL: " + shopPicURL);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddBusinessVendorsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddBusinessVendors", "Image upload failed", e);
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

        String companyProducts = tvproductsss.getText().toString().trim();
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

        if (TextUtils.isEmpty(companyId) || TextUtils.isEmpty(companyName) || TextUtils.isEmpty(companyShortName) ||
                TextUtils.isEmpty(companyMailingAddress) || TextUtils.isEmpty(companyWebsite) || TextUtils.isEmpty(companyCity) ||
                TextUtils.isEmpty(companyCountry) || TextUtils.isEmpty(companyFax) || TextUtils.isEmpty(companyTelephone) ||
                TextUtils.isEmpty(companyEmail) || TextUtils.isEmpty(companyPocName) || TextUtils.isEmpty(companyPocEmail) ||
                TextUtils.isEmpty(companyAltContact1) || TextUtils.isEmpty(companyAltContact2) ||
                TextUtils.isEmpty(companyCrNumber) || TextUtils.isEmpty(companyVatNumber) || TextUtils.isEmpty(additionalInfo) ||
                TextUtils.isEmpty(googleLocationLink) || TextUtils.isEmpty(bankName) || TextUtils.isEmpty(beneficiaryName) ||
                TextUtils.isEmpty(accountNumber) || TextUtils.isEmpty(bankAddress) || TextUtils.isEmpty(ibanNumber) || TextUtils.isEmpty(notes)) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated. Please log in and try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();

        BusinessVendor vendor = new BusinessVendor(companyId, companyName, companyShortName, companyMailingAddress,
                companyWebsite, companyCity, companyCountry, companyFax, companyTelephone, companyEmail,
                companyPocName, companyPocEmail, companyAltContact1, companyAltContact2, companyProducts,
                companyCrNumber, companyVatNumber, additionalInfo, googleLocationLink, bankName,
                beneficiaryName, accountNumber, bankAddress, ibanNumber, notes, shopPicURL, bCardURL);


        saveVendorToDatabase(vendor);
    }

// Rest of the methods remain the same


    private void saveVendorToDatabase(BusinessVendor vendor) {
        vendorsRef.push().setValue(vendor, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(AddBusinessVendorsActivity.this, "Vendor details saved successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddBusinessVendorsActivity.this, "Error saving vendor details. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddBusinessVendors", "Error saving vendor details", databaseError.toException());
                }
            }
        });
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

}
