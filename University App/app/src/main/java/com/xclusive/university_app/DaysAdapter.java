package com.xclusive.university_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {

    private  List<DaysModel> daysModelList;

    public DaysAdapter(List<DaysModel> daysModelList) {
        this.daysModelList = daysModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dayslayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           holder.days.setText(daysModelList.get(position).getDays());
    }

    @Override
    public int getItemCount() {
        return daysModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView days;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            days = itemView.findViewById(R.id.days);
        }
    }
}
