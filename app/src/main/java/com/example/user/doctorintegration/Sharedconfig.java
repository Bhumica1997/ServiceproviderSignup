package com.example.user.doctorintegration;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by qwinix on 29/3/18.
 */

public class Sharedconfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public Sharedconfig(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(context.getResources().getString(R.string.login_preference),Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status){

        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_preference_status),status);
        editor.commit();

    }

    public boolean readLoginStatus(){
        boolean status=false;
        status=sharedPreferences.getBoolean(context.getResources().getString(R.string.login_preference_status),false);
        return status;
    }
}



