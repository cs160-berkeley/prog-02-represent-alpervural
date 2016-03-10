package com.example.alpervural.prog2c;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    String zip = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome to Represent!");

        setSupportActionBar(toolbar);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Wearable.API)
                    .build();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connResult) {}

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void zipSelect(View view) {
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        Log.d("T", "Manual zip code selected!");
        EditText e = (EditText) findViewById(R.id.editText);
        zip = e.getText().toString();
        if(zip.length() == 5 && TextUtils.isDigitsOnly(zip)){
            String urlString = "http://congress.api.sunlightfoundation.com/legislators/locate?" +
                    "zip=" + zip + "&apikey=a7ecd21ca90f48c59256fc75c283c642";
            try{
                new RequestTask(this, "mainToCong").execute(urlString);
            }
            catch(Exception ex){
                Log.d("T", "Exception 1: " + ex.toString());
            }
        }
        else{
            Toast.makeText(this, "Please enter a valid 5 digit zip code.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void curLocSelect(View view) {
        Log.d("T", "Cur loc selected!");
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        catch(SecurityException e){
            Log.d("T", "Security exception: " + e.toString());
        }
        //Spoof geolocations
        if (mLastLocation == null || 1 == 1) {
            Double lati = 37.9;
            Double longi = -122.3;
            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());
            try{
                addresses = geocoder.getFromLocation(lati, longi, 1);
                Log.d("T", "Addresses: " + addresses.toString());
            }
            catch(Exception e){
                Log.d("T", "Error: " + e.toString());
            }
            zip = addresses.get(0).getPostalCode();
            String urlString = "http://congress.api.sunlightfoundation.com/legislators/locate?" +
                    "zip=" + zip + "&apikey=a7ecd21ca90f48c59256fc75c283c642";
            try{
                new RequestTask(this, "mainToCong").execute(urlString);
            }
            catch(Exception ex){
                Log.d("T", "Exception 1: " + ex.toString());
            }
        }
    }
}