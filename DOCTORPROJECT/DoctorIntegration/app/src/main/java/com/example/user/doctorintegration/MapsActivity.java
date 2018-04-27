package com.example.user.doctorintegration;


import  android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.android.gms.maps.SupportMapFragment.*;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Location slocation;

    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    SharedPreferences mPref;
    String spinnerval,categoryval,gridval,areaval;
    private DatabaseReference mDatabase;
    // public static Object ServiceProviderInformation;
    private int Default_radius=8000;
    ArrayList<ServiceProviderInformation> mProvider=new ArrayList<ServiceProviderInformation>();
    private ArrayList<String> mkeys= new ArrayList<>();
    private ArrayList<String> mInfo= new ArrayList<>();
    private LatLng newlocation;
    private static LatLng latLng1;
    public Spinner spinner;



    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        spinner = (Spinner) findViewById(R.id.spinner);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // if(mMap==null) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //}
        mPref = getSharedPreferences("navigation", MODE_PRIVATE);
        spinnerval = mPref.getString("spinnerval", "");
        categoryval = mPref.getString("categoryval", "");
        gridval = mPref.getString("gridval", "");
        areaval = mPref.getString("areaval", "");
        Log.d("----logcat", "spinner=" + spinnerval);
        Log.d("----logcat", "category=" + categoryval);
        Log.d("----logcat", "gridval=" + gridval);
        Log.d("stupid", "log");
