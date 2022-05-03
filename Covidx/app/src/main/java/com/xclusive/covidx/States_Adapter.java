package com.xclusive.covidx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;

import kotlin.Unit;

public class States_Adapter extends RecyclerView.Adapter<States_Adapter.ViewHolder> {
    Context context;
    ArrayList<StateData> stateDataArrayList = new ArrayList<>();
    private static int confirm, active, recovered, death, todaycases, trecovered, tdeaths, population;

    public States_Adapter(Context context, ArrayList<StateData> stateDataArrayList) {
        this.context = context;
        this.stateDataArrayList = stateDataArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public States_Adapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.states_layout, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull States_Adapter.ViewHolder holder, int position) {
        holder.state_name.setText(stateDataArrayList.get(position).getState());
        holder.confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(stateDataArrayList.get(position).getConfirmed())));
        holder.active.setText(NumberFormat.getInstance().format(Integer.parseInt(stateDataArrayList.get(position).getActive())));
        holder.recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(stateDataArrayList.get(position).getRecovered())));
        holder.deaths.setText(NumberFormat.getInstance().format(Integer.parseInt(stateDataArrayList.get(position).getDeaths())));

        holder.d_c.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(stateDataArrayList.get(position).getDeltaconfirmed())));
        holder.d_a.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(stateDataArrayList.get(position).getDeltaconfirmed())));
        holder.d_r.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(stateDataArrayList.get(position).getDeltarecovered())));
        holder.d_d.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(stateDataArrayList.get(position).getDeltadeaths())));
        confirm = Integer.parseInt(stateDataArrayList.get(position).getConfirmed());
        active = Integer.parseInt(stateDataArrayList.get(position).getActive());
        recovered = Integer.parseInt(stateDataArrayList.get(position).getRecovered());
        death = Integer.parseInt(stateDataArrayList.get(position).getDeaths());
        ////for progress bar////
        ///////////confirmed//////////////
        double a = (double) confirm / confirm;
        double per1 = a * 100;
        //,activebar,recoveredbar,deathsbar;
        cirprogress(holder.confirmbar, (float) per1);
        ////active//////
        double a1 = (double) active / confirm;
        double per2 = a1 * 100;
        cirprogress(holder.activebar, (float) per2);
        ////recovered////
        double a2 = (double) recovered / confirm;
        double per3 = a2 * 100;
        cirprogress(holder.recoveredbar, (float) per3);
        ////deceased////
        double a3 = (double) death / confirm;
        double per4 = a2 * 100;
        cirprogress(holder.deathsbar, (float) per4);
        ///////////////////////////////////

    }

    @Override
    public int getItemCount() {
        return stateDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView state_name, confirmed, active, recovered, deaths, d_c, d_a, d_r, d_d;
        CircularProgressBar confirmbar, activebar, recoveredbar, deathsbar;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            state_name = itemView.findViewById(R.id.states_name);
            confirmed = itemView.findViewById(R.id.textView4);
            active = itemView.findViewById(R.id.s_active);
            recovered = itemView.findViewById(R.id.s_recovered);
            deaths = itemView.findViewById(R.id.s_deceased);
            d_c = itemView.findViewById(R.id.textView5);
            d_a = itemView.findViewById(R.id.s_active_delta);
            d_r = itemView.findViewById(R.id.s_recovered_delta);
            d_d = itemView.findViewById(R.id.s_deceased_delta);

            confirmbar = itemView.findViewById(R.id.circularProgressBar);
            activebar = itemView.findViewById(R.id.circularProgressBaractive);
            recoveredbar = itemView.findViewById(R.id.circularProgressBarrecovered);
            deathsbar = itemView.findViewById(R.id.circularProgressBardeceased);



        }
    }

    private void cirprogress(CircularProgressBar circularProgressBar, float progress1) {
        circularProgressBar.setProgressMax(100);
        circularProgressBar.setProgressWithAnimation(progress1, (long) 2000); // =1s
        circularProgressBar.setRoundBorder(true);
        circularProgressBar.setStartAngle(0f);
        circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);

        ////


        circularProgressBar.setOnIndeterminateModeChangeListener(isEnable -> {
            // Do something
            return Unit.INSTANCE;
        });

        circularProgressBar.setOnProgressChangeListener(progress -> {
            // Do something
            return Unit.INSTANCE;
        });
    }
    public  void updatelist(ArrayList<StateData> newlist){
        if (newlist.isEmpty()){
            return;
        }

        stateDataArrayList = new ArrayList<>();
        stateDataArrayList.addAll(newlist);
        notifyDataSetChanged();


    }
}
