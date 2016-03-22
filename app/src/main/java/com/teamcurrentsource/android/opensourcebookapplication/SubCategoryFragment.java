package com.teamcurrentsource.android.opensourcebookapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Antti on 21.3.2016.
 */

public class SubCategoryFragment extends Fragment {

    private SubCategoryAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<SubCategory> subCategoryArray;

    //tämä on periaatteessa sama asia kuin list view, Recycler view /= list view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_sub_category_fragment, container, false);

        initializeItems();

        recyclerView = (RecyclerView) view.findViewById(R.id.subcategory_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        adapter = new SubCategoryAdapter(subCategoryArray);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initializeItems() {
        subCategoryArray = new ArrayList<SubCategory>();
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            String data = bundle.getString(SubCategoryFragmentActivity.INDEX);
            Log.d(HttpRequestTask.LOG_TAG, data);
            JsonDataObject dataObject = new Gson().fromJson(data, JsonDataObject.class);

            for(JsonDataObject.Children c  : dataObject.children) {
                subCategoryArray.add(new SubCategory(c.translated_title, ""));
            }
        }
    }

    private class SubCategoryHolder extends RecyclerView.ViewHolder {

        private SubCategory subCategory;

        public SubCategoryHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.d(HttpRequestTask.LOG_TAG, subCategoryArray.get(position).title);
                    //startActivity(VideoFragmentActivity.getCategoryIntent(this, 10));
                    /*
                    new HttpRequestTask("CATEGORY", new HttpRequestListener() {
                        @Override
                        public void processHttpRequest(Gson gson, BufferedReader reader) {
                            dataObject = gson.fromJson(reader, JsonDataObject.class);
                            Log.d(LOG_TAG, dataObject.toString());
                        }
                    },  this, CategoryActivity.class).execute();*/
                    //Intent i = new Intent(getActivity(), VideoFragmentActivity.class);
                    //startActivity(i);
                }
            });
        }

        public void bindSubCaterogy(SubCategory sub) {
            subCategory = sub;
            //itemView on recyclerviewin sisäisen muuttuja
            Spinner spinner = (Spinner) itemView.findViewById(R.id.spinnerDropdown);
            ArrayList<String> sampledata = new ArrayList<String>();
            sampledata.add("1");sampledata.add("2");sampledata.add("3");sampledata.add("4");

            //adapteri spinnerille
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sampledata);

            //itemin leiska
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(spinnerAdapter);

            ((TextView) itemView.findViewById(R.id.subTitleText)).setText(subCategory.title);
            ((TextView) itemView.findViewById(R.id.subDescText)).setText(subCategory.desc);
        }

    }

    //vastaa listviewin custom adapteria
    private class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryHolder> {
        private ArrayList<SubCategory> subCategories;

        public SubCategoryAdapter(ArrayList<SubCategory> arr) {
            subCategories = arr;
        }

        @Override
        public SubCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //haetaan layoutin täyttäjä aktiviteesti jossa ollaan tällä hetkellä
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //täytetään layoutti
            View view = layoutInflater.inflate(R.layout.list_item_subcaterogy, parent, false);

            return new SubCategoryHolder(view);
        }

        @Override
        public void onBindViewHolder(SubCategoryHolder holder, int position) {
            SubCategory subCategory = subCategories.get(position);
            holder.bindSubCaterogy(subCategory);
        }

        @Override
        public int getItemCount() {
            return subCategories.size();
        }
    }

    private class SubCategory {
        String title = "";
        String desc = "";

        public SubCategory(String title_, String desc_) {
            title = title_;
            desc = desc_;
        }
    }
}
