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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class chtwnd extends AppCompatActivity {

    TextView chatPersonName;
    ImageView chatPersonImage,emojiImage;
    EditText edtmsg;
    String chatPerson;
    String userNameForGrp;
    userdatamodel umd4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chtwnd);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        chatPersonName = findViewById(R.id.chatPersonName);
        chatPersonImage = findViewById(R.id.chatPersonImage);
        emojiImage=findViewById(R.id.emojiImage);
        edtmsg = findViewById(R.id.edtmsg);
        Intent intent = getIntent();
        chatPerson = intent.getStringExtra("name");
        int chatPersonImages = intent.getIntExtra("image", R.drawable.userimage);
        chatPersonName.setText(chatPerson);
        chatPersonImage.setImageResource(chatPersonImages);


    }

    public void send(View view) {


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

                chatwndmodel ch2 = new chatwndmodel();
                ch2.setMsg(mssg);
                ch2.setUid(uid);

                ref1.push().setValue(ch);
                ref2.push().setValue(ch2);
                edtmsg.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void emoji(View view) {


    }
}