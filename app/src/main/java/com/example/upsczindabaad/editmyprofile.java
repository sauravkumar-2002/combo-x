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
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import java.io.InputStream;

import es.dmoral.toasty.Toasty;

public class editmyprofile extends AppCompatActivity {
Uri profileurl;
ImageView editprofpic;
Bitmap bitmap;
    String uid;
    String s1;
    boolean x=false;
String profimsage,edtusername,edtemail,edtfullname,edtuid,edtpassword;
TextView txtusername,txtemail,txtfullname;
    userdatamodel usrd,profimage;
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmyprofile);
        editprofpic=findViewById(R.id.editmpprof);
        txtemail=findViewById(R.id.edtemail1);
        txtfullname=findViewById(R.id.edtfullname1);
        txtusername=findViewById(R.id.edtusername1);
        getalldata();
        setprofileimage();

    }

    private void getalldata() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

         uid=user.getUid();
        FirebaseDatabase dbsr=FirebaseDatabase.getInstance();
        DatabaseReference ret=dbsr.getReference("List of Users").child(uid);
        ret.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //userdatamodel usrd=new userdatamodel();
                usrd=snapshot.getValue(userdatamodel.class);
                edtemail=usrd.getEmail();
                edtfullname=usrd.getFullname();
                edtpassword=usrd.getPassword();
                edtusername=usrd.getUsername();
                edtuid=usrd.getUid();
                txtusername.setText(edtusername);
                txtfullname.setText(edtfullname);
                txtemail.setText(edtemail);
             //   Toasty.error(getApplicationContext(),"yeer").show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void profchange(View view) {
        Dexter.withActivity(editmyprofile.this)
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

    public void uploaddone(View view) {
        if (x==false) {
            Toast.makeText(getApplicationContext(), "Choose Image First", Toast.LENGTH_SHORT).show();
        }
         else{
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("file uploader");
        dialog.show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference().child("image").child(uid);
        uploader.putFile(profileurl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                profimsage = uri.toString();
                                FirebaseDatabase dfg = FirebaseDatabase.getInstance();
                                DatabaseReference redf = dfg.getReference("List of Users").child(uid);
                                userdatamodel udrt = new userdatamodel();
                                udrt.setPimage(profimsage);
                                udrt.setEmail(edtemail);
                                udrt.setFullname(edtfullname);
                                udrt.setPassword(edtpassword);
                                udrt.setUid(edtuid);
                                udrt.setUsername(edtusername);
                                redf.setValue(udrt);
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
        setprofileimage();
    }
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
                    Glide.with(editmyprofile.this)
                            .load(link)
                            .into(editprofpic);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK){
            profileurl=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(profileurl);
                bitmap= BitmapFactory.decodeStream(inputStream);
                editprofpic.setImageBitmap(bitmap);
                x=true;
            }catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}