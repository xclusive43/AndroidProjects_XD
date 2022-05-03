package com.xclusive.abcmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
private TextView email;
private TextView username;
private TextView mobile;
private TextView password;
private TextView cpassword;
private CheckBox male,female,other;
public String gender;
private Button signup,signin;
private ImageView backbtn;
private ProgressBar signupprogressbar;
private FirebaseAuth firebaseAuth;
private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        mobile = findViewById(R.id.mobilenumber);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.confirmpassword);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        other = findViewById(R.id.othergender);
        signup = findViewById(R.id.signupbtn);
        signin = findViewById(R.id.signinbtn);
        backbtn = findViewById(R.id.backbtn);
        signupprogressbar = findViewById(R.id.signupprogressbar);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent productdetailsintent = new Intent(Signup.this,MainActivity2.class);
//                startActivity(productdetailsintent);
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinintent = new Intent(Signup.this , login.class);
                startActivity(signinintent);
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!emailvalid() | !namevalid() | !mobilevalid() | !gendervalid() | !passwordvalid() | !cpasswordvalid() ){

                      return;

                }
                else
                { signupprogressbar.setVisibility(View.VISIBLE);
                   firebaseAuth.createUserWithEmailAndPassword(email.getText().toString() ,password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){

                               loaddata();
                               signupprogressbar.setVisibility(View.GONE);

                           }
                           else {
                               signupprogressbar.setVisibility(View.GONE);
                               Toast.makeText(Signup.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                               finish();
                           }
                       }
                   });
                    //Toast.makeText(Signup.this,"gender",Toast.LENGTH_SHORT).show();
                    
                }
            }
        });





    }

    private void loaddata() {

//        Toast.makeText(Signup.this,"task.getException().getMessage()",Toast.LENGTH_SHORT).show();

        Map<Object,String > usedata  = new HashMap<>();
        usedata.put("fullname",username.getText().toString());
        usedata.put("mobile",mobile.getText().toString());
        usedata.put("gender",gender);
        usedata.put("password",password.getText().toString());

        firebaseFirestore.collection("USER").document(firebaseAuth.getUid())
                .set(usedata)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Map<Object,Long > listsize  = new HashMap<>();
                        listsize.put("list_size", (long) 0);
                        if(task.isSuccessful()){
                            firebaseFirestore.collection("USER").document(firebaseAuth.getUid()).collection("USER_DATE")
                                    .document("MY_WISHLIST").set(listsize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                  if(task.isSuccessful()){
                                      Toast.makeText(Signup.this,"Registration Successfully Done",Toast.LENGTH_SHORT).show();

                                      Intent loginactivity = new Intent(Signup.this, login.class);
                                      startActivity(loginactivity);
                                      finish();
                                  }
                                  else {
                                      signupprogressbar.setVisibility(View.INVISIBLE);
                                      Toast.makeText(Signup.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                  }
                                }
                            });


                        }
                        else {

                            Toast.makeText(Signup.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });




    }

    private boolean cpasswordvalid() {
        String val1 =cpassword.getText().toString();
        String val = password.getText().toString();

        String p ="^" +
                "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";
        if(val1.isEmpty())
        {
            Toast.makeText(Signup.this,"password empty!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(val1.isEmpty()){

            Toast.makeText(Signup.this,"password empty!",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(!val1.matches(p) )

        {
            Toast.makeText(Signup.this, "Field should contain at least one symbol ,character ,upper and lower case letters",Toast.LENGTH_SHORT).show();
            return false;

        }
        else if(val1.length() != val.length()| val==val1 | val1.length()<8){
            Toast.makeText(Signup.this, "invalid or missed matched password",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean passwordvalid() {
        String val = password.getText().toString();
        String val1 =cpassword.getText().toString();

        String p ="^" +
                "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";
        if(val.isEmpty())
        {
            Toast.makeText(Signup.this,"password empty!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(val1.isEmpty()){

            Toast.makeText(Signup.this,"password empty!",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(!val.matches(p) )

        {
                Toast.makeText(Signup.this, "Field should contain at least one symbol ,character ,upper and lower case letters",Toast.LENGTH_SHORT).show();
                return false;

        }
         else {
             return true;
        }

    }
    private boolean gendervalid() {
//        if(!male.isChecked() | !female.isChecked() | !other.isChecked()){
//            Toast.makeText(Signup.this,"sdfgfsdg",Toast.LENGTH_LONG).show();
//            return  true;
//        }
        if(male.isChecked()){
            gender="";
            gender ="Male";
            return true;
        }
        else if(female.isChecked()){
            gender="";
            gender = "Female";
            return true;
        }
        else if(other.isChecked()){
            gender="";
            gender = "Others";
            return true;
        }
        else {
            return false;
        }

    }

    private boolean mobilevalid() {
        if(mobile.getText().toString().isEmpty()){
            mobile.setError("empty!");
            return  false;
        }
        else if(mobile.getText().toString().length()<10 |mobile.getText().toString().length()>10 |
                !mobile.getText().toString().trim().matches("[0-9]+")){
            mobile.setError("invalid number!");
            return false;
        }
        else {
            return true;
        }
    }

    private boolean namevalid() {
        String v = username.getText().toString();
        if(v.isEmpty())
        {
            username.setError("empty!");
            return false;
        }
        else if(v.length()<=2 | v.length() >100 | v.matches("[0-9]+")){
            username.setError("user name invalid");
            return false;
        }
        else {
            return  true;
        }

    }

    private boolean emailvalid() {

        String d ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String v = email.getText().toString();

        if(v.isEmpty()){

            email.setError("empty!");
            return false;

        }
        else if(!v.matches(d))
        {
            email.setError("invalid email");
            return false;
        }

        else {

            email.setError(null);
            return true;
        }

    }
}