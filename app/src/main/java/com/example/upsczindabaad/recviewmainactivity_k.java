package com.example.upsczindabaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
adapterk adapter;
EditText etks1,etks2;
ArrayList<modelk>mak;
FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recviewmaink);
        rvk=(RecyclerView)findViewById(R.id.recvk);
        rvk.setLayoutManager(new LinearLayoutManager(this));
        mak=new ArrayList<>();
        modelk md=new modelk();
etks1=(EditText)findViewById(R.id.etk1);
etks2=(EditText)findViewById(R.id.etk2);
user=FirebaseAuth.getInstance().getCurrentUser();

         db=FirebaseDatabase.getInstance();
        ref=db.getReference("saurav_sandeep");

        adapter=new adapterk(mak);
        rvk.setAdapter(adapter);

        fetchdata();
    }

    private void fetchdata() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void send(View view) {
        String msg=etks1.getText().toString().trim();
        String name=etks2.getText().toString().trim();
        String id=user.getUid();

        dataholder obj=new dataholder(msg,name,id);
        ref.setValue(obj);
        etks1.setText("");
        etks2.setText("");
        Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
    }
}