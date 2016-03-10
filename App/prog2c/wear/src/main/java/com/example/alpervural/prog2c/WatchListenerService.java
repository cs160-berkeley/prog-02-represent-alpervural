package com.example.alpervural.prog2c;

import android.content.Context;
import android.content.Intent;
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
            String info = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String infoArr[] = info.split("\n");
            for(int i = 0; i < infoArr.length; i+=3){
                startAct(infoArr[i], infoArr[i+1] + "\n" + infoArr[i+2]);
            }
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