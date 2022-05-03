package com.xclusive.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;


import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import static com.xclusive.musicapp.Allmusicadapter.musiclist;
import static com.xclusive.musicapp.album_activity_adapter.albumactivityArrayList;
import static com.xclusive.musicapp.dashboard.repeat;
import static com.xclusive.musicapp.dashboard.shuffle;

public class playingnow extends AppCompatActivity implements MediaPlayer.OnCompletionListener {


    private TextView songtitle,starttime,totaltime,artist,songpos;
    private ImageView songimage;
    private ImageView play,pre,next,back,settingbtn,shufflebtn,repeatbtn,likedbtn,deletebtn;
    private SeekBar progressbar,volumecontrol;
    private AudioManager audioManager;
    public static ArrayList<Allmusicmodel> arrayList = new ArrayList<>();
    int position =-1;
    public static  MediaPlayer  mediaPlayer;
    private Handler handler = new Handler();

    private Thread playpausethread, previousthread, nextthred;

  private static Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playingnow);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

        init();
        getdata();
        mediaPlayer.setOnCompletionListener(playingnow.this);

        progressbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer!=null && b){
                    mediaPlayer.seekTo(i*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        playingnow.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer!=null){
                    int currentpot = mediaPlayer.getCurrentPosition()/1000;
                    progressbar.setProgress(currentpot);
                    starttime.setText(seekbartimne(currentpot));
                }
                handler.postDelayed(this,  0);
            }
        });

    }

    public void init(){
        songtitle = findViewById(R.id.songtitle);
        artist = findViewById(R.id.artist);
        starttime = findViewById(R.id.starttime);
        totaltime = findViewById(R.id.totaltime);
        songimage = findViewById(R.id.songimage);
        songpos = findViewById(R.id.songpos);
        play = findViewById(R.id.play);
        pre = findViewById(R.id.pre);
        next = findViewById(R.id.next);
        back = findViewById(R.id.backbtn1);
        shufflebtn = findViewById(R.id.shufflebtn);
        repeatbtn = findViewById(R.id.repeatbtn);
        progressbar = findViewById(R.id.progressbar);
        volumecontrol = findViewById(R.id.volumecontrol);
        likedbtn = findViewById(R.id.likedbtn);
        deletebtn = findViewById(R.id.deletebtn);

         back.setOnClickListener(v-> onBackPressed());
        ////////////volume control////////////////////
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        volumecontrol.setMax(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        int macvol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volumecontrol.setProgress(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumecontrol.setMax(macvol);

        volumecontrol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                     audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
                     volumecontrol.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ///////////////////////////////////////////////

        //if media player is null then
        if (mediaPlayer!= null){
            mediaPlayer.stop();
        }



    }
    public void getdata(){
        position = getIntent().getIntExtra("POSITION",-1);
        String album = getIntent().getStringExtra("SENDER");

        if ( album != null && album.equals("albumDetails")){
            arrayList = albumactivityArrayList;
        }
        else {
            arrayList = musiclist;
        }

        if (arrayList !=null){
            play.setImageResource(R.drawable.pause);
            uri = Uri.parse(arrayList.get(position).getMusicimage());
        }

        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        }


        progressbar.setMax(mediaPlayer.getDuration() /1000);
        startmusicplayer(uri);

        songtitle_pos_artist();


    }
    private void songtitle_pos_artist()
    {
        String artist1 =  arrayList .get(position).getArtist();
        songtitle.setText( arrayList .get(position).getMusictitle());
        songtitle.setSelected(true);
        if(artist1.contains("<unknown>")){
            artist.setText("");
        }
        else {
            artist.setText(artist1);
        }
        songpos.setText(position+1+"/"+arrayList.size());

    }
    private void startmusicplayer(Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());

        int totalduration = Integer.parseInt(arrayList.get(position).getDuration())/1000;
        totaltime.setText(seekbartimne(totalduration));
        byte[] art = retriever.getEmbeddedPicture();
        Bitmap bitmap;
        if (art != null){
            bitmap = BitmapFactory.decodeByteArray(art,0, art.length);
            anim(this, songimage, bitmap);

        }
        else {

            songimage.setImageResource(R.drawable.currentmusic);

        }
        retriever.release();




    }
    public String seekbartimne(int dur){
        String time = "";
        String timenew = "";
        String sec = String.valueOf(dur%60);
        String min = String.valueOf(dur/60);
        time = min +":" +sec;
        timenew = min +":"+ "0" + sec;
        if (sec.length()==1){
            return timenew;
        }
        else {
            return time;
        }


    }

    @Override
    protected void onResume() {
        if (arrayList.size()<=1){
            shufflebtn.setEnabled(false);
        }
        else {
            shufflebtn.setEnabled(true);
        }
        playthreadbtn();
        previousthreadbtn();
        nextthreadbtn();
        shufflebtn();
        repeatbtn();
        deletefile();
        super.onResume();

    }
