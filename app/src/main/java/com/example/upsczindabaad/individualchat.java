package com.example.upsczindabaad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class individualchat extends AppCompatActivity {
TextView icusername;
ImageView icprofilepic;
RecyclerView icrecv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individualchat);
        icusername=(TextView)findViewById(R.id.icusername);
        icprofilepic=(ImageView)findViewById(R.id.icprofilepic);
        icrecv=(RecyclerView)findViewById(R.id.icrecview);
        icrecv.setLayoutManager(new LinearLayoutManager(this));
    }
}