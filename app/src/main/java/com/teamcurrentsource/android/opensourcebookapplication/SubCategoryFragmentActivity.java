package com.teamcurrentsource.android.opensourcebookapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Antti on 21.3.2016.
 */

public class SubCategoryFragmentActivity extends FragmentActivity {

    public FragmentManager fragmentManager;
    public static String INDEX = "DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_category);


        String data = getIntent().getStringExtra(HttpRequestTask.INDEX);
        Log.d(HttpRequestTask.LOG_TAG, "-------------------------");

        Log.d(HttpRequestTask.LOG_TAG, data);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.subcategory_container);

        if(fragment == null) {
            Log.d("123", "beginTransac");
            Bundle bundle = new Bundle();
            bundle.putString(INDEX, data);
            fragment = createNewFragment();
            fragment.setArguments(bundle);
            //aloitetaan transaktio, lisätään itse fragmentti layouttiin ja komitoidaan.
            fragmentManager.beginTransaction().add(R.id.subcategory_container, fragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
        fragmentManager.popBackStack();
        }
    }

    protected Fragment createNewFragment() {
        //luodaan itse fragmentti
        return new SubCategoryFragment();
    }
}
