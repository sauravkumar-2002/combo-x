package com.example.upsczindabaad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class postimageupload extends AppCompatActivity {
String username,profpic,uploadedpic;
Uri profileurl;
Bitmap bitmap;
boolean x=false;
ImageView imgpost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postimageupload);

        imgpost=findViewById(R.id.imgpostbrowse);
    }

    public void browseimage(View view) {
        Dexter.withActivity(postimageupload.this)
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

    public void uploadimager(View view) {
        if (x==false) {
            Toast.makeText(getApplicationContext(), "Choose Image First", Toast.LENGTH_SHORT).show();
        }
        else {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("file uploader");
            dialog.show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference uploader = storage.getReference().child("post").child("images").child(uid+System.currentTimeMillis());
            uploader.putFile(profileurl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.dismiss();

                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                   uploadedpic=uri.toString();
                                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                                    String uid=user.getUid();
                                    DatabaseReference dh=FirebaseDatabase.getInstance().getReference("List of Users").child(uid);
                                    dh.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            userdatamodel userdatamodel=snapshot.getValue(com.example.upsczindabaad.userdatamodel.class);
                                            username=userdatamodel.getUsername();
                                            profpic=userdatamodel.getPimage();
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                                            String currentDateandTime = sdf.format(new Date());
                                            DatabaseReference dery= FirebaseDatabase.getInstance().getReference("posts").child(uid);
                                            postuploadmodel postuploadmodel=new postuploadmodel();
                                            postuploadmodel.setUsername(username);
                                            postuploadmodel.setPosttype("i");
                                            postuploadmodel.setPost(uploadedpic);
                                            postuploadmodel.setTime(currentDateandTime);
                                            postuploadmodel.setUseruploadedpic(profpic);
                                            dery.push().setValue(postuploadmodel);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK){
            profileurl=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(profileurl);
                bitmap= BitmapFactory.decodeStream(inputStream);
                imgpost.setImageBitmap(bitmap);
                x=true;
            }catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}