package com.example.upsczindabaad;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class individualchat extends AppCompatActivity {
    TextView icusername;
    ImageView icprofilepic;
    RecyclerView icrecv;
    userdatamodel umd2;
    ArrayList<icmodel> list;
    icadapter icadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individualchat);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        icusername = (TextView) findViewById(R.id.icusername);
        icprofilepic = (ImageView) findViewById(R.id.icprofilepic);
        icrecv = (RecyclerView) findViewById(R.id.icrecview);
        icrecv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        icadapter = new icadapter(list, this);
        icrecv.setAdapter(icadapter);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("List of Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    userdatamodel umd3 = dataSnapshot.getValue(userdatamodel.class);
                    if (umd3.getUid().equals(uid)) {
                        continue;
                    }
                    String individual_chat = umd3.getUsername();
                    icmodel icm = new icmodel();
                    icm.setTv(individual_chat);
                    list.add(icm);
                }
                icadapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        setUserName();
    }

    private void setUserName() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("List of Users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                umd2 = new userdatamodel();
                umd2 = snapshot.getValue(userdatamodel.class);

                String username = umd2.getUsername();


                icusername.setText(username);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}