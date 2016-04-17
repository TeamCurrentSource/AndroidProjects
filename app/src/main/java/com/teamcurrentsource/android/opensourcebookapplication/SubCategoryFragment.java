package com.teamcurrentsource.android.opensourcebookapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;


import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;



public class SubCategoryFragment extends Fragment {

    public static final String LOG_TAG = "sub";
    private SubCategoryAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<SubCategory> subCategoryArray;
    public FragmentManager mFragmentManager;
    public JsonDataObject dataObject;
    public ArrayList<String> imageUrls;
    public ArrayList<JsonVideoObject>videos;
    public ProgressDialog progressDialog;
    public Boolean flag = true;
    public Boolean hasVideos = false;
    public Boolean initialized = false;
    //public ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {

        View view = inflater.inflate(R.layout.activity_sub_category_fragment, container, false);
        imageUrls = new ArrayList<String>();
        Log.d("arvo", "1 " + Boolean.toString(flag));

        initializeItems();
        Log.d("arvo", "2 " +Boolean.toString(flag));
        while (flag) {
            try { Thread.sleep(100); Log.d(LOG_TAG, "JUMISSA");}
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        Log.d("arvo", "3 " +Boolean.toString(flag));

        MyImageLoader.initializeImageLoader(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.subcategory_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new SubCategoryAdapter(getContext(), subCategoryArray);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void initializeItems() {
        subCategoryArray = new ArrayList<SubCategory>();
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            String data = bundle.getString("DATA");
            final JsonDataObject dataObject = new Gson().fromJson(data, JsonDataObject.class);

            Log.d(LOG_TAG, "Initialize items");
            Log.d(LOG_TAG, "Initialize items. DATA: " + data);


            Boolean visited = false;
            hasVideos = false;
            SubCategoryFragmentActivity.headerTextView.setText(dataObject.children[0].kind + "s");


            for (final JsonDataObject.Children c : dataObject.children) {
                    Log.d("tyyppi", c.kind);
                    if(c.kind.equals("Topic") || c.kind.equals("Video")) {
                        subCategoryArray.add(new SubCategory(c.translated_title, "", c.node_slug, "", c.kind, ""));
                    }
                    if (c.kind.equals("Video")) {
                        hasVideos = true;
                        Log.d(LOG_TAG, "Initialize items. c.kind = video");
                        if (!visited) {
                            new HttpRequestTask(Routes.getVideoImageUrl(dataObject.node_slug), new HttpRequestListener() {
                                @Override
                                public void processHttpRequest(Gson data, BufferedReader reader) {
                                    Log.d(LOG_TAG, "fetching video from domain: " + dataObject.node_slug);
                                    videos = new ArrayList<JsonVideoObject>(Arrays.asList(data.fromJson(reader, JsonVideoObject[].class)));
                                    Log.d(LOG_TAG, "Fetch videos: ");
                                    int i = 0;
                                    for (JsonVideoObject vid : videos) {
                                        Log.d(LOG_TAG, "URLIT: " + vid.download_urls);

                                        imageUrls.add(vid.download_urls.png);
                                        subCategoryArray.get(i).image = vid.download_urls.png;
                                        subCategoryArray.get(i).youtube_id = vid.youtube_id;
                                        Log.d(LOG_TAG, "ELEMENTTI: " + subCategoryArray.get(i).title);

                                        i++;
                                    }
                                    flag = false;
                                    hasVideos = false;
                                }
                            }, null, null).execute();
                            visited = true;
                        }
                    }
                }
            flag = hasVideos ? true : false;
        }

    }

    private class SubCategoryHolder extends RecyclerView.ViewHolder {

        private SubCategory subCategory;
        public ImageView mImageView;
        private Context context;


        public SubCategoryHolder(Context c, View v) {
            super(v);

            mImageView = (ImageView) v.findViewById(R.id.title_image_view);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String node_slug = subCategoryArray.get(position).node_slug;

                    if (subCategoryArray.get(position).youtube_id.isEmpty()) {
                        new HttpRequestTask(Routes.CATEROGY + node_slug, new HttpRequestListener() {
                            @Override
                            public void processHttpRequest(Gson data, BufferedReader reader) {
                                dataObject = data.fromJson(reader, JsonDataObject.class);

                                Fragment fragment;
                                if (!subCategoryArray.isEmpty() && subCategoryArray.get(0).kind.equals("Topic")) {
                                    Bundle bundle = new Bundle();

                                    bundle.putString("DATA", new Gson().toJson(dataObject));
                                    bundle.putString("newcat", "sda");
                                    fragment = new SubCategoryFragment();
                                    fragment.setArguments(bundle);

                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.subcategory_container, fragment)
                                            .addToBackStack(null)
                                            .commit();
                                }
                            }
                        }, null, null).execute();
                    } else {

                        Bundle extras = new Bundle();
                        extras.putString("Title", subCategoryArray.get(position).title);
                        extras.putString("Description", subCategoryArray.get(position).desc);
                        extras.putString("youtubeId", subCategoryArray.get(position).youtube_id);

                        Intent intent = new Intent(getContext(), YoutubeActivity.class);
                        intent.putExtras(extras);
                        startActivity(intent);

                    }
                }
            });

        }

        public void bindSubCaterogy(SubCategory sub) {
            subCategory = sub;
            Log.d(LOG_TAG, "bindSubCategory, elementti: " + sub.title + " " + sub.image);

            Log.d(LOG_TAG, "bindSubCategory, bindataan kuvat!");
            if(!subCategory.image.isEmpty()) {
                Log.d(LOG_TAG, "kuva löytyy, laitetaash siihe kuva");
                Boolean isImage = mImageView.getDrawable() == null;
                //ImageLoader.getInstance().displayImage(subCategory.image, mImageView);
                MyImageLoader.getMyImageLoader().displayImage(subCategory.image, mImageView);
            } else {
                MyImageLoader.getMyImageLoader().displayImage("http://cdn.embed.ly/providers/logos/khanacademy.png", mImageView);
            }

            ((TextView) itemView.findViewById(R.id.myImageViewText)).setText(subCategory.title);
        }
    }

    //vastaa listviewin custom adapteria
    private class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryHolder> {
        private ArrayList<SubCategory> subCategories;
        private Context context;
        public SubCategoryAdapter(Context cont, ArrayList<SubCategory> arr) {
            context = cont;
            subCategories = arr;
        }

        @Override
        public SubCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.image_item, parent, false);

            return new SubCategoryHolder(context, view);
        }

        @Override
        public void onBindViewHolder(SubCategoryHolder holder, int position) {
            Log.d(LOG_TAG, "onBindViewHolder... position: " + Integer.toString(position));
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
        String node_slug = "";
        String image = "";
        String kind = "";
        String youtube_id = "";

        public SubCategory(String title_, String desc_, String slug_, String image_, String kind_, String youtube_id_) {
            title = title_;
            desc = desc_;
            node_slug = slug_;
            image = image_;
            kind = kind_;
            youtube_id = youtube_id_;
        }
    }
}
