package com.xclusive.abcmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Script;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private TextView email1,password1;
    private Button forgotbtn,signinbtn1,singupbtn1;
    private ProgressBar progressBar;
    private ImageView backbtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email1 = findViewById(R.id.email_input);
        password1 = findViewById(R.id.password_input);

        forgotbtn = findViewById(R.id.forgot_passwordbtn);
        singupbtn1 = findViewById(R.id.registerbtn);
        signinbtn1  = findViewById(R.id.loginbtn);
        progressBar = findViewById(R.id.loginprogressbar);
        backbtn = findViewById(R.id.backbtn1);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        singupbtn1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
                Intent signupintent = new Intent(login.this ,Signup.class);
                startActivity(signupintent);
                finish();
                }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        signinbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email1.getText().toString().isEmpty() ){
                    email1.setError("invalid email");
                }
                else if( password1.getText().toString().isEmpty()){
                    password1.setError("invalid password");
                }
                else{

                    //Toast.makeText(login.this, "task.getException().getMessage()", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(email1.getText().toString(),password1.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(login.this, MainActivity2.class));
                            finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                }
            }
        });



    }
}