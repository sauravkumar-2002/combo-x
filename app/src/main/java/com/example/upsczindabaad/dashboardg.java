package com.example.upsczindabaad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class dashboardg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardg);
    }

    public void joinroom(View view) {
    }

    public void startchat(View view) {
        Intent intent1=new Intent(getApplicationContext(),recviewmainactivity_k.class);
        startActivity(intent1);
    }

    public void createroom(View view) {
    }
}