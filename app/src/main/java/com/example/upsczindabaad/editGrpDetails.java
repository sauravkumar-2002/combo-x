package com.example.upsczindabaad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
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

public class editGrpDetails extends AppCompatActivity {

    TextView grpName1, grpCode;
    ImageView grpImage1;
    RecyclerView recyclerView;

    ArrayList<partmodel> data;
    partadapter partadapter;
    String inviteCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_grp_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        grpName1 = findViewById(R.id.grpname);
        grpCode = findViewById(R.id.grpCode);
        grpImage1 = findViewById(R.id.grpImage1);
        recyclerView = findViewById(R.id.rvParticipants);

        Intent intent = getIntent();
        String gname = intent.getStringExtra("grpname");
        String gpic = intent.getStringExtra("grpimage");
        inviteCode = intent.getStringExtra("grpInvite");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();
        partadapter = new partadapter(data, this);
        recyclerView.setAdapter(partadapter);

        grpName1.setText(gname);
        grpCode.setText(inviteCode);
        Glide.with(getApplicationContext()).load(gpic).into(grpImage1);

        getAllParticipants();


    }

    private void getAllParticipants() {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("grup").child(inviteCode).child("members");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {



                    modelgrupparticipants modelgrupparticipants = snapshot1.getValue(com.example.upsczindabaad.modelgrupparticipants.class);
                    String uid = modelgrupparticipants.getUid();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String myUid = user.getUid();

                    if (myUid.equals(uid)) {
                        continue;
                    } else {

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("List of Users").child(uid);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                userdatamodel userdatamodel = snapshot.getValue(com.example.upsczindabaad.userdatamodel.class);
                                String participantname = userdatamodel.getUsername();
                                String participantsImage = userdatamodel.getPimage();

                                partmodel partmodel = new partmodel();
                                partmodel.setImage(participantsImage);
                                partmodel.setName(participantname);
                                Log.v("sandy", participantname);
                                data.add(partmodel);
                                partadapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }



                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}