//        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                switch (position) {
//                    case 0:
//                        mPref.edit().putString("areaval", "Kuvempunagar").commit();
//
//                        LatLng nl=new LatLng(slocation.getLatitude(),slocation.getLongitude());
//
//                         mMap.addMarker(new MarkerOptions().position(nl).title("KUVEMPUN"));
//
//                        break;
//                    case 1:
//                        mPref.edit().putString("areaval", "Agrahara").commit();
//                        break;
//                    case 2:
//                        mPref.edit().putString("areaval", "Vijaynagar").commit();
//                        break;
//                    case 3:
//                        mPref.edit().putString("areaval", "metagalli").commit();
//                        break;
//
//                }
//
//
////                mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("ServiceProviderInformation");
//                mDatabase.keepSynced(true);
//                mDatabase.addChildEventListener(new ChildEventListener() {
//
//
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        double distance = 0;
//
//
//                        for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
//
//                            //ServiceProviderInformation user=Snapshot.getValue(ServiceProviderInformation.class);
//                            //String user1=dataSnapshot.child("latitude").getValue().toString();
//                            //String user2=dataSnapshot.child("latitude").getValue().toString();
//                            if (spinnerval.equalsIgnoreCase("doctor") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
//
//                                if (categoryval.equalsIgnoreCase("allopathy") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
//                                    Log.d("---maps", "come");
//
//                                    if (gridval.equalsIgnoreCase("orthopaedician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//
//                                        if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                        if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                    } else if (gridval.equalsIgnoreCase("diabetician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                                        if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                        if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                    } else if (gridval.equalsIgnoreCase("gynocologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                                        if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                        if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                    } else if (gridval.equalsIgnoreCase("general physician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                                        if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                        if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                    } else if (gridval.equalsIgnoreCase("neurologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                                        if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                        if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                    } else if (gridval.equalsIgnoreCase("cardiologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
////                            Log.d("mathew", "" + prov.getUsername());
////                            Log.d("mathew", "" + prov.getPhonenumber())
//                                        if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                        if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                    } else if (gridval.equalsIgnoreCase("dermatologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
////                            Log.d("mathew", "" + prov.getUsername());
////                            Log.d("mathew", "" + prov.getPhonenumber())
//                                        if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                        if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                    } else if (gridval.equalsIgnoreCase("opthalmologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
////                            Log.d("mathew", "" + prov.getUsername());
////                            Log.d("mathew", "" + prov.getPhonenumber())
//                                        if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                        if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                    } else if (gridval.equalsIgnoreCase("pediatrician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
////                            Log.d("mathew", "" + prov.getUsername());
////                            Log.d("mathew", "" + prov.getPhonenumber())
//                                        if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                        if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                    } else if (gridval.equalsIgnoreCase("dentist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                            if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                        if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//
//                                        if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                            Log.d("mathew", "there");
//                                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                            Log.d("----sony", "" + newlocation);
//                                            double check = markerreturn(distance);
//                                            Log.d("---plss", "come");
//                                            if (check < Default_radius) {
//
//
//                                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                            }
//                                        }
//                                    }
//
//
//                                } else if (categoryval.equalsIgnoreCase("ayurvedic") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
////                        Log.d("mathew", "" + prov.getUsername());
////                        Log.d("mathew", "" + prov.getPhonenumber());
//                                    if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//
//                                    if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//                                    if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//
//                                    if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//                                } else if (categoryval.equalsIgnoreCase("homeopathy") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
////                        Log.d("mathew", "" + prov.getUsername());
////                        Log.d("mathew", "" + prov.getPhonenumber());
//                                    if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//
//                                    if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//                                    if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//
//                                    if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//                                }
//
//
//                            } else if (spinnerval.equalsIgnoreCase("pharmacy") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
////                        Log.d("mathew", "" + prov.getUsername());
////                        Log.d("mathew", "" + prov.getPhonenumber());
//                                if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//
//                                if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//                                if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//
//                                if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//                            } else if (spinnerval.equalsIgnoreCase("pathology") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
////                        Log.d("mathew", "" + prov.getUsername());
////                        Log.d("mathew", "" + prov.getPhonenumber());
//                                if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//
//                                if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//                                if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//
//                                if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//                            } else if (spinnerval.equalsIgnoreCase("ambulance") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
////                        Log.d("mathew", "" + prov.getUsername());
////                        Log.d("mathew", "" + prov.getPhonenumber());
//                                if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//
//                                if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//                                if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//
//                                if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//                            } else if (spinnerval.equalsIgnoreCase("blood bank") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
//                                if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//
//                                if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//                                if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//
//                                if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                    Log.d("mathew", "there");
//                                    newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                    Log.d("----sony", "" + newlocation);
//                                    double check = markerreturn(distance);
//                                    Log.d("---plss", "come");
//                                    if (check < Default_radius) {
//
//
//                                        mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                    }
//                                }
//                            } else if (spinnerval.equalsIgnoreCase("Hospital") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
//                                if (categoryval.equalsIgnoreCase("allopathy") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
//                                    if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//
//                                    if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//                                    if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//
//                                    if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//
//                                } else if (categoryval.equalsIgnoreCase("ayurvedic") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
////                        Log.d("mathew", "" + prov.getUsername());
////                        Log.d("mathew", "" + prov.getPhonenumber());
//                                    if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//
//                                    if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//                                    if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//
//                                    if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//                                } else if (categoryval.equalsIgnoreCase("homeopathy") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
////                        Log.d("mathew", "" + prov.getUsername());
////                        Log.d("mathew", "" + prov.getPhonenumber());
//                                    if (areaval.equalsIgnoreCase("Kuvempunagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//
//                                    if (areaval.equalsIgnoreCase("Agrahara") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//                                    if (areaval.equalsIgnoreCase("Vijaynagar") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//
//                                    if (areaval.equalsIgnoreCase("metagalli") && dataSnapshot.child("spinner").getValue().toString().equalsIgnoreCase(areaval)) {
////                            Log.d("mathew", "" + prov.getPhonenumber());
//                                        Log.d("mathew", "there");
//                                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                                        Log.d("----sony", "" + newlocation);
//                                        double check = markerreturn(distance);
//                                        Log.d("---plss", "come");
//                                        if (check < Default_radius) {
//
//
//                                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                                        }
//                                    }
//                                }
//
//
//                            }
//                        }
//                    }
//
//
//
//
//
//
//
//                        @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                            String value = dataSnapshot.getValue().toString();
//                            String key = dataSnapshot.getKey();
//                            int index = mkeys.indexOf(key);
//                            Log.d("child","childdd"+dataSnapshot.child("latitude").getValue().toString());
//                            ;
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//                        String value = dataSnapshot.getValue().toString();
//                        String key = dataSnapshot.getKey();
//                        int index = mkeys.indexOf(key);
//                        Log.d("rem","remove"+dataSnapshot.getValue().toString());
//
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            }
//
//            }
//           @Override
//          public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//
//      });

