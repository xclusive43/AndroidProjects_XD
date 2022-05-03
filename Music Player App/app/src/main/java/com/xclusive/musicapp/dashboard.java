 package com.xclusive.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.viewpager.widget.ViewPager;

import android.Manifest;

import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;


import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;




import java.util.ArrayList;
public class dashboard extends AppCompatActivity implements SearchView.OnQueryTextListener {

  private TabLayout tabLayout;
  private TabItem currentmusic,playerlist,allmusic;
  private ViewPager viewPager;
  private TextView tootbartitle1;
  private ProgressBar loading;
  public static final int Request = 1;
  public MenuItem playingnowbtn;
  public  static boolean shuffle = false, repeat =false;
  MenuItem menuid;
    public static boolean isplaying = false;
  Toolbar toolbar;
   /////////dialog////////////
  public static ArrayList<Allmusicmodel> allmusicmodelArrayList;
  public static ArrayList<Allmusicmodel>  albumslist = new ArrayList<>();
    ///////////////////////////
  fragmentcontroller fragmentcontroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        permission();
        layoutfun();



    }

      private void layoutfun() {

        tabLayout = findViewById(R.id.tablayout);
        currentmusic = findViewById(R.id.currentmusic);
        playerlist = findViewById(R.id.playlist);


        viewPager = findViewById(R.id.viewpager);
        loading = findViewById(R.id.loading);

        /////////////fragment code////////////////
        loading.setVisibility(View.VISIBLE);

        fragmentcontroller = new fragmentcontroller(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(fragmentcontroller);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        loading.setVisibility(View.GONE);

        //////////////////////////////////////////

    }
      private void permission() {

            if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(dashboard.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    ,Request);

                }
            else{
                    allmusicmodelArrayList = getAllmusic( this);
                    layoutfun();

                }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==Request){
            if (grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                allmusicmodelArrayList = getAllmusic( this);
                layoutfun();


            }
            else {
                ActivityCompat.requestPermissions(dashboard.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        ,Request);
            }
        }

    }
    public static ArrayList<Allmusicmodel> getAllmusic(Context context){
        ArrayList<String> removeduplicatealbums = new ArrayList<>();
        ArrayList<Allmusicmodel> rawmusiclist = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] list = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media._ID





        };
        Cursor cursor = context.getContentResolver().query(uri, list,null , null, MediaStore.Audio.Media.DISPLAY_NAME);
        {//sort ny name: MediaStore.Audio.Media.DISPLAY_NAME;
            if (cursor!=null){
                while (cursor.moveToNext()){
                    String album = cursor.getString(0);
                    String title = cursor.getString(1);
                    String duration= cursor.getString(2);
                    String data = cursor.getString(3);
                    String artist = cursor.getString(4);
                    String id= cursor.getString(5);

                    Allmusicmodel allmusicmodel = new Allmusicmodel(title,data,artist,album,duration,id);
                    rawmusiclist.add(allmusicmodel);
                    if(!removeduplicatealbums.contains(album)){//to remove duplicate albums from list
                        albumslist.add(allmusicmodel);
                        removeduplicatealbums.add(album);
                    }
                }
                cursor.close();
            }

            return rawmusiclist;
        }




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        menuid =  menu.findItem(R.id.searchbtn);
        SearchView searchView = (SearchView) menuid.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        String searchinput = s.toLowerCase();
        ArrayList<Allmusicmodel> searchlist = new ArrayList<>();

        for (Allmusicmodel song: allmusicmodelArrayList){

            if (song.getMusictitle().toLowerCase().contains(searchinput)){
                searchlist.add(song);

            }

        }
        com.xclusive.musicapp.allmusic.allmusicadapter.updatelist(searchlist);
        return true;
    }


}