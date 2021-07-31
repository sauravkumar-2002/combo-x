package com.example.upsczindabaad.posts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.upsczindabaad.allPost;
import com.example.upsczindabaad.myPost;

public class postFragManager extends FragmentStateAdapter {
    public postFragManager(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){

            case 1:
                return new myPost();

        }
        return new allPost();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
