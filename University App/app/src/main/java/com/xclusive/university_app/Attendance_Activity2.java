package com.xclusive.university_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Attendance_Activity2 extends AppCompatActivity {

    private RecyclerView attendance2recyclerview;
    private TextView code,paprename;
    private AttendanceAdapter2 attendanceAdapter2;
    private ArrayList<AttendanceModel2> attendanceModel2ArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance2);

        code = findViewById(R.id.subjectrcode1);
        paprename = findViewById(R.id.subjectname1);
        attendance2recyclerview = findViewById(R.id.attendancerecyclerview2);

        code.setText("PAPER CODE : "+getIntent().getStringExtra("CODE"));
        paprename.setText(getIntent().getStringExtra("PAPERNAME"));

        LinearLayoutManager layoutManager = new GridLayoutManager(this,2);//LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        attendance2recyclerview.setLayoutManager(layoutManager);

        attendanceModel2ArrayList.add(new AttendanceModel2("A","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("A","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("p","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("A","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("p","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("A","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("p","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("A","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("A","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("p","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("A","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("p","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("A","jai bhanushali","12/11/2020","10:00-11:30 AM"));
        attendanceModel2ArrayList.add(new AttendanceModel2("p","jai bhanushali","12/11/2020","10:00-11:30 AM"));

        attendanceAdapter2 = new AttendanceAdapter2(attendanceModel2ArrayList);
        attendance2recyclerview.setAdapter(attendanceAdapter2);
        attendanceAdapter2.notifyDataSetChanged();


    }
}