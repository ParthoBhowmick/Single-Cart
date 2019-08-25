package com.example.singlepagerivew.Cart_CheckOut;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class Cart {

    public Cart(int proId,int id, String product_name, String sku, String displayImg, String vaiants, int qunatity, int stock, float product_price) {
        this.proId = proId;
        this.id = id;
        this.product_name = product_name;
        this.sku = sku;
        this.displayImg = displayImg;
        this.vaiants = vaiants;
        this.qunatity = qunatity;
        this.stock = stock;
        this.product_price = product_price;
    }

    public Cart(int proId,String product_name, String sku, String displayImg, int qunatity, float prouct_price, int stock , String vaiants, boolean chck) {
        this.proId = proId;
        this.product_name = product_name;
        this.checked = chck;
        this.sku = sku;
        this.displayImg = displayImg;
        this.qunatity = qunatity;
        this.product_price = prouct_price;
        this.vaiants = vaiants;
        this.stock = stock;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String product_name,sku,displayImg,vaiants;
    private int qunatity,stock;
    private int proId;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }


    private float product_price;


    public void setId(int id) {
        this.id = id;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getSku() {
        return sku;
    }

    public String getDisplayImg() {
        return displayImg;
    }

    public String getVaiants() {
        return vaiants;
    }

    public int getQunatity() {
        return qunatity;
    }

    public float getProduct_price() {
        return product_price;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setDisplayImg(String displayImg) {
        this.displayImg = displayImg;
    }

    public void setVaiants(String vaiants) {
        this.vaiants = vaiants;
    }

    public void setQunatity(int qunatity) {
        this.qunatity = qunatity;
    }

    public void setProuct_price(int prouct_price) {
        this.product_price = prouct_price;
    }

}
