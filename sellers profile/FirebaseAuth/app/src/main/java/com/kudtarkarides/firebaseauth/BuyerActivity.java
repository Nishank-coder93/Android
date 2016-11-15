package com.kudtarkarides.firebaseauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuyerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mFirstName;
    private TextView mExp;
    private EditText mLat;
    private EditText mLong;
    private Button buttonSellerDetails;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseUsers;
    private String uidString;
    private String sellerNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        mFirstName = (TextView) findViewById(R.id.textFirstName);
        mExp = (TextView) findViewById(R.id.textExp);
        mLat = (EditText) findViewById(R.id.editTextLat);
        mLong = (EditText) findViewById(R.id.editTextLong);

        buttonSellerDetails = (Button) findViewById(R.id.buttonSellerDetails);
        buttonSellerDetails.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        getSellersInfo();
    }

    private void getSellersInfo() {

        /*String mLatString = mLat.getText().toString().trim();
        String mLongString = mLong.getText().toString().trim();

        Toast.makeText(this, mLatString+" "+mLongString, Toast.LENGTH_SHORT).show();

        if (mLatString == "1" && mLongString == "2")
            mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("id").child("sellers").child("1").child("uid");
        else if (mLatString == "5" && mLongString == "15")
            mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("id").child("sellers").child("2").child("uid");
*/
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("id").child("sellers").child("1").child("uid");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uidString = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFirstName.setText(dataSnapshot.child(uidString).child("firstName").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mExp.setText(dataSnapshot.child(uidString).child("lastName").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
