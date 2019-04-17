package com.example.lab2;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

interface OnTaskCompleted{
    void onTaskCompleted(Boolean success);
}

public class DataFetcher extends AsyncTask<String,Void,Boolean> {
    private WeakReference<Context> context;
    private OnTaskCompleted listener;

    DataFetcher(Context context, OnTaskCompleted listener) {
        this.context = new WeakReference<>(context);
        this.listener=listener;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String urlPath = strings[0];
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

            Data.clear(context.get());
            Data.saveToFile(context.get(),jsonSB.toString());

            try {
                JSONArray arr = new JSONArray(jsonSB.toString());
                for (int i = 0; i <arr.length(); i++) {
                    JSONObject obj = (JSONObject)arr.get(i);

                    if (obj.has("name")) {
                        Item it = new Item(obj.optString("name"),
                                obj.optString("helptext"),
                                obj.optString("graphic"));
                        Data.addItem(it, context.get());
                    }

                    if (isCancelled()) break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        listener.onTaskCompleted(result);
    }
}