package com.example.upsczindabaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class signUpactivity extends AppCompatActivity {
    public String usernamestring;
    EditText username,fullname,emailSg,passwordSg;
    ProgressBar progressBarS;
    private FirebaseAuth mAuth1;
    savingdatamodel savingdatamodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username=findViewById(R.id.username1);
        fullname=findViewById(R.id.fullname1);
        emailSg=findViewById(R.id.emailsg1);
        passwordSg=findViewById(R.id.passwords1);
        progressBarS=findViewById(R.id.progressBarS);
        mAuth1 = FirebaseAuth.getInstance();

    }

    public void signUp(View view) {
        savedata();
        String emailtext = emailSg.getText().toString().trim();
        String passwordtext = passwordSg.getText().toString().trim();

        // ALL THE VALIDATIONS-----
         usernamestring=username.getText().toString().trim();

        //EMAIL VALIDATIONS--
        if (emailtext.isEmpty()) {
            this.emailSg.setError("Field can't be empty");
            this.emailSg.requestFocus();
            return;
        }


        //PASSWORD VALIDATIONS----

        if (passwordtext.isEmpty()) {
            passwordSg.setError("field required");
            passwordSg.requestFocus();
            return;
        }

        if (passwordtext.length() < 6) {
            passwordSg.setError("minimum length of password is 6 leter");
            passwordSg.requestFocus();
        }


        //USERNAME VALIADTIONS---
        progressBarS.setVisibility(View.VISIBLE);


        mAuth1.createUserWithEmailAndPassword(emailtext,passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progressBarS.setVisibility(View.INVISIBLE);
                    Toasty.success(getApplicationContext(), "Successfully registered", Toast.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(getApplicationContext(), dashboardg.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        progressBarS.setVisibility(View.INVISIBLE);
                       Toasty.info(getApplicationContext(),"You are already registered!!!").show();
                    } else {
                        progressBarS.setVisibility(View.INVISIBLE);
                        Toasty.error(getApplicationContext(),task.getException().getMessage()).show();
                    }
                }
            }
        });


    }

    private void savedata() {
        FirebaseUser useruid=FirebaseAuth.getInstance().getCurrentUser();
        String uid1=useruid.getUid();
        String username1=username.getText().toString().trim();
        String fullname1=fullname.getText().toString().trim();
        String email1=emailSg.getText().toString().trim();
        String password1=passwordSg.getText().toString().trim();
        FirebaseDatabase df=FirebaseDatabase.getInstance();
        DatabaseReference reft=df.getReference(uid1);
        savingdatamodel=new savingdatamodel();
        savingdatamodel.setEmail1(email1);
        savingdatamodel.setFullname1(fullname1);
        savingdatamodel.setUsername1(username1);
        savingdatamodel.setPassword1(password1);
        reft.setValue(savingdatamodel);

    }


    public void LogIN(View view) {

        Intent intent=new Intent(getApplicationContext(),loginActivityG.class);
        startActivity(intent);
    }
}