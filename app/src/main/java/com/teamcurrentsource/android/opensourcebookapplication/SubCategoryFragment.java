package com.teamcurrentsource.android.opensourcebookapplication;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        recyclerView = (RecyclerView) view.findViewById(R.id.subcategory_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        subCategoryArray = new ArrayList<SubCategory>();
        for(int i = 0; i < 50; i++) {
            subCategoryArray.add(new SubCategory("Title "+ i, "Desc " + i));
        }

        adapter = new SubCategoryAdapter(subCategoryArray);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class SubCategoryHolder extends RecyclerView.ViewHolder {

        private SubCategory subCategory;

        public SubCategoryHolder(View v) {
            super(v);
        }

        public void bindSubCaterogy(SubCategory sub) {
            subCategory = sub;

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
        String title = "titteli";
        String desc = "desCiX";

        public SubCategory(String title_, String desc_) {
            title = title_;
            desc = desc_;
        }
    }
}
