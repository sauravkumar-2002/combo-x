package com.example.upsczindabaad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.upsczindabaad.R;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class postaudioupload extends AppCompatActivity {
Uri audiourl;
VideoView audioview;
String uploadaudiourl,username,profilepic;
boolean x=false;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postaudioupload);
        audioview=(VideoView)findViewById(R.id.postaudioView);
        mediaController=new MediaController(this);
        audioview.setMediaController(mediaController);
        audioview.start();
    }

    public void browseaudeo(View view) {
        Dexter.withActivity(postaudioupload.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent=new Intent();
                        intent.setType("audio/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"choose audio"),101);
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

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode==RESULT_OK){
            audiourl=data.getData();
            audioview.setVideoURI(audiourl);
            x=true;

        }
    }

    public void uploadaudeo(View view) {
        if (x == false) {
            Toast.makeText(getApplicationContext(), "Choose Video First", Toast.LENGTH_SHORT).show();
        } else {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("file uploader");
            dialog.show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("post").child("myaudios/"+System.currentTimeMillis()+new Random().nextInt(100));
            storageReference.putFile(audiourl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.dismiss();
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    uploadaudiourl = uri.toString();
                                    DatabaseReference dh = FirebaseDatabase.getInstance().getReference("List of Users").child(uid);
                                    dh.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            userdatamodel userdatamodel = snapshot.getValue(com.example.upsczindabaad.userdatamodel.class);
                                            username = userdatamodel.getUsername();
                                            profilepic = userdatamodel.getPimage();
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                                            String currentDateandTime = sdf.format(new Date());
                                            DatabaseReference dery = FirebaseDatabase.getInstance().getReference("posts").child(uid);
                                            DatabaseReference deryall = FirebaseDatabase.getInstance().getReference("allposts");
                                            postuploadmodel postuploadmodel = new postuploadmodel();
                                            postuploadmodel.setUsername(username);
                                            postuploadmodel.setPosttype("a");
                                            postuploadmodel.setPost(uploadaudiourl);
                                            postuploadmodel.setTime(currentDateandTime);
                                            postuploadmodel.setUseruploadedpic(profilepic);
                                            dery.push().setValue(postuploadmodel);
                                            deryall.push().setValue(postuploadmodel);
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
}