package com.example.upsczindabaad;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class postpageadaapter extends FragmentPagerAdapter {

    int tabcount;
    public postpageadaapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new postfragment_posts();
            case 1:return new postfragment_videos();
            case 2:return new postfragment_status();
            default:return null;
        }


    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
