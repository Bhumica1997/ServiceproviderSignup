package com.example.user.doctorintegration;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class buttons_doctor extends AppCompatActivity {
    public Button button10;
    public Button button11;
    public Button button12;
    SharedPreferences mPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons_doctor);
        mPref=getSharedPreferences("navigation",MODE_PRIVATE);

        button10 = (Button)findViewById(R.id.button10);

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPref.edit().putString("categoryval","allopathy").commit();
                Intent toy = new Intent(buttons_doctor.this, GridActivity.class);

                Log.d("----grid","act");
                startActivity(toy);

            }
        });
        button11 = (Button) findViewById(R.id.button11);

        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(buttons_doctor.this, MapsActivity.class);
                mPref.edit().putString("categoryval","ayurvedic").commit();

                //toy.putExtra("KEY_BONE","doctor1");
                Log.d("---doctor","unfs");
                startActivity(toy);

            }
        });


        button12 = (Button) findViewById(R.id.button12);

        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPref.edit().putString("categoryval","homeopathy").commit();
                Intent toy = new Intent(buttons_doctor.this, MapsActivity.class);
                // toy.putExtra("KEY_BONE","doctor2");
                Log.d("---doctor2","unfs");
                startActivity(toy);

            }
        });

    }
    @Override
    public  void onBackPressed(){
        startActivity(new Intent(buttons_doctor.this,NavigateActivity.class));
        finish();
    }
}


