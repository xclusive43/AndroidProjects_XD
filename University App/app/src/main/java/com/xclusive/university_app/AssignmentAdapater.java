package com.xclusive.university_app;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class AssignmentAdapater extends RecyclerView.Adapter<AssignmentAdapater.ViewHolder> {
    private List<AssignmentModel> assignmentModelList;

    public AssignmentAdapater(List<AssignmentModel> assignmentModelList) {
        this.assignmentModelList = assignmentModelList;
    }

    @NonNull
    @Override
    public AssignmentAdapater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapater.ViewHolder holder, int position)
    {
         holder.papernamecode.setText("Paper: "+assignmentModelList.get(position).getPapernamecode());
         holder.assignmetntype.setText("Type: "+assignmentModelList.get(position).getAssignmenttype());
         holder.assignmenmarks.setText("Marks: "+assignmentModelList.get(position).getObtainmark()+"/"+assignmentModelList.get(position).getMarks());

         holder.assignmentsubmit.setOnClickListener(v->{

             Intent assignmentsubmit= new Intent(holder.itemView.getContext(), Assignment_submit.class);
             holder.itemView.getContext().startActivity( assignmentsubmit);
             //Toast.makeText(holder.itemView.getContext(), "submit clicked", Toast.LENGTH_SHORT).show();
         });
         holder.assignmentdownolad.setOnClickListener(v->{
             Toast.makeText(holder.itemView.getContext(), "download clicked", Toast.LENGTH_SHORT).show();
         });
    }

    @Override
    public int getItemCount() {
        return assignmentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView papernamecode,assignmetntype, assignmenmarks ;
        private Button assignmentsubmit,assignmentdownolad;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            papernamecode = itemView.findViewById(R.id.assignmentpapernamecode);
            assignmetntype = itemView.findViewById(R.id.assignmenttype);
            assignmenmarks = itemView.findViewById(R.id.assignmentmarks);

            assignmentsubmit = itemView.findViewById(R.id.assignmentsubmitbtn);
            assignmentdownolad = itemView.findViewById(R.id.assignmentdownloadbtn);
        }
    }
}
