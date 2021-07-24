package com.example.upsczindabaad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class chtwnd extends AppCompatActivity {

    TextView chatPersonName;
    ImageView chatPersonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chtwnd);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        chatPersonName=findViewById(R.id.chatPersonName);
        chatPersonImage=findViewById(R.id.chatPersonImage);

        Intent intent=getIntent();
        String chatPerson=intent.getStringExtra("name");
        int chatPersonImages=intent.getIntExtra("image",R.drawable.userimage);
        chatPersonName.setText(chatPerson);
        chatPersonImage.setImageResource(chatPersonImages);

    }
}