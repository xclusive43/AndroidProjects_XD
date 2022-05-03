package com.xclusive.university_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_activity extends AppCompatActivity {
    private EditText email1,password1;
    private Button  login;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        fireauth();
    }
    private void fireauth() {
        firebaseAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(v -> {
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
                            startActivity(new Intent(Login_activity.this, MainActivity.class));
                            finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Login_activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
    }

    private void init() {

        email1 = findViewById(R.id.email);
        password1 = findViewById(R.id.password);
        login = findViewById(R.id.loginbtn);

        progressBar = findViewById(R.id.loginprogress);




    }
}