package com.xclusive.x_note.Adapter;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.xclusive.x_note.MainActivity;
import com.xclusive.x_note.R;
import com.xclusive.x_note.model.Model_notes;
import com.xclusive.x_note.savednotes;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import static com.xclusive.x_note.MainActivity.Title;
import static com.xclusive.x_note.MainActivity.data1;
import static com.xclusive.x_note.MainActivity.editlayout;
import static com.xclusive.x_note.MainActivity.id;
import static com.xclusive.x_note.MainActivity.input;
import static com.xclusive.x_note.MainActivity.subtitle;
import static com.xclusive.x_note.MainActivity.subtitle1;
import static com.xclusive.x_note.MainActivity.title1;
import static com.xclusive.x_note.MainActivity.color;
import static com.xclusive.x_note.MainActivity.update;

public class Adapter_notes  extends RecyclerView.Adapter<Adapter_notes.ViewHolder> {


    public static List<Model_notes> model_notesList;

    public Adapter_notes(List<Model_notes> model_notesList) {

        this.model_notesList = model_notesList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.snoteitem_layout,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.title.setText(model_notesList.get(position).getTitle());
         holder.date.setText(model_notesList.get(position).getDates());
         holder.data.setText(model_notesList.get(position).getNotes());
         holder.id.setText(model_notesList.get(position).getId());
         //String color = "#FFDF8C72";

         holder.cardview_l.setCardBackgroundColor(Color.parseColor(model_notesList.get(position).getColor()));
       // Toast.makeText(holder.itemView.getContext(), "color"+"\""+model_notesList.get(position).getColor()+"\"", Toast.LENGTH_SHORT).show();
         holder.itemView.setOnClickListener(V->{
             Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);
             ((Activity)holder.itemView.getContext()).finish();
             holder.itemView.getContext().startActivity(intent);
             data1 = model_notesList.get(position).getNotes();
             title1  =model_notesList.get(position).getTitle();
             subtitle1 = model_notesList.get(position).getSubtitle();
             color = model_notesList.get(position).getColor();
             update = 1;
             id = model_notesList.get(position).getId();



         });


    }


    @Override
    public int getItemCount() {
        return model_notesList.size();
    }







    public class ViewHolder extends RecyclerView.ViewHolder {
        public  TextView title,data,date,id;
        public CardView cardview_l;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_L);
            data = itemView.findViewById(R.id.notes_l);
            date = itemView.findViewById(R.id.date_l);
            id = itemView.findViewById(R.id.Idd);
            cardview_l = itemView.findViewById(R.id.cardview_l);
        }
    }

    public void updatelist(ArrayList<Model_notes> newlist){
        if (newlist.isEmpty()){
            return;
        }

        model_notesList = new ArrayList<>();
        model_notesList.addAll(newlist);
        notifyDataSetChanged();


    }
}
