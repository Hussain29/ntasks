package com.example.ntasks.rents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ntasks.R;

import java.util.ArrayList;
import java.util.List;

public class BusinessVendors extends AppCompatActivity {
    EditText editTextProduct;
    TextView textViewProducts;
    Button btnAddProduct;
    private List<String> productsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_vendors);



          editTextProduct = findViewById(R.id.etproduct);
          textViewProducts = findViewById(R.id.tvproducts);
        btnAddProduct = findViewById(R.id.btnaddproducts);

        productsList = new ArrayList<>();

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
    }

    private void addProduct() {
        String product = editTextProduct.getText().toString().trim();

        if (!product.isEmpty()) {
            // Add product to the list
            productsList.add(product);

            // Update the TextView to display the products
            updateProductsTextView();

            // Clear the EditText for the next input
            editTextProduct.getText().clear();
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
}