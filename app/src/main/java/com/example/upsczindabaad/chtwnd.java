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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class chtwnd extends AppCompatActivity {

    TextView chatPersonName;
    ImageView chatPersonImage, emojiImage;
    EditText edtmsg;
    String chatPerson;
    String userNameForGrp;
    userdatamodel umd4;
    userdatamodel umd5;
    RecyclerView chatrecv;
    ic_adapter ic_adapter;
    ArrayList<ic_model1> list;
    String nameown, msges, chetperson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chtwnd);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        chatPersonName = findViewById(R.id.chatPersonName);
        chatPersonImage = findViewById(R.id.chatPersonImage);
        emojiImage = findViewById(R.id.emojiImage);
        chatrecv = findViewById(R.id.chatrecv);




        boolean reverseLayout = false; // Or false if your data is already reversed
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, reverseLayout);
        manager.setStackFromEnd(true);
        chatrecv.setLayoutManager(manager);

        //chatrecv.setLayoutManager(new LinearLayoutManager(this));
        edtmsg = findViewById(R.id.edtmsg);
        Intent intent = getIntent();
        chatPerson = intent.getStringExtra("name");
        int chatPersonImages = intent.getIntExtra("image", R.drawable.userimage);
        chatPersonName.setText(chatPerson);
        chatPersonImage.setImageResource(chatPersonImages);

        list = new ArrayList<>();
        ic_adapter = new ic_adapter(list, this);
        chatrecv.setAdapter(ic_adapter);
        chatrecv.hasFixedSize();
        FirebaseUser user1=FirebaseAuth.getInstance().getCurrentUser();
        String myuser=user1.getUid();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("List of Users").child(myuser);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userdatamodel userdatamodel1=snapshot.getValue(userdatamodel.class);
                String myusername=userdatamodel1.getUsername();
                String groupname=myusername+chatPerson;
                DatabaseReference refacces=FirebaseDatabase.getInstance().getReference("private chats").child(groupname);
                refacces.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot s:snapshot.getChildren()){
                            chatwndmodel chatmodel=s.getValue(chatwndmodel.class);
                            String sendername=chatmodel.getSendername();
                            String msgsend=chatmodel.getMsg();



                            ic_model1 modelrec=new ic_model1();
                            if(sendername.equals(myusername)){
                                modelrec.setViewtype(0);

                            }
                            else{
                                modelrec.setViewtype(1);
                            }
                           // modelrec.setName(sendername);
                            modelrec.setMsg(msgsend);
                            list.add(modelrec);



                        }
                        ic_adapter.notifyDataSetChanged();
                        chatrecv.smoothScrollToPosition(chatrecv.getAdapter().getItemCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void send(View view) {
        if (edtmsg.getText().toString().trim().isEmpty()) {
            Toasty.error(getApplicationContext(), "enter message").show();
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("List of Users").child(uid);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    umd4 = new userdatamodel();
                    umd4 = snapshot.getValue(userdatamodel.class);
                    userNameForGrp = umd4.getUsername();

                    String groupName = chatPerson + userNameForGrp;
                    String mirrorName = userNameForGrp + chatPerson;
                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("private chats").child(groupName);
                    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("private chats").child(mirrorName);

                    String mssg = edtmsg.getText().toString().trim();

                    chatwndmodel ch = new chatwndmodel();
                    ch.setMsg(mssg);
                    ch.setUid(uid);
                    ch.setSendername(userNameForGrp);

                    chatwndmodel ch2 = new chatwndmodel();
                    ch2.setMsg(mssg);
                    ch2.setUid(uid);
                    ch2.setSendername(userNameForGrp);

                    ref1.push().setValue(ch);
                    ref2.push().setValue(ch2);
                    edtmsg.setText("");


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }


    public void emoji(View view) {


    }
}