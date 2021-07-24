package com.example.upsczindabaad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class individualchat extends AppCompatActivity {
TextView icusername;
ImageView icprofilepic;
RecyclerView icrecv;
ArrayList<icmodel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individualchat);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        icusername=(TextView)findViewById(R.id.icusername);
        icprofilepic=(ImageView)findViewById(R.id.icprofilepic);
        icrecv=(RecyclerView)findViewById(R.id.icrecview);
        icrecv.setLayoutManager(new LinearLayoutManager(this));

        icadapter icadapter=new icadapter(givelist(),this);
        icrecv.setAdapter(icadapter);
    }

    private ArrayList<icmodel> givelist() {

        list=new ArrayList<>();
        icmodel md=new icmodel();
        md.setTv("Tokyo");
        md.setImageUrl(R.drawable.tokyo);
        list.add(md);

        icmodel md1=new icmodel();
        md1.setTv("Arturo");
        md1.setImageUrl(R.drawable.arturo);
        list.add(md1);

        icmodel md2=new icmodel();
        md2.setTv("Denver");
        md2.setImageUrl(R.drawable.denver);
        list.add(md2);

        icmodel md3=new icmodel();
        md3.setTv("Monica");
        md3.setImageUrl(R.drawable.monica);
        list.add(md3);

        icmodel md4=new icmodel();
        md4.setTv("Berlin");
        md4.setImageUrl(R.drawable.berlin);
        list.add(md4);

        icmodel md5=new icmodel();
        md5.setTv("Moscow");
        md5.setImageUrl(R.drawable.moscow);
        list.add(md5);

        icmodel md6=new icmodel();
        md6.setTv("Proffesor");
        md6.setImageUrl(R.drawable.proff);
        list.add(md6);

        icmodel md7=new icmodel();
        md7.setTv("Rio");
        md7.setImageUrl(R.drawable.rio);
        list.add(md7);

        icmodel md8=new icmodel();
        md8.setTv("Raquel");
        md8.setImageUrl(R.drawable.rqquel);
        list.add(md8);

        return  list;
    }
}