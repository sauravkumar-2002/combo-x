package com.example.upsczindabaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class recviewmainactivity_k extends AppCompatActivity {
    RecyclerView rvk;
    String id;
    signUpactivity svg;
    TextView textall;
    adapterk adapter;
    EditText etks1, etks2;
    ArrayList<modelk> mak;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference ref;
    fetchDataModel data;
    retrievemodelclass retrievedata;
    savingdatamodel savingdatamodel1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recviewmaink);
        rvk = (RecyclerView) findViewById(R.id.recvk);
        rvk.setLayoutManager(new LinearLayoutManager(this));
        mak = new ArrayList<>();
        modelk md = new modelk();
        etks1 = (EditText) findViewById(R.id.etk1);
//etks2=(EditText)findViewById(R.id.etk2);
        user = FirebaseAuth.getInstance().getCurrentUser();
//textall=(TextView)findViewById(R.id.tet1);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("saurav_sandeep");

        adapter = new adapterk(mak);
        rvk.setAdapter(adapter);
        svg = new signUpactivity();

    }

    private void fetchdata() {
        savingdatamodel1 = new savingdatamodel();
        FirebaseDatabase dg = FirebaseDatabase.getInstance();
        DatabaseReference refg = dg.getReference(id);
        retrievedata = new retrievemodelclass();
        refg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                savingdatamodel1=snapshot.getValue(savingdatamodel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data = new fetchDataModel();
                data = snapshot.getValue(fetchDataModel.class);
                String s1 = data.id + "\n" + data.msg + " \n" + data.name + "\n";
              //  textall.setText(s1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void send(View view) {

        String msg = etks1.getText().toString().trim();
        String name = savingdatamodel1.getUsername1();
        id = user.getUid();

        dataholder obj = new dataholder(msg, name, id);
        ref.setValue(obj);
        etks1.setText("");
        etks2.setText("");
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
        fetchdata();
    }
}