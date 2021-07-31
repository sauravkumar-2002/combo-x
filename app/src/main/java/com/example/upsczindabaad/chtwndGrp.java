package com.example.upsczindabaad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chtwndGrp extends AppCompatActivity {

    ImageView grpImage;
    EditText grpmsg;
    TextView grpName;
    String grpN, grpPic, inviteCode;
    ArrayList<ic_model1> grpList;
    grpmsgAdapter grpmsgAdapter;
    RecyclerView grpRv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chtwnd_grp);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        grpName = findViewById(R.id.grpName);
        grpImage = findViewById(R.id.grpImage);
        grpmsg = findViewById(R.id.edtmsggrp);
        grpRv = findViewById(R.id.grprecview);

        Intent intent = getIntent();
        grpN = intent.getStringExtra("name");
        grpPic = intent.getStringExtra("image");
        inviteCode = intent.getStringExtra("invite");

        grpName.setText(grpN);
        Glide.with(getApplicationContext()).load(grpPic).into(grpImage);

        grpList = new ArrayList<>();
        grpmsgAdapter = new grpmsgAdapter(grpList, this);
        grpRv.setLayoutManager(new LinearLayoutManager(this));
        grpRv.setAdapter(grpmsgAdapter);

        fetchChats();

    }

    private void fetchChats() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("groupChat").child(inviteCode);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                grpList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Map<String, String> map = (Map) snapshot1.getValue();

                    String msg = map.get("msg");
                    String userName = map.get("username");
                    String uid = map.get("uid");


                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uids = user.getUid();


                    ic_model1 ic_model1 = new ic_model1();
                    ic_model1.setName(userName);
                    ic_model1.setMsg(msg);
                    if (uids.equals(uid)) ic_model1.setViewtype(0);
                    else ic_model1.setViewtype(1);

                    grpList.add(ic_model1);

                }

                grpmsgAdapter.notifyDataSetChanged();
                grpRv.smoothScrollToPosition(grpRv.getAdapter().getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void send(View view) {

        String grpMsg = grpmsg.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("List of Users").child(uid);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Map<String, String> map = (Map) snapshot.getValue();
                if (map != null) {
                    String userNAme = map.get("username");

                    Map<String, String> mp = new HashMap<>();
                    mp.put("msg", grpMsg);
                    mp.put("uid", uid);
                    mp.put("username", userNAme);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("groupChat").child(inviteCode);
                    reference.push().setValue(mp);
                    //  fetchChats();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void edtGrp(View view) {

        Intent intent = new Intent(getApplicationContext(), editGrpDetails.class);
        intent.putExtra("grpname", grpN);
        intent.putExtra("grpimage", grpPic);
        intent.putExtra("grpInvite", inviteCode);
        startActivity(intent);

    }

    public void emojigrp(View view) {


    }
}