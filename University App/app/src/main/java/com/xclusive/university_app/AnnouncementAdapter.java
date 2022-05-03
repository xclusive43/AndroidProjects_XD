package com.xclusive.university_app;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

   private List<AnnouncementModel> announcementModelList;

    public AnnouncementAdapter(List<AnnouncementModel> announcementModelList) {
        this.announcementModelList = announcementModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_item_layout,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
         holder.title.setText(announcementModelList.get(position).getAnnouncetitle());
         holder.date.setText(announcementModelList.get(position).getAnnouncedate());
         holder.body.setText(announcementModelList.get(position).getAnnouncebody());


         holder.itemView.setOnClickListener(v->{

             //Toast.makeText(holder.itemView.getContext(), announcementModelList.get(position).getAnnouncefile(), Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(holder.itemView.getContext(),pdf_reader_activity.class);
             intent.putExtra("PDFFILE",announcementModelList.get(position).getAnnouncefile());
             intent.putExtra("PDFTITLE",announcementModelList.get(position).getAnnouncetitle());
             holder.itemView.getContext().startActivity(intent);

         });
    }


    @Override
    public int getItemCount() {
        return announcementModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,body,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.examhead);
            body = itemView.findViewById(R.id.announcement_body);
            date = itemView.findViewById(R.id.exammode);


        }
    }
}
