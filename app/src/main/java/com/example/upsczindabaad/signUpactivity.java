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

import es.dmoral.toasty.Toasty;

public class signUpactivity extends AppCompatActivity {

    EditText username,fullname,emailSg,passwordSg;
    ProgressBar progressBarS;
    private FirebaseAuth mAuth1;

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
        String emailtext = emailSg.getText().toString().trim();
        String passwordtext = passwordSg.getText().toString().trim();

        // ALL THE VALIDATIONS-----


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



    public void LogIN(View view) {

        Intent intent=new Intent(getApplicationContext(),loginActivityG.class);
        startActivity(intent);
    }
}