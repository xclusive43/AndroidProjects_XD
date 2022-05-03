package com.xclusive.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.xclusive.musicapp.dashboard.allmusicmodelArrayList;

public class Album_activity extends AppCompatActivity {
    private RecyclerView albumrecyclerview1;
    private ImageView albumimage1,backbtn;
    private TextView albumtitle1;
    private String albumname;
    private ArrayList<Allmusicmodel> albumlist = new ArrayList<>();
    private int index =0;
    private album_activity_adapter album_activity_adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        backbtn = findViewById(R.id.backbtn1);
        backbtn.setOnClickListener(v-> onBackPressed());

        albumrecyclerview1 = findViewById(R.id.albumrecyclerview1);
        albumimage1 = findViewById(R.id.albumimage1);
        albumtitle1 = findViewById(R.id.albumtitle1);
        albumname =getIntent().getStringExtra("albumname");

        addsonginlist();


    }

    private void addsonginlist() {

        for (int i=0;i<allmusicmodelArrayList.size();i++){
            if (albumname.equals(allmusicmodelArrayList.get(i).getAlbum())){

                albumlist.add(index,allmusicmodelArrayList.get(i));
                index++;
            }
        }
        albumtitle1.setText( albumlist.get(0).getAlbum()
        );
        byte[] image = albumimage1(albumlist.get(0).getMusicimage());

        if(image !=null){
            Glide.with(this).asBitmap().load(image).into(albumimage1);
        }
        else {
            albumimage1.setImageResource(R.drawable.currentmusic);
        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        album_activity_adapter1 = new album_activity_adapter(Album_activity.this,albumlist);
        albumrecyclerview1.setAdapter(album_activity_adapter1);

        albumrecyclerview1.setNestedScrollingEnabled(false);
        albumrecyclerview1.setHasFixedSize(true);

        if (!(albumlist.size()<0)){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            albumrecyclerview1.setLayoutManager(linearLayoutManager);

        }

    }
    private byte[] albumimage1(String songuri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(songuri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();

        return art;

    }
}