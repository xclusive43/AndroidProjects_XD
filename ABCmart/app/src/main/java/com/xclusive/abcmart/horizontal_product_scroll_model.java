package com.xclusive.abcmart;

class horizontal_product_scroll_model {
    private String productID;
    private String product_image; //    private int product_image;
    private String product_title,product_discription,product_amount;

    public horizontal_product_scroll_model() {
    }

    public horizontal_product_scroll_model(String productID, String product_image, String product_title, String product_discription, String product_amount) {
        this.productID = productID;
        this.product_image = product_image;
        this.product_title = product_title;
        this.product_discription = product_discription;
        this.product_amount = product_amount;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_discription() {
        return product_discription;
    }

    public void setProduct_discription(String product_discription) {
        this.product_discription = product_discription;
    }

    public String getProduct_amount() {
        return product_amount;
    }

    public void setProduct_amount(String product_amount) {
        this.product_amount = product_amount;
    }
}
