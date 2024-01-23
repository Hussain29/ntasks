package com.example.ntasks.rents;
// ReportsActivity.java
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.ArrayList;
import java.util.List;

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
            File pdfDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MyPDFs");
            if (!pdfDir.exists()) {
                pdfDir.mkdirs();
            }

            // Create a PDF file
            File pdfFile = new File(pdfDir, "my_report.pdf");

            // Generate PDF with data from RecyclerViews
            String expensesData = generateDataFromRecyclerView(rvExpenses);
            String collectionsData = generateDataFromRecyclerView(rvCollections);

            PdfGenerator.generatePdf(pdfFile, expensesData, collectionsData);

            // Display a message or open the PDF file as needed
            // ...
        } catch (IOException e) {
            e.printStackTrace();
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

}

