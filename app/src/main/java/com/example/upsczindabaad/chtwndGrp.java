package com.example.upsczindabaad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class chtwndGrp extends AppCompatActivity {

    ImageView grpImage;
    TextView grpName;
    String grpN, grpPic,inviteCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chtwnd_grp);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        grpName = findViewById(R.id.grpName);
        grpImage = findViewById(R.id.grpImage);

        Intent intent = getIntent();
        grpN = intent.getStringExtra("name");
        grpPic = intent.getStringExtra("image");
        inviteCode = intent.getStringExtra("invite");

        grpName.setText(grpN);
        Glide.with(getApplicationContext()).load(grpPic).into(grpImage);


    }

    public void send(View view) {


    }

    public void edtGrp(View view) {

        Intent intent = new Intent(getApplicationContext(), editGrpDetails.class);
        intent.putExtra("grpname", grpN);
        intent.putExtra("grpimage", grpPic);
        intent.putExtra("grpInvite", inviteCode);
        startActivity(intent);

    }
}