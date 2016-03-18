package com.teamcurrentsource.android.opensourcebookapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryListView extends AppCompatActivity {

    ArrayList<CategoryItem> categoryItemArrayList;
    CategoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlist_view);

        String sample = "jeejee";
        categoryItemArrayList = new ArrayList<CategoryItem>();
        categoryItemArrayList.add(new CategoryItem(sample));
        adapter = new CategoryListAdapter(this, categoryItemArrayList);

        ListView categoryList = (ListView) findViewById(R.id.list);
        categoryList.setAdapter(adapter);
    }

    public class CategoryItem {
        public String sample;

        public CategoryItem(String val) {
            sample = val;
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

            TextView text = (TextView) convertView.findViewById(R.id.myText);
            text.setText("MitBit?");

            return convertView;
        }
    }
}
