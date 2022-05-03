package com.xclusive.musicapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class fragmentcontroller extends FragmentPagerAdapter {
int tabcounts;
    public fragmentcontroller(@NonNull FragmentManager fm, int tabcounts) {
        super(fm);
        this.tabcounts = tabcounts;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:  return  new allmusic();
            case 1:  return  new Albums();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return tabcounts;
    }
}
