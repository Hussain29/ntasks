package com.example.ntasks.rents;

import static com.example.ntasks.rents.addflats.RandomIdGenerator.generateRandomId;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;

import java.util.Random;

public class addflats extends AppCompatActivity {
    EditText etflatid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addflats);

        etflatid = findViewById(R.id.etflatid);

        // Generate a random ID
        String generatedId = generateRandomId();

        // Set the generated ID to the EditText
        etflatid.setText(generatedId);
        etflatid.setEnabled(false);




        // Assuming 'your_spinner' is the ID of your Spinner in the layout file
        Spinner spinner = findViewById(R.id.spintype);

// Get the string array from resources
        String[] items = getResources().getStringArray(R.array.flattypes);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("ADD FLATS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the home button click
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static class RandomIdGenerator {

        public static String generateRandomId() {
            // You can adjust the length of the ID as needed
            int idLength = 4;

            // Characters allowed in the ID
            String allowedCharacters = "0123456789";

            // StringBuilder to store the generated ID
            StringBuilder randomId = new StringBuilder();

            // Random object to generate random indices
            Random random = new Random();
// Add the starting letter 'F'
            randomId.append('F');
            // Generate random ID
            for (int i = 0; i < idLength; i++) {
                int randomIndex = random.nextInt(allowedCharacters.length());
                randomId.append(allowedCharacters.charAt(randomIndex));
            }

            // You can concatenate it with a timestamp or other information for uniqueness
            // Example: randomId.append(System.currentTimeMillis());

            return randomId.toString();
        }

        public static void main(String[] args) {
            String generatedId = generateRandomId();
            /////    System.out.println("Generated ID: ");


        }

    }
}