package com.xclusive.abcmart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.xclusive.abcmart.DBQueries.category_modelList;

import static com.xclusive.abcmart.DBQueries.lists;
import static com.xclusive.abcmart.DBQueries.loadedcategoriesNames;
import static com.xclusive.abcmart.DBQueries.loadfragmentdata;
import static com.xclusive.abcmart.DBQueries.loadgetcategories;


public class homeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public homeFragment() {
        // Required empty public constructor
    }
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
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
    //////////////////////////////////////
    private RecyclerView categoryRecyclerView;
    private RecyclerView homepagerecyclerview;
    private  category_adapter category_adapter1;
    private homePageAdapter adapter;
    private ImageView no_network;
    public static SwipeRefreshLayout swiperefresh_layout;
    private Button retrybtn;
    //private List<banner_model> banner_modelList;

    //connectivity manager
    private   ConnectivityManager connectivityManager;
    private  NetworkInfo networkInfo;
    //

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_home, container, false);
        swiperefresh_layout = view.findViewById(R.id.swiperefresh_layout);
        retrybtn = view.findViewById(R.id.retrybtn);
        no_network = view.findViewById(R.id.no_network);

 //Todo no network check//
        connectivityManager =(ConnectivityManager) getActivity().getSystemService((Context.CONNECTIVITY_SERVICE));
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!= null && networkInfo.isConnected()==true){

            MainActivity2.drawer.setDrawerLockMode(0);//unlocking the drawer
            no_network.setVisibility(View.GONE);
            retrybtn.setVisibility(View.GONE);

 //todo category page layout with firebase using dgquerry class (category recycler view)
            categoryRecyclerView = view.findViewById(R.id.category_recycleview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecyclerView.setLayoutManager(layoutManager);
            category_adapter1 = new category_adapter((category_modelList));//import from dbquerry class
            categoryRecyclerView.setAdapter(category_adapter1);

            if(category_modelList.size()==0){// todo if the list is empty then call the method for data else just notify data set changed
                loadgetcategories(category_adapter1,getContext());////import from dbquerry class
            }
            else
            {
                category_adapter1.notifyDataSetChanged();//refresh th adapter
            }

  //todo fragment home page layout with firebase using dgquerry class (home page recyclerview)
            homepagerecyclerview = view.findViewById(R.id.home_page_recyclerview);
            LinearLayoutManager testinglayoutmanager  = new LinearLayoutManager(getContext());
            testinglayoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
            homepagerecyclerview.setLayoutManager(testinglayoutmanager);


            if(lists.size()==0){ // todo if the list is empty then call the method for data else just notify data set changed
                loadedcategoriesNames.add("HOME");
                lists.add(new ArrayList<homePageModel>());
                adapter = new homePageAdapter(lists.get(0));//todo import from public static void setfragmentdata(final homePageAdapter adapter, final Context context){
                loadfragmentdata(swiperefresh_layout,adapter,getContext(),0,"HOME");
            }else {
                adapter = new homePageAdapter(lists.get(0));//todo import from public static void setfragmentdata(final homePageAdapter adapter, final Context context){
                adapter.notifyDataSetChanged();
            }
             homepagerecyclerview.setAdapter(adapter);
        }
        else{

            MainActivity2.drawer.setDrawerLockMode(1);////locking the drawer
            categoryRecyclerView.setVisibility(View.GONE);
            homepagerecyclerview.setVisibility(View.GONE);

            Glide.with(this).load(R.drawable.no_network).into(no_network);
            no_network.setVisibility(View.VISIBLE);
            retrybtn.setVisibility(View.VISIBLE);
        }

        //-------//
 //todo swiperefresh_layout code
        swiperefresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swiperefresh_layout.setRefreshing(true);
                reload();

            }
        });
        //--


        retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });

        return view;
    }

    @SuppressLint("WrongConstant")
    private void reload(){

        networkInfo = connectivityManager.getActiveNetworkInfo();
        category_modelList.clear();
        lists.clear();
        loadedcategoriesNames.clear();

        if(networkInfo!= null && networkInfo.isConnected()==true) {
            MainActivity2.drawer.setDrawerLockMode(0);//unlocking the drawer
            no_network.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homepagerecyclerview.setVisibility(View.VISIBLE);
            retrybtn.setVisibility(View.GONE);

            loadgetcategories(category_adapter1,getContext());////import from dbquerry class

            loadedcategoriesNames.add("HOME");
            lists.add(new ArrayList<homePageModel>());
            loadfragmentdata(swiperefresh_layout,adapter,getContext(),0,"HOME");

        }
        else{
            MainActivity2.drawer.setDrawerLockMode(1);//locking the drawer
            Glide.with(getContext()).load(R.drawable.no_network).into(no_network);
            no_network.setVisibility(View.VISIBLE);
            categoryRecyclerView.setVisibility(View.GONE);
            homepagerecyclerview.setVisibility(View.GONE);
            retrybtn.setVisibility(View.VISIBLE);
            swiperefresh_layout.setRefreshing(false);
        }

    }
}