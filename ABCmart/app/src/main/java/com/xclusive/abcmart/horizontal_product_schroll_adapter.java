package com.xclusive.abcmart;

import android.content.Intent;
import android.icu.text.Replaceable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

class horizontal_product_schroll_adapter extends RecyclerView.Adapter<horizontal_product_schroll_adapter.ViewHolder> {

    private List<horizontal_product_scroll_model> horizontal_product_scroll_modelList;

    public horizontal_product_schroll_adapter(List<horizontal_product_scroll_model> horizontal_product_scroll_modelList) {
        this.horizontal_product_scroll_modelList = horizontal_product_scroll_modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizantal_scroll_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      String resource = horizontal_product_scroll_modelList.get(position).getProduct_image();
      String title= horizontal_product_scroll_modelList.get(position).getProduct_title();
      String description = horizontal_product_scroll_modelList.get(position).getProduct_discription();
      String amount= horizontal_product_scroll_modelList.get(position).getProduct_amount();
      String product_id = horizontal_product_scroll_modelList.get(position).getProductID();

      holder.setProduct_image(resource,product_id);
      holder.setProduct_title(title);
      holder.setProduct_description(description);
      holder.setProduct_amount(amount);
    }

    @Override
    public int getItemCount() {
        if(horizontal_product_scroll_modelList.size()>8)
        {
            return 8 ;
        }else
        {
            return horizontal_product_scroll_modelList.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView product_image;
        private TextView product_title,product_description,product_amount;

        public ViewHolder(@NonNull final View itemView) {

            super(itemView);

            product_image=itemView.findViewById(R.id.h_s_product_image);
            product_title = itemView.findViewById(R.id.h_s_product_title);
            product_description = itemView.findViewById(R.id.h_s_product_discription);
            product_amount =itemView.findViewById(R.id.h_s_amount);


        }

        private void setProduct_image(String resource, final String productid)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.dot_selector)).into(product_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productdetailsintent = new Intent(itemView.getContext(),product_details.class);
                    productdetailsintent.putExtra("PRODUCT_ID",productid);
                    itemView.getContext().startActivity(productdetailsintent);
                }
            });

        }
        private  void setProduct_title(String title)
        {
            product_title.setText(title);
        }

        private  void  setProduct_description(String description)
        {
            product_description.setText(description);
        }
        private  void setProduct_amount(String amount)
        {
            product_amount.setText("Rs."+amount+"/-");
        }
    }
}
