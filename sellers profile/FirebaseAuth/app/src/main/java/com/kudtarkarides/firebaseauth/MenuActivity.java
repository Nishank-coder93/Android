package com.kudtarkarides.firebaseauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mDishField;
    private EditText mPriceField;
    private EditText mQuantityField;
    private Button mAddButton;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        userId = mAuth.getCurrentUser().getUid();

        mDishField = (EditText) findViewById(R.id.editTextDish);
        mPriceField = (EditText) findViewById(R.id.editTextPrice);
        mQuantityField = (EditText) findViewById(R.id.editTextQuantity);
        mAddButton = (Button) findViewById(R.id.buttonAdd);

        mAddButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mAddButton)
            addMenu();
    }

    private void addMenu() {

        final String dish = mDishField.getText().toString().trim();
        final String price = mPriceField.getText().toString().trim();
        final String quantity = mQuantityField.getText().toString().trim();

        //int quantity =  Integer.parseInt(mQuantityField.getText().toString());
        mDatabaseUsers.child(userId).child("menu").child("dish").setValue(dish);
        mDatabaseUsers.child(userId).child("menu").child("price").setValue(price);
        mDatabaseUsers.child(userId).child("menu").child("quantity").setValue(quantity);

    }
}
