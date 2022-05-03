package com.xclusive.university_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Exam_ativity extends AppCompatActivity {

    private RecyclerView examrecyclerview;
    private ExamAdapter examAdapter;
    private ArrayList<ExamModel> examModellist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        examrecyclerview = findViewById(R.id.examrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        examrecyclerview.setLayoutManager(linearLayoutManager);

        examModellist.add(new ExamModel("1st Test Exam","Online","CSUG101-ITA", "02","12/12/2020","10:00 Am - 11:00 Am","9:45 Am"));
        examModellist.add(new ExamModel("1st Test Exam","Online","CSUG101-ITA", "02","12/12/2020","10:00 Am - 11:00 Am","9:45 Am"));
        examModellist.add(new ExamModel("1st Test Exam","Online","CSUG101-ITA", "02","12/12/2020","10:00 Am - 11:00 Am","9:45 Am"));
        examModellist.add(new ExamModel("1st Test Exam","Online","CSUG101-ITA", "02","12/12/2020","10:00 Am - 11:00 Am","9:45 Am"));
        examModellist.add(new ExamModel("1st Test Exam","Online","CSUG101-ITA", "02","12/12/2020","10:00 Am - 11:00 Am","9:45 Am"));
        examModellist.add(new ExamModel("1st Test Exam","Online","CSUG101-ITA", "02","12/12/2020","10:00 Am - 11:00 Am","9:45 Am"));
        examModellist.add(new ExamModel("1st Test Exam","Online","CSUG101-ITA", "02","12/12/2020","10:00 Am - 11:00 Am","9:45 Am"));
        examModellist.add(new ExamModel("1st Test Exam","Online","CSUG101-ITA", "02","12/12/2020","10:00 Am - 11:00 Am","9:45 Am"));


        examAdapter  = new ExamAdapter(examModellist);
        examrecyclerview.setAdapter(examAdapter);
        examAdapter.notifyDataSetChanged();



    }
}