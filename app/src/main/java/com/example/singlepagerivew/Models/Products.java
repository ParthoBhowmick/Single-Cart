package com.example.singlepagerivew.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("wp_data")
    @Expose
    private String wpData;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("brand_id")
    @Expose
    private Object brandId;
    @SerializedName("brand_label")
    @Expose
    private Object brandLabel;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("file_name")
    @Expose
    private String fileName;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("tags")
    @Expose
    private Object tags;
    @SerializedName("gallery")
    @Expose
    private List<Gallery> gallery = null;
    @SerializedName("sku")
    @Expose
    private List<Sku> sku = null;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWpData() {
        return wpData;
    }

    public void setWpData(String wpData) {
        this.wpData = wpData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDescription() {
        return description;
    }

    public Object getBrandId() {
        return brandId;
    }

    public void setBrandId(Object brandId) {
        this.brandId = brandId;
    }

    public Object getBrandLabel() {
        return brandLabel;
    }

    public void setBrandLabel(Object brandLabel) {
        this.brandLabel = brandLabel;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

    public List<Sku> getSku() {
        return sku;
    }

    public void setSku(List<Sku> sku) {
        this.sku = sku;
    }

}
