package com.xclusive.university_app;

import android.os.Bundle;

import  android.content.Intent;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;



import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;

import androidx.navigation.ui.AppBarConfiguration;

import androidx.appcompat.app.AppCompatActivity;

import soup.neumorphism.NeumorphCardView;


public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private BottomNavigationView bottomNavigationView;
    NavController navController;
    private ImageView userprofile;
    private TextView username;
    private NeumorphCardView announcements,lectures,exam,attendance,assignments,results;
    private FirebaseFirestore userdb;
    private FirebaseAuth user;

    public static String name,email,dob,dept,course,semester;
    public static String photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /////////bottom nav///////////////
        init();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottonhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.bottonhome:
                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bottomfee:Toast.makeText(MainActivity.this, "fee", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bottommore:Toast.makeText(MainActivity.this, "more", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });
        /////////////////////////////////
    }

    private void init() {
        userprofile = findViewById(R.id.userprofile);
        username = findViewById(R.id.username);

        announcements = findViewById(R.id.announcecard);
        lectures = findViewById(R.id.lecturecard);
        attendance = findViewById(R.id.attendancecard);
        assignments = findViewById(R.id.assignmentcard);
        results = findViewById(R.id.resultscard);
        exam = findViewById(R.id.examavailablecard);




        announcements.setOnClickListener(v->{
            Intent intent1 = new Intent(MainActivity.this, Announcement_Activity.class);
            startActivity(intent1);
        });

        lectures.setOnClickListener(v->{
            Intent intent2 = new Intent(MainActivity.this, Lectures_activity.class);
            startActivity(intent2);
        });
        attendance.setOnClickListener(v->{
            Intent intent3 = new Intent(MainActivity.this, Attendence_activity.class);
            startActivity(intent3);
        });
        assignments.setOnClickListener(v->{
            Intent intent4 = new Intent(MainActivity.this, Assignment_activty.class);
            startActivity(intent4);
        });
        results.setOnClickListener(v->{
            Intent intent5 = new Intent(MainActivity.this, Result_activity.class);
            startActivity(intent5);
        });
        exam.setOnClickListener(v->{
            Intent intent6 = new Intent(MainActivity.this, Exam_ativity.class);
            startActivity(intent6);
        });

        userprofile.setOnClickListener(v->{
            Intent profile = new Intent(MainActivity.this, Profile.class);
            profile.putExtra("name",name);
            profile.putExtra("email",email);
            profile.putExtra("dob",dob);
            profile.putExtra("photo",photo);
            profile.putExtra("dept",dept);
            profile.putExtra("course",course);
            profile.putExtra("semester",semester);

            startActivity(profile);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance();

        if (user.getUid() != null){
            userdb = FirebaseFirestore.getInstance();
            userdb.collection("USERS")
                    .document(user.getUid()).get()
                    .addOnCompleteListener(task1 -> {

                         name = task1.getResult().get("USERNAME").toString();
                         photo = task1.getResult().get("PHOTO").toString();
                         dob = task1.getResult().get("DOB").toString();
                         dept = task1.getResult().get("DEPARTMENT").toString();
                         course = task1.getResult().get("COURSE").toString();
                         semester= task1.getResult().get("SEMESTER").toString();
                         email = task1.getResult().get("EMAIL").toString();

                        Glide.with(this).load(photo).placeholder(R.drawable.money).into(userprofile);
                        username.setText(name);


            });
        }
    }
}