package com.teamcurrentsource.android.opensourcebookapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SubCategoryActivity extends SingleCategoryActivity {

    private static int currentPosition;

    public static Intent getCategoryIntent(Context packageContext, int position) {
        currentPosition = position;
        return new Intent(packageContext, SubCategoryActivity.class);
    }

    @Override
    protected Fragment createNewFragment() {
        return new VideoFragment();
    }
}
