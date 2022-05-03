package com.xclusive.university_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Result_activity extends AppCompatActivity {

    private RecyclerView resultrecyclerview;
    private Result_Adapter result_adapter;
    private ArrayList<Result_Model> result_modelArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultrecyclerview = findViewById(R.id.resultrecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        resultrecyclerview.setLayoutManager(layoutManager);

       result_modelArrayList.add(new Result_Model("Semester 1","8.8","toc","cg",
               "ita","algo","ict","network","8.9","9","7.8"
       ,"9","8.2","8"));
        result_modelArrayList.add(new Result_Model("Semester 1","8.8","toc","cg",
                "ita","algo","ict","network","8.9","9","7.8"
                ,"9","8.2","8"));
        result_modelArrayList.add(new Result_Model("Semester 1","8.8","toc","cg",
                "ita","algo","ict","network","8.9","9","7.8"
                ,"9","8.2","8"));
        result_modelArrayList.add(new Result_Model("Semester 1","8.8","toc","cg",
                "ita","algo","ict","network","8.9","9","7.8"
                ,"9","8.2","8"));
        result_modelArrayList.add(new Result_Model("Semester 1","8.8","toc","cg",
                "ita","algo","ict","network","8.9","9","7.8"
                ,"9","8.2","8"));
        result_adapter = new Result_Adapter(result_modelArrayList);
        resultrecyclerview.setAdapter(result_adapter);
        result_adapter.notifyDataSetChanged();
    }
}