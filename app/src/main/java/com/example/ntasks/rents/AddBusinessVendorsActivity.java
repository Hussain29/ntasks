package com.example.ntasks.rents;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;

    private ConstraintLayout vendorImageView;
    private Uri imageUri;
    private Uri documentUri;
    Button btnAddProduct;
    private List<String> productsList;

    private EditText etVendorId, etVendorName, etContactPerson, etMobileNumber, etLandlineNumber,
            etAddress, etEmailAddress, etAlternateContact1, etAlternateContact2, etProducts,
            etCoordinates, etNotes;

    private Button saveButton;
    private DatabaseReference vendorsRef;

    TextView textViewProducts;
    private Spinner spinDocType;

    private String photoUrl;
    private String docUrl;
    private String selectedDocType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business_vendors);

        etVendorId = findViewById(R.id.etbvendorid);
        etVendorName = findViewById(R.id.etbvendorname);
        etContactPerson = findViewById(R.id.etbcontactperson);
        etMobileNumber = findViewById(R.id.etbmobile);
        etLandlineNumber = findViewById(R.id.etblandline);
        etAddress = findViewById(R.id.etbaddress);
        etEmailAddress = findViewById(R.id.etbemail);
        etAlternateContact1 = findViewById(R.id.etbalternatecontact1);
        etAlternateContact2 = findViewById(R.id.etbalternatecontact2);
        etProducts = findViewById(R.id.etbproducts);
        etCoordinates = findViewById(R.id.etbcoordinates);
        etNotes = findViewById(R.id.etbnotes);

        saveButton = findViewById(R.id.btnbadd);

        vendorImageView = findViewById(R.id.ivaddbvendor);

        btnAddProduct = findViewById(R.id.btnaddproducts);
        textViewProducts = findViewById(R.id.tvproducts);

        productsList = new ArrayList<>();
        // Add this line to your existing declarations

        vendorsRef = FirebaseDatabase.getInstance().getReference().child("BusinessVendors");

        // Inside your onCreate method or wherever you initialize your views
        LinearLayout llAttach = findViewById(R.id.llattach);
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
        String product = etProducts.getText().toString().trim();

        if (!product.isEmpty()) {
            // Add product to the list
            productsList.add(product);

            // Update the TextView to display the products
            updateProductsTextView();

            // Clear the EditText for the next input
            etProducts.getText().clear();
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
                        docUrl = uri.toString();
                        Log.d("AddBusinessVendors", "Document URL: " + docUrl);
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
                        photoUrl = uri.toString();
                        Log.d("AddBusinessVendors", "Image URL: " + photoUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddBusinessVendorsActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddBusinessVendors", "Image upload failed", e);
                });
    }

    private void validateAndSaveVendorDetails() {
        String vendorId = etVendorId.getText().toString().trim();
        String vendorName = etVendorName.getText().toString().trim();
        String contactPerson = etContactPerson.getText().toString().trim();
        String mobileNumber = etMobileNumber.getText().toString().trim();
        String landlineNumber = etLandlineNumber.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String emailAddress = etEmailAddress.getText().toString().trim();
        String alternateContact1 = etAlternateContact1.getText().toString().trim();
        String alternateContact2 = etAlternateContact2.getText().toString().trim();
        String products = textViewProducts.getText().toString().trim();
        String coordinates = etCoordinates.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (TextUtils.isEmpty(vendorId) || TextUtils.isEmpty(vendorName) || TextUtils.isEmpty(contactPerson) || TextUtils.isEmpty(mobileNumber)
                || TextUtils.isEmpty(address) || TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(products)
                || TextUtils.isEmpty(coordinates) || TextUtils.isEmpty(notes)) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated. Please log in and try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();

        BusinessVendor vendor = new BusinessVendor(vendorId, vendorName, contactPerson, mobileNumber, landlineNumber, address,
                emailAddress, alternateContact1, alternateContact2, products, coordinates, notes, docUrl, userId, photoUrl);

        saveVendorToDatabase(vendor);
    }

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
}
