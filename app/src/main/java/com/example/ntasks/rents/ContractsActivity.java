package com.example.ntasks.rents;

import static com.itextpdf.kernel.geom.PageSize.A4;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.ntasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ContractsActivity extends AppCompatActivity {
    Button btnsave;
    EditText et1, et2, et3, et4, et5, et6;

    private Spinner ownerSpinner, tenantSpinner, propertySpinner;

    // Instances of your classes
    private DatabaseReference tenantsRef, ownersRef, flatRef;
    private List<Owner> ownersList = new ArrayList<>();  // Initialize this list
    private List<Tenant> tenantsList = new ArrayList<>();  // Initialize this list
    private List<Flats> flatsList = new ArrayList<>();  // Initialize this list
    private List<Independent> independentsList = new ArrayList<>();  // Initialize this list

    private ProgressDialog progressDialog;

    // Dynamic strings
    private String m1, m2, m3, m4, m5, m6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contracts);
        btnsave = findViewById(R.id.btnsave);


        tenantsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Tenants");
        ownersRef = FirebaseDatabase.getInstance().getReference().child("Rents/Owners");
        flatRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");
        flatRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // ... other setup

        // Assuming you have initialized and populated the spinners in your layout
        ownerSpinner = findViewById(R.id.spinnerowner);
        tenantSpinner = findViewById(R.id.spinnervendor);
        propertySpinner = findViewById(R.id.spinnerproperty);

        setupSpinnerWithOwners();
        setupSpinnerWithTenants();
        setupPropertiesSpinner();
        // ... other spinner initialization

        // Populate your spinners with data (names, for example)
        // (you can customize this based on your actual data structure)
        List<String> ownerNames = getOwnerNamesFromList(ownersList);
        List<String> tenantNames = getTenantNamesFromList(tenantsList);
        List<String> propertyNames = getPropertyNamesFromList(flatsList, independentsList);
        // ... other spinner population

        // Set up adapters for spinner

        /*rrayAdapter<String> tenantAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tenantNames);
        tenantSpinner.setAdapter(tenantAdapter);

        ArrayAdapter<String> propertyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, propertyNames);
        propertySpinner.setAdapter(propertyAdapter);*/

        // ... other spinner setup

        // Handle spinner selections
        ownerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle owner selection
                String selectedOwnerName = ownerSpinner.getSelectedItem().toString();
                Owner selectedOwner = findOwnerByName(selectedOwnerName);
                // Update dynamic string m3 based on selectedOwner
                m3 = buildOwnerString(selectedOwner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        tenantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle tenant selection
                String selectedTenantName = tenantSpinner.getSelectedItem().toString();
                Tenant selectedTenant = findTenantByName(selectedTenantName);
                // Update dynamic string m2 based on selectedTenant
                m2 = buildTenantString(selectedTenant);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        propertySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle property selection
                String selectedPropertyName = propertySpinner.getSelectedItem().toString();
                String selectedTenantName = tenantSpinner.getSelectedItem().toString();
                Tenant selectedTenant = findTenantByName(selectedTenantName);
                Flats selectedFlat = findFlatByName(selectedPropertyName);
                Independent selectedIndependent = findIndependentByName(selectedPropertyName);
                // Update dynamic strings m4, m5, m6 based on selectedFlat or selectedIndependent
                m4 =  "JISS International Corporate Services And Consultants House No 9-1-\n" +
                        "1/B/16/2, Defence Colony Langer House Golconda , Hyderabad ,Telangana 500008"; // Constant

                m5 = buildPropertyString(selectedFlat, selectedIndependent);
                m6 = buildRentAmountString(selectedTenant);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
        // ... other setup

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch selected items from spinners
                String selectedOwnerName = ownerSpinner.getSelectedItem().toString();
                String selectedTenantName = tenantSpinner.getSelectedItem().toString();
                String selectedPropertyName = propertySpinner.getSelectedItem().toString();

                // Find the selected instances
                Owner selectedOwner = findOwnerByName(selectedOwnerName);
                Tenant selectedTenant = findTenantByName(selectedTenantName);
                Flats selectedFlat = findFlatByName(selectedPropertyName);
                Independent selectedIndependent = findIndependentByName(selectedPropertyName);

                // Build dynamic strings based on user selections
                m2 = buildTenantString(selectedTenant);
                m3 = buildOwnerString(selectedOwner);
                m4 =  "JISS International Corporate Services And Consultants House No 9-1-\n" +
                        "1/B/16/2, Defence Colony Langer House Golconda , Hyderabad ,Telangana 500008"; // Constant
                m5 = buildPropertyString(selectedFlat, selectedIndependent);
                m6 = buildRentAmountString(selectedTenant);

                // Call createPdf with dynamic strings
                try {
                    createPdf(m1, m2, m3, m4, m5, m6);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("CONTRACTS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


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


    // Inside setupSpinnerWithOwners method
    private void setupSpinnerWithOwners() {
        ownersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ownersList.clear(); // Clear the list before populating it again

                List<String> ownerNames = new ArrayList<>();
                ownerNames.add("Select Owner");

                for (DataSnapshot ownerSnapshot : snapshot.getChildren()) {
                    Owner owner = ownerSnapshot.getValue(Owner.class);

                    if (owner != null) {
                        ownersList.add(owner);
                        ownerNames.add(owner.getOwnerName());
                    }
                }

                ArrayAdapter<String> ownerAdapter = new ArrayAdapter<>(ContractsActivity.this, android.R.layout.simple_spinner_item, ownerNames);
                ownerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ownerSpinner.setAdapter(ownerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }


    // Inside setupPropertiesSpinner method
    private void setupPropertiesSpinner() {
        DatabaseReference flatRef = FirebaseDatabase.getInstance().getReference().child("Rents/Flats");
        DatabaseReference independentRef = FirebaseDatabase.getInstance().getReference().child("Rents/Independents");

        List<String> propertyList = new ArrayList<>();
        propertyList.add("Select Property");
        AtomicInteger requestsCompleted = new AtomicInteger(0);

        ValueEventListener propertyListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot propertySnapshot : dataSnapshot.getChildren()) {
                    String propertyName;
                    if (dataSnapshot.getKey().equals("Flats")) {
                        propertyName = propertySnapshot.child("flatNo").getValue(String.class);
                    } else {
                        propertyName = propertySnapshot.child("indpName").getValue(String.class);
                    }
                    if (!TextUtils.isEmpty(propertyName)) {
                        propertyList.add(propertyName);
                    }
                }

                if (requestsCompleted.incrementAndGet() == 2) {
                    updatePropertiesSpinner(propertyList);
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ContractsActivity", "Error retrieving property data", databaseError.toException());
            }
        };

        flatRef.addListenerForSingleValueEvent(propertyListener);
        independentRef.addListenerForSingleValueEvent(propertyListener);
    }



    private void updatePropertiesSpinner(List<String> propertyList) {
        ArrayAdapter<String> propertyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, propertyList);
        propertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertySpinner.setAdapter(propertyAdapter);
    }

    private void setupSpinnerWithTenants() {
        tenantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> tenantNames = new ArrayList<>();

                tenantNames.add("Select Tenant");

                for (DataSnapshot tenantSnapshot : snapshot.getChildren()) {
                    String tenantName = tenantSnapshot.child("tenantName").getValue(String.class);

                    if (!TextUtils.isEmpty(tenantName)) {
                        tenantNames.add(tenantName);
                    }
                }

                ArrayAdapter<String> tenantAdapter = new ArrayAdapter<>(ContractsActivity.this, android.R.layout.simple_spinner_item, tenantNames);
                tenantAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tenantSpinner.setAdapter(tenantAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }



    private List<String> getOwnerNamesFromList(List<Owner> owners) {
        List<String> ownerNames = new ArrayList<>();

        // Check if ownersList is not null before processing
        if (owners != null) {
            for (Owner owner : owners) {
                ownerNames.add(owner.getOwnerName());
            }
        }

        return ownerNames;
    }

    private List<String> getTenantNamesFromList(List<Tenant> tenants) {
        List<String> tenantNames = new ArrayList<>();
        for (Tenant tenant : tenants) {
            tenantNames.add(tenant.getTenantName());
        }
        return tenantNames;
    }

    private List<String> getPropertyNamesFromList(List<Flats> flats, List<Independent> independents) {
        List<String> propertyNames = new ArrayList<>();
        for (Flats flat : flats) {
            propertyNames.add(flat.getFlatNo());
        }
        for (Independent independent : independents) {
            propertyNames.add(independent.getIndpName());
        }
        return propertyNames;
    }

    // ... other helper methods

    // Helper methods to find instances by name
    private Owner findOwnerByName(String ownerName) {
        for (Owner owner : ownersList) {
            if (owner.getOwnerName().equals(ownerName)) {
                return owner;
            }
        }
        return null; // Handle case when not found
    }

    private Tenant findTenantByName(String tenantName) {
        for (Tenant tenant : tenantsList) {
            if (tenant.getTenantName().equals(tenantName)) {
                return tenant;
            }
        }
        return null; // Handle case when not found
    }

    private Flats findFlatByName(String flatName) {
        for (Flats flat : flatsList) {
            if (flat.getFlatNo().equals(flatName)) {
                return flat;
            }
        }
        return null; // Handle case when not found
    }

    private Independent findIndependentByName(String independentName) {
        for (Independent independent : independentsList) {
            if (independent.getIndpName().equals(independentName)) {
                return independent;
            }
        }
        return null; // Handle case when not found
    }

    // Helper methods to build dynamic strings
    // Inside buildTenantString method
    private String buildTenantString(Tenant tenant) {
        if (tenant != null && tenant.getTenantName() != null) {
            // Build and return the string based on the Tenant object
            // Customize this based on your actual data structure
            return tenant.getTenantName() + "S/O" + tenant.getTenantFatherName() +
                    ", Aged about " + tenant.getAge() + ", Occ: " + tenant.getTenantOccupation() +
                    "." + tenant.getDocType() + ":- " + tenant.getTenantId() +
                    ", resident of " + tenant.getTenantPerAddress();
        } else {
            return ""; // Handle the case when Tenant or Tenant's name is null
        }
    }


    // Inside buildOwnerString method
    private String buildOwnerString(Owner owner) {
        if (owner != null && owner.getOwnerName() != null) {
            // Build and return the string based on the Owner object
            // Customize this based on your actual data structure
            return owner.getOwnerName() + "S/O" + owner.getFathername() +
                    ", Aged about " + owner.getAge() + ", Occ: " + owner.getOccupation() +
                    "." + owner.getDocType() + ":- " + owner.getOwnerId() +
                    ", resident of " + owner.getOwnerAddress();
        } else {
            return ""; // Handle the case when Owner or Owner's name is null
        }
    }


    /*private String buildFlatString(Flats flat) {
        // Build and return the string based on the Flats object
        // Customize this based on your actual data structure
        return "Area: " + flat.getArea() + ", Flat No: " + flat.getFlatNo() +
                ", Apartment: " + flat.getApartmentName() +
                ", Type: " + flat.getfType();
    }*/

    private String buildPropertyString(Flats flat, Independent independent) {
        // Build and return the string based on the selected Flat or Independent object
        // Customize this based on your actual data structure
        if (flat != null) {
            return flat.getFlatNo() +
                    ", " + flat.getApartmentAddress();
        } else if (independent != null) {
            return independent.getIndpName() +
                    ",  " + independent.getIndpAddress();
        } else {
            return ""; // Handle the case when neither Flat nor Independent is selected
        }
    }

    private String buildRentAmountString(Tenant tenant) {
        // Build and return the string based on the Tenant object
        // Customize this based on your actual data structure
        if (tenant != null) {
            return tenant.getTenantRent();
        } else {
            return ""; // Handle the case when Tenant is not selected
        }
    }


    // ... (existing code)

    private void createPdf(String m1, String m2, String m3, String m4, String m5, String m6) throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "myPDF.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        // Create a PdfWriter object with the specified file path
        PdfWriter writer = new PdfWriter(file);

        // Create a PdfDocument object using the PdfWriter
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        // Create a Document object with the PdfDocument
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(A4);
        document.setMargins(360, 60, 40, 50);
        // Add content to the document
        Paragraph n1 = new Paragraph("RENTAL AGREEMENT").setBold().setFontSize(18).setUnderline().setTextAlignment(TextAlignment.CENTER);

        document.add(n1);

        document.add(new Paragraph("This rental agreement is made and executed on this Date\t" + m1 + "\tHyderabad,Telangana.").setFontSize(14));
        document.add(new Paragraph(m2));
        document.add(new Paragraph("(Hereinafter called the TENANT which term shall mean and include all heirs, successors, legal\n" +
                "Representatives, administrators, assigns etc. of the ONE PART)"));
        document.add(new Paragraph(m3));

        document.add(new Paragraph("Represented By\t" + m4).setBold());
        document.add(new Paragraph("(Hereinafter called the LANDLORD which term shall mean and include all heirs, successors,\n" +
                "legal representatives, administrators, assigns etc.)"));
        document.add(new Paragraph("TENANT\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tLANDLORD"));
        document.add(new AreaBreak());
        document.add(new Paragraph("Whereas the above-named landlord is the absolute owner and possessor property bearing"));
        document.add(new Paragraph("House No." + m5));
        document.add(new Paragraph("And whereas being in need of premises the Tenant approached to the landlord and requested to let-out\n" +
                "the said property on a monthly rent of Rs " + m6 + ") and the landlord agreed to\n" +
                "let-out the same on the following terms and conditions"));
        document.add(new Paragraph(" Now This Rental Agreement Witnesseth as Follows :-").setUnderline());
        document.add(new Paragraph(" 1, That the tenancy commencing from the day of the agreement for a period of eleven months.\n" +
                "2, That the Tenant shall be entitled to pay a monthly rental of Rs" + m6 +
                "which shall be paid on or before 10 of every English calendar month without arrears’ to be accumulated.\n" +
                "3, That the Tenant shall keep the let-out property in neat and clean condition without any wastage and\n" +
                "damages. And the tenant shall not make any major repair or alterations without the written permission of the\n" +
                "landlord and return the premises in as it is condition.\n" +
                "5, That the Tenant shall not sublease the let-out the schedule property to any other person or persons.\n" +
                "6, That the above-mentioned rent excluding the electricity consumption charges which shall be paid by\n" +
                "the Tenant.\n" +
                "7, That the tenant shall permit the landlord or his representative to inspect the let-out premises at all\n" +
                "reasonable times.\n" +
                "8, That both parties shall serve (2) three months prior notice for the termination of this rental agreement.\n" +
                "9, That this Rental Agreement can further extend with the mutual consent of both the parties subject to the\n" +
                "conditions with the enhancement of rent, i.e., 5% on every renewal."));

        document.add(new Paragraph("10, that the Tenant has deposited a sum of Rs 18.600/- (Rupees Eighteen thousand and Six Hundred\n" +
                "only) which shall be refunded at the time of vacating the premises without any interest. That the Tenant\n" +
                "has paid running rent advance.\n" +
                "IN WITNESSES WHEREOF the Tenant & Landlord signed this Rental Agreement with their own free\n" +
                "will on this day, month and year first mentioned above in the presence of the following witnesses."));
        document.add(new AreaBreak());



        document.add(new Paragraph("\n\nWITNESSES\n\n").setUnderline().setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("TENANT\n\n").setUnderline().setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("LANDLORD").setUnderline().setTextAlignment(TextAlignment.RIGHT));

        document.close();
        Toast.makeText(this, "Pdf Created", Toast.LENGTH_SHORT).show();
    }

// ... (existing code)


    // ... other methods
}