////////delete file function//////
    private void deletefile() {
        deletebtn.setOnClickListener(v->{
            PopupMenu popupMenu = new PopupMenu(this,v);
            popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener((item)->{
                switch (item.getItemId()){
                    case R.id.delete:
                        delete(position, v);
                        break;
                }

                return true;
            });

        });
    }

    private void delete(int position1, View v) {
        Uri deletefileuri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Long.parseLong(arrayList.get(position1).getId()));

        File file = new File(arrayList.get(position1).getId());
        boolean  deleted = file.delete();
        if (deleted){
            this.getContentResolver().delete(deletefileuri, null,null);

            arrayList.remove(position);
             notify();
             Snackbar.make(v,"Song Deleted",Snackbar.LENGTH_LONG)
                    //.setActionTextColor(this.getResources().getColor(android.R.color.black))
                    .show();
        }
        else {
            Snackbar.make(v,"can't Delete the Song",Snackbar.LENGTH_LONG)
                    //.setActionTextColor(this.getResources().getColor(android.R.color.black))
                    .show();
        }


    }
///////////////////////////////////////////
    ////play thread with function///////
    private void playthreadbtn() {
       playpausethread = new Thread()
       {
           @Override
           public void run() {
               super.run();
               play.setOnClickListener(v->{
                   playfun();
                });
           }
       };
       playpausethread.start();
    }
    private void playfun() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            play.setImageResource(R.drawable.play);
            progressbar.setMax(mediaPlayer.getDuration()/1000);
            playingnow.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int currentpot = mediaPlayer.getCurrentPosition()/1000;
                        progressbar.setProgress(currentpot);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
        else {
            mediaPlayer.start();
            play.setImageResource(R.drawable.pause);
            progressbar.setMax(mediaPlayer.getDuration()/1000);
            playingnow.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int currentpot = mediaPlayer.getCurrentPosition()/1000;
                        progressbar.setProgress(currentpot);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }
    ////////////////////////////////////
    ////next btn thread and function////
    private void nextthreadbtn() {
        nextthred = new Thread()
        {
            @Override
            public void run() {
                super.run();
                next.setOnClickListener(v->{
                    nextfun();
                });
            }


        };
        nextthred.start();
    }
    private void nextfun() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();

            if (shuffle&&!repeat){

                position = random(arrayList.size()-1);
            }
            else if(!shuffle && !repeat){
                position = ((position+1)%arrayList.size());
            }

            uri = Uri.parse(arrayList.get(position).getMusicimage());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            startmusicplayer(uri);
            songtitle_pos_artist();

            progressbar.setMax(mediaPlayer.getDuration()/1000);
            playingnow.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int currentpot = mediaPlayer.getCurrentPosition()/1000;
                        progressbar.setProgress(currentpot);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaPlayer.setOnCompletionListener(playingnow.this);
            play.setImageResource(R.drawable.pause);
            mediaPlayer.start();
        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();

            if (shuffle&&!repeat){

                position = random(arrayList.size()-1);
            }
            else if(!shuffle && !repeat){
                position = ((position+1)%arrayList.size());
            }



    uri = Uri.parse(arrayList.get(position).getMusicimage());

    mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
    startmusicplayer(uri);
    songtitle_pos_artist();

    progressbar.setMax(mediaPlayer.getDuration()/1000);
    playingnow.this.runOnUiThread(new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer!=null){
                int currentpot = mediaPlayer.getCurrentPosition()/1000;
                progressbar.setProgress(currentpot);

            }
            handler.postDelayed(this, 1000);
        }
    });
    mediaPlayer.setOnCompletionListener(playingnow.this);
    play.setImageResource(R.drawable.play);
    //mediaPlayer.start();
    }


    }
    ////////////////////////////////////
    ////previous btn thread and function
    private void previousthreadbtn() {
        previousthread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                pre.setOnClickListener(v->{
                    prefun();
                });
            }


        };
        previousthread.start();
    }
    private void prefun() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();

            if (shuffle&&!repeat){

                position =random(arrayList.size()-1);
            }
            else if(!shuffle && !repeat){
                position = ((position-1) <0 ? (arrayList.size()-1) : (position-1));
            }

            uri = Uri.parse(arrayList.get(position).getMusicimage());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            startmusicplayer(uri);
            songtitle_pos_artist();



            progressbar.setMax(mediaPlayer.getDuration()/1000);
            playingnow.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int currentpot = mediaPlayer.getCurrentPosition()/1000;
                        progressbar.setProgress(currentpot);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaPlayer.setOnCompletionListener(playingnow.this);
            play.setImageResource(R.drawable.pause);
            mediaPlayer.start();
        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (shuffle&&!repeat){

                position = random(arrayList.size()-1);
            }
            else if(!shuffle && !repeat){
                position = ((position-1) <0 ? (arrayList.size()-1) : (position-1));
            }

            uri = Uri.parse(arrayList.get(position).getMusicimage());

            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            startmusicplayer(uri);
            songtitle_pos_artist();



            progressbar.setMax(mediaPlayer.getDuration()/1000);
            playingnow.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int currentpot = mediaPlayer.getCurrentPosition()/1000;
                        progressbar.setProgress(currentpot);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaPlayer.setOnCompletionListener(playingnow.this);
            play.setImageResource(R.drawable.play);
           // mediaPlayer.start();
        }
    }

    ////////////////////////////////////
    public void anim(Context context, ImageView imageView , Bitmap bitmap){

        Animation animation_in = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        Animation animation_out = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);

        animation_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                   Glide.with(context).load(bitmap).into(imageView);
                   animation_in.setAnimationListener(new Animation.AnimationListener() {
                       @Override
                       public void onAnimationStart(Animation animation) {

                       }

                       @Override
                       public void onAnimationEnd(Animation animation) {

                       }

                       @Override
                       public void onAnimationRepeat(Animation animation) {

                       }
                   });
                   imageView.startAnimation(animation_in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animation_out);

    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer1) {

        nextfun();
        if (mediaPlayer !=null){

            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            play.setImageResource(R.drawable.pause);
            mediaPlayer.setOnCompletionListener(playingnow.this);

        }
    }

    private void shufflebtn(){
        shufflebtn.setOnClickListener(v->{
            mediaPlayer.stop();



            if (shuffle && !repeat){
                shuffle = false;
                shufflebtn.setImageResource(R.drawable.shuffle_off);

            }

            else {
                shuffle = true;
                shufflebtn.setImageResource(R.drawable.shuffle);
                repeat = false;
                repeatbtn.setImageResource(R.drawable.repeat_off);

                if (arrayList.size() <= 1) {
                    shufflebtn.setEnabled(false);
                    repeatbtn();
                    return;
                } else {
                    shufflebtn.setEnabled(true);
                    position = random(arrayList.size() - 1);
                    mediaPlayer.start();

                }
            }





        });
    }
    private void repeatbtn(){
        repeatbtn.setOnClickListener(v->{
            mediaPlayer.stop();
            if (repeat){
                repeat = false;
                repeatbtn.setImageResource(R.drawable.repeat_off);
            }
            else {
                repeat = true;
                repeatbtn.setImageResource(R.drawable.repeat);
                shuffle = false;
                shufflebtn.setImageResource(R.drawable.shuffle_off);
                mediaPlayer.start();

            }


        });
    }
    private int random(int size) {
        if (size<1){
            return 1;
        }
        else {
            Random random = new Random();
            return random.nextInt(size + 1);
        }
    }



}