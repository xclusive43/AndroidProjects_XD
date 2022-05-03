package com.xclusive.newserialmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler n = new Handler();
        n.postDelayed(new Runnable() {
            @Override
            public void run() {
                  Intent n1 = new Intent(MainActivity.this, MainActivity2.class);
                  startActivity(n1);
                  finish();
            }
        },1000);
    }
}