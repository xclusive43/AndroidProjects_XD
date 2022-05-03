package com.xclusive.university_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder> {

    public List<LectureModel> lectureModelList;
    public LectureAdapter(List<LectureModel> lectureModelList) {
        this.lectureModelList = lectureModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lectures_items_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.lecturetime.setText("TIME:"+lectureModelList.get(position).getTime());
        holder.lecturefacultyname.setText("FACULTY: "+lectureModelList.get(position).getFacultyname());
        holder.lecturebody.setText(lectureModelList.get(position).getLecturebody());
        holder.lecturedate.setText("DATE:  "+lectureModelList.get(position).getLecturedate());


    }

    @Override
    public int getItemCount() {
        return lectureModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lecturetime,lecturefacultyname, lecturebody,lecturedate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lecturetime = itemView.findViewById(R.id.lecturetime);
            lecturefacultyname = itemView.findViewById(R.id.lecturefacultyname);
            lecturebody = itemView.findViewById(R.id.lecturesbody);
            lecturedate = itemView.findViewById(R.id.lecturedate);
        }
    }
}
