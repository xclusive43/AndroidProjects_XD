package com.xclusive.university_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private List<AttendanceModel> attendanceModelList;

    public AttendanceAdapter(List<AttendanceModel> attendanceModelList) {
        this.attendanceModelList = attendanceModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendence_item_layout,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.attendancepaper.setText("paper code : "+attendanceModelList.get(position).getAttendancepapercode());
        holder.facultyname.setText(attendanceModelList.get(position).getFacultyname());
        holder.papername.setText(attendanceModelList.get(position).getPapername());
        holder.facultydutyleaves.setText(attendanceModelList.get(position).getFacultydutyleaver());
        holder.sections.setText(attendanceModelList.get(position).getSection());
        holder.rollno.setText(attendanceModelList.get(position).getRollno());


        holder.itemView.setOnClickListener(v->{
            Intent attendaanceactivity = new Intent(holder.itemView.getContext(), Attendance_Activity2.class);
            attendaanceactivity.putExtra("CODE",attendanceModelList.get(position).getAttendancepapercode());
            attendaanceactivity.putExtra("PAPERNAME",attendanceModelList.get(position).getPapername());
            holder.itemView.getContext().startActivity(attendaanceactivity);
            //Toast.makeText(holder.itemView.getContext(), "clicked", Toast.LENGTH_SHORT).show();

        });

    }

    @Override
    public int getItemCount() {
        return attendanceModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView attendancepaper,facultyname,papername,facultydutyleaves,sections,rollno;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            attendancepaper = itemView.findViewById(R.id.attendancepapercode);
            facultyname = itemView.findViewById(R.id.facutlyname);
            papername =  itemView.findViewById(R.id.subjectname);
            facultydutyleaves = itemView.findViewById(R.id.dutyleaves);
            sections = itemView.findViewById(R.id.sectionname);
            rollno =  itemView.findViewById(R.id.rollno);

        }
    }
}
