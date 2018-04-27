package com.example.user.doctorintegration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Splashscreen extends AppCompatActivity {

    private int SLEEP_TIMER=2;
    private Sharedconfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceConfig=new Sharedconfig(getApplicationContext());


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();

        LogoLauncher logoLauncher=new LogoLauncher();
        logoLauncher.start();
    }

    private class LogoLauncher extends Thread{

        public void run() {

            try{
                sleep(1000 * SLEEP_TIMER);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(preferenceConfig.readLoginStatus()==false){
                Intent toy = new Intent(Splashscreen.this, MainActivity.class);

                startActivity(toy);
                Splashscreen.this.finish();

            }


            else {
                Intent intent = new Intent(Splashscreen.this, NavigateActivity.class);


                startActivity(intent);
                Splashscreen.this.finish();
            }
        }
    }
}



