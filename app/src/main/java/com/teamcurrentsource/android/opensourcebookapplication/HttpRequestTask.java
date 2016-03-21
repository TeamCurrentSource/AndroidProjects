package com.teamcurrentsource.android.opensourcebookapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Antti on 17.3.2016.
 */
public class HttpRequestTask extends AsyncTask<Object, Void, String> {
    private static final String BASEURL = "http://www.khanacademy.org/api/v1";
    public static final String LOG_TAG = "HttpRequestTask";
    private static final String CATEGORIES = "/topictree?kind=topic";
    public static final String INDEX = "JsonDataObject";
    private HttpRequestListener listener;
    private Context source;
    private Class<?> destination;
    private String currentUrl;

    public HttpRequestTask(String value, HttpRequestListener lstener, Context src, Class<?> dest) {
        currentUrl = value;
        listener = lstener;
        source = src;
        destination = dest;
    }

    @Override
    protected String doInBackground(Object... params) {
        HttpURLConnection connection = null;
        try {
            Log.d(LOG_TAG, "Entering doInBackground");
            URL url = new URL(BASEURL + CATEGORIES);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream in = connection.getInputStream();
            Reader rdr = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            Gson gson = new GsonBuilder().create();
            listener.processHttpRequest(gson, reader);

            //lähetään gsoni mielummin

        } catch (MalformedURLException e) {
            Log.d(LOG_TAG, "Error @Malformed");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(LOG_TAG, "Error @IOException");
            e.printStackTrace();
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error @JSONException");
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "Leaving doInBackground");

        return "e-e-e";
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(LOG_TAG, "Staring new intent");

        //tarkasta jos luokka on mainact
        ((MainActivity) source).cancelProgressDialog();
        Intent intent = new Intent(source,destination);
        JsonDataObject obj = ((MainActivity) source).getDataObject();
        intent.putExtra(INDEX, new Gson().toJson(obj));
        Log.d(LOG_TAG, new Gson().toJson(obj));
        source.startActivity(intent);        //passataan data stringinä
        Log.d(LOG_TAG, "onPostExecute finesed!");
    }

    private String redirect() {
        Log.d(LOG_TAG, currentUrl);

        switch(currentUrl) {
            case CATEGORIES:
                Log.d(LOG_TAG, CATEGORIES);
                return CATEGORIES;
            default:
                break;
        }
        return null;
    }
}