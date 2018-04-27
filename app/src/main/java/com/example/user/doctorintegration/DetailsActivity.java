package com.example.user.doctorintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference commandsRef = rootRef.child("ServiceProviderInformation");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String executed = ds.child("Address").getValue(String.class);
                    String text = ds.child("Email").getValue(String.class);
                  //  double timestamp = ds.child("timestamp").getValue(Double.class);
                    Log.d("TAG \n", executed + " / " + text + " / " );
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        commandsRef.addListenerForSingleValueEvent(eventListener);
    }
}
