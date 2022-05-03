package com.xclusive.abcmart;

import android.app.LauncherActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class homePageAdapter extends RecyclerView.Adapter {

    private RecyclerView.RecycledViewPool recycledViewPool;
    List<homePageModel> homePageModelList;

    public homePageAdapter(List<homePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
         switch (homePageModelList.get(position).getType()){
             case 0:
                 return homePageModel.banner_slider;
             case 1:
                 return homePageModel.strip_ad_banner;
             case 2:
                 return homePageModel.horizontal_product;
             case 3:
                 return homePageModel.grid_product_view;
             default:
                 return -1;
         }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case homePageModel.banner_slider:
                View bannersliderview = LayoutInflater.from(parent.getContext()).inflate(R.layout.silding_add_banner,parent,false);
                return new bannersliderviewholder(bannersliderview);
            case homePageModel.strip_ad_banner:
                View stripadview = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout,parent,false);
                return new stripadbannerviewholder(stripadview);

            case homePageModel.horizontal_product:
                View horizontalproductview = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout,parent,false);
                return new horizontalproductviewholder(horizontalproductview);
            case homePageModel.grid_product_view:
                View gridproductview = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout,parent,false);
                return new gridproductviewholder(gridproductview);
            default:
                return null;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (homePageModelList.get(position).getType()){
            case homePageModel.banner_slider:
                List<banner_model> banner_modelList = homePageModelList.get(position).getBanner_modelList();
                ((bannersliderviewholder)holder).setBannersliderviewpager(banner_modelList);
                break;

            case homePageModel.strip_ad_banner:
                String resource = homePageModelList.get(position).getResourse();
                String color = homePageModelList.get(position).getBackgroundcolor();
                ((stripadbannerviewholder)holder).setstripad(resource,color);
                break;
            case homePageModel.horizontal_product:
                String color1 = homePageModelList.get(position).getBackgroundcolor();
                String title= homePageModelList.get(position).getTitle();
                List<wistlist_model> view_all_product_list = homePageModelList.get(position).getView_all_productlist();

                List<horizontal_product_scroll_model> horizontal_product_scroll_modelList = homePageModelList.get(position).getHorizontal_product_scroll_modelList();
                ((horizontalproductviewholder)holder).sethorizontalproductlayout(horizontal_product_scroll_modelList,title,color1,view_all_product_list);
                break;
            case homePageModel.grid_product_view:
                String gridtitle = homePageModelList.get(position).getTitle();
                String color2 = homePageModelList.get(position).getBackgroundcolor();
                List<horizontal_product_scroll_model> gridhorizontal_product_scroll_modelList = homePageModelList.get(position).getHorizontal_product_scroll_modelList();
                ((gridproductviewholder)holder).setgridproductlayout(gridhorizontal_product_scroll_modelList,gridtitle,color2);
                break;

            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public  class  bannersliderviewholder extends RecyclerView.ViewHolder{

        private ViewPager bannersliderviewpager;
        private  int currentpage;
        private Timer timer;
        final private long delay = 2500;
        final private long period = 2500;
        private List<banner_model> arrangedlist;

        public bannersliderviewholder(@NonNull View itemView) {
            super(itemView);
            bannersliderviewpager = itemView.findViewById(R.id.viewpabanner_viewpager);

        }
        private  void setBannersliderviewpager(final List<banner_model>banner_modelList){
            currentpage =2;
            if(timer!=null){ // TODO: To fix the banner slider when we scroll the page the time of the banner goes
                              //TODO: random times which create problem to the slider to operate serially ....
                timer.cancel();
            }

            // TODO: logic for slider to operate
            arrangedlist = new ArrayList<>();
            for(int x=0; x<banner_modelList.size();x++){

                arrangedlist.add(x,banner_modelList.get(x));

            }

            arrangedlist.add(0,banner_modelList.get(banner_modelList.size()-2)); //TODO: last banner in the first position
            arrangedlist.add(1,banner_modelList.get(banner_modelList.size()-1)); //TODO: 2nd last banner in the 2nd position

            arrangedlist.add(banner_modelList.get(0));//TODO: first banner in the last position
            arrangedlist.add(banner_modelList.get(1));//TODO: 2nd  banner in the 2nd last position


            banner_adapter banner_adapter = new banner_adapter( arrangedlist);
            bannersliderviewpager.setAdapter(banner_adapter);
            bannersliderviewpager.setClipToPadding(false);
            bannersliderviewpager.setPageMargin(15);
            bannersliderviewpager.setCurrentItem(currentpage);


            ViewPager.OnPageChangeListener onPageChangeListener =new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentpage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state==ViewPager.SCROLL_STATE_IDLE){
                        pagelooper(  arrangedlist);
                    }
                }
            };

            bannersliderviewpager.addOnPageChangeListener(onPageChangeListener);

            startanimation(  arrangedlist);

            bannersliderviewpager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pagelooper(  arrangedlist);
                    stopanimation();
                    if(event.getAction()==MotionEvent.ACTION_UP)
                    {
                        startanimation(  arrangedlist);
                    }
                    return false;
                }
            });
        }
        private void  pagelooper(List<banner_model> banner_modelList) {
            if(currentpage==1){
                currentpage=banner_modelList.size()-3;
                bannersliderviewpager.setCurrentItem(currentpage,false);
            }
            if(currentpage==banner_modelList.size()-2){
                currentpage=2;
                bannersliderviewpager.setCurrentItem(currentpage,false);
            }

        }
        private void startanimation(final List<banner_model> banner_modelList){
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if(currentpage >= banner_modelList.size())
                    {
                        currentpage = 1;
                    }
                    bannersliderviewpager.setCurrentItem(currentpage++,true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            },delay,period);
        }
        private void stopanimation(){
            timer.cancel();
        }
    }

    public class stripadbannerviewholder extends  RecyclerView.ViewHolder{

        private ImageView stripimage;
        private ConstraintLayout strip_ad_container;

        public stripadbannerviewholder(@NonNull View itemView) {
            super(itemView);
            stripimage = itemView.findViewById(R.id.strip_ad_image);
            strip_ad_container = itemView.findViewById(R.id.strip_container);

        }

        private  void setstripad(String resourse, String color)
        {
            //stripimage.setImageResource(resourse);
            Glide.with(itemView.getContext()).load(resourse).apply(new RequestOptions().placeholder(R.drawable.home)).into(stripimage);
            strip_ad_container.setBackgroundColor(Color.parseColor(color));

        }
    }

    public  class horizontalproductviewholder extends RecyclerView.ViewHolder{
        private TextView layouttitle;
        private Button viewallbtn;
        private ConstraintLayout container;
        private RecyclerView horizontalrecyclerview;


    public horizontalproductviewholder(@NonNull View itemView) {
            super(itemView);
            layouttitle = itemView.findViewById(R.id.horizontal_layout_title);
            viewallbtn = itemView.findViewById(R.id.horizontal_viewall_btn);
            container = itemView.findViewById(R.id.horizontal_scroll_container);
            horizontalrecyclerview = itemView.findViewById(R.id.horizontal_recycler_view);

            horizontalrecyclerview.setRecycledViewPool(recycledViewPool);//for smoothness in recycler we need

        }
        private  void sethorizontalproductlayout(List<horizontal_product_scroll_model>horizontal_product_scroll_modelList, final String title, String color1, final List<wistlist_model> view_all_product_list){
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color1)));
            layouttitle.setText(title);

            if(horizontal_product_scroll_modelList.size()>7){ //if the product list consist of 8 products the view all button is visible;
                viewallbtn.setVisibility(View.VISIBLE);

                //for moving in recycler view in viewallactivity.java
                viewallbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ViewAllActivity2.wistlist_modelList = view_all_product_list;
                        Intent viewallintent = new Intent(itemView.getContext(),ViewAllActivity2.class);

                        viewallintent.putExtra("layout_code",0);//means grid view
                        viewallintent.putExtra("title",title);
                        itemView.getContext().startActivity(viewallintent);

                    }
                });

            }
            else {
                viewallbtn.setVisibility(View.INVISIBLE);
            }


            horizontal_product_schroll_adapter horizontal_product_schroll_adapter =new horizontal_product_schroll_adapter(horizontal_product_scroll_modelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

            horizontalrecyclerview.setLayoutManager(linearLayoutManager);
            horizontalrecyclerview.setAdapter(horizontal_product_schroll_adapter);

            horizontal_product_schroll_adapter.notifyDataSetChanged();
        }
    }

    private class gridproductviewholder extends RecyclerView.ViewHolder {
        private TextView gridlayouttitle;
        private Button gridlayoutviewallbtn;
        private GridLayout gridproductlayout;
        private  ConstraintLayout gridproduct_layout;

        public gridproductviewholder(@NonNull View itemView) {
            super(itemView);
            gridproduct_layout = itemView.findViewById(R.id.grid_product_container);
            gridlayouttitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridlayoutviewallbtn = itemView.findViewById(R.id.grid_product_layout_btn);
            gridproductlayout = itemView.findViewById(R.id.gridlayout);

        }
        private  void setgridproductlayout(final List<horizontal_product_scroll_model>horizontal_product_scroll_modelList, final String title, String color2){
            gridproduct_layout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color2)));
            gridlayouttitle.setText(title);


           for (int i=0; i<4;i++){
               ImageView productimage = gridproductlayout.getChildAt(i).findViewById(R.id.h_s_product_image);
               TextView producttitle = gridproductlayout.getChildAt(i).findViewById(R.id.h_s_product_title);
               TextView productdiscription = gridproductlayout.getChildAt(i).findViewById(R.id.h_s_product_discription);
               TextView productprice = gridproductlayout.getChildAt(i).findViewById(R.id.h_s_amount);

               //gile//
             Glide.with(itemView.getContext()).load(horizontal_product_scroll_modelList.get(i).getProduct_image()).
                       apply(new RequestOptions().placeholder(R.drawable.dot_selector)).into(productimage);

               producttitle.setText(horizontal_product_scroll_modelList.get(i).getProduct_title());

               productdiscription.setText(horizontal_product_scroll_modelList.get(i).getProduct_discription());

               productprice.setText("Rs."+horizontal_product_scroll_modelList.get(i).getProduct_amount()+"/-");

               gridproductlayout.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
               //Toast.makeText(itemView.getContext(),horizontal_product_scroll_modelList.get(i).getProductID(),Toast.LENGTH_LONG).show();


               final int  I = i;
               gridproductlayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       //Toast.makeText(itemView.getContext(),horizontal_product_scroll_modelList.get(finalI1).getProductID(),Toast.LENGTH_LONG).show();

                       Intent viewallintent1 = new Intent(itemView.getContext(),product_details.class);
                       viewallintent1.putExtra("PRODUCT_ID",horizontal_product_scroll_modelList.get(I).getProductID());
                       itemView.getContext().startActivity(viewallintent1);
                   }
               });


           }

            gridlayoutviewallbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {//for moving in grid view in viewallactivity.java
                 ViewAllActivity.horizontal_product_scroll_modelList = horizontal_product_scroll_modelList;//todo setting the list in viewall activity for grid layout to schow all products
                 Intent viewallintent = new Intent(itemView.getContext(),ViewAllActivity.class);
                 viewallintent.putExtra("layout_code",1);//means grid view
                 viewallintent.putExtra("title",title);
                 itemView.getContext().startActivity(viewallintent);
             }
         });


        }
    }
}
