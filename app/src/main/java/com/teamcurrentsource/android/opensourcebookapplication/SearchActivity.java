package com.teamcurrentsource.android.opensourcebookapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {



    ArrayList<VideoItem> videoItemArrayList;
    VideoListApadter adapter;
    private JsonDataObject dataObject;

    ListView listView;
    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Intent intent =  getIntent();

        String json = getIntent().getStringExtra(HttpRequestTask.INDEX);

        Log.d(HttpRequestTask.LOG_TAG, json);
        videoItemArrayList = new ArrayList<VideoItem>();
        initializeListItems(json);

        adapter = new VideoListApadter(this, videoItemArrayList);


        listView = (ListView) findViewById(R.id.list);
        searchView = (SearchView) findViewById(R.id.searchView);
      //  listView.setVisibility(View.INVISIBLE);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = listView.getItemAtPosition(position).toString();
                Intent intent = new Intent(SearchActivity.this, YoutubeActivity.class);
                intent.putExtra("key",title);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                if (listView.getAdapter().getCount() == 0) {
                    Toast.makeText(SearchActivity.this, "No Results", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SearchActivity.this, Integer.toString(listView.getAdapter().getCount()) + " Results", Toast.LENGTH_LONG).show();
                }
                return false;

            }

            @Override
            public boolean onQueryTextChange(String text) {
                if (text.length() == 0) {

                  //  listView.setVisibility(View.INVISIBLE);
                } else {
                    adapter.getFilter().filter(text);
                    //listView.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }  //tähän loppuu oncreate


    public JsonDataObject getDataObject() {
        return dataObject;
    }

    private void initializeListItems(String json) {
        JsonDataObject dataObject = new Gson().fromJson(json, JsonDataObject.class);

        for(JsonDataObject.Children c  : dataObject.children) {
            videoItemArrayList.add(new VideoItem(c.title, c.description, c.youtube_id));
            for(JsonDataObject.Children.SubChildren  b : c.children){
                videoItemArrayList.add(new VideoItem(b.title,b.description, b.youtube_id));
            }
        }
    }


    public class VideoItem {
        public String title;
        public String description;
        public String youtube_id;

        public VideoItem(String title_, String description_, String youtube_id_) {
            title = title_;
            description = description_;
            youtube_id = youtube_id_;
        }
    }

    public class VideoListApadter extends ArrayAdapter<VideoItem> {

        //passataan mikä activity kutsunut ja itse data
        public VideoListApadter(Context context, ArrayList<VideoItem> categories) {
            super(context, 0, categories);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //
            VideoItem item = getItem(position);
            //jos alustamaton haetaan layoutti resourceista
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            ((TextView) convertView.findViewById(R.id.titleText)).setText(item.title);
            ((TextView) convertView.findViewById(R.id.descriptionText)).setText(item.description);

            return convertView;
        }
    }

}
