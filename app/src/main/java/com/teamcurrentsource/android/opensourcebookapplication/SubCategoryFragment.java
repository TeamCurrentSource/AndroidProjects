package com.teamcurrentsource.android.opensourcebookapplication;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.Fragment;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Antti on 21.3.2016.
 */

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
    public ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {

        Log.d(LOG_TAG, "onCreate");
        View view = inflater.inflate(R.layout.activity_sub_category_fragment, container, false);
        imageUrls = new ArrayList<String>();
        initializeItems();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
                .writeDebugLogs()
                .defaultDisplayImageOptions(defaultOptions)
                .diskCacheExtraOptions(480, 320, null)
                .build();

        ImageLoader.getInstance().init(config);
        while (flag) {
            try { Thread.sleep(100); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
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
            Log.d(LOG_TAG, "BUNTLE IS NAT EMPTI");

            String data = bundle.getString("DATA");
            Log.d(LOG_TAG, data);
            JsonDataObject dataObject = new Gson().fromJson(data, JsonDataObject.class);
            Log.d(LOG_TAG, "GZON");

            boolean visited = false;
                for (final JsonDataObject.Children c : dataObject.children) {
                    subCategoryArray.add(new SubCategory(c.translated_title, "", c.node_slug, "", c.kind));
                    Log.d(LOG_TAG, c.kind);
                    if (c.kind.equals("Video")) {
                        if (!visited) {
                            new HttpRequestTask(Routes.getVideoImageUrl(dataObject.domain_slug), new HttpRequestListener() {
                                @Override
                                public void processHttpRequest(Gson data, BufferedReader reader) {
                                    videos = new ArrayList<JsonVideoObject>(Arrays.asList(data.fromJson(reader, JsonVideoObject[].class)));

                                    Log.d("sub", "sizeof adapter: " + Integer.toString(subCategoryArray.size()));
                                    int i = 0;
                                    for (JsonVideoObject vid : videos) {
                                        //subCategoryArray.add(new SubCategory(Integer.toString(i), "", c.node_slug, vid.download_urls.png, c.kind));

                                        Log.d("123", "SETTING URLS");
                                        Log.d("sub", vid.download_urls.png);
                                        imageUrls.add(vid.download_urls.png);
                                        subCategoryArray.get(i).image = vid.download_urls.png;
                                        i++;
                                    }
                                    Log.d("sub", "URLFINISH");
                                    flag = false;
                                }
                            }, null, null).execute();
                            visited = true;
                        }
                    } else {
                        flag = false;
                    }
                }
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
                    Log.d(LOG_TAG, subCategoryArray.get(position).node_slug);
                    Log.d(LOG_TAG, "click...!!!!sads");
                    Log.d(LOG_TAG, subCategoryArray.get(0).kind);

                    Log.d(LOG_TAG, Routes.CATEROGY + node_slug);

                    new HttpRequestTask(Routes.CATEROGY + node_slug, new HttpRequestListener() {
                       @Override
                       public void processHttpRequest(Gson data, BufferedReader reader) {
                           dataObject = data.fromJson(reader, JsonDataObject.class);
                           Log.d(LOG_TAG, dataObject.toString());
                           Log.d(LOG_TAG, dataObject.children[0].kind);

                           Fragment fragment;
                           if(dataObject.children[0].kind.equals("Topic")) {
                               Log.d(LOG_TAG, "Its a topic, move on");
                               Bundle bundle = new Bundle();

                               bundle.putString("DATA", new Gson().toJson(dataObject));
                               bundle.putString("newcat", "sda");
                               fragment = new SubCategoryFragment();
                               fragment.setArguments(bundle);
                           } else {
                               fragment = new VideoFragment();
                           }


                           FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                           fragmentManager.beginTransaction()
                                   .replace(R.id.subcategory_container, fragment)
                                   .addToBackStack(null)
                                   .commit();
                       }
                    },  null, null).execute();
                }
            });

        }

        public void bindSubCaterogy(SubCategory sub) {
            subCategory = sub;
            if(!subCategory.image.isEmpty()) {

                Log.d("123", "IMAGEVIEWI");
                Boolean isImage = mImageView.getDrawable() == null;
                Log.d("123", Boolean.toString(isImage));
                Log.d("sub", subCategory.image);
                ImageLoader.getInstance().displayImage(subCategory.image, mImageView);

            }
            Log.d(LOG_TAG, "SETTING TEXT");

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

        public SubCategory(String title_, String desc_, String slug_, String image_, String kind_) {
            title = title_;
            desc = desc_;
            node_slug = slug_;
            image = image_;
            kind = kind_;
        }
    }
}
