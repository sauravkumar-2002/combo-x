package com.example.upsczindabaad;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class signUpactivity extends AppCompatActivity {
    String usernamestring, userpimage;
    EditText username, fullname, emailSg, passwordSg;
    ProgressBar progressBarS;
    private FirebaseAuth mAuth1;
    int ans=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username = findViewById(R.id.username1);
        fullname = findViewById(R.id.fullname1);
        emailSg = findViewById(R.id.emailsg1);
        passwordSg = findViewById(R.id.passwords1);
        progressBarS = findViewById(R.id.progressBarS);
        mAuth1 = FirebaseAuth.getInstance();

    }

    public void signUp(View view) {


        String emailtext = emailSg.getText().toString().trim();
        String passwordtext = passwordSg.getText().toString().trim();

        // ALL THE VALIDATIONS-----
        usernamestring = username.getText().toString().trim();

        int check = usernamestring.indexOf('.');
        if (check != -1) {
            this.username.setError("Invalid Format(.)");
            this.username.requestFocus();
            return;
        }

        int check1 = usernamestring.indexOf('$');
        if (check1 != -1) {
            this.username.setError("Invalid Format($)");
            this.username.requestFocus();
            return;
        }

        int check2 = usernamestring.indexOf('[');
        if (check2 != -1) {
            this.username.setError("Invalid Format([)");
            this.username.requestFocus();
            return;
        }

        int check3 = usernamestring.indexOf(']');
        if (check3 != -1) {
            this.username.setError("Invalid Format(])");
            this.username.requestFocus();
            return;
        }

        int check4 = usernamestring.indexOf('#');
        if (check4 != -1) {
            this.username.setError("Invalid Format(#)");
            this.username.requestFocus();
            return;
        }
        int check5 = usernamestring.indexOf('/');
        if (check5 != -1) {
            this.username.setError("Invalid Format(/)");
            this.username.requestFocus();
            return;
        }


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




        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("List of Users");

reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        ans=1;
        for(DataSnapshot s:snapshot.getChildren()){
          userdatamodel userdatamodel=s.getValue(com.example.upsczindabaad.userdatamodel.class);

          if(userdatamodel.getUsername().equals(usernamestring)){
             ans=0;
              break;
          }


        }

        if(ans==1){

            progressBarS.setVisibility(View.VISIBLE);


            mAuth1.createUserWithEmailAndPassword(emailtext, passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        storeDataToFirebase();
                        checkUserName();

                        progressBarS.setVisibility(View.INVISIBLE);
                        Toasty.success(getApplicationContext(), "Successfully registered", Toast.LENGTH_SHORT, true).show();
                        Intent intent = new Intent(getApplicationContext(), dashboardg.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);


                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            progressBarS.setVisibility(View.INVISIBLE);
                            Toasty.info(getApplicationContext(), "You are already registered!!!").show();
                        } else {
                            progressBarS.setVisibility(View.INVISIBLE);
                            Toasty.error(getApplicationContext(), task.getException().getMessage()).show();
                        }
                    }
                }
            });




        }
        else{
            Toasty.error(getApplicationContext(),"Username already exists").show();
        }






    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
















        //USERNAME VALIADTIONS---






       /* Intent myIntent = new Intent(view.getContext(),chtwnd.class);
        myIntent.putExtra("name",usernamestring);
        startActivity(myIntent);


        */
    }

    private void checkUserName() {


    }

    private void storeDataToFirebase() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("List of Users");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getUid();
        String email = emailSg.getText().toString().trim();
        String pass = passwordSg.getText().toString();
        String fullname1 = fullname.getText().toString().trim();
        String userNm = username.getText().toString().trim();
        userpimage = "notuploaded";

        userdatamodel um = new userdatamodel();
        um.setEmail(email);
        um.setFullname(fullname1);
        um.setPassword(pass);
        um.setUid(uid);
        um.setUsername(userNm);
        um.setPimage(userpimage);
        ref.child(uid).setValue(um);
    }


    public void LogIN(View view) {

        Intent intent = new Intent(getApplicationContext(), loginActivityG.class);
        startActivity(intent);
    }
}