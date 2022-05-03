package com.xclusive.abcmart;

import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

class homePageModel {

    public  static final int banner_slider=0;
    public  static final int strip_ad_banner=1;
    public  static final int horizontal_product=2;
    public  static final int grid_product_view=3;
    private  String backgroundcolor;
    private  int type;


    //11111111///////////banner slider//////////// 1
    private List<banner_model> banner_modelList;
    public homePageModel(int type, List<banner_model> banner_modelList) {
        this.type = type;
        this.banner_modelList = banner_modelList;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public List<banner_model> getBanner_modelList() {
        return banner_modelList;
    }
    public void setBanner_modelList(List<banner_model> banner_modelList) {
        this.banner_modelList = banner_modelList;
    }
    /////////////End -banner slider////////////

    //////strip ad banner//////
    private  String resourse;

    public homePageModel(int type,String resourse, String backgroundcolor) {
        this.type = type;
        this.resourse = resourse;
        this.backgroundcolor = backgroundcolor;
    }
    public String getResourse() {
        return resourse;
    }
    public void setResourse(String resourse) {
        this.resourse = resourse;
    }
    public String getBackgroundcolor() {
        return backgroundcolor;
    }
    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }
    ///-------------//


    private  String title;
    private  List<horizontal_product_scroll_model> horizontal_product_scroll_modelList;

    ///-horizontal product layout //
    private List<wistlist_model> view_all_productlist;
    public homePageModel(int type, String title,String backgroundcolor, List<horizontal_product_scroll_model> horizontal_product_scroll_modelList,List<wistlist_model> view_all_productlist) {
        this.type = type;
        this.title = title;
        this.backgroundcolor = backgroundcolor;
        this.horizontal_product_scroll_modelList = horizontal_product_scroll_modelList;
        this.view_all_productlist =view_all_productlist;
    }

    public List<wistlist_model> getView_all_productlist() {
        return view_all_productlist;
    }

    public void setView_all_productlist(List<wistlist_model> view_all_productlist) {
        this.view_all_productlist = view_all_productlist;
    }

    //----------------------------//

    ///- grid product layout//
    public homePageModel(int type, String title, String backgroundcolor, List<horizontal_product_scroll_model> horizontal_product_scroll_modelList) {
        this.type = type;
        this.title = title;
        this.backgroundcolor = backgroundcolor;
        this.horizontal_product_scroll_modelList = horizontal_product_scroll_modelList;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<horizontal_product_scroll_model> getHorizontal_product_scroll_modelList() {
        return horizontal_product_scroll_modelList;
    }
    public void setHorizontal_product_scroll_modelList(List<horizontal_product_scroll_model> horizontal_product_scroll_modelList) {
        this.horizontal_product_scroll_modelList = horizontal_product_scroll_modelList;
    }


    //gird product view--//////
    //-----------------------//
}
