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

public class editmyprofile extends AppCompatActivity {
Uri profileurl;
ImageView editprofpic;
Bitmap bitmap;
String profimsage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmyprofile);
        editprofpic=findViewById(R.id.editmpprof);
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
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("file uploader");
        dialog.show();
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploader=storage.getReference().child("image/*");
        uploader.putFile(profileurl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                            }
                        });
                        Toast.makeText(getApplicationContext(),"uploaded",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("uploaded :"+(int)percent+"%");
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
            }catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}