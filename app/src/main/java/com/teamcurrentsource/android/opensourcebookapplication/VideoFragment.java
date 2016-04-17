package com.teamcurrentsource.android.opensourcebookapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;




public class VideoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        initializeItems();
        return view;
    }

    public void initializeItems() {
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            Log.d(SubCategoryFragment.LOG_TAG, "theres some bundles");
            String data = bundle.getString(SubCategoryFragmentActivity.INDEX);
            Log.d(SubCategoryFragment.LOG_TAG, data);
            //JsonDataObject dataObject = new Gson().fromJson(data, JsonDataObject.class);
        }
    }

}

