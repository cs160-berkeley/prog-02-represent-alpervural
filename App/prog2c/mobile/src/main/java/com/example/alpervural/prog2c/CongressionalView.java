package com.example.alpervural.prog2c;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

public class CongressionalView extends AppCompatActivity {

    public static ArrayList<String> titles = new ArrayList<String>();
    public static String defaultTweet = "California is very very hot today " +
            "and I need more votes. I wish tweets could have more characters." +
            "I think that norcal is much hotter than socal but I am not sure. What do " +
            "you guys think?";
    ArrayList<String> ids = null;
    ArrayList<String> names = null;
    ArrayList<String> parties = null;
    ArrayList<String> terms = null;
    String id = null;
    String name = null;
    String party = null;
    String bills = null;
    String committees = null;
    String term = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle extras = getIntent().getExtras();
        toolbar.setTitle("Representatives for " + extras.getString("zip"));
        setSupportActionBar(toolbar);
        names = extras.getStringArrayList("names");
        ids = extras.getStringArrayList("ids");
        parties = extras.getStringArrayList("parties");
        terms = extras.getStringArrayList("terms");
        ArrayList<String> emails = extras.getStringArrayList("emails");
        ArrayList<String> websites = extras.getStringArrayList("websites");
        ArrayList<String> tweets = extras.getStringArrayList("tweets");
        for(int i = 0; i < names.size(); i++){
            String title = names.get(i) + ":\n" + parties.get(i) + "\n";
            String info = "\nContact:\n" + emails.get(i) + "\n" + websites.get(i) +
                    "\n\nTwitter:\n" + tweets.get(i);
            titles.add(title + info);
        }
        ListView lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, titles, ids));
    }
}