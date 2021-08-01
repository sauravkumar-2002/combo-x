package com.example.upsczindabaad;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dashboardg extends AppCompatActivity {

    ImageView mainImage;
    userdatamodel umd, profimage;
    TextView helloMSg;
    TextView icText;
    String uid, link;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardg);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainImage = findViewById(R.id.mainUserIMage);
        helloMSg = findViewById(R.id.hellomsg);
        icText = findViewById(R.id.individualChatText);


        setprofileimage();
        setCorrectHelloMsg();
    }

    private void setprofileimage() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        uid = user.getUid();
        FirebaseDatabase db1 = FirebaseDatabase.getInstance();
        DatabaseReference ref2 = db1.getReference("List of Users").child(uid);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profimage = snapshot.getValue(userdatamodel.class);
                link = profimage.pimage;
                if (link.equals("notuploaded")) {
                    mainImage.setImageResource(R.drawable.prof);
                    // Toasty.success(getApplicationContext(), "Default image has been setted").show();
                } else {
                    Glide.with(getApplicationContext())
                            .load(link)
                            .into(mainImage);
                    //Toasty.success(getApplicationContext(), "Updated").show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setCorrectHelloMsg() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("List of Users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                umd = new userdatamodel();
                umd = snapshot.getValue(userdatamodel.class);

                 username = umd.getUsername();

                helloMSg.setText("Hello,\n" + username);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void joinroom(View view) {
        Bundle bundle=new Bundle();
        bundle.putString("username",username);
        bundle.putString("link",link);
        bottomFragmentJoin bottomFragmentJoin = new bottomFragmentJoin();
        bottomFragmentJoin.setArguments(bundle);
        bottomFragmentJoin.show(getSupportFragmentManager(), bottomFragmentJoin.getTag());


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startchat(View view) {
        Intent intent = new Intent(getApplicationContext(), tabbedWindow.class);
        intent.putExtra("check", 0);
        startActivity(intent);
    }

    public void createroom(View view) {
        Bundle bundle=new Bundle();
        bundle.putString("username",username);
        bundle.putString("link",link);
        fragmentForcreate fragmentForcreate = new fragmentForcreate();
        fragmentForcreate.setArguments(bundle);
        fragmentForcreate.show(getSupportFragmentManager(), fragmentForcreate.getTag());




    }


    public void posts(View view) {
        Intent intent = new Intent(getApplicationContext(), com.example.upsczindabaad.posts.posttab.class);
        startActivity(intent);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void myprofile(View view) {
        Intent intent1 = new Intent(getApplicationContext(), myprofile.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(mainImage, "myProfileImage");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
        startActivity(intent1, options.toBundle());

    }
}