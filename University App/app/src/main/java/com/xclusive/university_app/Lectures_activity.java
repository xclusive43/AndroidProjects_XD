package com.xclusive.university_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Lectures_activity extends AppCompatActivity {

    private LectureAdapter lectureAdapter;
    private DaysAdapter daysAdapter;
    private RecyclerView lecturerecyclerview;
    private RecyclerView daysrecyclerview;
    private Spinner dayspinner;
    private TextView daystextview;
    private FirebaseFirestore lectureDB;
    private static List<LectureModel> lectureModelList= new ArrayList<>();
    private static List<String> dayslist= new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures);
        dayspinner = findViewById(R.id.daysspinner);
        daystextview = findViewById(R.id.examsort);

        ///spinner code//
        dayslist.add("Choose days");
        dayslist.add("MONDAY");
        dayslist.add("TUESDAY");
        dayslist.add("WEDNESDAY");
        dayslist.add("THURSDAY");
        dayslist.add("FRIDAY");
        ArrayAdapter<String> daysAdapter;
        daysAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,dayslist   );
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayspinner.setAdapter(daysAdapter);

        dayspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose days")){

                }
                else {
                    daystextview.setText(parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lecturerecyclerview = findViewById(R.id.examrecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        lecturerecyclerview.setLayoutManager(layoutManager);
        //lectureModelList.add(new LectureModel("12:20 - 1:20 Am","ajay","hello",""));
        ///firebase///
        lectureDB = FirebaseFirestore.getInstance();
        lectureDB.collection("CATEGORIES").document("LECTURES")
                .collection("lectures").get()
                .addOnCompleteListener((task)->{
                    if (task.isSuccessful()){

                        for (QueryDocumentSnapshot snapshot : task.getResult()){
                            long no_of_lectures = (long) snapshot.get("totallectures");
                            for (long i=1; i<=no_of_lectures;i++){
                                lectureModelList.add(new LectureModel(
                                        snapshot.get("ltime"+i).toString(),
                                        snapshot.get("lfaculty"+i).toString(),
                                        snapshot.get("lbody"+i).toString(),
                                        snapshot.get("ldate"+i).toString()));


                            }
                          lectureAdapter.notifyDataSetChanged();
                        }

                    }
                    else{
                        String error = task.getException().getMessage();
                        Toast.makeText(Lectures_activity.this,error,Toast.LENGTH_LONG).show();

                    }
                });

        //////////////


        lectureAdapter = new LectureAdapter(lectureModelList);
        lecturerecyclerview.setAdapter(lectureAdapter);
        lectureAdapter.notifyDataSetChanged();


    }
}