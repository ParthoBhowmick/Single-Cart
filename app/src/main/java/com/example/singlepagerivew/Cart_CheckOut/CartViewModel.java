package com.example.singlepagerivew.Cart_CheckOut;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private CartRepository repository;
    private LiveData<List<Cart>> allCarts;

    public CartViewModel(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
        allCarts = repository.getAllCarts();
    }

    public void insert(Cart note) {
        repository.insert(note);
    }

    public void update(Cart note,Context ctx) {
        repository.update(note,ctx);
    }

    public void delete(Cart note) {
        repository.delete(note);
    }

    public void deleteAllCarts() {
        repository.deleteAllCarts();
    }

    public LiveData<List<Cart>> getAllCarts() {
        return allCarts;
    }

    public void couponImplementation(Context context,int type) {
        repository.couponImplementation(context,type);
    }

    public void findProductnAction(String productSku, Cart cart, Context context) {
        repository.findProductnAction(productSku,cart,context);
    }

}