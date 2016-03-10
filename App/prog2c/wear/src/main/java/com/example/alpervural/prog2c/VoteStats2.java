package com.example.alpervural.prog2c;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class VoteStats2 extends Activity {
    //Shake code from this tutorial http://jasonmcreynolds.com/?p=388
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    boolean accelerated = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("T", "Here2");
        super.onCreate(savedInstanceState);
        Log.d("T", "Here3");
        setContentView(R.layout.round_activity_vote_stats2);
        Log.d("T", "Here4");
        String stats = getIntent().getExtras().getString("stats");
        TextView t = (TextView) findViewById(R.id.textView4);
        t.setText(stats);
        if (getIntent().getExtras().getBoolean("shake")) {
            Toast toast = Toast.makeText(this, "This screen was generated from" +
                    "shaking the watch!", Toast.LENGTH_LONG);
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

    public void goBack(View view){
        Intent intent = new Intent(this, WearMainActivity.class );
        intent.putExtra("zip", getIntent().getStringExtra("zip"));
        intent.putExtra("name",getIntent().getStringExtra("name"));
        startActivity(intent);
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

}
