package com.xclusive.musicapp;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.view.ViewGroup;



import java.io.File;
import java.util.ArrayList;
import java.util.List;


class files_controller {
    private static String songs[];
    public static   ArrayList<File> music;
    public static List<Allmusicmodel> allmusicmodelList = new ArrayList<>();

    public void Loadallmusic(final Allmusicadapter allmusicadapter, final Context context){


    }
    private static ArrayList<File> musicfinder(File file){
        ArrayList<File > Allmusicfiles = new ArrayList<>();
        File [] files = file.listFiles();

        for (File currentfile: files){
            if (currentfile.isDirectory() && !currentfile.isHidden()){
                Allmusicfiles.addAll(musicfinder(currentfile));
            }
            else {
                if (currentfile.getName().endsWith(".wav")|| currentfile.getName().endsWith(".mp3") || currentfile.getName().endsWith(".mp4a")) {
                    Allmusicfiles.add(currentfile);
                }
                //can get total number of files
            }
        }
        return Allmusicfiles;
    }
}
