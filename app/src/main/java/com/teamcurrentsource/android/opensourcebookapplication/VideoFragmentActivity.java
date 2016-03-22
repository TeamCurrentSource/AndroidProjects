package com.teamcurrentsource.android.opensourcebookapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class VideoFragmentActivity extends SubCategoryFragmentActivity {

    private static int currentPosition;

    public static Intent getCategoryIntent(Context packageContext, int position) {
        currentPosition = position;
        return new Intent(packageContext, VideoFragmentActivity.class);
    }

    @Override
    protected Fragment createNewFragment() {
        return new VideoFragment();
    }
}
