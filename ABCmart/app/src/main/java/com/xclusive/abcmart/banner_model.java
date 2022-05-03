package com.xclusive.abcmart;

class banner_model {
    private  String banner;
    private String backcolor;

    public banner_model() {
    }

    public banner_model(String banner, String backcolor) {
        this.banner = banner;
        this.backcolor = backcolor;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBackcolor() {
        return backcolor;
    }

    public void setBackcolor(String backcolor) {
        this.backcolor = backcolor;
    }
}
