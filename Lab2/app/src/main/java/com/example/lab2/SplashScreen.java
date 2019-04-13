package com.example.lab2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SplashScreen extends AppCompatActivity {

    fetchData dataFetcher_AsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        setContentView(R.layout.activity_splash_screen);

        ImageView iv = (ImageView)findViewById(R.id.iv1);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in));
        animationSet.addAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up));
        animationSet.addAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_down));
        iv.startAnimation(animationSet);

        dataFetcher_AsyncTask = new fetchData();
        dataFetcher_AsyncTask.execute();
    }

    @Override
    protected void onDestroy() {
        dataFetcher_AsyncTask.cancel(true);
        super.onDestroy();
    }

    public class fetchData extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String urlPath = getResources().getString(R.string.jsonTextURL);
            try {
                URL url = new URL(urlPath);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                StringBuilder jsonSB = new StringBuilder();
                while (line != null) {
                    line = bufferedReader.readLine();
                    jsonSB.append(line);
                }

                try {
                    JSONArray arr = new JSONArray(jsonSB.toString());
                    for (int i = 0; i <arr.length(); i++) {
                        JSONObject obj = (JSONObject)arr.get(i);

                        if (obj.has("name")) {
                            Item it = new Item(obj.optString("name"),
                                                obj.optString("helptext"),
                                                obj.optString("graphic"));
                            Data.addItem(it);
                        }

                        if (isCancelled()) break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

            super.onPostExecute(aVoid);
        }
    }

}
