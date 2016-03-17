package com.teamcurrentsource.android.opensourcebookapplication;

import android.os.AsyncTask;
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
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("E-Kirjasto");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickGetCategories(View v) {
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Object, Void, String> {
        private static final String BASEURL = "http://www.khanacademy.org/api/v1";
        private static final String LOG_TAG = "HttpRequestTask";

        @Override
        protected String doInBackground(Object... params) {
            HttpURLConnection connection = null;

            try {
                Log.d(LOG_TAG, this.getStatus().toString());
                URL url = new URL(BASEURL + "/topictree?kind=topic");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();

                Reader rdr = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                Gson gson = new GsonBuilder().create();
                JsonDataObject o = gson.fromJson(rdr, JsonDataObject.class);

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

            return "doInBackgroundFinished";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(LOG_TAG, "FINISHED!");
            Log.d(LOG_TAG, "RESULT= " + result);
        }
    }

    private class JsonDataObject {
        private String id;
        private String node_slug;
        private children children[];

        @Override
        public String toString() {
            return "JsonDataObject{" +
                    "id='" + id + '\'' +
                    ", node_slug='" + node_slug + '\'' +
                    ", children=" + Arrays.toString(children) +
                    '}';
        }

        private class children {
            String node_slug;
            String relative_url;
            String title;

            @Override
            public String toString() {
                return "children{" +
                        "node_slug='" + node_slug + '\'' +
                        ", url='" + relative_url + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }
        }
    }
}
