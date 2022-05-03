package com.xclusive.university_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Attendence_activity extends AppCompatActivity {

    private RecyclerView attendancerecyclerview;
    private AttendanceAdapter attendanceAdapter;
    private ArrayList<AttendanceModel> attendanceModelArrayList = new ArrayList<>();
    private FirebaseFirestore attendancedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        attendancerecyclerview = findViewById(R.id.attendancerecyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        attendancerecyclerview.setLayoutManager(linearLayoutManager);


//        modelArrayList.add(new AttendanceModel("CG105","rakesh","Computer Graphics"
//                ,"12/11/2020","0","A","18MCA02","40%"));
//

        ///firebase///

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        attendancedb = FirebaseFirestore.getInstance();
        attendancedb.collection("CATEGORIES").document("ATTENDENCES")
                .collection("paper2").get().addOnCompleteListener((task)->{
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot snapshot : task.getResult()){
                    //Toast.makeText(Attendence_activity.this,snapshot.get("facultyname").toString() , Toast.LENGTH_SHORT).show();
                    attendanceModelArrayList.add(new AttendanceModel(
                            snapshot.get("papercode").toString(),
                            snapshot.get("facultyname").toString(),
                            snapshot.get("papername").toString(),
                            snapshot.get("dutyleaves").toString(),
                            snapshot.get("section").toString(),
                            snapshot.get("s1day1").toString(),
                            snapshot.get("day1").toString(),
                            snapshot.get("totalabsentday1").toString(),
                            snapshot.get("totalpresentday1").toString(),
                            snapshot.get("totalstudents").toString(),
                            snapshot.get("totaldays").toString()));



                    attendanceAdapter.notifyDataSetChanged();
                }

            }
            else{
                String error = task.getException().getMessage();

                Toast.makeText(Attendence_activity.this,error,Toast.LENGTH_LONG).show();

            }
        });
        attendancedb.collection("CATEGORIES").document("ATTENDENCES")
                .collection("paper1").get().addOnCompleteListener((task)->{
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot snapshot : task.getResult()){
                            //Toast.makeText(Attendence_activity.this,snapshot.get("facultyname").toString() , Toast.LENGTH_SHORT).show();
                            attendanceModelArrayList.add(new AttendanceModel(
                                        snapshot.get("papercode").toString(),
                                        snapshot.get("facultyname").toString(),
                                        snapshot.get("papername").toString(),
                                        snapshot.get("dutyleaves").toString(),
                                        snapshot.get("section").toString(),
                                        snapshot.get("s1day1").toString(),
                                        snapshot.get("day1").toString(),
                                        snapshot.get("totalabsentday1").toString(),
                                        snapshot.get("totalpresentday1").toString(),
                                        snapshot.get("totalstudents").toString(),
                                        snapshot.get("totaldays").toString()));



                            attendanceAdapter.notifyDataSetChanged();
                        }

                    }
                    else{
                        String error = task.getException().getMessage();

                        Toast.makeText(Attendence_activity.this,error,Toast.LENGTH_LONG).show();

                    }
                });







        //////////////

        attendanceAdapter = new AttendanceAdapter(attendanceModelArrayList );
        attendancerecyclerview.setAdapter(attendanceAdapter);
        attendanceAdapter.notifyDataSetChanged();




    }
}