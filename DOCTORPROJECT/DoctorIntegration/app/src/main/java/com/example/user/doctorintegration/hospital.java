package com.example.user.doctorintegration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by USER on 4/11/2018.
 */

public class hospital extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.hospital, container, false);
//        Intent toy2 = new Intent(getActivity(), MapsActivity.class);
//        toy2.putExtra("KEY_BONE","pathology");
//
//        startActivity(toy2);

        return rootView;
    }
}
