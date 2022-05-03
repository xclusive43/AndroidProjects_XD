package com.xclusive.musicapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;


class Allmusicadapter extends RecyclerView.Adapter<Allmusicadapter.ViewHolder> {

    int lastpos = -1;
    private Context context;

    public static ArrayList<Allmusicmodel> musiclist;

    public Allmusicadapter(ArrayList<Allmusicmodel> musiclist , Context context) {
        this.musiclist = musiclist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allmusic_item_layout,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //if(holder.getAdapterPosition()>lastpos){//animation
            //Animation animation =  AnimationUtils.loadAnimation(context,R.anim.recycleranimation);

            //((ViewHolder)holder).itemView.startAnimation(animation);
            //lastpos = holder.getAdapterPosition();
            holder.songnumber.setText(String.valueOf(position+1));
            String title =  musiclist.get(position).getMusictitle();
            String imageurl  = musiclist.get(position).getMusicimage();
            holder.setTitle(title);

            byte[] image =  holder.songimagefun(imageurl);
            if (image != null){

                Glide.with(context).asBitmap()
                        .load(image)
                        .into(holder.songimage);
            }
            else {
                holder.songimage.setImageResource(R.drawable.currentmusic);
            }

       // }


/////////////click listerner////////////////////////
        holder.itemView.setOnClickListener(v->{
            Intent playingnow = new Intent(context, playingnow.class);
             playingnow.putExtra("POSITION",position);

            context.startActivity(playingnow);

        });
    }

    @Override
    public int getItemCount() {
        return  musiclist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView songimage;
        private TextView title,songnumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.musictitle);
            songnumber = itemView.findViewById(R.id.songnumber);
            songimage = itemView.findViewById(R.id.musicimage);

        }

        private byte[] songimagefun(String songuri){
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(songuri);
            byte[] art = retriever.getEmbeddedPicture();
            retriever.release();

            return art;

        }

        private void setTitle(String title1){
            title.setText(title1);

    }




    }
    public void updatelist(ArrayList<Allmusicmodel> allmusicmodelArrayList1){
        if (allmusicmodelArrayList1.isEmpty()){
            return;
        }

        musiclist = new ArrayList<>();
        musiclist.addAll(allmusicmodelArrayList1);
        notifyDataSetChanged();


    }
}
