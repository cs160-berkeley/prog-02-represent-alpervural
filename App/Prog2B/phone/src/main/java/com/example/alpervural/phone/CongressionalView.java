package com.example.alpervural.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ListView;

public class CongressionalView extends AppCompatActivity {

  public static int [] prgmImages={R.drawable.diane,R.drawable.diane, R.drawable.diane};
    public static String base = "\nContact:\nsenator@feinstein.senate.gov\n" +
            "www.dfeinwebsite.com\n\nTwitter:\nCalifornia is very very hot today " +
            "and I need more votes. I wish tweets could have more characters." +
            "I think that norcal is much hotter than socal but I am not sure. What do " +
            "you guys think?";
    public static String [] prgmNameList={"Representative Dianne L. Feinstein 1:\nDemocrat\n" + base,
            "Representative Dianne M. Feinstein 2:\nDemocrat\n" +
            base, "Representative Dianne G. Feinstein 3:\nDemocrat\n" + base};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Representatives for " + getIntent().getExtras().getString("zip"));
        setSupportActionBar(toolbar);
        ListView lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, prgmNameList, prgmImages));
    }
}