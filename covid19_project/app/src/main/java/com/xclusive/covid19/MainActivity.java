package com.xclusive.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

   ImageView imageView1;
   TextView textView1,textView2;

   Animation top,bottom;

    private  static  int SPLASH_SCREEN=1500;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        imageView1 =findViewById(R.id.imageview);
        textView1 = findViewById(R.id.textview1);
        textView2 = findViewById(R.id.textview2);

        top = AnimationUtils.loadAnimation(this,R.anim.top);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom);

        imageView1.setAnimation(top);
        textView1.setAnimation(bottom);
        textView2.setAnimation(bottom);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseUser !=null)
                {
                    Intent intent = new Intent(MainActivity.this,dashboard.class);

                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this,loginactivity.class);

                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_SCREEN);
    }
}