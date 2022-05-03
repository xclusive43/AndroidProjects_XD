package com.xclusive.musicapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import static com.xclusive.musicapp.dashboard.albumslist;
import static com.xclusive.musicapp.dashboard.allmusicmodelArrayList;

public class Albums extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Albums() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Albums newInstance(String param1, String param2) {
        Albums fragment = new Albums();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public albumAdapter albumadapter1;
    private FastScrollRecyclerView albumfastrecyclerview1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_albums, container, false);

        albumfastrecyclerview1 = view.findViewById(R.id.albumfastrecyclerview);
        albumfastrecyclerview1.setNestedScrollingEnabled(false);
        albumfastrecyclerview1.setHasFixedSize(true);


        if (!(albumslist.size()<0)){
            albumadapter1 = new albumAdapter(getContext(),albumslist);
            albumfastrecyclerview1.setAdapter(albumadapter1);
            albumfastrecyclerview1.setLayoutManager(new GridLayoutManager(getContext(),2));

        }



        return view;
    }
}