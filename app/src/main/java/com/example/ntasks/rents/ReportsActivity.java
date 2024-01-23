package com.example.ntasks.rents;
// ReportsActivity.java
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends AppCompatActivity {

    private TextView tvExpenses, tvCollections;
    private RecyclerView rvExpenses, rvCollections;
    private List<Expenses> expenseList;
    private List<Collection> collectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        tvExpenses = findViewById(R.id.tvExpenses);
        tvCollections = findViewById(R.id.tvCollections);
        rvExpenses = findViewById(R.id.rvExpenses);
        rvCollections = findViewById(R.id.rvCollections);

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
}