//
//
////    private void getsortedlist() {
////        for (ServiceProviderInformation prov : mProvider) {
////
////
////
////    }


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.*/


    }



            @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;



        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("ServiceProviderInformation");
        mDatabase.keepSynced(true);

        mDatabase.addChildEventListener(new ChildEventListener() {


            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                double distance = 0;
                int size = (int) dataSnapshot.getChildrenCount(); //
                Marker[] allMarkers = new Marker[size];
               // mMap.clear();   //Assuming you're using mMap

                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                    //ServiceProviderInformation user=Snapshot.getValue(ServiceProviderInformation.class);
                    //String user1=dataSnapshot.child("latitude").getValue().toString();
                    //String user2=dataSnapshot.child("latitude").getValue().toString();
                    if (spinnerval.equalsIgnoreCase("doctor") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {

                        if (categoryval.equalsIgnoreCase("allopathy") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
                            Log.d("---maps", "come");

                            if (gridval.equalsIgnoreCase("orthopaedician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getPhonenumber());
                                Log.d("mathew", "there");
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                                Log.d("----sony", "" + newlocation);
//                                double check = markerreturn(distance);
//
//                                Log.d("---plss", "come");
//                                if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                               // }
                            } else if (gridval.equalsIgnoreCase("diabetician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber());
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                                Log.d("----sony", "" + newlocation);

                               // double check = markerreturn(distance);
                               // Log.d("radii", "" + check);
                               // if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                               // }


                            } else if (gridval.equalsIgnoreCase("gynaecologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber());
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                                Log.d("----sony", "" + newlocation);


                                //double check = markerreturn(distance);
                              //  if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                              //  }
                            } else if (gridval.equalsIgnoreCase("general physician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber());
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                                Log.d("----sony", "" + newlocation);


                               // double check = markerreturn(distance);
                              //  if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                              //  }
                            } else if (gridval.equalsIgnoreCase("neurologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber())
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                               // double check = markerreturn(distance);
                              //  if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                              //  }
                            } else if (gridval.equalsIgnoreCase("cardiologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber())
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                              //  double check = markerreturn(distance);
                             //   if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                              //  }
                            } else if (gridval.equalsIgnoreCase("dermatologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber())
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                               // double check = markerreturn(distance);
                             //   if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                              //  }
                            } else if (gridval.equalsIgnoreCase("opthalmologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber())
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                               Log.d("opthal",""+newlocation);
                              //  double check = markerreturn(distance);
                              //  if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                              //  }
                            } else if (gridval.equalsIgnoreCase("pediatrician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber())
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                    Log.d("pedia",""+newlocation);
                              //  double check = markerreturn(distance);
                              //  if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                              //  }
                            } else if (gridval.equalsIgnoreCase("dentist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
//                            Log.d("mathew", "" + prov.getUsername());
//                            Log.d("mathew", "" + prov.getPhonenumber())
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                               Log.d("log","dentist"+newlocation);
                               // double check = markerreturn(distance);
                              //  if (check < Default_radius) {


                                    mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                              //  }
                            }


                        } else if (categoryval.equalsIgnoreCase("ayurvedic") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                           // newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                            Log.d("----AyurveIfElseGrp", "" + newlocation);
//                            if (check < Default_radius) {

                                try{double  latitude = dataSnapshot.child("latitude").getValue(double.class);
                                    double  longitude = dataSnapshot.child("longitude").getValue(double.class);
                                    Log.d("----sony1", "" + latitude +longitude);

                                    LatLng latlng = new LatLng(latitude, longitude);
                                    //  mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                    double check = markerreturnTest(latlng);
                                   // Log.d("----AyurveCheck", "" + check);
                                    mMap.addMarker(new MarkerOptions().position(latlng).title(dataSnapshot.child("Username").getValue().toString()));

                                }catch(Exception exp){
                                    Log.d("---ExceptionAyrv",""+exp);
                                }

                            }
                         else if (categoryval.equalsIgnoreCase("homeopathy") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));


                            Log.d("----sony", "" + newlocation);


//                            double check = markerreturn(distance);
//                            if (check < Default_radius) {


                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                           // }
                        }
                    } else if (spinnerval.equalsIgnoreCase("pharmacy") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                        Log.d("----sony", "" + newlocation);


                       double check = markerreturn(distance);
                        if (check < Default_radius) {


                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                       }
                    } else if (spinnerval.equalsIgnoreCase("pathology") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                        Log.d("-sony", "" + newlocation);


                       // double check = markerreturn(distance);
                      //  if (check < Default_radius) {


                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                       // }
                    } else if (spinnerval.equalsIgnoreCase("ambulance") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                        Log.d("----sony", "" + newlocation);


                      //  double check = markerreturn(distance);
                       // Log.d("-ambu", "lance" + check);
                       // if (check < Default_radius) {
                            Log.d("-----ambu", "lance");


                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                       // }
                    } else if (spinnerval.equalsIgnoreCase("blood bank") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
                        Log.d("--mmm","ddd");

                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));




                      //  double check = markerreturn(distance);
                     //  if (check < Default_radius) {


                            mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                       // }
                    } else if (spinnerval.equalsIgnoreCase("Hospital") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
                       // if (categoryval.equalsIgnoreCase("allopathy") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {

                            Log.d("---maps", "come");

//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                      //  newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));

                      //  Log.d("----sony1", "" + newlocation);
                     //   for(int i=0;i<size;i++){
                            try{

//                              newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class),
//                                      dataSnapshot.child("longitude").getValue(double.class));


                             double  latitude = dataSnapshot.child("latitude").getValue(Double.class);
                             double  longitude = dataSnapshot.child("longitude").getValue(Double.class);
                               Log.d("----sony1", "" + latitude +longitude);

                               LatLng latlng = new LatLng(latitude, longitude);
                              //  mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                mMap.addMarker(new MarkerOptions().position(latlng).title(dataSnapshot.child("Username").getValue().toString()));

                            }catch (Exception exp){
                                Log.d("--Exception",""+exp);
                            }
                       // }


                          //  double check = markerreturn(distance);

/*
                        double check = markerreturnTest(newlocation);
                        Log.d("-ambu", "lance" + check);
                            if (check < Default_radius) {
                                Log.d("---hosp","plsss"+check);
                                Log.d("-----ambu1", "lance");
                               mMap.addMarker(new MarkerOptions().position(newlocation).title("test"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(newlocation));
                                // }
                        }else{
                                Log.d("--data","null");
                            }
*/

//                        else if (categoryval.equalsIgnoreCase("ayurvedic") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
////                        Log.d("mathew", "" + prov.getUsername());
////                        Log.d("mathew", "" + prov.getPhonenumber());
//                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//                            Log.d("----sony", "" + newlocation);
//
//
//                            double check = markerreturn(distance);
//                            if (check < Default_radius) {
//
//
//                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                            }
//                        } else if (categoryval.equalsIgnoreCase("homeopathy") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
////                        Log.d("mathew", "" + prov.getUsername());
////                        Log.d("mathew", "" + prov.getPhonenumber());
//                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
//
//
//                            Log.d("----sony", "" + newlocation);
//
//
//                            double check = markerreturn(distance);
//                            if (check < Default_radius) {
//
//
//                                mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
//                            }
                      //  }


                    }
                }


                    String key = dataSnapshot.getKey();
                    mkeys.add(key);
                    String value = dataSnapshot.child("Username").getValue().toString();
                    //Log.d("--info","come"+value);
                    mInfo.add(value);
                    String value1 = dataSnapshot.child("Phonenumber").getValue().toString();
                    mInfo.add(value1);
                    String value2 = dataSnapshot.child("Address").getValue().toString();
                    mInfo.add(value2);
                    String value3 = dataSnapshot.child("Email").getValue().toString();
                    mInfo.add(value3);
                    String value4 = dataSnapshot.child("time").getValue().toString();
                    mInfo.add(value4);
                    String value5 = dataSnapshot.child("ratings").getValue().toString();
                    mInfo.add(value5);
                    String value6 = dataSnapshot.child("ratings").getValue().toString();
                    mInfo.add(value6);
                    String value7 = dataSnapshot.child("ratings").getValue().toString();
                    mInfo.add(value7);
                    Log.d("array","list"+mInfo);

            }

            //LatLng newlocation= new LatLng(dataSnapshot.child("latitude").getValue(double.class),dataSnapshot.child("longitude").getValue(double.class) );


            //Log.d("----sony",""+user2);


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();
                int index = mkeys.indexOf(key);
                Log.d("child","childdd"+dataSnapshot.child("latitude").getValue().toString());
                ;
                //mProvider.set(index,value);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();
                int index = mkeys.indexOf(key);
                Log.d("rem","remove"+dataSnapshot.getValue().toString());
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }

    @Override
    public  void onBackPressed(){
        super.onBackPressed();

/*        if(categoryval=="allopathy" )
        startActivity(new Intent(MapsActivity.this,GridActivity.class));
        else
            if(categoryval=="ayurvedic" || categoryval=="homeopathy")
        {
            startActivity(new Intent(MapsActivity.this,buttons_hospital.class));
        }
        finish();*/

    }



    public double markerreturn(double distance) {
        Location locationA = new Location("POINT A");
        locationA.setLatitude(latLng1.latitude);
        locationA.setLongitude(latLng1.longitude);
        Location locationB = new Location("POINT B");
        locationB.setLatitude(newlocation.latitude);
        locationB.setLongitude(newlocation.longitude);
        distance = locationA.distanceTo(locationB);
        Log.d("--dayavittu","baa"+distance);

        return distance;
    }

    public double markerreturnTest(LatLng locallatlng) {
        Location locationA = new Location("POINT A");
        locationA.setLatitude(latLng1.latitude);
        locationA.setLongitude(latLng1.longitude);
        Location locationB = new Location("POINT B");
        locationB.setLatitude(locallatlng.latitude);
        locationB.setLongitude(locallatlng.longitude);
      double  distance = locationA.distanceTo(locationB);
        Log.d("--dayavittu","baa"+distance);

        return(distance);
    }


    protected synchronized void buildGoogleApiClient() {
        Log.d("--map","common");
        try{
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();


        }catch(Exception exp){
            Log.d("--BuildGoogleExp",""+exp);

        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(final Bundle bundle) {

        mLocationRequest =  LocationRequest.create();
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Location location=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            latLng1 = new LatLng(location.getLatitude(), location.getLongitude());

                Log.d("--OnConnectMap",""+latLng1);

        }



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {


        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        latLng1 = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        Geocoder geocoder = new Geocoder(getApplicationContext());
        markerOptions.position(latLng1);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));



        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 100000;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}