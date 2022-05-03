package com.xclusive.musicapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class Playerlistadapter extends RecyclerView.Adapter<Playerlistadapter.ViewHolder> {
    private Context context;

    private ArrayList<Allmusicmodel> musiclist1;

    public Playerlistadapter(Context context, ArrayList<Allmusicmodel> musiclist1) {
        this.context = context;
        this.musiclist1 = musiclist1;
    }

    @NonNull
    @Override
    public Playerlistadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allmusic_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Playerlistadapter.ViewHolder holder, int position) {

        String title =  musiclist1.get(position).getMusictitle();
        String imageurl  = musiclist1.get(position).getMusicimage();
        holder.title.setText(title);

        byte[] image =  holder.songimagefun(imageurl);
        if (image != null){

            Glide.with(context).asBitmap()
                    .load(image)
                    .into(holder.songimage);
        }
        else {
            holder.songimage.setImageResource(R.drawable.currentmusic);
        }



    holder.itemView.setOnClickListener(v->{
         Intent playingnow = new Intent(context, playingnow.class);
         playingnow.putExtra("POSITION",position);
         context.startActivity(playingnow);

    });

    }

    @Override
    public int getItemCount() {
        return musiclist1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView songimage;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.musictitle);
            songimage = itemView.findViewById(R.id.musicimage);
        }


        private byte[] songimagefun(String songuri){
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(songuri);
            byte[] art = retriever.getEmbeddedPicture();
            retriever.release();
            return art;

        }

    }
}
