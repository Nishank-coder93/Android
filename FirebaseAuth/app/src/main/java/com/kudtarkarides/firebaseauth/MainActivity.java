package com.kudtarkarides.firebaseauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPassword1;
    private EditText editTextExperience;
    private EditText editTextCuisine;
    //private NumberFormat editPhone;
    private TextView textViewSignIn;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassword1 = (EditText) findViewById(R.id.editTextPassword1);
        editTextExperience = (EditText) findViewById(R.id.editTextExperience);
        editTextCuisine = (EditText) findViewById(R.id.editTextCuisine);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);

        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
    }

    //Registration method
    private void registerUser() {
        final String firstName = editTextFirstName.getText().toString().trim();
        final String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String password1 = editTextPassword1.getText().toString().trim();
        final String experience = editTextExperience.getText().toString().trim();
        final String cuisine = editTextCuisine.getText().toString().trim();

        if(TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email-id", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!(password.equals(password1))) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = databaseReference.child(userId);
                            current_user_db.child("firstName").setValue(firstName);
                            current_user_db.child("lastName").setValue(lastName);
                            current_user_db.child("experience").setValue(experience);
                            current_user_db.child("cuisine").setValue(cuisine);
                            Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }else {
                            Toast.makeText(MainActivity.this, "Could not register... Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*private void updateUserData() {
        String phone = editPhone.getText().toString().trim();

        if(TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            return;
        }
    }*/

    @Override
    public void onClick(View v) {
        if(v == buttonRegister) {
            registerUser();
            //updateUserData();
        }
        if (v == textViewSignIn) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
