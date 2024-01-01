package com.example.ntasks;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
private EditText editTextRegisterFullName,editTextRegisterEmail,editTextRegisterDoB,editTextRegisterMobile,editTextRegisterPwd,
        editTextRegisterConfirmPwd;
private ProgressBar progressBar;
private RadioGroup radioGroupRegisterGender;
private RadioButton radioButtonRegisterGenderSelected;
private static final String TAG="Register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        Toast.makeText(this, "You Can Register Now", Toast.LENGTH_SHORT).show();
progressBar =findViewById(R.id.progressBar);

        editTextRegisterFullName=findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail=findViewById(R.id.editText_register_email);
        editTextRegisterDoB=findViewById(R.id.editText_register_dob);
        editTextRegisterMobile=findViewById(R.id.editText_register_mobile);
        editTextRegisterPwd=findViewById(R.id.editText_register_password);
       ///// editTextRegisterConfirmPwd=findViewById(R.

    radioGroupRegisterGender=findViewById(R.id.radio_group_register_gender);
    radioGroupRegisterGender.clearCheck();


        Button buttonRegister =findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId= radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected=findViewById(selectedGenderId);

                //obtain the enterd data
                String textFullName=editTextRegisterFullName.getText().toString();
                String textEmail=editTextRegisterEmail.getText().toString();
                String textDoB=editTextRegisterDoB.getText().toString();
                String textMobile=editTextRegisterMobile.getText().toString();
                String textPwd=editTextRegisterPwd.getText().toString();
                String textGender;



                if(TextUtils.isEmpty(textFullName)){
                Toast.makeText(Register.this, "Please Enter your full Name", Toast.LENGTH_SHORT).show();
                editTextRegisterFullName.setError("Full Name Is Required");
                editTextRegisterFullName.requestFocus();
                }
                else if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(Register.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("EMAIL Is Required");
                    editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {

                    Toast.makeText(Register.this, "Please Re-Enter Your Email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Valid Email Is Required");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textDoB)) {

                    Toast.makeText(Register.this, "Please Enter Your DOB", Toast.LENGTH_SHORT).show();
                    editTextRegisterDoB.setError("DOB Is Required");
                    editTextRegisterDoB.requestFocus();

                } else if (radioGroupRegisterGender.getCheckedRadioButtonId()==-1) {
                    Toast.makeText(Register.this, "Please Select Your Gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelected.setError("GENDER Is Required");
                    radioButtonRegisterGenderSelected.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(Register.this, "Please Re-Enter Your Mobile No.", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Mobile no. is required");
                    editTextRegisterMobile.requestFocus();
                } else if (textMobile.length()==1) {
                    Toast.makeText(Register.this, "Please Re-Enter Your Mobile No.", Toast.LENGTH_SHORT).show();
                editTextRegisterMobile.setError("Mobile no. should be 10 digits");
                editTextRegisterMobile.requestFocus();

                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(Register.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.requestFocus();
                } else if (textPwd.length()<6) {

                    Toast.makeText(Register.this, "Password Should Be Atleast 6 Digits", Toast.LENGTH_SHORT).show();
                editTextRegisterPwd.setError("Password too weak");
                editTextRegisterPwd.requestFocus();
                editTextRegisterPwd.clearComposingText();
                }else {
                    textGender=radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registeruser(textFullName,textEmail,textDoB,textGender,textMobile,textPwd);

                }


            }
        });


    }

    private void registeruser(String textFullName, String textEmail, String textDoB, String textGender, String textMobile, String textPwd) {

        FirebaseAuth auth=FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textEmail,textPwd).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    //update display name if user
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);


                    //enter user data into the firebase realtime database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textFullName, textDoB,textGender,textMobile);



                    ////Extracting user reference from db for"Registered users"
                    DatabaseReference referenceprofile = FirebaseDatabase.getInstance().getReference("Registered Users");
                    referenceprofile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                //send verficatin email
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(Register.this, "Registered succesfully, please verify your email", Toast.LENGTH_SHORT).show();
                                finish();
                                /*Intent intent2=new Intent(Register.this,UserProfileActivity.class);
                                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent2);
                                finish();///to close register acticitys*/

                            }
                            else{
                                Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);

                        }
                    });



                    /*
                    //open user profile
                    Intent intent2=new Intent(Register.this,UserProfileActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    finish();///to close register acticity
                    */

                } else{
                    try {
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwd.setError("Your password is too weak, kindly use a mix of alphabets");
                        editTextRegisterPwd.requestFocus();
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwd.setError("Email Is Invalid or already in use");
                        editTextRegisterPwd.requestFocus();
                    }catch(FirebaseAuthUserCollisionException e){
                        editTextRegisterPwd.setError("User is already Registered, please use new email");
                        editTextRegisterPwd.requestFocus();
                    }catch(Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
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