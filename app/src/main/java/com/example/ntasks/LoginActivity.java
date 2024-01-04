package com.example.ntasks;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginEmail, editTextLoginPwd;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        editTextLoginEmail = findViewById(R.id.editText_login_email);
        editTextLoginPwd = findViewById(R.id.editText_login_pwd);
        progressBar = findViewById(R.id.progressBar);

        authProfile = FirebaseAuth.getInstance();
       /* if (getSupportActionBar() != null) {

            getSupportActionBar().hide();

        }
*/
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("LOGIN");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.blueeee); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));



        // Login User
        Button buttonLogin = findViewById(R.id.button_lgin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPwd = editTextLoginPwd.getText().toString();

                if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Enter Email Here");
                    editTextLoginEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(LoginActivity.this, "Please Re-Enter Email", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Enter Email Here");
                    editTextLoginEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    editTextLoginPwd.setError("Enter Password Here");
                    editTextLoginPwd.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail, textPwd);
                }
            }
        });
    }





    // Method to obtain and log the FCM token
    private void obtainAndLogFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    // Get FCM token
                    String token = task.getResult();

                    // Log the FCM token
                    Log.d("FCMToken", "Token: " + token);

                    // Continue with your login success logic...
                    Toast.makeText(LoginActivity.this, "FCM Token: " + token, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Handle the error
                    Toast.makeText(LoginActivity.this, "Error obtaining FCM Token", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Call this method in the loginUser method after successful login
    private void loginUser(String email, String pwd) {
        authProfile.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "You are Logged in now", Toast.LENGTH_SHORT).show();
                    FirebaseUser currentUser = authProfile.getCurrentUser();
                    if (currentUser != null) {
                        // String uname = currentUser.getDisplayName();
                        // Log.d("FirebaseData", "UID: " + uname);

                        // Get and log FCM token
                        obtainAndLogFCMToken();
                    } else {
                        Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                } else {
                    // ... (your existing code)
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the home button click
                Intent intent = new Intent(this, Master.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}



/*
package com.example.ntasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
private EditText editTextLoginEmail, editTextLoginPwd;
private ProgressBar progressBar;
private FirebaseAuth authProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        editTextLoginEmail=findViewById(R.id.editText_login_email);
        editTextLoginPwd=findViewById(R.id.editText_login_pwd);
        progressBar=findViewById(R.id.progressBar);

        authProfile=FirebaseAuth.getInstance();

        //Login User
        Button buttonLogin= findViewById(R.id.button_lgin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail=editTextLoginEmail.getText().toString();
                String textPwd=editTextLoginPwd.getText().toString();

                if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Enter Email Here");
                    editTextLoginEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){

                    Toast.makeText(LoginActivity.this, "Please Re-Enter Email", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Enter Email Here");
                    editTextLoginEmail.requestFocus();
                }else if (TextUtils.isEmpty(textPwd)){
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    editTextLoginPwd.setError("Enter Password Here");
                    editTextLoginPwd.requestFocus();

                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail,textPwd);
                }

            }
        });


    }

    private void loginUser(String email, String pwd) {
        authProfile.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){
               Toast.makeText(LoginActivity.this, "You are Logged in now", Toast.LENGTH_SHORT).show();
               FirebaseUser currentUser = authProfile.getCurrentUser();
               if (currentUser != null) {
                   String uname = currentUser.getDisplayName();
                   Log.d("FirebaseData", "UID: " + uname);
               finish();
           }     else {
               Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();


           }
           progressBar.setVisibility(View.GONE);
           }
            }
        });
    }

}*/
