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

import es.dmoral.toasty.Toasty;

public class loginActivityG extends AppCompatActivity {

    EditText email,password;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_g);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.emailg);
        password=findViewById(R.id.passwordg);
        progressBar=findViewById(R.id.progressBarg);
    }

    public void SignUp(View view) {
    }

    public void forgotPassword(View view) {
    }

    public void loging(View view) {
        String EmailS=email.getText().toString().toLowerCase().trim();
        String passwordS=password.getText().toString().trim();


        if (EmailS.isEmpty()) {
            this.email.setError("Field can't be empty");
            this.email.requestFocus();
            return;
        }


        if (passwordS.isEmpty()) {
            password.setError("field required");
            password.requestFocus();
            return;
        }

        if (passwordS.length() < 6) {
            password.setError("minimum length of passwword is 6 leter");
            password.requestFocus();
        }


progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(EmailS, passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Toasty.success(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(getApplicationContext(), dashboardg.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toasty.error(getApplicationContext(),task.getException().getMessage()).show();
                }
            }
        });

    }
}