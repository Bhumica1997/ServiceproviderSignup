package com.example.user.scrollingactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ScrollingActivity extends AppCompatActivity {

    private ImageButton callbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageButton callbtn = (ImageButton) findViewById(R.id.callbutton);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent toy1 = new Intent(ScrollingActivity.this,MainActivity.class);
                //startActivity(toy1);

                //Button fab = (Button) findViewById(R.id.button);
                // callbtn.setOnClickListener(new View.OnClickListener() {
                //   @Override
                // public void onClick(View view) {
                //   Snackbar.make(view, "Replace with your own action",Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();

            }
        });


        //Button btn = (Button) findViewById(R.id.button);

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });
    }
}

