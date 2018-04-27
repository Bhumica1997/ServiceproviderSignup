package com.example.user.doctorintegration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class NavigateActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences mPref;
   private Sharedconfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
       preferenceConfig=new Sharedconfig(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        fab.setOnClickListener(new View.OnClickListener() {
//            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mPref=getSharedPreferences("navigation",MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

     /*   DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            preferenceConfig.writeLoginStatus(false);
            Intent toy1 = new Intent(NavigateActivity.this, MainActivity.class);

            startActivity(toy1);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
    private void displaySelectedScreen(int id) {
        Fragment fragment=null;
        switch(id) {
            case R.id.nav_hospital:
                mPref.edit().putString("spinnerval","hospital").commit();

               //fragment = new hospital();
                Intent fromHospitals = new Intent(NavigateActivity.this, MapsActivity.class);

           // fromHospitals.putExtra("KEY_BONE","pathology");
                startActivity(fromHospitals);


                break;
            case R.id.nav_doctor:
                mPref.edit().putString("spinnerval","doctor").commit();
             //Intent fromDoctor = new Intent(NavigateActivity.this, MapsActivity.class);

             fragment = new doctor();


           //     startActivity(fromDoctor);
                //Intent fromDoctor = new Intent(NavigateActivity.this, buttons_doctor.class);

//                fromDoctor.putExtra("KEY_BONE","pathology");
//                startActivity(fromDoctor);



                break;
            case R.id.nav_pathology:
                //fragment = new pharmacy();

                Intent fromPathology = new Intent(NavigateActivity.this, MapsActivity.class);
                mPref.edit().putString("spinnerval","pathology").commit();

              //  fromPathology.putExtra("KEY_BONE","pathology");
                startActivity(fromPathology);

                break;
            case R.id.nav_pharmacy:
                //fragment = new pathology();

                Intent toy2 = new Intent(NavigateActivity.this, MapsActivity.class);
                mPref.edit().putString("spinnerval","pharmacy").commit();

               // toy2.putExtra("KEY_BONE","pharmacy");
                startActivity(toy2);

                break;
            case R.id.nav_ambulance:
                //fragment = new ambulance();

                Intent fromAmbulance = new Intent(NavigateActivity.this, MapsActivity.class);
                mPref.edit().putString("spinnerval","ambulance").commit();

                //fromAmbulance.putExtra("KEY_BONE","ambulance");
                startActivity(fromAmbulance);

                break;
            case R.id.nav_bloodbank:
                Intent frombloodbank = new Intent(NavigateActivity.this, MapsActivity.class);
                mPref.edit().putString("spinnerval","blood bank").commit();
                startActivity(frombloodbank);

                break;

        }
        if(fragment!=null) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_navigate,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }
}
