package com.example.user.doctorintegration;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.doctor, container, false);
        button1 = (Button) rootView.findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(getActivity(), GridActivity.class);
                startActivity(toy);

            }
        });
        button2 = (Button) rootView.findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(v.getContext(), MapsActivity.class);
                toy.putExtra("KEY_BONE","doctor1");
                Log.d("---doctor","unfs");
                startActivity(toy);

            }
        });


        button3 = (Button) rootView.findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(v.getContext(), MapsActivity.class);
                toy.putExtra("KEY_BONE","doctor2");
                Log.d("---doctor2","unfs");
                startActivity(toy);

            }
        });


        return rootView;
    }
}


