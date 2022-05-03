package com.xclusive.newserialmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class usernameActivity extends AppCompatActivity {
    private EditText name;
    private FloatingActionButton activitybtn;
    private FirebaseFirestore firebaseFirestore;
    public ArrayList suggestionlist;
    public long index;
    public long size1;
    public static boolean user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
    }
}