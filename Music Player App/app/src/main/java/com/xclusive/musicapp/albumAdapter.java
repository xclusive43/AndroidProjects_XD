package com.xclusive.musicapp;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class albumAdapter extends RecyclerView.Adapter<albumAdapter.Myviewholder> {

    private Context context;
    private ArrayList<Allmusicmodel> albumlist;

    public albumAdapter(Context context, ArrayList<Allmusicmodel> albumlist) {
        this.context = context;
        this.albumlist = albumlist;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.albumitems,parent,false);

        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {

        holder.albumtitle.setText(albumlist.get(position).getAlbum());
        String albumimageuri  = albumlist.get(position).getMusicimage();


        byte[] albumart =   holder.albumimage1(albumimageuri);
        if (albumart != null){
            Glide.with(context).asBitmap().load(albumart).into(holder.albumimage);
        }
        else {
             holder.albumimage.setImageResource(R.drawable.currentmusic);
        }

        holder.itemView.setOnClickListener(v->{
               Intent albumactivity = new Intent(context, Album_activity.class);
               albumactivity.putExtra("albumname", albumlist.get(position).getAlbum());
               context.startActivity(albumactivity);

        });
    }

    @Override
    public int getItemCount() {
        return albumlist.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        ImageView albumimage ;
        TextView albumtitle;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            albumimage = itemView.findViewById(R.id.albumimage);

            albumtitle =itemView.findViewById(R.id.albumtitle);
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
