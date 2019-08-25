package com.example.singlepagerivew.Cart_CheckOut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.singlepagerivew.R;

import java.util.List;

public class CheckoutOrderItemAdapter extends RecyclerView.Adapter<CheckoutOrderItemAdapter.MyViewHolder> {

    private List<Cart> cartList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView prodImg;
        TextView productName,prodSku,productVariation,productQuant,productPrice;

        public MyViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.checkout_productName);
            productPrice = view.findViewById(R.id.checkout_productPrice);
            productVariation = view.findViewById(R.id.checkout_productVariation);
            productQuant = view.findViewById(R.id.checkout_prodtuct_quant);
            prodSku = view.findViewById(R.id.checkout_productSku);
            prodImg = view.findViewById(R.id.checkout_singleImg);
        }
    }


    public CheckoutOrderItemAdapter(List<Cart> cartList, Context mcontext) {
        this.cartList = cartList;
        this.context = mcontext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_out_single, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        Glide.with(context)
                .load(cart.getDisplayImg())
                .into(holder.prodImg);
        holder.productName.setText(cart.getProduct_name());
        holder.productPrice.setText("৳"+cart.getProduct_price()+"");
        holder.prodSku.setText("Sku : "+cart.getSku());
        if(cart.getVaiants()!=null && cart.getVaiants().length()>0) {
            holder.productVariation.setVisibility(View.VISIBLE);
            holder.productVariation.setText(cart.getVaiants());
        }
        holder.productQuant.setText("x"+cart.getQunatity());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}