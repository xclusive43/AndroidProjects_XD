package com.xclusive.covidx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.xclusive.covidx.MainActivity.stateData;


public class States extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private RecyclerView states_view;
    private ArrayList<StateData> stateDataArrayList =new ArrayList<>();
    private States_Adapter states_adapter;
    private SearchView searchView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);

        searchView2 = findViewById(R.id.searchView2);
        states_view = findViewById(R.id.states_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        states_view.setLayoutManager(linearLayoutManager);
        states_adapter = new States_Adapter(this,stateData);
        states_view.setAdapter(states_adapter);
        states_adapter.notifyDataSetChanged();

        searchView2.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        String skey = newText.toLowerCase();
        ArrayList<StateData> searchlist = new ArrayList<>();

        for (StateData s_name: stateData){
            if (s_name.getState().toLowerCase().contains(skey)){
                searchlist.add(s_name);
            }
        }
        states_adapter.updatelist(searchlist);
        states_adapter.notifyDataSetChanged();
        return true;
    }

    public void onback(View view) {
        onBackPressed();
    }
}