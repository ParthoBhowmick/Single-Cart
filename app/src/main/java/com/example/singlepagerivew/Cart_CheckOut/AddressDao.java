package com.example.singlepagerivew.Cart_CheckOut;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AddressDao {

    @Insert
    void insert(ShippingAddress address);

    @Update
    void update(ShippingAddress address);

    @Delete
    void delete(ShippingAddress address);

    @Query("SELECT * FROM address_table ORDER BY id ASC")
    LiveData<List<ShippingAddress>> getAllAddress();


    @Query("SELECT * FROM address_table WHERE id = :addid")
    ShippingAddress findAddress(int addid);

}
