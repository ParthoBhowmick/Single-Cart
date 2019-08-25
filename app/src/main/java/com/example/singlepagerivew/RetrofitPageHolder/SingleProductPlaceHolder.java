package com.example.singlepagerivew.RetrofitPageHolder;

import com.example.singlepagerivew.Models.Product_Pojo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SingleProductPlaceHolder {
    @GET("jsonTest.php")
    Call<Product_Pojo> getPosts();
}
