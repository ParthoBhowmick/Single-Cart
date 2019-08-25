package com.example.singlepagerivew.Cart_CheckOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Coupon {

    @SerializedName("coupon_id")
    @Expose
    private String couponId;
    @SerializedName("users")
    @Expose
    private List<User> users = null;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("number_of_uses")
    @Expose
    private String numberOfUses;
    @SerializedName("uses_limit")
    @Expose
    private Object usesLimit;
    @SerializedName("products")
    @Expose
    private List<Integer> products = null;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("vendor_id")
    @Expose
    private Object vendorId;
    @SerializedName("coupon_life")
    @Expose
    private String couponLife;
    @SerializedName("coupon_type")
    @Expose
    private String couponType;
    @SerializedName("coupon_to_cart")
    @Expose
    private String couponToCart;
    @SerializedName("categories")
    @Expose
    private List<Object> categories;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumberOfUses() {
        return numberOfUses;
    }

    public void setNumberOfUses(String numberOfUses) {
        this.numberOfUses = numberOfUses;
    }

    public Object getUsesLimit() {
        return usesLimit;
    }

    public void setUsesLimit(Object usesLimit) {
        this.usesLimit = usesLimit;
    }

    public List<Integer> getProducts() {
        return products;
    }

    public void setProducts(List<Integer> products) {
        this.products = products;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Object getVendorId() {
        return vendorId;
    }

    public void setVendorId(Object vendorId) {
        this.vendorId = vendorId;
    }

    public String getCouponLife() {
        return couponLife;
    }

    public void setCouponLife(String couponLife) {
        this.couponLife = couponLife;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponToCart() {
        return couponToCart;
    }

    public void setCouponToCart(String couponToCart) {
        this.couponToCart = couponToCart;
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}