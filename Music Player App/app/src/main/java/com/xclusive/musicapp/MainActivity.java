package com.xclusive.musicapp;
/*dexter library working in sdk version less than 29*/
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler  handler  = new Handler();

        handler.postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, dashboard.class));
            finish();
        },1000);

    }
}