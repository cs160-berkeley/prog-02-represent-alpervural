package com.example.alpervural.prog2c;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//Used the following tutorials to write this file:
//http://www.androidauthority.com/use-remote-web-api-within-android-app-617869/
//http://stackoverflow.com/questions/18192891/conversion-from-string-to-json-object-android
//http://www.tutorialspoint.com/android/android_json_parser.htm

class RequestTask extends AsyncTask<String, String, String> {

    private Context mContext;
    String requestType = "";
    ProgressBar pb = null;
    Intent intent = null;
    int stop = 0;
    int cur = 0;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> parties = new ArrayList<String>();
    ArrayList<String> emails = new ArrayList<String>();
    ArrayList<String> websites = new ArrayList<String>();
    ArrayList<String> tweets = new ArrayList<String>();
    ArrayList<String> terms = new ArrayList<String>();
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "SIDiHgzY26jSxaW0EC9X63pL5";
    private static final String TWITTER_SECRET =
            "TUz3nlJm60xd1AjjqCAQU2aMrYqzJ0lBdL4q5Un8O0M0kitZl4";

    public RequestTask (Context context, String type){
        mContext = context;
        requestType = type;
        if(type.equals("mainToCong")){
            intent = new Intent(mContext, CongressionalView.class);
            ((TextView) ((MainActivity) mContext).findViewById(R.id.textView)).
                    setText("Loading...");
        }
        if(type.equals("congToDet")){
            intent = new Intent(mContext, DetailedView.class);
        }
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("T", "Execution finished: " + result);
        if(requestType.equals("mainToCong")) {
            try {
                JSONObject obj = new JSONObject(result);
                JSONArray jsonArray = obj.optJSONArray("results");
                stop = jsonArray.length() - 1;
                for (int i = 0; i < jsonArray.length(); i++) {
                    cur = i;
                    JSONObject subobj = jsonArray.getJSONObject(i);
                    String id = subobj.getString("bioguide_id");
                    String middle = subobj.getString("middle_name");
                    if (middle == null || middle.length() < 1 || middle.equals("null")) {
                        names.add(subobj.getString("title") + " " + subobj.getString("first_name") +
                                " " + subobj.getString("last_name"));
                    } else {
                        names.add(subobj.getString("title") + " " + subobj.getString("first_name") +
                                " " + middle + " " + subobj.getString("last_name"));
                    }
                    ids.add(id);
                    parties.add(subobj.getString("party"));
                    emails.add(subobj.getString("oc_email"));
                    websites.add(subobj.getString("website"));
                    tweets.add(subobj.getString("twitter_id"));
                    terms.add(subobj.getString("term_end"));
                }
                intent.putExtra("tweets", tweets);
                intent.putExtra("names", names);
                intent.putExtra("ids", ids);
                intent.putExtra("parties", parties);
                intent.putExtra("emails", emails);
                intent.putExtra("websites", websites);
                intent.putExtra("terms", terms);
                intent.putExtra("zip", ((MainActivity) mContext).zip);
                    Intent sendIntent = new Intent(((MainActivity) mContext).getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("zip", ((MainActivity) mContext).zip);
                sendIntent.putExtra("names", names);
                sendIntent.putExtra("parties", parties);
                mContext.startService(sendIntent);
                mContext.startActivity(intent);
                ((TextView) ((MainActivity) mContext).findViewById(R.id.textView)).
                        setText("Please enter your zip code or select \"current " +
                                "location\" to find congressional " +
                                "representatives.");
            }
            catch (Exception e) {
                Log.d("T", "Exception in request task: " + e.toString());
                //Toast.makeText(mContext, "Invalid zip code", Toast.LENGTH_LONG).show();
            }
                catch (Throwable t) {
                    Log.d("T", "throwable in request task: " + t.toString());
                //Toast.makeText(mContext, "Invalid zip code", Toast.LENGTH_LONG).show();
            }
        }
        else if (requestType.equals("congToDet")){
            Log.d("T", "Cong to det started");
            try{
                JSONObject obj = new JSONObject(result);
                JSONArray jsonArray = obj.optJSONArray("results");
                String bills = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject subobj = jsonArray.getJSONObject(i);
                    bills += subobj.getString("official_title") + "\n\n";
                }
                Log.d("T", "Out: " + obj.toString());
                Intent intent = new Intent(mContext, DetailedView.class);
                intent.putExtra("name", ((CongressionalView) mContext).name);
                intent.putExtra("party", ((CongressionalView) mContext).party);
                intent.putExtra("term", ((CongressionalView) mContext).term);
                intent.putExtra("id", ((CongressionalView) mContext).id);
                intent.putExtra("committees", ((CongressionalView) mContext)
                        .committees);
                intent.putExtra("bills", bills);
                mContext.startActivity(intent);
            }
            catch(Exception e){
                Log.d("T","Exception: " + e.toString());
            }
        }
        else if (requestType.equals("congToDetPre")){
            Log.d("T", "Cong to det started");
            try {
                String committees = "";
                JSONObject obj = new JSONObject(result);
                JSONArray jsonArray = obj.optJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject subobj = jsonArray.getJSONObject(i);
                    committees += subobj.getString("name") + "\n\n";
                }
                ((CongressionalView) mContext).committees = committees;
                String url = "https://congress.api.sunlightfoundation.com/bills?sponsor_id=" +
                        ((CongressionalView) mContext).id +
                        "&apikey=a7ecd21ca90f48c59256fc75c283c642";
                try{
                    new RequestTask(mContext, "congToDet").execute(url);
                }
                catch(Exception ex){
                    Log.d("T", "Exception Req congToDet: " + ex.toString());
                }
            }
            catch(Exception e){
                Log.d("T","Exception congToDet: " + e.toString());
            }
        }
    }
}