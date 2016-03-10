package com.example.alpervural.prog2c;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class DetailedView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detailed View");
        setSupportActionBar(toolbar);
        String id = getIntent().getExtras().getString("id");
        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute("https://theunitedstates.io/images/congress/225x275/" +
                        id + ".jpg");
        String name = getIntent().getExtras().getString("name");
        String party = getIntent().getExtras().getString("party");
        String term = getIntent().getExtras().getString("term");
        String committees = getIntent().getExtras().getString("committees");
        String bills = getIntent().getExtras().getString("bills");
        TextView t=(TextView)findViewById(R.id.textView3);
        t.setText(name);
        String info = party + "\n\nTerms ends: " + term + "\n\nCommittees:\n" + committees +
                "\n\nBills:\n" + bills;
        TextView t2=(TextView)findViewById(R.id.info);
        t2.setMovementMethod(new ScrollingMovementMethod());
        t2.setText(info);
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
