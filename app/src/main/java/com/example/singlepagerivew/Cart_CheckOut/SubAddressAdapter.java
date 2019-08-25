package com.example.singlepagerivew.Cart_CheckOut;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.singlepagerivew.R;

import java.util.List;

public class SubAddressAdapter extends RecyclerView.Adapter<SubAddressAdapter.MyViewHolder> {
    List<String> subAddressElementList;
    String check;
    OnSubAddressClickListener mListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView subAddressElement;
        OnSubAddressClickListener mlistener;
        RadioButton radioButton;

        public MyViewHolder(View view, OnSubAddressClickListener listener) {
            super(view);
            subAddressElement = view.findViewById(R.id.subAddressName);
            radioButton = view.findViewById(R.id.subAddressSelect);
            mlistener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mlistener.onAddressClick(getAdapterPosition());
        }
    }


    public SubAddressAdapter(List<String> moviesList, OnSubAddressClickListener listener, String checked) {
        this.subAddressElementList = moviesList;
        this.mListener = listener;
        this.check = checked;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subaddress_single_element, parent, false);

        return new MyViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.subAddressElement.setText(subAddressElementList.get(position));
        if(check.equals(holder.subAddressElement.getText())) {
            holder.radioButton.setVisibility(View.VISIBLE);
            holder.radioButton.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return subAddressElementList.size();
    }

    public interface OnSubAddressClickListener {
        void onAddressClick(int positon);
    }

}
