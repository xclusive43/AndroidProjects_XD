package com.xclusive.university_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Assignment_activty extends AppCompatActivity {
    private RecyclerView assignmentrecyclerview;
    private AssignmentAdapater assignmentAdapater;
    private ArrayList<AssignmentModel> assignmentModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        assignmentrecyclerview = findViewById(R.id.assignmentsrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        assignmentrecyclerview.setLayoutManager(linearLayoutManager);

        assignmentModelArrayList.add(new AssignmentModel("coa 101","Online","20","19"
        ,"https:com.google.com","12/12/2020","11:59pm","11/12/2020","1","1","1"));

        assignmentModelArrayList.add(new AssignmentModel("coa 101","Online","20","19"
                ,"https:com.google.com","12/12/2020","11:59pm","11/12/2020","1","1","1"));

        assignmentModelArrayList.add(new AssignmentModel("coa 101","Online","20","19"
                ,"https:com.google.com","12/12/2020","11:59pm","11/12/2020","1","1","1"));

        assignmentModelArrayList.add(new AssignmentModel("coa 101","Online","20","19"
                ,"https:com.google.com","12/12/2020","11:59pm","11/12/2020","1","1","1"));

        assignmentModelArrayList.add(new AssignmentModel("coa 101","Online","20","19"
                ,"https:com.google.com","12/12/2020","11:59pm","11/12/2020","1","1","1"));


        assignmentAdapater = new AssignmentAdapater(assignmentModelArrayList);
        assignmentrecyclerview.setAdapter(assignmentAdapater);
        assignmentAdapater.notifyDataSetChanged();
    }
}