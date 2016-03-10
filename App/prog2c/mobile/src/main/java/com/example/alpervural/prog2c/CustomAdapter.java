package com.example.alpervural.prog2c;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

//Note: this code is from https://www.caveofprogramming.com/guest-posts/custom-listview-with-imageview-and-textview-in-android.html
public class CustomAdapter extends BaseAdapter{
    ArrayList<String> result;
    Context context;
    ArrayList<String> ids;
    CongressionalView cv;
    private static LayoutInflater inflater=null;
    public CustomAdapter(CongressionalView mainActivity, ArrayList<String> prgmNameList,
                         ArrayList<String> prgmImages) {
        // TODO Auto-generated constructor stub
        cv = mainActivity;
        result=prgmNameList;
        context=mainActivity;
        ids=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result.get(position));
        new DownloadImageTask(holder.img)
                .execute("https://theunitedstates.io/images/congress/225x275/" +
                        ids.get(position) + ".jpg");
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = "http://congress.api.sunlightfoundation.com/committees?member_ids=" +
                        cv.ids.get(position) + "&apikey=a7ecd21ca90f48c59256fc75c283c642";
                cv.name = cv.names.get(position);
                cv.party = cv.parties.get(position);
                cv.id = cv.ids.get(position);
                cv.term = cv.terms.get(position);
                try{
                    new RequestTask(cv, "congToDetPre").execute(url);
                }
                catch(Exception ex){
                    Log.d("T", "Exception 1: " + ex.toString());
                }
            }
        });
        return rowView;
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