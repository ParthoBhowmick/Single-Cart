package com.example.singlepagerivew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.singlepagerivew.R;

public class RecycleAdapterNoImg extends RecyclerView.Adapter<RecycleAdapterNoImg.RecycleHolderNoImg> {
    Object[] valueName;
    Context mcontext;
    int mposition;

    int row_index = 0;

    OnOptionClickListenerNoImg mlistener;

    public RecycleAdapterNoImg(Context context,int postion,OnOptionClickListenerNoImg listenerNoImg) {
        this.mcontext = context;
        this.mposition = postion;
        this.mlistener = listenerNoImg;
    }

    public void setItems(Object[] items) {
        this.valueName = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecycleHolderNoImg onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_single_noimg_layout, parent, false);
        return new RecycleHolderNoImg(itemView,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleAdapterNoImg.RecycleHolderNoImg holder, int position) {


        holder.btn.setText(valueName[position].toString());

        if(row_index==position) {
            holder.btn.setBackgroundResource(R.drawable.a);
        }

        else {
            holder.btn.setBackgroundResource(R.drawable.b);
        }

    }

    @Override
    public int getItemCount() {
        return valueName.length;
    }


    class RecycleHolderNoImg extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView btn;
        private OnOptionClickListenerNoImg mListener;

        public RecycleHolderNoImg(final View itemView,OnOptionClickListenerNoImg listener) {
            super(itemView);
            btn = itemView.findViewById(R.id.optionVal);
            itemView.setOnClickListener(this);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            row_index = getAdapterPosition();
            notifyDataSetChanged();
            //Toast.makeText(mcontext, valueName[row_index].toString() + "\n" + mposition  , Toast.LENGTH_SHORT).show();
            mListener.onOptionClickNoImg(valueName[row_index].toString(),mposition);
        }
    }

    public interface OnOptionClickListenerNoImg {
        void onOptionClickNoImg(String val,int postion);
    }
}