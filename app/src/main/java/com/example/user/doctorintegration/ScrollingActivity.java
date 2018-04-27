package com.example.user.doctorintegration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.user.doctorintegration.R;

public class ScrollingActivity extends AppCompatActivity {

    private ImageButton callbtn;
    public TextView name;
    public TextView addr;
    public TextView phone;
    public TextView time;
    public TextView email;
    public TextView rating;
    public TextView rare;
    public TextView discount;
    public Button appointment;
    public Button rate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        name = (TextView) findViewById(R.id.namedet);
        addr = (TextView) findViewById(R.id.addrdet);
        phone = (TextView) findViewById(R.id.phnodet);
        time = (TextView) findViewById(R.id.timedet);
        email = (TextView) findViewById(R.id.textemail);
        rating = (TextView) findViewById(R.id.ratedet);
        rare = (TextView) findViewById(R.id.textrare);
        discount = (TextView) findViewById(R.id.textdiscount);
        appointment = (Button) findViewById(R.id.appbutton);
        rate = (Button) findViewById(R.id.ratebutton);

        Intent fromMap = getIntent();

        String usernamedet = fromMap.getStringExtra("map_detail_user");
        name.setText(usernamedet);

        String addressdet = fromMap.getStringExtra("map_detail_addr");
        addr.setText(addressdet);

        String phonedet = fromMap.getStringExtra("map_detail_pho");
        phone.setText(phonedet);

        String timingsdet = fromMap.getStringExtra("map_detail_time");
        time.setText(timingsdet);

        String emaildet = fromMap.getStringExtra("map_detail_email");
        email.setText(emaildet);

        String ratingdet = fromMap.getStringExtra("map_detail_rate");
        rating.setText(ratingdet);

        String raredet = fromMap.getStringExtra("map_detail_raremedi");
        rare.setText(raredet);

        String discountdet = fromMap.getStringExtra("map_detail_discount");
        discount.setText(discountdet);





        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageButton callbtn = (ImageButton)findViewById(R.id.imageButton2);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Replace with your own action",Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                });


                callbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:0123456789"));
                        startActivity(intent);
                    }
                });


            }
        });
    }
}

//package com.example.user.doctorintegration;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//public class ScrollingActivity extends AppCompatActivity {
//
//    private ImageButton callbtn;
//    public TextView name;
//    public TextView addr;
//    public TextView phone;
//    public TextView time;
//    public TextView email;
//    public TextView rating;
//    public TextView rare;
//    public TextView discount;
//    public Button appointment;
//    public Button rate;
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scrolling);
//
//
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//         name = (TextView) findViewById(R.id.namedet);
//         addr = (TextView) findViewById(R.id.addrdet);
//         phone = (TextView) findViewById(R.id.phnodet);
//         time = (TextView) findViewById(R.id.timedet);
//         email = (TextView) findViewById(R.id.textemail);
//         rating = (TextView) findViewById(R.id.ratedet);
//         rare = (TextView) findViewById(R.id.textrare);
//         discount = (TextView) findViewById(R.id.textdiscount);
//         appointment = (Button) findViewById(R.id.appbutton);
//         rate = (Button) findViewById(R.id.ratebutton);
//
//         Intent fromMap = getIntent();
//
//         try {
//             String usernamedet = fromMap.getStringExtra("map_detail_user");
//             name.setText(usernamedet);
//
//         }catch (Exception exp) {
//             Log.d("detail", ""+exp);
//         }
//
//
//        setSupportActionBar(toolbar);
//        if(getSupportActionBar() != null)
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        final ImageButton callbtn = (ImageButton)findViewById(R.id.imageButton2);
//        callbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Intent toy1 = new Intent(ScrollingActivity.this,MainActivity.class);
//                //startActivity(toy1);
//
//                //Button fab = (Button) findViewById(R.id.button);
//                callbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Snackbar.make(view, "Replace with your own action",Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//
//                    }
//                });
//
//                //Button btn = (Button) findViewById(R.id.button);
//
//                callbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(Intent.ACTION_DIAL);
//                        intent.setData(Uri.parse("tel:0123456789"));
//                        startActivity(intent);
//                    }
//                });
//
//
//            }
//        });
//    }
//}
