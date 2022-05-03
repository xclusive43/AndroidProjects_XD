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


class album_activity_adapter extends RecyclerView.Adapter<album_activity_adapter.ViewHolder> {

    Context context;
    public static ArrayList<Allmusicmodel>albumactivityArrayList;

    public album_activity_adapter(Context context, ArrayList<Allmusicmodel> albumactivityArrayList) {
        this.context = context;
        this.albumactivityArrayList = albumactivityArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allmusic_item_layout,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                 holder.textView.setText(albumactivityArrayList.get(position).getMusictitle());
        String albumimageuri  =albumactivityArrayList.get(position).getMusicimage();


        byte[] albumart =   holder.albumimage1(albumimageuri);
        if (albumart != null){
            Glide.with(context).asBitmap().load(albumart).into(holder.imageView);
        }
        else {
            holder.imageView.setImageResource(R.drawable.currentmusic);
        }

        holder.itemView.setOnClickListener(v->{

            Intent albumactivity = new Intent(context, playingnow.class);
            albumactivity.putExtra("POSITION",position);
            albumactivity.putExtra("SENDER","albumDetails");

            context.startActivity(albumactivity);


        });
    }

    @Override
    public int getItemCount() {
        return albumactivityArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.musictitle);
            imageView = itemView.findViewById(R.id.musicimage);
        }

        private byte[] albumimage1(String songuri){
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(songuri);
            byte[] art = retriever.getEmbeddedPicture();
            retriever.release();

            return art;

        }
    }
}
