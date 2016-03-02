package com.example.alpervural.phone;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailedView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detailed View");
        setSupportActionBar(toolbar);
        String name = getIntent().getExtras().getString("name");
        TextView t=(TextView)findViewById(R.id.textView3);
        t.setText("Representative Diane Feinstein " + name);
        String bills = "This is to simulate having an extremely long bill name. There will" +
                " probably be bill names longer than this but this is good for now.";
        String committees = "Hardcoded committee 1; hardcoded committee 2; hardcoded comittee 3";
        String info = "Democrat\n\nTerms ends: 2/2/16\n\nCommittees:\n" + committees + "\n\nBills:\n" +
                bills;
        TextView t2=(TextView)findViewById(R.id.info);
        t2.setText(info);
    }
}
