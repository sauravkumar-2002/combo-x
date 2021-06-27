package com.example.upsczindabaad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class recviewmainactivity_k extends AppCompatActivity {
RecyclerView rvk;
adapterk adapter;
ArrayList<modelk>mak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recviewmaink);
        rvk=(RecyclerView)findViewById(R.id.recvk);
        rvk.setLayoutManager(new LinearLayoutManager(this));
        mak=new ArrayList<>();
        modelk md=new modelk();


        adapter=new adapterk(mak);
        rvk.setAdapter(adapter);
    }
}