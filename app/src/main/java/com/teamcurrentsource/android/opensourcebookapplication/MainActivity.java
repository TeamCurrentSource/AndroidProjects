package com.teamcurrentsource.android.opensourcebookapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.util.ArrayList;


// TErveppä terve

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainAct";
    private ProgressDialog progressDialog;
    private JsonDataObject dataObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("E-Kirjasto");
    }

    public void onClickGetCategories(View v) {
        progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);
        new HttpRequestTask("CATEGORY" ,new HttpRequestListener() {
            @Override
            public void processHttpRequest(Gson data, BufferedReader reader) {
                dataObject = data.fromJson(reader, JsonDataObject.class);
                Log.d(LOG_TAG, dataObject.toString());
            }
        },  this, CategoryListView.class).execute();
    }

    public void cancelProgressDialog() {
        if(progressDialog != null) {
            progressDialog.cancel();
        }
    }

    public JsonDataObject getDataObject() {
        return dataObject;
    }
}
