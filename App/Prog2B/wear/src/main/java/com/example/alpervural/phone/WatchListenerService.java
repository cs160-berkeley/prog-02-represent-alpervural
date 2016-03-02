package com.example.alpervural.phone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class WatchListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Success!", Toast.LENGTH_LONG);
        toast.show();

        if(messageEvent.getPath().equalsIgnoreCase( "/zip" ) ) {
            String zip = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            startAct(zip, "Senator Dianne Feinstein 1\nDemocrat");
            startAct(zip, "Senator Holly Feinstein 2\nDemocrat");
            startAct(zip, "Senator Richard Marie Feinstein 3\nDemocrat");
        }  else {
            super.onMessageReceived( messageEvent );
        }
    }

    public void startAct(String zip, String name){
        Intent intent1 = new Intent(this, WearMainActivity.class );
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("zip", zip);
        intent1.putExtra("name", name);
        startActivity(intent1);
    }
}