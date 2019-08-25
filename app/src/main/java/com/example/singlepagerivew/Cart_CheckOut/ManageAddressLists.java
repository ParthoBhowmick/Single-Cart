package com.example.singlepagerivew.Cart_CheckOut;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.singlepagerivew.R;

import java.util.List;

public class ManageAddressLists extends AppCompatActivity implements Manage_Adress.onManageAddressFragmentInteractionListener, AddEditAddress.OnAddEditAddressFragmentInteractionListener {

    private Fragment fragment;
    private FragmentManager manager;

    private ShippingAddress shippingAddress;

    private AddressViewModel addressViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_address_layout);
        addFragment();
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel.class);
    }

    public void addFragment() {
        manager = getSupportFragmentManager();
        fragment = manager.findFragmentById(R.id.AddressframeContainer);
        if (fragment == null) {
            fragment = new Manage_Adress();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.AddressframeContainer, fragment);
            transaction.commit();
        }
    }

    public void changeFragment(boolean type, ShippingAddress address) {
        AddEditAddress addEditAddress = new AddEditAddress();
        Bundle bundle = new Bundle();

        if (type == false) {
            //setTitle("Add Address");
            bundle.putString("userName", "Blank");
            addEditAddress.setArguments(bundle);
        } else {
            bundle.putString("userName", address.getUserName());
            bundle.putString("userDiv", address.getUserDivi());
            bundle.putString("userEmail", address.getUserEmail());
            bundle.putString("userContact", address.getUserContact());
            bundle.putString("userDist", address.getUserDist());
            bundle.putString("userPlace", address.getUserPlace());
            bundle.putString("userAddress", address.getUserAdress());
            addEditAddress.setArguments(bundle);
            //setTitle("Edit Address");
            shippingAddress = address;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.AddressframeContainer, addEditAddress)
                .addToBackStack(null)
                .commit();

    }

    public void addAddress(ShippingAddress address) {
        if(shippingAddress!=null) addressViewModel.delete(shippingAddress);
        addressViewModel.insert(address);
        getSupportFragmentManager().popBackStack();
    }

    public void deleteAddress(ShippingAddress address) {
        addressViewModel.delete(address);
        if(address.getUserEmail().length()>0) {
            checkWithEmail(address);
        }
        else {
            checkWithoutEmail(address);
        }

    }

    public void checkWithEmail(ShippingAddress address) {
        SharedPreferences addressPrefs = getSharedPreferences("AddressPrefs",MODE_PRIVATE);
        if(address.getUserName().equals(addressPrefs.getString("userNameBA", null)) &&
                address.getUserContact().equals(addressPrefs.getString("userContactBA", null)) &&
                address.getUserEmail().equals(addressPrefs.getString("userEmailBA", null)) &&
                address.getUserDivi().equals(addressPrefs.getString("userDivBA", null)) &&
                address.getUserDist().equals(addressPrefs.getString("userDistBA", null)) &&
                address.getUserPlace().equals(addressPrefs.getString("userPlaceBA", null)) &&
                address.getUserAdress().equals(addressPrefs.getString("userAddressBA", null))) {
            addressPrefs.edit().remove("userNameBA").apply();
            addressPrefs.edit().remove("userContactBA").apply();
            addressPrefs.edit().remove("userEmailBA").apply();
            addressPrefs.edit().remove("userDivBA").apply();
            addressPrefs.edit().remove("userDistBA").apply();
            addressPrefs.edit().remove("userPlaceBA").apply();
            addressPrefs.edit().remove("userAddressBA").apply();
            Toast.makeText(this, "Billing Removed!", Toast.LENGTH_SHORT).show();
        }
        else if(address.getUserName().equals(addressPrefs.getString("userName", null)) &&
                address.getUserContact().equals(addressPrefs.getString("userContact", null)) &&
                address.getUserEmail().equals(addressPrefs.getString("userEmail", null)) &&
                address.getUserDivi().equals(addressPrefs.getString("userDiv", null)) &&
                address.getUserDist().equals(addressPrefs.getString("userDist", null)) &&
                address.getUserPlace().equals(addressPrefs.getString("userPlace", null)) &&
                address.getUserAdress().equals(addressPrefs.getString("userAddress", null))) {
            addressPrefs.edit().remove("userName").apply();
            addressPrefs.edit().remove("userContact").apply();
            addressPrefs.edit().remove("userEmail").apply();
            addressPrefs.edit().remove("userDiv").apply();
            addressPrefs.edit().remove("userDist").apply();
            addressPrefs.edit().remove("userPlace").apply();
            addressPrefs.edit().remove("userAddress").apply();
            Toast.makeText(this, "Shipping Removed!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Nothing Removed!\n" + addressPrefs.getString("userAddress",null) + "\n" + address.getUserAdress(), Toast.LENGTH_SHORT).show();
        }
    }

    public void checkWithoutEmail(ShippingAddress address) {
        SharedPreferences addressPrefs = getSharedPreferences("AddressPrefs",MODE_PRIVATE);
        if(address.getUserName().equals(addressPrefs.getString("userNameBA", null)) &&
                address.getUserContact().equals(addressPrefs.getString("userContactBA", null))&&
                address.getUserDivi().equals(addressPrefs.getString("userDivBA", null)) &&
                address.getUserDist().equals(addressPrefs.getString("userDistBA", null)) &&
                address.getUserPlace().equals(addressPrefs.getString("userPlaceBA", null)) &&
                address.getUserAdress().equals(addressPrefs.getString("userAddressBA", null))) {
            addressPrefs.edit().remove("userNameBA").apply();
            addressPrefs.edit().remove("userContactBA").apply();
            addressPrefs.edit().remove("userEmailBA").apply();
            addressPrefs.edit().remove("userDivBA").apply();
            addressPrefs.edit().remove("userDistBA").apply();
            addressPrefs.edit().remove("userPlaceBA").apply();
            addressPrefs.edit().remove("userAddressBA").apply();
            Toast.makeText(this, "Billing Removed!", Toast.LENGTH_SHORT).show();
        }
        else if(address.getUserName().equals(addressPrefs.getString("userName", null)) &&
                address.getUserContact().equals(addressPrefs.getString("userContact", null)) &&
                address.getUserDivi().equals(addressPrefs.getString("userDiv", null)) &&
                address.getUserDist().equals(addressPrefs.getString("userDist", null)) &&
                address.getUserPlace().equals(addressPrefs.getString("userPlace", null)) &&
                address.getUserAdress().equals(addressPrefs.getString("userAddress", null))) {
            addressPrefs.edit().remove("userName").apply();
            addressPrefs.edit().remove("userContact").apply();
            addressPrefs.edit().remove("userEmail").apply();
            addressPrefs.edit().remove("userDiv").apply();
            addressPrefs.edit().remove("userDist").apply();
            addressPrefs.edit().remove("userPlace").apply();
            addressPrefs.edit().remove("userAddress").apply();
            Toast.makeText(this, "Shipping Removed!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Nothing Removed!\n" + addressPrefs.getString("userAddress",null) + "\n" + address.getUserAdress(), Toast.LENGTH_SHORT).show();
        }
    }


    public void getView(final AddressAdapter addressAdapter, final LinearLayout ll) {
        AddressViewModel addressViewModel = ViewModelProviders.of(this).get(AddressViewModel.class);
        addressViewModel.getAllAddress().observe(this, new Observer<List<ShippingAddress>>() {

            @Override
            public void onChanged(@Nullable List<ShippingAddress> addresses) {
                if (addresses.size() > 0) {
                    ll.setVisibility(View.GONE);
                } else {
                    ll.setVisibility(View.VISIBLE);
                }
                addressAdapter.setAddressList(addresses);
            }
        });
    }

    @Override
    public void onManageAddressFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAddEditAddressFragmentInteraction(Uri uri) {

    }

    public void changeActivity() {
        Intent intent = new Intent(this, CheckoutPage.class);
        startActivity(intent);
    }


}
