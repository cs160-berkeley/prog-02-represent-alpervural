package com.example.alpervural.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome to Represent!");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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
        String zip = e.getText().toString();
        if(zip.length() == 5 && TextUtils.isDigitsOnly(zip)){
            sendIntent.putExtra("zip", zip);
            Intent intent = new Intent(this, CongressionalView.class);
            intent.putExtra("zip", zip);
            startService(sendIntent);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please enter a valid 5 digit zip code.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void curLocSelect(View view) {
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        Log.d("T", "Cur loc selected!");
        sendIntent.putExtra("zip", "94709");
        Intent intent = new Intent(this, CongressionalView.class);
        intent.putExtra("zip", "94709");
        startService(sendIntent);
        startActivity(intent);
    }
}
