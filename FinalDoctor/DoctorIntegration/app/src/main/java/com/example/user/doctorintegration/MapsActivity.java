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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

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
    String spinnerval, categoryval, gridval, areaval;
    private DatabaseReference mDatabase;
    // public static Object ServiceProviderInformation;
    private int Default_radius = 8000;
    ArrayList<ServiceProviderInformation> mProvider = new ArrayList<ServiceProviderInformation>();
    private ArrayList<String> mkeys = new ArrayList<>();
    private ArrayList<String> mInfo = new ArrayList<>();
    private LatLng newlocation;
    private static LatLng latLng1;
    public Spinner spinner;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    List<ServiceProvideDetailModel> mServiceProvider = new ArrayList<ServiceProvideDetailModel>();
    private Map<Marker, ServiceProvideDetailModel> markerMap = new HashMap<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        spinner = (Spinner) findViewById(R.id.spinner);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mPref = getSharedPreferences("navigation", MODE_PRIVATE);
        spinnerval = mPref.getString("spinnerval", "");
        categoryval = mPref.getString("categoryval", "");
        gridval = mPref.getString("gridval", "");
        areaval = mPref.getString("areaval", "");
        Log.d("----logcat", "spinner=" + spinnerval);
        Log.d("----logcat", "category=" + categoryval);
        Log.d("----logcat", "gridval=" + gridval);
        Log.d("stupid", "log");
        Log.d("---plss", "come");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                try {
                    ServiceProvideDetailModel serviceProviderModelData = markerMap.get(marker);
                    Log.d("dataam", "" + serviceProviderModelData.getAddress());
                    Intent fromMap = new Intent(MapsActivity.this, ScrollingActivity.class);
                    fromMap.putExtra("map_detail_user",serviceProviderModelData.getUsername().toString());
                    fromMap.putExtra("map_detail_addr",serviceProviderModelData.getAddress().toString());
                    fromMap.putExtra("map_detail_pho",serviceProviderModelData.getPhoneNumber().toString());
                    fromMap.putExtra("map_detail_time",serviceProviderModelData.getTime().toString());
                    fromMap.putExtra("map_detail_email",serviceProviderModelData.getEmail().toString());
                    fromMap.putExtra("map_detail_rate",serviceProviderModelData.getRatings());
                    fromMap.putExtra("map_detail_raremedi",serviceProviderModelData.getRarestMedicine().toString());
                    fromMap.putExtra("map_detail_discount",serviceProviderModelData.getDiscounts().toString());
                    startActivity(fromMap);
                }catch (Exception   exp){
                    Log.d("excp",""+exp);
                }
                return false;
            }
        });
        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("ServiceProviderInformation");
        mDatabase.keepSynced(true);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                double distance = 0;

                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    ServiceProvideDetailModel ambluanceObj = new ServiceProvideDetailModel();
                    if (spinnerval.equalsIgnoreCase("doctor") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
                        if (categoryval.equalsIgnoreCase("allopathy") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
                            Log.d("---maps", "allopathy");
                            if (gridval.equalsIgnoreCase("orthopaedician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                Log.d("----sony", "" + newlocation);
                                double check = markerreturn(distance);
                                if (check < Default_radius) {
                                    ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                    Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                    ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                    ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                    ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                    ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                    ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                    ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                    ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                    ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                    ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                    ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                    ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                    ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                    ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                    ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                    mServiceProvider.add(ambluanceObj);
                                    Log.d("object", "" + mServiceProvider);
                                    MarkerOptions opt = new MarkerOptions();
                                    opt.position(newlocation);
                                    opt.title(dataSnapshot.child("Username").getValue(String.class));
                                    Marker marker = mMap.addMarker(opt);
                                    markerMap.put(marker, ambluanceObj);
                                }
                            } else if (gridval.equalsIgnoreCase("diabetician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                Log.d("----sony", "" + newlocation);
                                double check = markerreturn(distance);
                                if (check < Default_radius) {
                                    ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                    Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                    ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                    ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                    ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                    ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                    ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                    ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                    ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                    ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                    ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                    ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                    ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                    ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                    ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                    ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                    mServiceProvider.add(ambluanceObj);
                                    Log.d("object", "" + mServiceProvider);
                                    MarkerOptions opt = new MarkerOptions();
                                    opt.position(newlocation);
                                    opt.title(dataSnapshot.child("Username").getValue(String.class));
                                    Marker marker = mMap.addMarker(opt);
                                    markerMap.put(marker, ambluanceObj);
                                }
                            } else if (gridval.equalsIgnoreCase("gynaecologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                Log.d("----sony", "" + newlocation);
                                double check = markerreturn(distance);
                                if (check < Default_radius) {
                                    ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                    Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                    ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                    ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                    ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                    ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                    ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                    ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                    ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                    ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                    ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                    ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                    ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                    ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                    ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                    ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                    mServiceProvider.add(ambluanceObj);
                                    Log.d("object", "" + mServiceProvider);
                                    MarkerOptions opt = new MarkerOptions();
                                    opt.position(newlocation);
                                    opt.title(dataSnapshot.child("Username").getValue(String.class));
                                    Marker marker = mMap.addMarker(opt);
                                    markerMap.put(marker, ambluanceObj);
                                }
                            } else if (gridval.equalsIgnoreCase("general physician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                Log.d("----sony", "" + newlocation);
                                double check = markerreturn(distance);
                                if (check < Default_radius) {
                                    ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                    Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                    ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                    ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                    ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                    ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                    ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                    ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                    ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                    ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                    ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                    ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                    ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                    ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                    ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                    ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                    mServiceProvider.add(ambluanceObj);
                                    Log.d("object", "" + mServiceProvider);
                                    MarkerOptions opt = new MarkerOptions();
                                    opt.position(newlocation);
                                    opt.title(dataSnapshot.child("Username").getValue(String.class));
                                    Marker marker = mMap.addMarker(opt);
                                    markerMap.put(marker, ambluanceObj);
                                }
                            } else if (gridval.equalsIgnoreCase("neurologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                double check = markerreturn(distance);
                                if (check < Default_radius) {
                                    ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                    Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                    ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                    ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                    ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                    ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                    ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                    ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                    ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                    ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                    ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                    ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                    ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                    ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                    ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                    ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                    mServiceProvider.add(ambluanceObj);
                                    Log.d("object", "" + mServiceProvider);
                                    MarkerOptions opt = new MarkerOptions();
                                    opt.position(newlocation);
                                    opt.title(dataSnapshot.child("Username").getValue(String.class));
                                    Marker marker = mMap.addMarker(opt);
                                    markerMap.put(marker, ambluanceObj);
                                }
                            } else if (gridval.equalsIgnoreCase("cardiologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                double check = markerreturn(distance);
                                if (check < Default_radius) {
                                    ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                    Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                    ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                    ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                    ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                    ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                    ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                    ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                    ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                    ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                    ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                    ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                    ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                    ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                    ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                    ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                    mServiceProvider.add(ambluanceObj);
                                    Log.d("object", "" + mServiceProvider);
                                    MarkerOptions opt = new MarkerOptions();
                                    opt.position(newlocation);
                                    opt.title(dataSnapshot.child("Username").getValue(String.class));
                                    Marker marker = mMap.addMarker(opt);
                                    markerMap.put(marker, ambluanceObj);
                                }
                            } else if (gridval.equalsIgnoreCase("dermatologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                double check = markerreturn(distance);
                                if (check < Default_radius) {
                                    ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                    Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                    ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                    ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                    ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                    ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                    ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                    ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                    ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                    ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                    ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                    ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                    ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                    ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                    ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                    ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                    mServiceProvider.add(ambluanceObj);
                                    Log.d("object", "" + mServiceProvider);
                                    MarkerOptions opt = new MarkerOptions();
                                    opt.position(newlocation);
                                    opt.title(dataSnapshot.child("Username").getValue(String.class));
                                    Marker marker = mMap.addMarker(opt);
                                    markerMap.put(marker, ambluanceObj);
                                }
                            } else if (gridval.equalsIgnoreCase("opthalmologist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                Log.d("opthal", "" + newlocation);
                                double check = markerreturn(distance);
                                if (check < Default_radius) {
                                    ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                    Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                    ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                    ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                    ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                    ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                    ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                    ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                    ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                    ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                    ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                    ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                    ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                    ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                    ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                    ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                    mServiceProvider.add(ambluanceObj);
                                    Log.d("object", "" + mServiceProvider);
                                    MarkerOptions opt = new MarkerOptions();
                                    opt.position(newlocation);
                                    opt.title(dataSnapshot.child("Username").getValue(String.class));
                                    Marker marker = mMap.addMarker(opt);
                                    markerMap.put(marker, ambluanceObj);
                                }
                            } else if (gridval.equalsIgnoreCase("pediatrician") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                Log.d("pedia", "" + newlocation);
                                double check = markerreturn(distance);
                                if (check < Default_radius) {
                                    ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                    Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                    ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                    ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                    ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                    ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                    ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                    ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                    ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                    ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                    ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                    ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                    ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                    ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                    ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                    ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                    mServiceProvider.add(ambluanceObj);
                                    Log.d("object", "" + mServiceProvider);
                                    MarkerOptions opt = new MarkerOptions();
                                    opt.position(newlocation);
                                    opt.title(dataSnapshot.child("Username").getValue(String.class));
                                    Marker marker = mMap.addMarker(opt);
                                    markerMap.put(marker, ambluanceObj);
                                }
                            } else if (gridval.equalsIgnoreCase("dentist") && dataSnapshot.child("Spinner3").getValue().toString().equalsIgnoreCase(gridval)) {
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                Log.d("log", "dentist" + newlocation);
                                double check = markerreturn(distance);
                                if (check < Default_radius) {
                                    ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                    Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                    ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                    ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                    ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                    ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                    ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                    ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                    ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                    ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                    ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                    ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                    ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                    ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                    ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                    ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                    mServiceProvider.add(ambluanceObj);
                                    Log.d("object", "" + mServiceProvider);
                                    MarkerOptions opt = new MarkerOptions();
                                    opt.position(newlocation);
                                    opt.title(dataSnapshot.child("Username").getValue(String.class));
                                    Marker marker = mMap.addMarker(opt);
                                    markerMap.put(marker, ambluanceObj);
                                }
                            }
                        } else if (categoryval.equalsIgnoreCase("ayurvedic") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
                            Log.d("----AyurveIfElseGrp", "" + newlocation);
                            try {
                                newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                                double check = markerreturn(distance);
                                if (check < Default_radius) {
                                    ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                    Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                    ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                    ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                    ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                    ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                    ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                    ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                    ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                    ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                    ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                    ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                    ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                    ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                    ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                    ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                    mServiceProvider.add(ambluanceObj);
                                    Log.d("object", "" + mServiceProvider);
                                    MarkerOptions opt = new MarkerOptions();
                                    opt.position(newlocation);
                                    opt.title(dataSnapshot.child("Username").getValue(String.class));
                                    Marker marker = mMap.addMarker(opt);
                                    markerMap.put(marker, ambluanceObj);
                                }
                            } catch (Exception exp) {
                                Log.d("---ExceptionAyrv", "" + exp);
                            }
                        } else if (categoryval.equalsIgnoreCase("homeopathy") && dataSnapshot.child("Spinner2").getValue().toString().equalsIgnoreCase(categoryval)) {
                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                            Log.d("----sony", "" + newlocation);
                            double check = markerreturn(distance);
                            if (check < Default_radius) {
                                ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                mServiceProvider.add(ambluanceObj);
                                Log.d("object", "" + mServiceProvider);
                                MarkerOptions opt = new MarkerOptions();
                                opt.position(newlocation);
                                opt.title(dataSnapshot.child("Username").getValue(String.class));
                                Marker marker = mMap.addMarker(opt);
                                markerMap.put(marker, ambluanceObj);
                            }
                        }
                    } else if (spinnerval.equalsIgnoreCase("pharmacy") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                        Log.d("----sony", "" + newlocation);
                        double check = markerreturn(distance);
                        if (check < Default_radius) {
                            ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                            Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                            ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                            ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                            ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                            ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                            ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                            ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                            ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                            ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                            ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                            ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                            ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                            ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                            ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                            ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                            mServiceProvider.add(ambluanceObj);
                            Log.d("object", "" + mServiceProvider);
                            MarkerOptions opt = new MarkerOptions();
                            opt.position(newlocation);
                            opt.title(dataSnapshot.child("Username").getValue(String.class));
                            Marker marker = mMap.addMarker(opt);
                            markerMap.put(marker, ambluanceObj); b
                        }
                    } else if (spinnerval.equalsIgnoreCase("pathology") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
//                        Log.d("mathew", "" + prov.getUsername());
//                        Log.d("mathew", "" + prov.getPhonenumber());
                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                        Log.d("-sony", "" + newlocation);
                        double check = markerreturn(distance);
                        if (check < Default_radius) {
                            ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                            Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                            ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                            ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                            ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                            ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                            ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                            ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                            ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                            ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                            ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                            ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                            ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                            ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                            ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                            ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                            mServiceProvider.add(ambluanceObj);
                            Log.d("object", "" + mServiceProvider);
                            MarkerOptions opt = new MarkerOptions();
                            opt.position(newlocation);
                            opt.title(dataSnapshot.child("Username").getValue(String.class));
                            Marker marker = mMap.addMarker(opt);
                            markerMap.put(marker, ambluanceObj);
                        }
                    } else if (spinnerval.equalsIgnoreCase("ambulance") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                        Log.d("----sony", "" + newlocation);
                        double check = markerreturn(distance);
                        // Log.d("-ambu", "lance" + check);
                        if (check < Default_radius) {
                            Log.d("-----ambu", "lance");
                            //  mMap.addMarker(new MarkerOptions().position(newlocation).title(dataSnapshot.getKey()));
                            ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                            Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                            ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                            ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                            ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                            ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                            ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                            ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                            ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                            ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                            ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                            ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                            ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                            ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                            ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                            ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                            mServiceProvider.add(ambluanceObj);
                            Log.d("object", "" + mServiceProvider);
                            MarkerOptions opt = new MarkerOptions();
                            opt.position(newlocation);
                            opt.title(dataSnapshot.child("Username").getValue(String.class));
                            Marker marker = mMap.addMarker(opt);
                            markerMap.put(marker, ambluanceObj);
                        }
                    } else if (spinnerval.equalsIgnoreCase("blood bank") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {
                        Log.d("--mmm", "ddd");
                        newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                        double check = markerreturn(distance);
                        if (check < Default_radius) {
                            ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                            Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                            ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                            ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                            ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                            ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                            ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                            ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                            ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                            ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                            ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                            ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                            ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                            ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                            ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                            ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                            mServiceProvider.add(ambluanceObj);
                            Log.d("object", "" + mServiceProvider);
                            MarkerOptions opt = new MarkerOptions();
                            opt.position(newlocation);
                            opt.title(dataSnapshot.child("Username").getValue(String.class));
                            Marker marker = mMap.addMarker(opt);
                            markerMap.put(marker, ambluanceObj);
                        }
                    } else if (spinnerval.equalsIgnoreCase("Hospital") && dataSnapshot.child("Spinner1").getValue().toString().equalsIgnoreCase(spinnerval)) {

                        Log.d("---maps", "come");
                        try {

                            newlocation = new LatLng(dataSnapshot.child("latitude").getValue(double.class), dataSnapshot.child("longitude").getValue(double.class));
                            double check = markerreturn(distance);
                            if (check < Default_radius) {
                                ambluanceObj.setAddress(dataSnapshot.child("Address").getValue(String.class));
                                Log.d("---modeldata", "add" + dataSnapshot.child("Address").getValue(String.class));
                                ambluanceObj.setEmail(dataSnapshot.child("Email").getValue(String.class));
                                ambluanceObj.setUsername(dataSnapshot.child("Username").getValue(String.class));
                                ambluanceObj.setPassWord(dataSnapshot.child("Password").getValue(String.class));
                                ambluanceObj.setPhoneNumber(dataSnapshot.child("Phonenumber").getValue(String.class));
                                ambluanceObj.setSpinner1(dataSnapshot.child("Spinner1").getValue(String.class));
                                ambluanceObj.setSpinner2(dataSnapshot.child("Spinner2").getValue(String.class));
                                ambluanceObj.setSpinner3(dataSnapshot.child("Spinner3").getValue(String.class));
                                ambluanceObj.setTime(dataSnapshot.child("time").getValue(String.class));
                                ambluanceObj.setRatings(Double.valueOf(dataSnapshot.child("ratings").getValue(double.class)));
                                ambluanceObj.setArea(dataSnapshot.child("area").getValue(String.class));
                                ambluanceObj.setDiscounts(dataSnapshot.child("discounts").getValue(String.class));
                                ambluanceObj.setRarestMedicine(dataSnapshot.child("rarestmedicine").getValue(String.class));
                                ambluanceObj.setLatitude(Double.valueOf(dataSnapshot.child("latitude").getValue(double.class)));
                                ambluanceObj.setLongitude(Double.valueOf(dataSnapshot.child("longitude").getValue(double.class)));
                                mServiceProvider.add(ambluanceObj);
                                Log.d("object", "" + mServiceProvider);
                                MarkerOptions opt = new MarkerOptions();
                                opt.position(newlocation);
                                opt.title(dataSnapshot.child("Username").getValue(String.class));
                                Marker marker = mMap.addMarker(opt);
                                markerMap.put(marker, ambluanceObj);

                            }


                        } catch (Exception exp) {
                            Log.d("--Exception", "" + exp);
                        }
                    }
                }
                getMap();
            }





            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();
                int index = mkeys.indexOf(key);
                Log.d("child", "childdd" + dataSnapshot.child("latitude").getValue().toString());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();
                int index = mkeys.indexOf(key);
                Log.d("rem", "remove" + dataSnapshot.getValue().toString());
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
    public void onBackPressed() {
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
        Log.d("--dayavittu", "baa" + distance);
        return distance;
    }


    protected synchronized void buildGoogleApiClient() {
        Log.d("--map", "common");
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        } catch (Exception exp) {
            Log.d("--BuildGoogleExp", "" + exp);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(final Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            latLng1 = new LatLng(location.getLatitude(), location.getLongitude());
            Log.d("--OnConnectMap", "" + latLng1);
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

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
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


    private void getMap() {
        int dataSize = mServiceProvider.size();
        for (int i = 0; i < dataSize; i++) {
            String adress = mServiceProvider.get(i).getAddress();
            String email = mServiceProvider.get(i).getEmail();
            String password = mServiceProvider.get(i).getPassWord();
            String phone = mServiceProvider.get(i).getPhoneNumber();
            String spinner1 = mServiceProvider.get(i).getSpinner1();
            String spinner2 = mServiceProvider.get(i).getSpinner2();
            String spinner3 = mServiceProvider.get(i).getSpinner3();
            String user = mServiceProvider.get(i).getUsername();
            String area1 = mServiceProvider.get(i).getArea();
            String discounts = mServiceProvider.get(i).getDiscounts();
//            String lati=mServiceProvider.get(i).getLatitude();
//            String long=mServiceProvider.get(i).getLongitude();
            Log.d("--ObjData", adress + " :: " + email + " :: " + password + " :: " + phone + " :: " + spinner1 + " :: " + spinner2 + " :: " + spinner3 + " :: ");
        }
    }
}
