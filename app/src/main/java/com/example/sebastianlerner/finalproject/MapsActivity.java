package com.example.sebastianlerner.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.List;

//import android.os.AsyncTask.Status;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnConnectionFailedListener {

    private GoogleMap mMap;
    private int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        double latitude = 38.035050;
        double longitude =  -78.495032;
        final String[] LOCATION_PERMS={
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        requestPermissions(LOCATION_PERMS, 1337+3);

        try {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if(location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            mMap.setMyLocationEnabled(true);

        }
        catch (SecurityException e) {

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            Log.i("no permission", "no permission");
        }



        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {

            final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, 0, this)
                    .addApi(Places.GEO_DATA_API)
                    .build();

            String query = "parking";
            LatLngBounds bounds = new LatLngBounds(new LatLng(38.034982, -78.495118), new LatLng(38.044982, -78.495118));

            PendingResult<AutocompletePredictionBuffer> result =
                    Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, query, bounds, null);

            result.setResultCallback(new ResultCallbacks<AutocompletePredictionBuffer>() {
                @Override
                public void onFailure(Status status) {
                    Log.i("failed", "failed");
                }

                @Override
                public void onSuccess(AutocompletePredictionBuffer test) {
                    // Success! Handle the query result.

                    // ...
                    for (int k = 0; k < test.getCount(); k++) {
                        Log.i("fuck me: ", test.get(k).getFullText(null).toString());
                        String response = getLatLongByURL(test.get(k).getFullText(null).toString());
                        Log.i("the lat/long", response+",");
                        Log.i("hmm","hmm");
                        if(response != null) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(response.substring(0, (response.indexOf("|") ))), Double.parseDouble(response.substring(response.indexOf("|")+1))))
                                    .title(test.get(k).getPrimaryText(null).toString()));
                        }
                    }


                }
            });

            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build();

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(this);
            //
            //    startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            Log.i("testMe", "hi there");


        }
        catch (GooglePlayServicesRepairableException
                | GooglePlayServicesNotAvailableException e) {
            Log.i("MapsActivity", "test");
            e.printStackTrace();
        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))      // Sets the center of the map to Mountain View
                .zoom(16)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east

                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



    }

    public String getLatLongByURL(String url)
    {
        Geocoder geocoder = new Geocoder(this);
        List<Address> address;
        Log.i("url", url);
        boolean good = false;
        Barcode.GeoPoint p1 = null;
        Address location = new Address(null);

        try {
            address = geocoder.getFromLocationName(url,5);
            //Log.i("address", address.get(0).getAddressLine(0));
            if (address.size() == 0) {
                return null;
            }
            location=address.get(0);
            good = true;
            // location.getLatitude();
            // location.getLongitude();



        }
        catch(IOException e)
        {
            Log.i("io exception", "io excpetion");
        }

        if(good == true) {
            String newString = location.getLatitude() + "";
            newString += "|";
            newString += location.getLongitude();
            Log.i("newstring", newString);
            return newString;
        }
        else
        {
            return "";
        }


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                Log.i("umm", "test");
            }
        }
    }
    public void onConnectionFailed(ConnectionResult result) {
        // An unresolvable error has occurred and a connection to Google APIs
        // could not be established. Display an error message, or handle
        // the failure silently

        // ...
    }

}
