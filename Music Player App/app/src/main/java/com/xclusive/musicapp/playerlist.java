package com.xclusive.musicapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.xclusive.musicapp.dashboard.allmusicmodelArrayList;

public class playerlist extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public playerlist() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static playerlist newInstance(String param1, String param2) {
        playerlist fragment = new playerlist();
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
    //////////////////////////////////////////////////////////
    private RecyclerView playerlist;
    private Playerlistadapter playerlistadapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playerlist, container, false);
        playerlist = view.findViewById(R.id.playerlist1);
        playerlist.setNestedScrollingEnabled(false);
        playerlist.setHasFixedSize(true);

        if (!(allmusicmodelArrayList.size()<1)){
            playerlistadapter = new Playerlistadapter(getContext(),allmusicmodelArrayList);
            playerlist.setAdapter(playerlistadapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            playerlist.setLayoutManager(linearLayoutManager);

        }

        return view;
    }
}