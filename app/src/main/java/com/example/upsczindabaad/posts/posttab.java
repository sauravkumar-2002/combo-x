package com.example.upsczindabaad.posts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.upsczindabaad.FragmentAdapter;
import com.example.upsczindabaad.R;
import com.google.android.material.tabs.TabLayout;

public class posttab extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    postFragManager postFragManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posttab);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        tabLayout=findViewById(R.id.tab_layout_post);
        pager2=findViewById(R.id.view_pager2_post);
        FragmentManager fragmentManager=getSupportFragmentManager();
        postFragManager=new postFragManager(fragmentManager,getLifecycle());

        pager2.setAdapter(postFragManager);


        tabLayout.addTab(tabLayout.newTab().setText("All Posts"));
        tabLayout.addTab(tabLayout.newTab().setText("My Posts"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }
}