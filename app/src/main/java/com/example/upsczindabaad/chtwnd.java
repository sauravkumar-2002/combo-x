package com.example.upsczindabaad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chtwnd extends AppCompatActivity {

    TextView chatPersonName;
    ImageView chatPersonImage;
   EditText edtmsg;
   FirebaseUser user;
    String chatPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chtwnd);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        chatPersonName=findViewById(R.id.chatPersonName);
        chatPersonImage=findViewById(R.id.chatPersonImage);
        edtmsg=findViewById(R.id.edtmsg);
        Intent intent=getIntent();
       chatPerson=intent.getStringExtra("name");
        int chatPersonImages=intent.getIntExtra("image",R.drawable.userimage);
        chatPersonName.setText(chatPerson);
        chatPersonImage.setImageResource(chatPersonImages);

    }

    public void send(View view) {
        String mmsge=edtmsg.getText().toString().trim();
        user= FirebaseAuth.getInstance().getCurrentUser();
        String mailname=user.getUid();
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference reference=db.getReference("chat").child(mailname);
        chatwndmodel chatwndmodel=new chatwndmodel();
        chatwndmodel.setEmailname(mailname);
        chatwndmodel.setMsg(mmsge);
        reference.child(chatPerson).push().setValue(chatwndmodel);
    }
}