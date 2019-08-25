package com.example.singlepagerivew.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.singlepagerivew.Cart_CheckOut.Cart;
import com.example.singlepagerivew.Cart_CheckOut.CartPage;
import com.example.singlepagerivew.Cart_CheckOut.CartViewModel;
import com.example.singlepagerivew.Fragments.SingleProductFragment;
import com.example.singlepagerivew.Fragments.ViewImageFragment;
import com.example.singlepagerivew.R;

import java.util.ArrayList;

public class SingleProductPage extends AppCompatActivity implements SingleProductFragment.OnSingleProductFragmentInteractionListener,ViewImageFragment.OnViewImageFragmentInteractionListener {

    private Fragment fragment;
    private FragmentManager manager;



    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product_page_layout);



        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);


        addSingleProductViewFragment();
    }


    public void addSingleProductViewFragment() {
        manager = getSupportFragmentManager();
        fragment = manager.findFragmentById(R.id.frameContainer);
        if(fragment==null) {
            fragment = new SingleProductFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.frameContainer,fragment);
            transaction.commit();
        }
    }

    public void viewCart() {

        Intent intent = new Intent(this, CartPage.class);
        startActivity(intent);

    }

    public void addCart(Cart cart) {

        if (cart.getProduct_name()!=null) {
            cartViewModel.findProductnAction(cart.getSku(),cart,this);
        }

    }

    public void insertItem(Cart cart) {
        cartViewModel.insert(cart);
    }

    public void updateCartItem(Cart cart, Context ctx) {
        cartViewModel.update(cart,ctx);
    }

    public void openShare() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://jadroo.com";
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share Using"));
    }

    public void ViewImage(String imgUrl, ArrayList<String> Images, int curr_Img) {
        Bundle bundle = new Bundle();
        bundle.putString("url", imgUrl);
        bundle.putStringArrayList("imgs", Images);
        bundle.putInt("current", curr_Img);
        ViewImageFragment viewFrag = new ViewImageFragment();
        viewFrag.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameContainer,viewFrag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onViewImageFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSingleProductFragmentFragmentInteraction(Uri uri) {

    }
}
