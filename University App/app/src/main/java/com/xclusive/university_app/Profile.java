package com.xclusive.university_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Profile extends AppCompatActivity {

    private String name,email,dob,photo,dept,course,sem;
    private ImageView profile;
    private TextView name1,email1,dob1,dept1,course1,sem1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name1 = findViewById(R.id.pname);
        profile = findViewById(R.id.pproflie);
        email1 = findViewById(R.id.pemail);
        dob1 = findViewById(R.id.dob);
        dept1 = findViewById(R.id.department);
        course1 = findViewById(R.id.course);
        sem1 = findViewById(R.id.semester);



    }

    @Override
    protected void onStart() {
        super.onStart();
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        dob = getIntent().getStringExtra("dob");
        dept = getIntent().getStringExtra("dept");
        course = getIntent().getStringExtra("course");
        sem = getIntent().getStringExtra("semester");
        photo = getIntent().getStringExtra("photo");


        name1.setText(name);
        email1.setText(email);
        Glide.with(this).load(photo).placeholder(R.drawable.profile).into(profile);
        dob1.setText("DOB: "+dob);
        dept1.setText("Department: "+dept);
        course1.setText("Course: "+course);
        sem1.setText("Semester: "+sem);
    }
}