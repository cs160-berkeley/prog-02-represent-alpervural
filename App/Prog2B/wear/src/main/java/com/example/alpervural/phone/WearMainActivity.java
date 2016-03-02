package com.example.alpervural.phone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import com.example.alpervural.prog2b.R;

//Shake code from this tutorial http://jasonmcreynolds.com/?p=388
public class WearMainActivity extends Activity {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    //public ArrayList<String> arr = new ArrayList<String>();
    int ind = 0;
    boolean accelerated = false;
    String zip = "";
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_wear_main);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //For now just always use zip code 94704 as default, otherwise change
        if (extras != null) {
            TextView t=(TextView)findViewById(R.id.textTitle);
            zip = getIntent().getExtras().getString("zip");
            name = getIntent().getExtras().getString("name");
            t.setText("Zip code: " + zip);
            Button t2=(Button)findViewById(R.id.textView);
            t2.setText(name);
            if(getIntent().getExtras().getBoolean("shake") == true){
                accelerated = true;
                Toast toast = Toast.makeText(this, "This screen was generated from" +
                        " shaking the watch!", Toast.LENGTH_LONG);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                if( v != null) v.setGravity(Gravity.CENTER);
                toast.show();
            }
        }
        else {
            //Spoof multiple screens
            Intent newIntent1 = new Intent(this, WearMainActivity.class);
            newIntent1.putExtra("name", "Represenatative\nDianne Feinstein 1\nDemocrat");
            newIntent1.putExtra("zip", "94704");
            newIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent newIntent2 = new Intent(this, WearMainActivity.class);
            newIntent2.putExtra("name", "Represenative\nHolly Political 2\nDemocrat");
            newIntent2.putExtra("zip", "94704");
            newIntent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent newIntent3 = new Intent(this, WearMainActivity.class);
            newIntent3.putExtra("name", "Representative\nBilly Feinstein 3\nDemocrat");
            newIntent3.putExtra("zip", "94704");
            newIntent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent1);
            startActivity(newIntent2);
            startActivity(newIntent3);
        }

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                if (!accelerated) {
                    handleShakeEvent(count);
                    accelerated = true;
                }
            }
        });
    }

    public void handleShakeEvent(int shakes){
        Log.d("T", "Shake event detected!");
        String zip = "91361";
        if(Math.random() < 0.5) {
            zip = "91360";
        }
        Intent newIntent1 = new Intent(this, WearMainActivity.class );
        newIntent1.putExtra("name", "Represenative\nDianne NewFein 1\nRepublican");
        newIntent1.putExtra("zip", zip);
        newIntent1.putExtra("shake", true);
        newIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent newIntent2 = new Intent(this, WearMainActivity.class );
        newIntent2.putExtra("name", "Representative\nBarbara NewFein 2\nDemocrat");
        newIntent2.putExtra("zip", zip);
        newIntent2.putExtra("shake", true);
        newIntent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent newIntent3 = new Intent(this, WearMainActivity.class );
        newIntent3.putExtra("name", "Representative\nRichard Feinstein 3\nDemocrat");
        newIntent3.putExtra("zip", zip);
        newIntent3.putExtra("shake", true);
        newIntent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent1);
        startActivity(newIntent2);
        startActivity(newIntent3);
        Intent sendIntent = new Intent(getBaseContext(), ToPhoneService.class);
        sendIntent.putExtra("zip", zip);
        sendIntent.putExtra("action","shake");
        startService(sendIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    public void voteStats(View view){
        if(zip != null){
            if(!zip.equals("94704")){
            Intent newIntent1 = new Intent(this, VoteStats2.class);
            newIntent1.putExtra("stats", "Ventura County, CA\nObama: 75% of vote\n" +
                    "Romney: 25% of vote");
            newIntent1.putExtra("zip", zip);
            newIntent1.putExtra("name", name);
            //newIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent1);}
            else{
            Intent newIntent2 = new Intent(this, VoteStats2.class );
            newIntent2.putExtra("stats", "Alameda County, CA\nObama: 80% of vote\n" +
                    "Romney: 20% of vote");
            newIntent2.putExtra("zip", zip);
            newIntent2.putExtra("name",name);
            //newIntent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent2);}
        }
    }

    public void sendtoPhone(View view){
        Intent sendIntent = new Intent(getBaseContext(), ToPhoneService.class);
        sendIntent.putExtra("zip", zip);
        sendIntent.putExtra("name", name);
        sendIntent.putExtra("action","detailed");
        Log.d("T", "Button pressed!\n");
        startService(sendIntent);
    }
}
