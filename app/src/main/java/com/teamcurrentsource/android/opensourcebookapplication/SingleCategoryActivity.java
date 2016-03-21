package com.teamcurrentsource.android.opensourcebookapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by Antti on 21.3.2016.
 */

public class SingleCategoryActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_category);

        //alustetaan fragment manageri
        FragmentManager fragmentManager = getSupportFragmentManager();
        //etsitään fragmentti, resourceista, fragment 'viittaa' itse juurilayoutin id:seen
        Fragment fragment = fragmentManager.findFragmentById(R.id.subcategory_container);

        if(fragment == null) {
            fragment = createNewFragment();
            //aloitetaan transaktio, lisätään itse fragmentti layouttiin ja komitoidaan.
            fragmentManager.beginTransaction().add(R.id.subcategory_container, fragment).commit();
        }

    }

    private Fragment createNewFragment() {
        //luodaan itse fragmentti
        return new SubCategoryFragment();
    }
}
