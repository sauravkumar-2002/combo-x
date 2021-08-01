package com.example.upsczindabaad;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class editGrpDetails extends AppCompatActivity {
Uri profileurl;
Bitmap bitmap;
boolean x=false;
ImageView tick;

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
        tick=findViewById(R.id.tick);
        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x==false) {
                    Toast.makeText(getApplicationContext(), "Choose Image First", Toast.LENGTH_SHORT).show();
                }
                else {
                    ProgressDialog dialog = new ProgressDialog(editGrpDetails.this);
                    dialog.setTitle("file uploader");
                    dialog.show();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference uploader = storage.getReference().child("grp_dp").child(uid);
                    uploader.putFile(profileurl)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    dialog.dismiss();
                                    uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
String x=uri.toString();
DatabaseReference db=FirebaseDatabase.getInstance().getReference("grup").child(inviteCode).child("groupdetails").child("gruppic");
db.setValue(x);
DatabaseReference db1=FirebaseDatabase.getInstance().getReference("userowngroup").child(uid);
db1.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for(DataSnapshot s:snapshot.getChildren()){
           Map<String,String>mp=(Map)s.getValue();
           String s1=mp.get("invitecode");
           if(s1.equals(inviteCode)){
               Map<String,String>mp2=new HashMap<>();
               mp2.put("grouppic",x);
               db1.child(s.getKey()).child("grouppic").setValue(x);
               tick.setVisibility(View.INVISIBLE);
               break;

           }
           else{
               continue;
           }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

                                        }
                                    });
                                    Toasty.success(getApplicationContext(), "Updated").show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                    dialog.setMessage("uploaded :" + (int) percent + "%");
                                }
                            });
                }
            }
        });
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
                               // Log.v("sandy", participantname);
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

    public void grpimagechaange(View view) {
        Dexter.withActivity(editGrpDetails.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent =new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent.createChooser(intent,"please select your profile pic"),1);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK){
            profileurl=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(profileurl);
                bitmap= BitmapFactory.decodeStream(inputStream);
                grpImage1.setImageBitmap(bitmap);
                x=true;
                tick.setVisibility(View.VISIBLE);
            }catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}