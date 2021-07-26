package com.example.upsczindabaad;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

public class myprofile extends AppCompatActivity {

   ImageView profpic;
    TextView profilefullname, profileusername;
    userdatamodel umd1,profimage;
    Uri profilepicurl;
    String uid,link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        profpic=findViewById(R.id.mpprof);
        profilefullname = findViewById(R.id.profileFullname);
        profileusername = findViewById(R.id.profileUserName);


        setCorrectUserDetails();
        setprofileimage();

    }

    private void setprofileimage() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        uid=user.getUid();
        FirebaseDatabase db1=FirebaseDatabase.getInstance();
        DatabaseReference ref2=db1.getReference("List of Users").child(uid);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profimage = snapshot.getValue(userdatamodel.class);
                link = profimage.pimage;
                if (link.equals("notuploaded")) {
                   // Toasty.success(getApplicationContext(), "Default image has been setted").show();
                }
                else {
                    Glide.with(myprofile.this)
                            .load(link)
                            .into(profpic);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setCorrectUserDetails() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("List of Users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                umd1 = new userdatamodel();
                umd1 = snapshot.getValue(userdatamodel.class);

                String username = umd1.getUsername();
                String fullname = umd1.getFullname();

                profilefullname.setText(fullname);
                profileusername.setText(username);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "false");
        editor.apply();
        Intent intent2 = new Intent(getApplicationContext(), loginActivityG.class);
        startActivity(intent2);
        finish();


    }

    public void profchange(View view) {
        Intent intent=new Intent(getApplicationContext(),editmyprofile.class);
        startActivity(intent);
    }
}