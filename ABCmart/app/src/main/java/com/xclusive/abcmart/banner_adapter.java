package com.xclusive.abcmart;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

class banner_adapter extends PagerAdapter {
    private List<banner_model> banner_modelList;
    public  banner_adapter(List<banner_model> banner_modelList){
        this.banner_modelList = banner_modelList;
    }

    @Override
    public int getCount() {
        return banner_modelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view==object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout_banner,   container,false);

        ConstraintLayout bannercontainer = view.findViewById(R.id.banner_container);

        bannercontainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(banner_modelList.get(position).getBackcolor())));
        ImageView imageView = view.findViewById(R.id.silder_image);
        //imageView.setImageResource(banner_modelList.get(position).getBanner());
///Todo glide library used
        Glide.with(container.getContext()).load(banner_modelList.get(position).getBanner()).apply(new RequestOptions().placeholder(R.drawable.ic_menu_gallery)).into(imageView);
        container.addView(view,0);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
         container.removeView((View) object);
    }
}
