package com.xclusive.university_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class AttendanceAdapter2 extends RecyclerView.Adapter<AttendanceAdapter2.ViewHolder> {
   private List<AttendanceModel2> attendanceModel2List;

    public AttendanceAdapter2(List<AttendanceModel2> attendanceModel2List) {
        this.attendanceModel2List = attendanceModel2List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_item_layout2,parent,false);
        return new ViewHolder(view);
    }

     @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          holder.present.setText(attendanceModel2List.get(position).getPresenttext());
          holder.facultyname.setText("Faculty : "+attendanceModel2List.get(position).getFacultynametext());
          holder.date.setText("Date : "+attendanceModel2List.get(position).getDatetext());
          holder.time.setText("Time : "+attendanceModel2List.get(position).getTimetext());


    }

     @Override
    public int getItemCount() {
        return attendanceModel2List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView present,facultyname,date,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            present = itemView.findViewById(R.id.assignmentpercentage);
            facultyname = itemView.findViewById(R.id.assignmentmarks);
            date= itemView.findViewById(R.id.assignmentpapernamecode);
            time = itemView.findViewById(R.id.assignmenttype);
        }
    }
}
