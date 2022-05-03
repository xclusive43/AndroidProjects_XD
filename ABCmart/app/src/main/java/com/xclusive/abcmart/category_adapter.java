package com.xclusive.abcmart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

class category_adapter extends RecyclerView.Adapter<category_adapter.ViewHolder> {

    private List<category_model> category_modelList;

    public category_adapter(List<category_model> category_modelList) {
        this.category_modelList = category_modelList;
    }



    @NonNull
    @Override
    public category_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull category_adapter.ViewHolder holder, int position) {
        String icon = category_modelList.get(position).getCategory_link();
        String name = category_modelList.get(position).getCategory_name();

        holder.setCategory(name,position);
        holder.setCategoryicon(icon);
    }

    @Override
    public int getItemCount() {
        return category_modelList.size();
    }


      public class ViewHolder extends RecyclerView.ViewHolder{
      private ImageView categoryicon;
      private TextView categoryname;
      public ViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryicon = itemView.findViewById(R.id.category_icon);
        categoryname = itemView.findViewById(R.id.category_name);
    }

    private void  setCategoryicon(String iconurl){ //for setting icons dynamically
                                       /////////TODO: firebase code/////
        if(!iconurl.equals("null")){
            Glide.with(itemView.getContext()).load(iconurl).apply(new RequestOptions().placeholder(R.drawable.home)).into(categoryicon);
        }


    }

    private  void  setCategory(final String name, final int position) ////for setting names dynamically
    {
        categoryname.setText(name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=0){
                    Intent category_intent= new Intent(itemView.getContext(), category_activity.class);
                    category_intent.putExtra("categoryname",name);
                    //Toast.makeText(itemView.getContext(),name,Toast.LENGTH_LONG).show();
                    itemView.getContext().startActivity(category_intent);

                }


            }
        });


    }
}

}
