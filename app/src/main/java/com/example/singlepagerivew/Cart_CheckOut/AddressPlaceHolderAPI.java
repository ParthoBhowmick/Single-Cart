package com.example.singlepagerivew.Cart_CheckOut;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AddressPlaceHolderAPI {
    @GET("dhaka.json")
    Call<SubAddress> getSubAddress();
}
