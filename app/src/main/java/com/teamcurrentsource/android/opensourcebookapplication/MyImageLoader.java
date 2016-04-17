package com.teamcurrentsource.android.opensourcebookapplication;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;


public class MyImageLoader {
    public static ImageLoader imageLoader;
    private static boolean isInitialized = false;

    public static void initializeImageLoader(Context context) {
        if(!isInitialized) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .writeDebugLogs()
                    .defaultDisplayImageOptions(defaultOptions)
                    .diskCacheExtraOptions(480, 320, null)
                    .build();


            imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);
        }
        isInitialized = true;
    }

    public static ImageLoader getMyImageLoader() {
        return imageLoader;
    }

}
