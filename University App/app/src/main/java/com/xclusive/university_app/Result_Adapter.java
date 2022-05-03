package com.xclusive.university_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class Result_Adapter extends RecyclerView.Adapter<Result_Adapter.ViewHolder> {
    private List<Result_Model> result_modelList;

    public Result_Adapter(List<Result_Model> result_modelList) {
        this.result_modelList = result_modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.resulthead.setText(result_modelList.get(position).getResulthead());
        holder.totalcgpa.setText("Total CGPA:"+result_modelList.get(position).getTotalcgpa());
        holder.papername1.setText(result_modelList.get(position).getPapername1());
        holder.papername2.setText(result_modelList.get(position).getPapername2());
        holder.papername3.setText(result_modelList.get(position).getPapername3());
        holder.papername4.setText(result_modelList.get(position).getPapername4());
        holder.papername5.setText(result_modelList.get(position).getPapername5());
        holder.papername6.setText(result_modelList.get(position).getPapername6());
        holder.papergrade1.setText(result_modelList.get(position).getPapergrade1());
        holder.papergrade2.setText(result_modelList.get(position).getPapergrade2());
        holder.papergrade3.setText(result_modelList.get(position).getPapergrade3());
        holder.papergrade4.setText(result_modelList.get(position).getPapergrade4());
        holder.papergrade5.setText(result_modelList.get(position).getPapergrade5());
        holder.papergrade6.setText(result_modelList.get(position).getPapergrade6());



    }


    @Override
    public int getItemCount() {
        return result_modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView resulthead,totalcgpa, papername1,papername2,papername3,papername4,papername5,papername6,
         papergrade1,papergrade2,papergrade3,papergrade4,papergrade5,papergrade6;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            resulthead = itemView.findViewById(R.id.examhead);
            totalcgpa = itemView.findViewById(R.id.exammode);
            papername1 = itemView.findViewById(R.id.papername1);
            papername2 = itemView.findViewById(R.id.papername2);
            papername3 = itemView.findViewById(R.id.papername3);
            papername4 = itemView.findViewById(R.id.papername4);
            papername5 = itemView.findViewById(R.id.papername5);
            papername6 = itemView.findViewById(R.id.papername6);
            papergrade1 = itemView.findViewById(R.id.papergrade1);
            papergrade2 = itemView.findViewById(R.id.papergrade2);
            papergrade3 = itemView.findViewById(R.id.papergrade3);
            papergrade4 = itemView.findViewById(R.id.papergrade4);
            papergrade5 = itemView.findViewById(R.id.papergrade5);
            papergrade6 = itemView.findViewById(R.id.papergrade6);

        }
    }
}
