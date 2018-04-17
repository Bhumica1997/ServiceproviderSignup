package com.example.user.doctorintegration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class GridActivity extends AppCompatActivity  {
    GridLayout mainGrid;
    SharedPreferences mPref;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        mPref=getSharedPreferences("navigation",MODE_PRIVATE);
        setToggleEvent(mainGrid);
        setSingleEvent(mainGrid);
    }

    private void setToggleEvent(GridLayout mainGrid) {

    }

    private void setSingleEvent(GridLayout mainGrid) {
        for(int i=0;i<mainGrid.getChildCount();i++) {

            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;



           cardView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   switch (view.getId()) {

                       case R.id.neuroid:
                           //Toast.makeText(this, "Neuro", Toast.LENGTH_LONG).show();

                           Intent intent = new Intent(GridActivity.this, MapsActivity.class);
                           startActivity(intent);
                           break;

                       case R.id.orthoid:
                           Intent intent1 = new Intent(GridActivity.this, MapsActivity.class);
                           mPref.edit().putString("gridval","orthopaedic").commit();
                           startActivity(intent1);
                           break;
                       case R.id.dermaid:
                           intent = new Intent(GridActivity.this, MapsActivity.class);
                           mPref.edit().putString("gridval","dermatologist").commit();
                           startActivity(intent);
                           break;
                       case R.id.pregid:
                           intent = new Intent(GridActivity.this, MapsActivity.class);
                           mPref.edit().putString("gridval","gynocologist").commit();
                           startActivity(intent);
                           break;
                       case R.id.kidid:
                           intent = new Intent(GridActivity.this, MapsActivity.class);
                           mPref.edit().putString("gridval","pediatrician").commit();
                           startActivity(intent);
                           break;
                       case R.id. heartid:
                           intent = new Intent(GridActivity.this, MapsActivity.class);
                           mPref.edit().putString("gridval","cardiology").commit();
                           startActivity(intent);
                           break;
                       case R.id.eyeid:
                           intent = new Intent(GridActivity.this, MapsActivity.class);
                           mPref.edit().putString("gridval","opthamologist").commit();
                           startActivity(intent);
                           break;
                       case R.id.  diabeticid:
                            intent = new Intent(GridActivity.this, MapsActivity.class);
                           mPref.edit().putString("gridval","diabeties").commit();
                           startActivity(intent);
                           break;
                       case R.id.generalid:
                           intent = new Intent(GridActivity.this, MapsActivity.class);
                           mPref.edit().putString("gridval","general").commit();
                           startActivity(intent);
                           break;
                       case R.id.dentistid:
                           intent = new Intent(GridActivity.this, MapsActivity.class);
                           mPref.edit().putString("gridval","dentist").commit();
                           startActivity(intent);
                           break;
                   }
               }
           });
        }
    }

}
            //@Override
//                public void onClick(View view) {
//
//                    if(finalI == 0)
//                    {
//                        Intent intent = new Intent(GridActivity.this, Neurologist.class);
//                        startActivity(intent);
//                    }
//
//                }
//            });































//package com.example.user.doctorintegration;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.GridView;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.GridView;
//import android.widget.LinearLayout;
//
//
//public class GridActivity extends Activity {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_grid);
//         @SuppressLint("WrongViewCast") GridView gridView = (GridView) findViewById(R.id.gridview);
//        //LinearLayout linearLayout=(LinearLayout) findViewById(R.id.linear);
//
//        // Instance of ImageAdapter Class
//        gridView.setAdapter(new ImageAdapter(this));
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//              /*.d("---i","pos"+position);
//                Intent intent;
//                intent =  new Intent(androidgridlayoutactivity.this,MapsActivity.class);
//                Log.d("---i","pos");
//                intent.putExtra("KEY_BONE","bone");
//                startActivity(intent);*/
//                // Sending image id to FullScreenActivity
//
//
//                Intent intent=null ;
//
//                // passing array index
//
//                switch(position)
//                {
//                    case 0:
//                        intent= new Intent(v.getContext(),MapsActivity.class);
//                        Log.d("---i","pos");
//                        intent.putExtra("KEY_BONE","bone");
//                        startActivity(intent);
//
//                        break;
//
//                    case 1:
//                        intent =  new Intent(v.getContext(), MapsActivity.class);
//                        Log.d("---i","pos1");
//                        intent.putExtra("KEY_BONE","DBC");
//                        startActivity(intent);
//
//                        break;
//                    case 2:
//                        intent =  new Intent(v.getContext(), MapsActivity.class);
//                        Log.d("---i","pos2");
//                        intent.putExtra("KEY_BONE","gyno");
//                        startActivity(intent);
//                        break;
//                    case 3:
//                        intent =  new Intent(v.getContext(), MapsActivity.class);
//                        Log.d("---i","pos3");
//                        intent.putExtra("KEY_BONE","general");
//                        startActivity(intent);
//                        break;
//                    case 4:
//                        intent =  new Intent(v.getContext(), MapsActivity.class);
//                        Log.d("---i","pos4");
//                        intent.putExtra("KEY_BONE","neuro");
//                        startActivity(intent);
//                        break;
//                    case 5:
//                        intent =  new Intent(v.getContext(), MapsActivity.class);
//                        Log.d("---i","pos5");
//                        intent.putExtra("KEY_BONE","derma");
//                        startActivity(intent);
//
//                        break;
//                    case 6:
//                        intent =  new Intent(v.getContext(), MapsActivity.class);
//                        Log.d("---i","pos6");
//                        intent.putExtra("KEY_BONE","pedia");
//                        startActivity(intent);
//
//                        break;
//                    case 7:
//                        intent =  new Intent(v.getContext(), MapsActivity.class);
//                        Log.d("---i","pos7");
//                        intent.putExtra("KEY_BONE","cardio");
//                        startActivity(intent);
//
//                        break;
//
//
//
//
//
//
//
//                }
//
//
//            }
//        });
//
//
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
