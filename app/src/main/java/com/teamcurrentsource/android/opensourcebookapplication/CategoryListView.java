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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CategoryListView extends AppCompatActivity {

    ArrayList<CategoryItem> categoryItemArrayList;
    CategoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlist_view);

        Intent intent =  getIntent();

        String json = getIntent().getStringExtra(HttpRequestTask.INDEX);

        Log.d(HttpRequestTask.LOG_TAG, json);
        categoryItemArrayList = new ArrayList<CategoryItem>();
        initializeListItems(json);

        adapter = new CategoryListAdapter(this, categoryItemArrayList);

        ListView categoryList = (ListView) findViewById(R.id.list);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(HttpRequestTask.LOG_TAG, String.valueOf(position));
                Intent i = new Intent(CategoryListView.this, SingleCategoryActivity.class);
                startActivity(i);
           }
         }
        );
        categoryList.setAdapter(adapter);
    }

    private void initializeListItems(String json) {
        JsonDataObject dataObject = new Gson().fromJson(json, JsonDataObject.class);

        for(JsonDataObject.Children c  : dataObject.children) {
            categoryItemArrayList.add(new CategoryItem(c.title, c.description, c.relative_url));
        }
    }

    public class CategoryItem {
        public String title;
        public String description;
        public String url;

        public CategoryItem(String title_, String description_, String url_) {
            title = title_;
            description = description_;
            url = url_;
        }
    }

    public class CategoryListAdapter extends ArrayAdapter<CategoryItem> {

        public CategoryListAdapter(Context context, ArrayList<CategoryItem> categories) {
            super(context, 0, categories);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CategoryItem item = getItem(position);
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            ((TextView) convertView.findViewById(R.id.titleText)).setText(item.title);
            ((TextView) convertView.findViewById(R.id.descriptionText)).setText(item.description);

            return convertView;
        }
    }
}
