package com.xclusive.musicapp;


import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import static com.xclusive.musicapp.dashboard.allmusicmodelArrayList;

public class allmusic extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public allmusic() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static allmusic newInstance(String param1, String param2) {
        allmusic fragment = new allmusic();
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
    public RecyclerView allmusicrecyclerview;
    public static Allmusicadapter allmusicadapter;
    private FastScrollRecyclerView allsongsfast;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_allmusic, container, false);
        allsongsfast = view.findViewById(R.id.allsongsfast);
        //allsongsfast.setTrackColor(ColorUtils.setAlphaComponent(ContextCompat.getColor(getContext(), R.color.black), sThemeInverted ? 15 : 30));

        LinearLayoutManager   mArtistsLayoutManager = new LinearLayoutManager(getContext());
        mArtistsLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allsongsfast.setLayoutManager(mArtistsLayoutManager);
        allsongsfast.setNestedScrollingEnabled(false);
        allsongsfast.setHasFixedSize(true);


        if (!(allmusicmodelArrayList.size()<1)){
            allmusicadapter = new Allmusicadapter(allmusicmodelArrayList,getContext());

            allsongsfast.setAdapter(allmusicadapter);



        }





     return view;
    }

}