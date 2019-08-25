package com.example.singlepagerivew.Cart_CheckOut;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AddressViewModel extends AndroidViewModel {
    private AddressRepository repository;
    private LiveData<List<ShippingAddress>> allAddress;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        repository = new AddressRepository(application);
        allAddress = repository.getAllAddresss();
    }

    public void insert(ShippingAddress shippingAddress) {
        repository.insert(shippingAddress);
    }

    public void update(ShippingAddress shippingAddress, Context ctx) {
        repository.update(shippingAddress,ctx);
    }

    public void delete(ShippingAddress shippingAddress) {
        repository.delete(shippingAddress);
    }

    public LiveData<List<ShippingAddress>> getAllAddress() {
        return allAddress;
    }

}
