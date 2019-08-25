package com.example.singlepagerivew.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product_Pojo {
    @SerializedName("products")
    @Expose
    private Products products;
    @SerializedName("currency_symbole")
    @Expose
    private String currencySymbole;

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public String getCurrencySymbole() {
        return currencySymbole;
    }

    public void setCurrencySymbole(String currencySymbole) {
        this.currencySymbole = currencySymbole;
    }
}


