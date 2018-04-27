package com.example.user.doctorintegration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by USER on 3/30/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by qwinix on 13/2/18.
 */

public class doctor extends Fragment {
    public Button button1;
    public Button button2;
    public Button button3;
    SharedPreferences mPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.doctor, container, false);

        mPref = this.getActivity().getSharedPreferences("navigation", Context.MODE_PRIVATE);

        button1 = (Button) rootView.findViewById(R.id.button);
        button2 = (Button) rootView.findViewById(R.id.button2);
        button3 = (Button) rootView.findViewById(R.id.button3);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPref.edit().putString("categoryval","allopathy").commit();
                Intent toy = new Intent(getActivity(), GridActivity.class);

                Log.d("----grid","act");
                startActivity(toy);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(v.getContext(), MapsActivity.class);
                mPref.edit().putString("categoryval","ayurvedic").commit();

                //toy.putExtra("KEY_BONE","doctor1");
                Log.d("---doctor","unfs");
                startActivity(toy);

            }
        });



        button3.setOnClickListener(new View.OnClickListener() {
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


