package com.example.singlepagerivew.Cart_CheckOut;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.singlepagerivew.R;

import java.util.List;

public class CheckoutPage extends AppCompatActivity {

    private Button viewAddressBtn;
    private LinearLayout viewShipping,viewBilling,discountLayout;
    private TextView tv, userNameSA,userContactSA,userEmailSA,userDistSA,userPlaceSA,userAdress,userDivSA,grandTotal,
            userNameBA,userContactBA,userEmailBA,userAddressBA,userDivBA,userPlaceBA,userDistBA,orderTotal,shipment,discountAmount,totalamount;
    private ImageView addressBtnCH,billingAddressCH;
    private CheckBox hideBilling;
    private RelativeLayout changeBilling;
    private RadioGroup rgPayment;
    private CartViewModel cartViewModel;
    private RecyclerView checkoutRecycler;

    private SharedPreferences addressPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_page);
        initialization();
        addressPrefs = getSharedPreferences("AddressPrefs",MODE_PRIVATE);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        cartViewModel.couponImplementation(this,1);
        viewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = addressPrefs.edit();
                editor.putInt("Case", 1);
                editor.apply();
                Intent intent = new Intent(CheckoutPage.this, ManageAddressLists.class);
                startActivity(intent);
            }
        });

        addressBtnCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = addressPrefs.edit();
                editor.putInt("Case", 1);
                editor.apply();
                Intent intent = new Intent(CheckoutPage.this, ManageAddressLists.class);
                startActivity(intent);
            }
        });

        billingAddressCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = addressPrefs.edit();
                editor.putInt("Case", 2);
                editor.apply();
                Intent intent = new Intent(CheckoutPage.this, ManageAddressLists.class);
                startActivity(intent);
            }
        });


        if (!addressPrefs.getString("userAddress","null").equals("null")) {
            viewAddressBtn.setVisibility(View.GONE);
            viewShipping.setVisibility(View.VISIBLE);
            userNameSA.setText(addressPrefs.getString("userName","null"));
            userContactSA.setText(addressPrefs.getString("userContact","null"));
            if(addressPrefs.getString("userEmail","null").length()<=4) {
                userEmailSA.setVisibility(View.GONE);
            }
            else {
                userEmailSA.setText(addressPrefs.getString("userEmail","null"));
            }
            userDivSA.setText(addressPrefs.getString("userDiv","null"));
            userDistSA.setText(addressPrefs.getString("userDist","null"));
            userPlaceSA.setText(addressPrefs.getString("userPlace","null"));
            userAdress.setText(addressPrefs.getString("userAddress","null"));
            changeBilling.setVisibility(View.VISIBLE);
            hideBilling.setVisibility(View.VISIBLE);
        }

        if(!addressPrefs.getString("userAddressBA","null").equals("null")) {
            viewBilling.setVisibility(View.VISIBLE);
            hideBilling.setVisibility(View.VISIBLE);
            hideBilling.setChecked(false);
            userNameBA.setText(addressPrefs.getString("userNameBA","null"));
            userContactBA.setText(addressPrefs.getString("userContactBA","null"));
            if(addressPrefs.getString("userEmailBA","null").length()<=4) {
                userEmailBA.setVisibility(View.GONE);
            }
            else {
                userEmailBA.setText(addressPrefs.getString("userEmailBA","null"));
            }
            userDivBA.setText(addressPrefs.getString("userDivBA","null"));
            userDistBA.setText(addressPrefs.getString("userDistBA","null"));
            userPlaceBA.setText(addressPrefs.getString("userPlaceBA","null"));
            userAddressBA.setText(addressPrefs.getString("userAddressBA","null"));
        }

        hideBilling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    deleteBillingInformaiton();
                }
                else {
                    SharedPreferences.Editor editor = addressPrefs.edit();
                    editor.putInt("Case", 2);
                    editor.apply();
                    Intent intent = new Intent(CheckoutPage.this, ManageAddressLists.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void deleteBillingInformaiton() {
        addressPrefs.edit().remove("userNameBA").apply();
        addressPrefs.edit().remove("userContactBA").apply();
        addressPrefs.edit().remove("userEmailBA").apply();
        addressPrefs.edit().remove("userDivBA").apply();
        addressPrefs.edit().remove("userDistBA").apply();
        addressPrefs.edit().remove("userPlaceBA").apply();
        addressPrefs.edit().remove("userAddressBA").apply();
        viewBilling.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (addressPrefs.getString("userAddress","null").equals("null")) {
            changeBilling.setVisibility(View.GONE);
            hideBilling.setVisibility(View.GONE);
            viewAddressBtn.setVisibility(View.VISIBLE);
            viewShipping.setVisibility(View.GONE);
            viewBilling.setVisibility(View.GONE);
            deleteBillingInformaiton();
        }

        if (!addressPrefs.getString("userAddress","null").equals("null") && addressPrefs.getString("userAddressBA","null").equals("null")) {
            hideBilling.setChecked(true);
            viewBilling.setVisibility(View.GONE);
        }
    }


    public void viewOrderSummary(List<Cart> cartList) {
        CheckoutOrderItemAdapter checkoutOrderItemAdapter = new CheckoutOrderItemAdapter(cartList,CheckoutPage.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        checkoutRecycler.setLayoutManager(mLayoutManager);
        checkoutRecycler.setItemAnimator(new DefaultItemAnimator());
        checkoutRecycler.setHasFixedSize(true);
        checkoutRecycler.setAdapter(checkoutOrderItemAdapter);
        checkoutOrderItemAdapter.notifyDataSetChanged();
        SharedPreferences couponPrefs = getSharedPreferences("CoupunPrefs", (CheckoutPage.this).MODE_PRIVATE);
        orderTotal.setText("৳ " + String.format("%,.2f", couponPrefs.getFloat("Order_Amount",0)));
        if (couponPrefs.getFloat("amount", 0) == 0.0) {
            discountLayout.setVisibility(View.GONE);
        }

        else {
            discountAmount.setText("- ৳ " + String.format("%,.2f", couponPrefs.getFloat("discAmount",0)));
        }

        shipment.setText("৳ 60");

        totalamount.setText("৳ " + String.format("%,.2f", (couponPrefs.getFloat("Order_Amount",0)  -
                couponPrefs.getFloat("discAmount",0) + 60 )));

        grandTotal.setText("Total : ৳ " + String.format("%,.2f", (couponPrefs.getFloat("Order_Amount",0)  -
                couponPrefs.getFloat("discAmount",0) + 60 )));
    }

    public void initialization() {
        tv = findViewById(R.id.toolbarHeader);
        tv.setText("Check Out");
        userNameSA = findViewById(R.id.userNameSA);
        userContactSA = findViewById(R.id.userContactSA);
        userDistSA = findViewById(R.id.userDistSA);
        userPlaceSA = findViewById(R.id.userPlaceSA);
        userAdress = findViewById(R.id.userAddress);
        hideBilling = findViewById(R.id.hideBilling);
        userNameBA = findViewById(R.id.userNameBA);
        userContactBA = findViewById(R.id.userContactBA);
        userAddressBA = findViewById(R.id.userAddressBA);
        userPlaceBA = findViewById(R.id.userPlaceBA);
        userDistBA = findViewById(R.id.userDistBA);
        orderTotal = findViewById(R.id.orderAmount);
        shipment = findViewById(R.id.shipmentAmount);
        discountAmount = findViewById(R.id.discountAmount);
        totalamount = findViewById(R.id.totalAmount);
        checkoutRecycler = findViewById(R.id.checkOutRecyler);
        discountLayout = findViewById(R.id.discountLayout);
        viewAddressBtn = findViewById(R.id.addAddressBtnCH);
        grandTotal = findViewById(R.id.TotalPrice);
        viewShipping=findViewById(R.id.ViewShipping);
        userEmailSA = findViewById(R.id.userEmailSA);
        userDivSA = findViewById(R.id.userDivSA);
        addressBtnCH = findViewById(R.id.changeShippingBtn);
        changeBilling = findViewById(R.id.ChangeBilling);
        billingAddressCH = findViewById(R.id.ChangeBillingBtn);
        viewBilling = findViewById(R.id.ViewBilling);
        userEmailBA = findViewById(R.id.userEmailBA);
        userDivBA = findViewById(R.id.userDivBA);
    }
}
