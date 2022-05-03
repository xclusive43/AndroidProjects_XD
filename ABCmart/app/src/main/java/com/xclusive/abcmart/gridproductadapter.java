package com.xclusive.abcmart;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

class gridproductadapter extends BaseAdapter {

    List<horizontal_product_scroll_model> horizontal_product_scroll_modelList;

    public gridproductadapter(List<horizontal_product_scroll_model> horizontal_product_scroll_modelList) {
        this.horizontal_product_scroll_modelList = horizontal_product_scroll_modelList;
    }

    @Override
    public int getCount() {

        return horizontal_product_scroll_modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final View view;
        if(convertView==null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizantal_scroll_item_layout,null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //transfer the product id from the list
                    //Toast.makeText(view.getContext(),horizontal_product_scroll_modelList.get(position).getProductID(),Toast.LENGTH_LONG).show();
                    Intent productdetailsIntent = new Intent(parent.getContext(),product_details.class);
                    productdetailsIntent.putExtra("PRODUCT_ID",horizontal_product_scroll_modelList.get(position).getProductID());
                    parent.getContext().startActivity(productdetailsIntent);
                }
            });

            ImageView productimage = view.findViewById(R.id.h_s_product_image);
            TextView producttitle = view.findViewById(R.id.h_s_product_title);
            TextView productdescription= view.findViewById(R.id.h_s_product_discription);
            TextView productamount = view.findViewById(R.id.h_s_amount);

            Glide.with(parent.getContext()).load(horizontal_product_scroll_modelList.get(position).
                    getProduct_image()).apply(new RequestOptions().
                    placeholder(R.drawable.dot_selector)).into(productimage);

            producttitle.setText(horizontal_product_scroll_modelList.get(position).getProduct_title());
            productdescription.setText(horizontal_product_scroll_modelList.get(position).getProduct_discription());
            productamount.setText("Rs."+horizontal_product_scroll_modelList.get(position).getProduct_amount()+"/-");


        }
        else
        {
            view = convertView;
        }
        return view;
    }
}
