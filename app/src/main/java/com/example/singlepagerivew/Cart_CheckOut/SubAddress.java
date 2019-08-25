package com.example.singlepagerivew.Cart_CheckOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubAddress {

    @SerializedName("Parent")
    @Expose
    private List<String> parent;

    public List<String> getParent() {
        return parent;
    }

    public void setParent(List<String> parent) {
        this.parent = parent;
    }

}
