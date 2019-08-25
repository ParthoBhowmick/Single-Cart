package com.example.singlepagerivew.Cart_CheckOut;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.singlepagerivew.R;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {
    private List<ShippingAddress> items = new ArrayList<>();
    private Context mcontext;
    OnAddressClickListener mListener;


    public AddressAdapter(Context context,OnAddressClickListener listener) {
        this.mcontext = context;
        this.mListener = listener;
    }

    public void setAddressList(List<ShippingAddress> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_item, parent, false);
        //cartViewModel = ViewModelProviders.of().get(CartViewModel.class);

        return new AddressHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddressHolder holder, int position) {
        final ShippingAddress currentitem = items.get(position);

        holder.userName.setText(currentitem.getUserName());
        holder.userContact.setText(currentitem.getUserContact());


        if(currentitem.getUserEmail().length()>0) {
            holder.userEmail.setText(currentitem.getUserEmail());
        }
        else {
            holder.userEmail.setVisibility(View.GONE);
        }

        holder.userAddress.setText(currentitem.getUserAdress());
        holder.userDist.setText(currentitem.getUserDist());
        holder.userPlace.setText(currentitem.getUserPlace());
        holder.userDiv.setText(currentitem.getUserDivi());

        holder.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ManageAddressLists)mcontext).changeFragment(true,currentitem);
            }
        });

        holder.dltAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ManageAddressLists)mcontext).deleteAddress(currentitem);
            }
        });

        holder.shippingID.setText("User Address #" + (position+1) );

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class AddressHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName,userEmail,userContact,userDist,userPlace,userAddress,userDiv,shippingID;
        ImageView editAddress,dltAddress;
        OnAddressClickListener mlistener;

        public AddressHolder(View itemView, OnAddressClickListener listener) {
            super(itemView);
            userName = itemView.findViewById(R.id.userNameLI);
            userContact = itemView.findViewById(R.id.userContactLI);
            userEmail = itemView.findViewById(R.id.userEmailLI);
            userDiv = itemView.findViewById(R.id.userDivLI);
            userDist = itemView.findViewById(R.id.userDistLI);
            userPlace = itemView.findViewById(R.id.userPlaceLI);
            userAddress = itemView.findViewById(R.id.userAddressLI);
            editAddress = itemView.findViewById(R.id.editAddress);
            dltAddress = itemView.findViewById(R.id.dltAddress);
            shippingID = itemView.findViewById(R.id.shippingAddressID);
            mlistener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SharedPreferences addressPrefs = mcontext.getSharedPreferences("AddressPrefs", mcontext.MODE_PRIVATE);
            SharedPreferences.Editor addressEditor = addressPrefs.edit();
            if(addressPrefs.getInt("Case", 0)==1) {
                addressEditor.putString("userName", items.get(getAdapterPosition()).getUserName());
                addressEditor.putString("userContact", items.get(getAdapterPosition()).getUserContact());
                addressEditor.putString("userEmail", items.get(getAdapterPosition()).getUserEmail());
                addressEditor.putString("userDiv", items.get(getAdapterPosition()).getUserDivi());
                addressEditor.putString("userDist", items.get(getAdapterPosition()).getUserDist());
                addressEditor.putString("userPlace", items.get(getAdapterPosition()).getUserPlace());
                addressEditor.putString("userAddress", items.get(getAdapterPosition()).getUserAdress());
                addressEditor.apply();
            }

            else if(addressPrefs.getInt("Case", 0)==2) {

                if(items.get(getAdapterPosition()).getUserEmail().length()>0) {
                    if(items.get(getAdapterPosition()).getUserName().equals(addressPrefs.getString("userName", null))
                    && items.get(getAdapterPosition()).getUserContact().equals(addressPrefs.getString("userContact", null))
                    && items.get(getAdapterPosition()).getUserEmail().equals(addressPrefs.getString("userEmail", null))
                    && items.get(getAdapterPosition()).getUserDivi().equals(addressPrefs.getString("userDiv", null))
                    && items.get(getAdapterPosition()).getUserDist().equals(addressPrefs.getString("userDist", null))
                    && items.get(getAdapterPosition()).getUserPlace().equals(addressPrefs.getString("userPlace", null))
                    && items.get(getAdapterPosition()).getUserAdress().equals(addressPrefs.getString("userAddress", null))) {
                        Toast.makeText(mcontext, "Please Select Another than Shipping Address!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        addressEditor.putString("userNameBA", items.get(getAdapterPosition()).getUserName());
                        addressEditor.putString("userContactBA", items.get(getAdapterPosition()).getUserContact());
                        addressEditor.putString("userEmailBA", items.get(getAdapterPosition()).getUserEmail());
                        addressEditor.putString("userDivBA", items.get(getAdapterPosition()).getUserDivi());
                        addressEditor.putString("userDistBA", items.get(getAdapterPosition()).getUserDist());
                        addressEditor.putString("userPlaceBA", items.get(getAdapterPosition()).getUserPlace());
                        addressEditor.putString("userAddressBA", items.get(getAdapterPosition()).getUserAdress());
                        addressEditor.apply();
                    }
                }
                else {
                    if(items.get(getAdapterPosition()).getUserName().equals(addressPrefs.getString("userName", null))
                            && items.get(getAdapterPosition()).getUserContact().equals(addressPrefs.getString("userContact", null))
                            && items.get(getAdapterPosition()).getUserDivi().equals(addressPrefs.getString("userDiv", null))
                            && items.get(getAdapterPosition()).getUserDist().equals(addressPrefs.getString("userDist", null))
                            && items.get(getAdapterPosition()).getUserPlace().equals(addressPrefs.getString("userPlace", null))
                            && items.get(getAdapterPosition()).getUserAdress().equals(addressPrefs.getString("userAddress", null))) {
                        Toast.makeText(mcontext, "Please Select Another \n than Shipping Address!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        addressEditor.putString("userNameBA", items.get(getAdapterPosition()).getUserName());
                        addressEditor.putString("userContactBA", items.get(getAdapterPosition()).getUserContact());
                        addressEditor.putString("userDivBA", items.get(getAdapterPosition()).getUserDivi());
                        addressEditor.putString("userDistBA", items.get(getAdapterPosition()).getUserDist());
                        addressEditor.putString("userPlaceBA", items.get(getAdapterPosition()).getUserPlace());
                        addressEditor.putString("userAddressBA", items.get(getAdapterPosition()).getUserAdress());
                        addressEditor.apply();
                    }
                }


            }

            mlistener.onMainAddressClick();
        }

    }

    public interface OnAddressClickListener {
        void onMainAddressClick();
    }
}