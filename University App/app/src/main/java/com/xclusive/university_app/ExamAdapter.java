package com.xclusive.university_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {

    private List<ExamModel> examModelList;

    public ExamAdapter(List<ExamModel> examModelList) {
        this.examModelList = examModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_item_layout,parent,false);
        return new ViewHolder(view);
    }

     @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.examhead.setText(examModelList.get(position).getExamhead());
        holder.exammode.setText("mode: "+examModelList.get(position).getExammode());
        holder.exampapername.setText("papername: "+examModelList.get(position).getExampapername());
        holder.examroom.setText("Room No.:"+examModelList.get(position).getExamroom());
        holder.examdate.setText("Date: "+examModelList.get(position).getExamdate());
        holder.examtime.setText("Time: "+examModelList.get(position).getExamtime());
        holder.examreporttime.setText("Reporting Time: "+examModelList.get(position).getExamreporttime());

    }

     @Override
    public int getItemCount() {
        return examModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView examhead,exammode,exampapername,
                examroom,examdate,examtime,examreporttime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            examhead = itemView.findViewById(R.id.examhead);
            exammode = itemView.findViewById(R.id.exammode);
            exampapername = itemView.findViewById(R.id.exampapername);
            examroom= itemView.findViewById(R.id.examroomnumber);
            examdate = itemView.findViewById(R.id.examdate);
            examtime = itemView.findViewById(R.id.examtime);
            examreporttime= itemView.findViewById(R.id.examreportingtime);

        }
    }
}
