package com.example.user.doctorintegration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by USER on 4/11/2018.
 */

public class hospital extends android.support.v4.app.Fragment {
    public Button button4;
    public Button button5;
    public Button button6;
    SharedPreferences mPref;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.hospital, container, false);
        mPref = this.getActivity().getSharedPreferences("navigation", Context.MODE_PRIVATE);
        button4 = (Button) rootView.findViewById(R.id.button);
        button5 = (Button) rootView.findViewById(R.id.button2);
        button6= (Button) rootView.findViewById(R.id.button3);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPref.edit().putString("categoryval","allopathy").commit();
                Intent toy = new Intent(getActivity(), MapsActivity.class);

                Log.d("----grid","act");
                startActivity(toy);

            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(v.getContext(), MapsActivity.class);
                mPref.edit().putString("categoryval","ayurvedic").commit();

                //toy.putExtra("KEY_BONE","doctor1");
                Log.d("---doctor","unfs");
                startActivity(toy);

            }
        });



        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPref.edit().putString("categoryval","homeopathy").commit();
                Intent toy = new Intent(v.getContext(), MapsActivity.class);
                // toy.putExtra("KEY_BONE","doctor2");
                Log.d("---doctor2","unfs");
                startActivity(toy);

            }
        });

        return rootView;
    }
}
