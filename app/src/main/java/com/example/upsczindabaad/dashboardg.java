package com.example.upsczindabaad;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class dashboardg extends AppCompatActivity {

    ImageView mainImage;
    TextView icText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardg);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainImage=findViewById(R.id.mainUserIMage);
        icText=findViewById(R.id.individualChatText);
    }

    public void joinroom(View view) {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startchat(View view) {
        Intent intent1=new Intent(getApplicationContext(),individualchat.class);
        Pair[] pairs=new Pair[1];
        pairs[0]=new Pair<View,String>(mainImage,"IndividualChatsImage");


        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(this,pairs);


        startActivity(intent1,options.toBundle());
    }

    public void createroom(View view) {
    }



    public void posts(View view) {


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void myprofile(View view) {
        Intent intent1=new Intent(getApplicationContext(),myprofile.class);
        Pair[] pairs=new Pair[1];
        pairs[0]=new Pair<View,String>(mainImage,"myProfileImage");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(this,pairs);
        startActivity(intent1,options.toBundle());
    }
}