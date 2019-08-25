package com.example.singlepagerivew.Cart_CheckOut;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AddressPlaceHolderAPIV2 {
    @GET("dhakacity.json")
    Call<SubAddress> getSubAddress();
}
