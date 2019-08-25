package com.example.singlepagerivew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.singlepagerivew.R;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleHolder> {
    Object[] valueName;
    Object[] imageLink;
    Context mcontext;
    String mUrl;
    int mposition;

    OnOptionClickListener mlistener;

    int row_index = 0;

    public RecycleAdapter(Context context,String url,int pos,OnOptionClickListener listener) {
        this.mcontext = context;
        this.mUrl = url;
        this.mposition = pos;
        this.mlistener = listener;
    }

    public void setItems(Object[] items, Object[] links) {
        this.valueName = items;
        this.imageLink = links;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_single_img_layout, parent, false);
        return new RecycleHolder(itemView,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleHolder holder, final int position) {

        Glide.with(mcontext)
                .load(mUrl+imageLink[position].toString())
                .placeholder( R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.itemImage);
        holder.itemName.setText(valueName[position].toString());

        if(row_index==position) {
            holder.linearLayout.setBackgroundResource(R.drawable.dashedline_rectangle_full_with_radius);
        }

        else {
            holder.linearLayout.setBackgroundResource(R.drawable.grey_rectangle_full_with_radius);
        }

    }

    @Override
    public int getItemCount() {
        return imageLink.length;
    }


    class RecycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView itemImage;
        private TextView itemName;
        private FrameLayout linearLayout;
        private OnOptionClickListener mListener;


        public RecycleHolder(final View itemView,OnOptionClickListener listener) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImg);
            itemName = itemView.findViewById(R.id.itemName);
            linearLayout = itemView.findViewById(R.id.wholeLayout);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            row_index = getAdapterPosition();
            notifyDataSetChanged();
            mListener.onOptionClick(valueName[row_index].toString(),mposition);
        }
    }

    public interface OnOptionClickListener {
        void onOptionClick(String val,int postion);
    }

}