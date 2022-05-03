package com.xclusive.newserialmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.hardware.usb.UsbDevice;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import java.io.UnsupportedEncodingException;

public class MainActivity2 extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar1 = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar1);
         getSupportActionBar().setTitle("PRIMARY HEALTHTECT");

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new usernamesuggestion_fragment(), "Devise List").commit();
        }
        else {
            onBackStackChanged();
        }


    }

    /**
     * Called whenever the contents of the back stack change.
     */
    @Override
    public void onBackStackChanged() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount()>0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        if(intent.getAction().equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
           Dashboard1_fragment terminal =  (Dashboard1_fragment) getSupportFragmentManager().findFragmentByTag("terminal");
            if (terminal != null)
                terminal.status("USB device detected");
        }
        super.onNewIntent(intent);
    }
}