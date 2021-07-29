package com.example.upsczindabaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tabbedWindow extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter fragmentAdapter;

    TextView usernameGrp;
    ImageView imageGrp;

    userdatamodel umd3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_window);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tabLayout=findViewById(R.id.tab_layout);
        usernameGrp=findViewById(R.id.icusername);
        imageGrp=findViewById(R.id.icprofilepic);
        pager2=findViewById(R.id.view_pager2);
        FragmentManager fm=getSupportFragmentManager();
        fragmentAdapter=new FragmentAdapter(fm,getLifecycle());
        pager2.setAdapter(fragmentAdapter);

       setUserName();



        tabLayout.addTab(tabLayout.newTab().setText("Friends"));
        tabLayout.addTab(tabLayout.newTab().setText("Groups"));


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

    private void setUserName() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("List of Users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                umd3 = new userdatamodel();
                umd3 = snapshot.getValue(userdatamodel.class);
                String userimage = umd3.getPimage();
                String username = umd3.getUsername();
                if (userimage.equals("notuploaded")) {
                    imageGrp.setImageResource(R.drawable.prof);
                    // Toasty.success(getApplicationContext(), "Default image has been setted").show();
                } else {
                    Glide.with(getApplicationContext())
                            .load(userimage)
                            .into(imageGrp);
                    //Toasty.success(getApplicationContext(), "Updated").show();
                }

                usernameGrp.setText(username);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}