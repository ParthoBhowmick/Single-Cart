package com.example.singlepagerivew.Cart_CheckOut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.singlepagerivew.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    List<Cart> items = new ArrayList<>();
    Context mcontext;

    public CartAdapter(Context context) {
        this.mcontext = context;
    }

    public void setCarts(List<Cart> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_single_element, parent, false);
        //cartViewModel = ViewModelProviders.of().get(CartViewModel.class);
        return new CartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder holder, int position) {

        final Cart currentitem = items.get(position);

        Glide.with(mcontext)
                .load(currentitem.getDisplayImg())
                .into(holder.cartImg);

        holder.productName.setText(currentitem.getProduct_name());
        holder.productPrice.setText("৳"+currentitem.getProduct_price()+"");
        holder.productSku.setText(currentitem.getSku());
        holder.subtotalTv.setText("Subtotal : ৳ " + String.format("%,.2f", currentitem.getQunatity()*currentitem.getProduct_price()));


        holder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.select.isPressed()) {
                    if (isChecked) {
                        currentitem.setChecked(true);
                        ((CartPage) mcontext).updateCartItem(currentitem, mcontext);
                    } else {
                        currentitem.setChecked(false);
                        ((CartPage) mcontext).updateCartItem(currentitem, mcontext);
                    }
                }
            }
        });


        if(currentitem.getVaiants()!=null && currentitem.getVaiants().length()>0) {
            holder.productVariation.setVisibility(View.VISIBLE);
            holder.productVariation.setText(currentitem.getVaiants());
        }

        holder.qtyEt.setText(currentitem.getQunatity()+"");
        holder.stockCount.setText(currentitem.getStock() + " products available");

        holder.select.setChecked(currentitem.isChecked());

        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = holder.qtyEt.getText().toString();
                int total = Integer.parseInt(val);
                total = total + 1;
                if(total>currentitem.getStock()) {
                    total = currentitem.getStock();
                    Toast.makeText(mcontext, "Not more than "+ currentitem.getStock() + " Qty is allowed", Toast.LENGTH_LONG).show();
                }
                currentitem.setQunatity(total);
                ((CartPage)mcontext).updateCartItem(currentitem,mcontext);
                holder.subtotalTv.setText("Subtotal : ৳ " + String.format("%,.2f", Integer.parseInt(val)*currentitem.getProduct_price()));
                holder.qtyEt.setText(total+"");

            }
        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = holder.qtyEt.getText().toString();
                int total = Integer.parseInt(val);
                total = total - 1;
                if(total<1) {
                    total = 1;
                    Toast.makeText(mcontext, "Less than 1 Qty is not allowed", Toast.LENGTH_LONG).show();
                }
                holder.qtyEt.setText(total+"");
                currentitem.setQunatity(total);
                ((CartPage)mcontext).updateCartItem(currentitem,mcontext);
                holder.subtotalTv.setText("Subtotal : ৳ " + String.format("%,.2f", Integer.parseInt(val)*currentitem.getProduct_price()));

            }
        });


        holder.qtyEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialogBuilder = new AlertDialog.Builder(mcontext).create();

                View dialogView =  LayoutInflater.from(mcontext)
                        .inflate(R.layout.custom_dialog, null);

                final TextInputEditText textInputet = dialogView.findViewById(R.id.edt_quantity);
                Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String val = textInputet.getText().toString();
                        if(val.length()>0) {
                            int total = Integer.parseInt(val);
                            if(total<1) {
                                Toast.makeText(mcontext, "0 Qty is not allowed", Toast.LENGTH_LONG).show();
                            }
                            else if(total>currentitem.getStock()) {
                                Toast.makeText(mcontext, "Not more than "+ currentitem.getStock() + " Qty is allowed", Toast.LENGTH_LONG).show();
                            }
                            else {
                                holder.qtyEt.setText(val);
                                currentitem.setQunatity(total);
                                ((CartPage)mcontext).updateCartItem(currentitem,mcontext);
                                holder.subtotalTv.setText("Subtotal : ৳ " + String.format("%,.2f", Integer.parseInt(val)*currentitem.getProduct_price()));
                            }
                        }

                        else {

                        }
                        dialogBuilder.dismiss();
                    }
                });

                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
            }
        });

        holder.dltFrmCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CartPage)mcontext).deleteItem(currentitem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class CartHolder extends RecyclerView.ViewHolder {
        private ImageView cartImg,dltFrmCart;
        private CheckBox select;
        private TextView productName,productVariation,productSku,productPrice,stockCount,subtotalTv;
        private Button plusBtn,minusBtn;
        private EditText qtyEt;


        public CartHolder(final View itemView) {
            super(itemView);
            cartImg = itemView.findViewById(R.id.cart_singleImg);
            dltFrmCart = itemView.findViewById(R.id.deletFromCartImg);
            select = itemView.findViewById(R.id.select);
            productName = itemView.findViewById(R.id.productName);
            productVariation = itemView.findViewById(R.id.variations);
            productSku = itemView.findViewById(R.id.productSku);
            productPrice = itemView.findViewById(R.id.productPrice);
            stockCount = itemView.findViewById(R.id.stockAvail);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            qtyEt = itemView.findViewById(R.id.qtyEditext);
            subtotalTv = itemView.findViewById(R.id.subtotalTv);

//            select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if(itemView.isPressed()) {
//                        if (isChecked) {
//                            items.get(getAdapterPosition()).setChecked(true);
//                            ((CartPage)mcontext).updateCartItem(items.get(getAdapterPosition()),mcontext);
//                        } else if(!isChecked) {
//                            items.get(getAdapterPosition()).setChecked(false);
//                            ((CartPage)mcontext).updateCartItem(items.get(getAdapterPosition()),mcontext);
//                        }
//                    }
//
//
//                }
//            });

        }
    }
}