package com.example.singlepagerivew.Cart_CheckOut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.singlepagerivew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.graphics.Color.RED;
import static android.graphics.Color.TRANSPARENT;

public class CartPage extends AppCompatActivity {

    private CartViewModel cartViewModel;
    private EditText couponEt;
    private float grandTotal = 0;
    private ProgressDialog dialog;

    public static final String CouponPreference = "CoupunPrefs";
    public SharedPreferences couponPrefs;
    private SharedPreferences.Editor editor;


    final CartAdapter adapter = new CartAdapter(this);
    private TextView priceTv, couponAply;

    private Button checkout;

    private List<Cart> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);

        TextView tv = findViewById(R.id.toolbarHeader);
        tv.setText("Cart");

        couponPrefs = getSharedPreferences(CouponPreference, (CartPage.this).MODE_PRIVATE);

        RecyclerView recyclerView = findViewById(R.id.cartList);
        couponEt = findViewById(R.id.couponEt);
        couponAply = findViewById(R.id.couponAplyBtn);
        checkout = findViewById(R.id.Checkout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        priceTv = findViewById(R.id.TotalPrice);

        recyclerView.setAdapter(adapter);
        viewCart();



        couponAply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(couponAply.getText() == "Change") {
                    editor.clear();
                    editor.commit();
                    couponEt.setText("");
                    couponEt.setHint("Enter Coupon Code Here");
                    couponAply.setText("Apply");
                    couponEt.setBackgroundColor(Color.parseColor("#ffffff"));
                    couponEt.setClickable(true);
                    couponEt.setCursorVisible(true);
                    couponEt.setFocusable(true);
                    couponEt.setFocusableInTouchMode(true);
                    cartViewModel.couponImplementation(CartPage.this,0);
                }

                else {
                    if (couponEt.getText().length() > 0) {

                        dialog = new ProgressDialog(CartPage.this);
                        dialog.setMessage("Updating...");
                        dialog.setIndeterminate(true);
                        dialog.show();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://accsectiondemo.site11.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CouponPlaceHolderAPI jsonPlaceHolderApi = retrofit.create(CouponPlaceHolderAPI.class);

                        Call<Coupon> call = jsonPlaceHolderApi.getPosts();


                        call.enqueue(new Callback<Coupon>() {
                            @Override
                            public void onResponse(Call<Coupon> call, Response<Coupon> response) {

                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }

                                if (!response.isSuccessful()) {
                                    Toast.makeText(CartPage.this, "Not responsed", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                Coupon coupon = response.body();

                                if (coupon != null) {

                                    if (couponCheck(coupon)) {
                                        editor = couponPrefs.edit();

                                        if (coupon.getProducts().size() > 0) {
                                            Set<String> ids = new HashSet<String>();
                                            for (int productId : coupon.getProducts()) {
                                                ids.add(productId + "");
                                            }

                                            editor.putBoolean("hasProduct", true);
                                            if (ids.size() > 0) {
                                                editor.putStringSet("productIDs", ids);
                                                editor.apply();
                                            }
                                        }

                                        applyCoupn(true, coupon.getCouponType(), coupon.getAmount());

                                    }

                                }

                            }

                            @Override
                            public void onFailure(Call<Coupon> call, Throwable t) {
                                couponEt.setHintTextColor(RED);
                                couponEt.setHint("Invalid Coupon! Try Another!");
                            }
                        });
                    } else {
                        couponEt.setHintTextColor(RED);
                        couponEt.setHint("Input Coupon Code First!");
                    }
                }


            }
        });


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                for (Cart cart: cartList) {
//
//                }


                Intent intent = new Intent(CartPage.this,CheckoutPage.class);
                startActivity(intent);
            }
        });
    }

    public void applyCoupn(Boolean bool, String type, String amount) {
        editor.putBoolean("couponApplied", true);
        editor.putString("type", type);
        editor.putFloat("amount", Float.parseFloat(amount));
        editor.apply();
        cartViewModel.couponImplementation(CartPage.this,0);
    }


    public boolean couponCheck(Coupon coupon) {

        Date currentTime = Calendar.getInstance().getTime();

        Date start = null, end = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            start = formatter.parse(coupon.getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            end = formatter.parse(coupon.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (coupon.getStatus().equals("active") && (currentTime.equals(start) || currentTime.equals(end)) || (currentTime.after(start) && currentTime.before(end))) {

            boolean couponHandle = true;

            if (coupon.getUsesLimit() != null) {
                if ((Integer.parseInt(coupon.getNumberOfUses())) >= Integer.parseInt(coupon.getUsesLimit().toString())) {
                    couponHandle = false;
                }
            }

            if (couponHandle) return true;
        }
        return false;

    }


    public void viewCart() {
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        cartViewModel.getAllCarts().observe(this, new Observer<List<Cart>>() {

            @Override
            public void onChanged(@Nullable List<Cart> notes) {
                cartList = notes;
                adapter.setCarts(notes);
                calculateBasePrice(notes);
            }
        });
    }


    public void calculateBasePrice(List<Cart> carts) {
        grandTotal = 0;
        boolean iscontain = false;
        float discountedProductPrice = 0;
        for (Cart cart : carts) {
            if (cart.isChecked()) {
                grandTotal = grandTotal + (cart.getProduct_price() * cart.getQunatity());
                if (couponPrefs.getBoolean("couponApplied", false) && couponPrefs.getBoolean("hasProduct", false)) {
                    if (couponPrefs.getStringSet("productIDs", null).contains(cart.getProId() + "")) {
                        iscontain = true;
                        discountedProductPrice = discountedProductPrice + (cart.getProduct_price() * cart.getQunatity());
                    }
                }
            }
        }

        editor = couponPrefs.edit();
        editor.putFloat("Order_Amount", grandTotal);
        editor.apply();

        if (iscontain) {
            grandTotal = grandTotal - discountedProductPrice + discount(discountedProductPrice);
            changeApperance();
        }
        else if(iscontain == false && couponPrefs.getBoolean("hasProduct", false)  && couponPrefs.getBoolean("couponApplied", false)) {
            Toast.makeText(this, "Coupon is not applicable with Cart items", Toast.LENGTH_LONG).show();
        }
        else if (iscontain==false && couponPrefs.getBoolean("couponApplied", false)) {
            grandTotal = discount(grandTotal);
            changeApperance();
        }
        setPrice(grandTotal);
    }


    public void changeApperance() {
        couponAply.setText("Change");
        couponEt.setText(couponPrefs.getString("Text", " "));
        couponEt.setBackgroundColor(TRANSPARENT);
        couponEt.setClickable(false);
        couponEt.setCursorVisible(false);
        couponEt.setFocusable(false);
        couponEt.setFocusableInTouchMode(false);
    }

    public float discount(float price) {
        float amount = 0;
        switch (couponPrefs.getString("type", "")) {
            case "fixed_amount":
                if (couponPrefs.getFloat("amount", 0) > price) {
                    amount = price;
                    editor = couponPrefs.edit();
                    editor.putString("Text", couponPrefs.getFloat("amount", 0) + "Tk Off!");
                    editor.putFloat("discAmount", amount);
                    editor.apply();
                }
                break;
            case "fixed_percentage":
                amount = price - (price * (couponPrefs.getFloat("amount", 0) / 100));
                editor = couponPrefs.edit();
                editor.putString("Text", (int) couponPrefs.getFloat("amount", 0) + " % Off!");
                editor.putFloat("discAmount", (price * (couponPrefs.getFloat("amount", 0) / 100)));
                editor.apply();
                break;

            case "free_shipping":
                editor.putBoolean("freeshipping", true);
                break;
        }
        return amount;
    }

    public void setPrice(float value) {
        priceTv.setText("Payable : ৳ " + String.format("%,.2f", value));
    }



    public void deleteItem(Cart cart) {
        cartViewModel.delete(cart);
    }

    public void updateCartItem(Cart cart, Context ctx) {
        cartViewModel.update(cart, ctx);
    }

}
