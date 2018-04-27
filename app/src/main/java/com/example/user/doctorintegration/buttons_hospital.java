package com.example.user.doctorintegration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class buttons_hospital extends AppCompatActivity {
    public Button button7;
    public Button button8;
    public Button button9;
    SharedPreferences mPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons_hospital);
        mPref=getSharedPreferences("navigation",MODE_PRIVATE);

        button7 = (Button)findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);



        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPref.edit().putString("categoryval","allopathy").commit();
                Intent toy = new Intent(buttons_hospital.this, MapsActivity.class);

                Log.d("----grid","act");
                startActivity(toy);

            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(buttons_hospital.this, MapsActivity.class);
                mPref.edit().putString("categoryval","ayurvedic").commit();

                //toy.putExtra("KEY_BONE","doctor1");
                Log.d("---doctor","unfs");
                startActivity(toy);

            }
        });



        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPref.edit().putString("categoryval","homeopathy").commit();
                Intent toy = new Intent(buttons_hospital.this, MapsActivity.class);
                // toy.putExtra("KEY_BONE","doctor2");
                Log.d("---doctor2","unfs");
                startActivity(toy);

            }
        });

    }
    @Override
    public  void onBackPressed(){
        // startActivity(new Intent(buttons_hospital.this,NavigateActivity.class));
        finish();
    }
}


//package com.example.user.doctorintegration;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//public class buttons_hospital extends AppCompatActivity {
//    public Button button7;
//    public Button button8;
//    public Button button9;
//    SharedPreferences mPref;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_buttons_hospital);
//        mPref=getSharedPreferences("navigation",MODE_PRIVATE);
//
//        button7 = (Button)findViewById(R.id.button7);
//        button8 = (Button) findViewById(R.id.button8);
//        button9 = (Button) findViewById(R.id.button9);
//
//
//
//        button7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPref.edit().putString("categoryval","allopathy").commit();
//                Intent toy = new Intent(buttons_hospital.this, MapsActivity.class);
//
//                Log.d("----grid","act");
//                startActivity(toy);
//
//            }
//        });
//
//        button8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toy = new Intent(buttons_hospital.this, MapsActivity.class);
//                mPref.edit().putString("categoryval","ayurvedic").commit();
//
//                //toy.putExtra("KEY_BONE","doctor1");
//                Log.d("---doctor","unfs");
//                startActivity(toy);
//
//            }
//        });
//
//
//
//        button9.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPref.edit().putString("categoryval","homeopathy").commit();
//                Intent toy = new Intent(buttons_hospital.this, MapsActivity.class);
//                // toy.putExtra("KEY_BONE","doctor2");
//                Log.d("---doctor2","unfs");
//                startActivity(toy);
//
//            }
//        });
//
//    }
//    @Override
//    public  void onBackPressed(){
//       // startActivity(new Intent(buttons_hospital.this,NavigateActivity.class));
//        finish();
//    }
//}
//
//
