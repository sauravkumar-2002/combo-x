package com.example.upsczindabaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class groupchat extends AppCompatActivity {
EditText edtmsggrup;
ImageView gruppic;
TextView grupname;
RecyclerView gruprecv;
String invitecode="vhvhvh",sendername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);
        grupname=findViewById(R.id.grupchatPersonName);
        edtmsggrup=findViewById(R.id.grupedtmsg);
        gruppic=findViewById(R.id.grupchatPersonImage);
    }

    public void emoji(View view) {
    }

    public void send(View view) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("List of Users").child(uid);
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userdatamodel userdatamodel=snapshot.getValue(com.example.upsczindabaad.userdatamodel.class);
                sendername=userdatamodel.getUsername();
                DatabaseReference reft= FirebaseDatabase.getInstance().getReference("grup").child(invitecode);
                chatwndmodel chatwndmodel=new chatwndmodel();
                chatwndmodel.setMsg(edtmsggrup.getText().toString().trim());
                chatwndmodel.setSendername(sendername);
                chatwndmodel.setUid(uid);
                reft.child("chats").push().setValue(chatwndmodel);
                edtmsggrup.setText("");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void edtgrup(View view) {
    }
}