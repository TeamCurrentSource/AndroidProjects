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
    public static final String LOG_TAG = "HttpRequestTask";
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
            URL url = new URL(currentUrl);
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
        Log.d(LOG_TAG, source.getClass().toString());
        Log.d(LOG_TAG, MainActivity.class.toString());
        Intent intent = new Intent(source,destination);
        //tarkasta luokka
        redirectToActivityAndDoProcedures(intent);
        source.startActivity(intent);
        Log.d(LOG_TAG, "onPostExecute finesed!");
    }

    private void redirectToActivityAndDoProcedures(Intent intent) {
        if(source.getClass() == MainActivity.class) {
            ((MainActivity) source).cancelProgressDialog();
            JsonDataObject obj = ((MainActivity) source).getDataObject();
            intent.putExtra(INDEX, new Gson().toJson(obj));
            Log.d(LOG_TAG, "Spinner stoppped");
        }
        if(source.getClass() == CategoryListView.class) {
            JsonDataObject obj = ((CategoryListView) source).getDataObject();
            intent.putExtra(INDEX, new Gson().toJson(obj));
        }
    }

}