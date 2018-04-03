package com.example.user.doctorintegration;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by USER on 3/22/2018.
 */

public class ImageAdapter extends BaseAdapter {
    private Context CTX;
    private Integer image_id[] = {R.drawable.diabeties,
            R.drawable.opthal, R.drawable.ortho, R.drawable.pregnant,
            R.drawable.teeth, R.drawable.skin, R.drawable.generalphysician,
            R.drawable.neurology, R.drawable.pediatrician};

    public ImageAdapter( Context CTX) {
        this.CTX = CTX;
    }


    @Override
    public int getCount() {
        return image_id.length;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ImageView img;
        if(arg1 == null) {
            img = new ImageView(CTX);
            img.setLayoutParams(new GridView.LayoutParams(200,200));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setPadding(8,8,8,8);
        }
        else {
            img = (ImageView) arg1;
        }
        img.setImageResource(image_id[arg0]);
        return img;
        //  return null;
    }
}
