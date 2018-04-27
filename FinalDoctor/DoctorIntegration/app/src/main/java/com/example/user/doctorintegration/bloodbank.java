package com.example.user.doctorintegration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by USER on 3/25/2018.
 */

public class bloodbank extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.bloodbank,container,false);
        final View rootView = inflater.inflate(R.layout.bloodbank, container, false);
        //Intent toy2 = new Intent(getActivity(), MapsActivity.class);
        //toy2.putExtra("KEY_BONE", "blood bank");
        //startActivity(toy2);


        return rootView;

    }
}




