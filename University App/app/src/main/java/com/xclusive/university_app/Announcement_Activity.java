package com.xclusive.university_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Announcement_Activity extends AppCompatActivity  {

    private RecyclerView announcementrecycleview;
    private AnnouncementAdapter announcementAdapter;

    private FirebaseFirestore annonceDB;
    private static List<AnnouncementModel> announcementModelList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_);


        progressDialog = new ProgressDialog(Announcement_Activity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progressdialoglayout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        announcementrecycleview = findViewById(R.id.announcementrecycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        announcementrecycleview.setHasFixedSize(true);
        announcementrecycleview.setLayoutManager(linearLayoutManager);
        ///firebase///
        annonceDB = FirebaseFirestore.getInstance();
        annonceDB.collection("CATEGORIES").document("ANNOUNCEMENTS")
                .collection("anounce").get()
                .addOnCompleteListener((task)->{
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot snapshot : task.getResult()){
                            long no_of_announcements = (long) snapshot.get("totalannounce");
                            for (long i=1; i<=no_of_announcements;i++){
                                announcementModelList.add(new AnnouncementModel(
                                        snapshot.get("announcementtitle"+i).toString(),
                                        snapshot.get("announcebody"+i).toString(),
                                        snapshot.get("announcedate"+i).toString(),
                                        snapshot.get("announcefile"+i).toString()));


                            }
                                announcementAdapter.notifyDataSetChanged();
                        }

                        progressDialog.dismiss();
                    }
                    else{
                        String error = task.getException().getMessage();
                        Toast.makeText(Announcement_Activity.this,error,Toast.LENGTH_LONG).show();

                    }
                });

        //////////////
        announcementAdapter = new AnnouncementAdapter(announcementModelList);
        announcementrecycleview.setAdapter(announcementAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        announcementModelList.clear();
    }
}