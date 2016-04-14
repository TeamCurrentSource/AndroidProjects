package com.teamcurrentsource.android.opensourcebookapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.BufferedReader;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainAct";
    private ProgressDialog progressDialog;
    private JsonDataObject dataObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Khans");
    }

    public void onClickGetCategories(View v) {
        progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);
        new HttpRequestTask(Routes.CATEGORIES, new HttpRequestListener() {
            @Override
            public void processHttpRequest(Gson gson, BufferedReader reader) {
                dataObject = gson.fromJson(reader, JsonDataObject.class);
                Log.d(LOG_TAG, dataObject.toString());
            }
        },  this, CategoryActivity.class).execute();
    }

    public void cancelProgressDialog() {
        if(progressDialog != null) {
            progressDialog.cancel();
        }
    }

    public void onClickSearchButton(View view){

        Intent intent = new Intent(MainActivity.this, YoutubeActivity.class);
        startActivity(intent);

/*
        progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);
        new HttpRequestTask(Routes.VIDEOS, new HttpRequestListener() {
            @Override
            public void processHttpRequest(Gson gson, BufferedReader reader) {
                dataObject = gson.fromJson(reader, JsonDataObject.class);
                Log.d(LOG_TAG, dataObject.toString());
            }
        },  this, SearchActivity.class).execute();*/

    }

    public JsonDataObject getDataObject() {
        return dataObject;
    }
}
