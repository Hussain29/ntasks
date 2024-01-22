package com.example.ntasks.rents;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ntasks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class hrAddEmployeeActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST_IMG = 4;
    private static final int PICK_FILE_REQUEST_DOC = 5;

    EditText etempid, etempname, etempiqno, etempiqexp1, etempiqexp2, etempaddper, etempemcon1, etempemcon2, etempnat, etempocc,
            etempbtdt1, etempbtdt2, etempcountry, etempmstat, etemprel, etemphins1, etemphins2, etempiq1, etempiq2, etempdl1, etempdl2,
            etemppp1, etemppp2, etempdep, etempppno, etempppcity, etempppdt, etempppexpdt, etempvinfo, etempdlno, etempdldate, etempdlexpdt,
            etemphisd, etemphied, etempnotes;
    Button saveButton;

    ConstraintLayout addImg;

    LinearLayout attachdoc;

    DatabaseReference employeesRef;

    private String photoUrl;
    private String docUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_add_employee);

        addImg = findViewById(R.id.claddempimg);
        etempid = findViewById(R.id.etempid);
        etempname = findViewById(R.id.etempname);
        etempiqno = findViewById(R.id.etempiqno);
        etempiqexp1 = findViewById(R.id.etempiqexp1);
        etempiqexp2 = findViewById(R.id.etempiqexp2);
        etempaddper = findViewById(R.id.etempaddper);
        etempemcon1 = findViewById(R.id.etempemcon1);
        etempemcon2 = findViewById(R.id.etempemcon2);
        etempnat = findViewById(R.id.etempnat);
        etempocc = findViewById(R.id.etempocc);
        etempbtdt1 = findViewById(R.id.etempbtdt1);
        etempbtdt2 = findViewById(R.id.etempbtdt2);
        etempcountry = findViewById(R.id.etempcountry);
        etempmstat = findViewById(R.id.etempmstat);
        etemprel = findViewById(R.id.etemprel);
        etemphins1 = findViewById(R.id.etemphins1);
        etemphins2 = findViewById(R.id.etemphins2);
        etempiq1 = findViewById(R.id.etempiq1);
        etempiq2 = findViewById(R.id.etempiq2);
        etempdl1 = findViewById(R.id.etempdl1);
        etempdl2 = findViewById(R.id.etempdl2);
        etemppp1 = findViewById(R.id.etemppp1);
        etemppp2 = findViewById(R.id.etemppp2);
        etempdep = findViewById(R.id.etempdep);
        etempppno = findViewById(R.id.etempppno);
        etempppcity = findViewById(R.id.etempppcity);
        etempppdt = findViewById(R.id.etempppdt);
        etempppexpdt = findViewById(R.id.etempppexpdt);
        etempvinfo = findViewById(R.id.etempvinfo);
        etempdlno = findViewById(R.id.etempdlno);
        etempdldate = findViewById(R.id.etempdldate);
        etempdlexpdt = findViewById(R.id.etempdlexpdt);
        etemphisd = findViewById(R.id.etemphisd);
        etemphied = findViewById(R.id.etemphied);
        etempnotes = findViewById(R.id.etempnotes);
        saveButton = findViewById(R.id.btnbadd);
        attachdoc = findViewById(R.id.llattach);


        employeesRef = FirebaseDatabase.getInstance().getReference().child("HR/Employees");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ADD EMPLOYEE");
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.orangeee);
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSaveEmployeeDetails();
            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_IMG);
            }
        });
        attachdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_FILE_REQUEST_DOC);
            }
        });

    }

    private void validateAndSaveEmployeeDetails() {
        String empId = etempid.getText().toString().trim();
        String empName = etempname.getText().toString().trim();
        String empIqNo = etempiqno.getText().toString().trim();
        String empIqExp1 = etempiqexp1.getText().toString().trim();
        String empIqExp2 = etempiqexp2.getText().toString().trim();
        String empAddPer = etempaddper.getText().toString().trim();
        String empEmCon1 = etempemcon1.getText().toString().trim();
        String empEmCon2 = etempemcon2.getText().toString().trim();
        String empNat = etempnat.getText().toString().trim();
        String empOcc = etempocc.getText().toString().trim();
        String empBtdt1 = etempbtdt1.getText().toString().trim();
        String empBtdt2 = etempbtdt2.getText().toString().trim();
        String empCountry = etempcountry.getText().toString().trim();
        String empMstat = etempmstat.getText().toString().trim();
        String empRel = etemprel.getText().toString().trim();
        String empIns1 = etemphins1.getText().toString().trim();
        String empIns2 = etemphins2.getText().toString().trim();
        String empIq1 = etempiq1.getText().toString().trim();
        String empIq2 = etempiq2.getText().toString().trim();
        String empDl1 = etempdl1.getText().toString().trim();
        String empDl2 = etempdl2.getText().toString().trim();
        String empPp1 = etemppp1.getText().toString().trim();
        String empPp2 = etemppp2.getText().toString().trim();
        String empDep = etempdep.getText().toString().trim();
        String empPpNo = etempppno.getText().toString().trim();
        String empPpCity = etempppcity.getText().toString().trim();
        String empPpDt = etempppdt.getText().toString().trim();
        String empPpExpDt = etempppexpdt.getText().toString().trim();
        String empVInfo = etempvinfo.getText().toString().trim();
        String empDlNo = etempdlno.getText().toString().trim();
        String empDlDate = etempdldate.getText().toString().trim();
        String empDlExpDt = etempdlexpdt.getText().toString().trim();
        String empHisD = etemphisd.getText().toString().trim();
        String empHisE = etemphied.getText().toString().trim();
        String empNotes = etempnotes.getText().toString().trim();

        if (isValidEmployeeId(empId)) {
            saveEmployeeDetails(empId, empName, empIqNo, empIqExp1, empIqExp2, empAddPer, empEmCon1, empEmCon2,
                    empNat, empOcc, empBtdt1, empBtdt2, empCountry, empMstat, empRel, empIns1, empIns2, empIq1,
                    empIq2, empDl1, empDl2, empPp1, empPp2, empDep, empPpNo, empPpCity, empPpDt, empPpExpDt,
                    empVInfo, empDlNo, empDlDate, empDlExpDt, empHisD, empHisE, empNotes);
        } else {
            Toast.makeText(hrAddEmployeeActivity.this, "Please fill all the necessary fields with valid data", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveEmployeeDetails(String empId, String empName, String empIqNo, String empIqExp1, String empIqExp2,
                                    String empAddPer, String empEmCon1, String empEmCon2, String empNat, String empOcc,
                                    String empBtDt1, String empBtDt2, String empCountry, String empMStat, String empRel,
                                    String empIns1, String empIns2, String empIq1, String empIq2, String empDl1, String empDl2,
                                    String empPp1, String empPp2, String empDep, String empPpNo, String empPpCity,
                                    String empPpDt, String empPpExpDt, String empVisaInfo, String empDlNo, String empDlDt,
                                    String empDlExpDt, String empHInsStartDt, String empHInsEndDt, String empNotes) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employees");

        Employee employee = new Employee(empId, empName, empIqNo, empIqExp1, empIqExp2, empAddPer, empEmCon1, empEmCon2,
                empNat, empOcc, empBtDt1, empBtDt2, empCountry, empMStat, empRel, empIns1, empIns2, empIq1, empIq2, empDl1,
                empDl2, empPp1, empPp2, empDep, empPpNo, empPpCity, empPpDt, empPpExpDt, empVisaInfo, empDlNo, empDlDt,
                empDlExpDt, empHInsStartDt, empHInsEndDt, empNotes, photoUrl, docUrl);

        /*databaseReference.child(empId).setValue(employee);*/
        employeesRef.child(empId).setValue(employee, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(hrAddEmployeeActivity.this, "Employee details saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(hrAddEmployeeActivity.this, "Failed to save employee details. Please try again.", Toast.LENGTH_SHORT).show();
                    error.toException().printStackTrace();
                }
            }
        });
    }

    private boolean isValidEmployeeId(String empId) {
        return !empId.isEmpty(); // You can add more validation if needed
    }

    private void showFileChooser(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(requestCode == PICK_FILE_REQUEST_IMG ? "image/*" : "*/*");
        startActivityForResult(intent, requestCode);
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

    private void uploadDocumentToFirebaseStorage(Uri docUri) {
        String fileName = "document:" + System.currentTimeMillis();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("uploads").child(fileName);

        storageRef.putFile(docUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        docUrl = uri.toString();
                        Log.d("AddTenantsActivity", "Document URL: " + docUrl);
                        Toast.makeText(hrAddEmployeeActivity.this, "Document upload successful.", Toast.LENGTH_SHORT).show();

                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(hrAddEmployeeActivity.this, "Document upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddTenantsActivity", "Document upload failed", e);
                });
    }

    private void uploadImageToFirebaseStorage(Uri imageUri) {
        String fileName = "image:" + System.currentTimeMillis();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("uploads").child(fileName);

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        photoUrl = uri.toString();
                        Log.d("AddTenantsActivity", "Image URL: " + photoUrl);
                        Toast.makeText(hrAddEmployeeActivity.this, "Image upload successful.", Toast.LENGTH_SHORT).show();

                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(hrAddEmployeeActivity.this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("AddTenantsActivity", "Image upload failed", e);
                });
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            return "Unknown User";
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
}
