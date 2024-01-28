package com.example.ntasks.rents;
// ReportsActivity.java
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportsActivity extends AppCompatActivity {

    private TextView tvExpenses, tvCollections;
    public RecyclerView rvExpenses, rvCollections;
    private List<Expenses> expenseList;
    private List<Collection> collectionList;
    Button btncreaterep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        tvExpenses = findViewById(R.id.tvExpenses);
        tvCollections = findViewById(R.id.tvCollections);
        rvExpenses = findViewById(R.id.rvExpenses);
        rvCollections = findViewById(R.id.rvCollections);
        btncreaterep=findViewById(R.id.btncreaterep);



        btncreaterep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdf();


            }
        });

        expenseList = new ArrayList<>();
        collectionList = new ArrayList<>();

        // Set up RecyclerView adapters and layouts for expenses and collections
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(expenseList);
        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        rvExpenses.setAdapter(expenseAdapter);

        CollectionAdapter collectionAdapter = new CollectionAdapter(collectionList);
        rvCollections.setLayoutManager(new LinearLayoutManager(this));
        rvCollections.setAdapter(collectionAdapter);

        // Load expenses and collections from Firebase
        loadExpenses();
        loadCollections();
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("REPORTS");

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

    private void loadExpenses() {
        DatabaseReference expensesRef = FirebaseDatabase.getInstance().getReference().child("Rents/Expenses");

        expensesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseList.clear();

                for (DataSnapshot expenseSnapshot : dataSnapshot.getChildren()) {
                    Expenses expense = expenseSnapshot.getValue(Expenses.class);
                    if (expense != null) {
                        expenseList.add(expense);
                    }
                }

                // Notify the adapter that the data set has changed
                rvExpenses.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void loadCollections() {
        DatabaseReference collectionsRef = FirebaseDatabase.getInstance().getReference().child("Rents/Payments");

        collectionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                collectionList.clear();

                for (DataSnapshot collectionSnapshot : dataSnapshot.getChildren()) {
                    Collection collection = collectionSnapshot.getValue(Collection.class);
                    if (collection != null) {
                        collectionList.add(collection);
                    }
                }

                // Notify the adapter that the data set has changed
                rvCollections.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void generatePdf() {
        try {
            // Get the directory for saving the PDF
            File pdfDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MyReportsPDFs");
            if (!pdfDir.exists()) {
                pdfDir.mkdirs();
            }
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String pdfFileName = "my_report_" + timeStamp + ".pdf";
            // Create a PDF file
            File pdfFile = new File(pdfDir, pdfFileName);

            // Generate PDF with data from RecyclerViews
            String expensesData = generateDataFromRecyclerView(rvExpenses);
            String collectionsData = generateDataFromRecyclerView(rvCollections);

            PdfGenerator.generatePdf(pdfFile, expensesData, collectionsData);
            Toast.makeText(this, "PDF CREATED In MyReportsPDFs Folder of your Mobile Directory", Toast.LENGTH_LONG).show();
            // Display a message or open the PDF file as needed
            // ...

            openPdfWithIntent(pdfFile);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }
    private String generateDataFromRecyclerView(RecyclerView recyclerView) {
        StringBuilder data = new StringBuilder();
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        if (adapter != null) {
            if (adapter instanceof ExpenseAdapter) {
                List<Expenses> expenseList = ((ExpenseAdapter) adapter).getExpenseList();

                for (Expenses expense : expenseList) {
                    // Assuming your Expenses class has a meaningful toString method
                    data.append(expense.toString()).append("\n");
                }
            } else if (adapter instanceof CollectionAdapter) {
                List<Collection> collectionList = ((CollectionAdapter) adapter).getCollectionList();

                for (Collection collection : collectionList) {
                    // Assuming your Collection class has a meaningful toString method
                    data.append(collection.toString()).append("\n");
                }
            }
        }

        return data.toString();
    }

    private void openPdfWithIntent(File pdfFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri pdfUri;

        // Check Android version for FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", pdfFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            pdfUri = Uri.fromFile(pdfFile);
        }

        intent.setDataAndType(pdfUri, "application/pdf");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
             return;}
    }




}

