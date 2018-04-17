package com.example.user.doctorintegration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Neurologist extends AppCompatActivity {

    CardView neuro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neurologist);
        Intent intent = new Intent(Neurologist.this, MapsActivity.class);
        intent.putExtra("KEY_BONE","neuro");
               startActivity(intent);
//        neuro = (CardView) findViewById(R.id.neuroid);
//
//        neuro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
    }
}
