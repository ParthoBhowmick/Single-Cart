package com.example.singlepagerivew.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import com.example.singlepagerivew.Activites.SingleProductPage;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;


public class ViewPagerAdapter extends PagerAdapter {

    private String url;
    private Context context;
    private Object[] list;
    int type;
    FrameLayout fr;

    public ViewPagerAdapter(Context context, Object[] IMAGES, String url, int type) {
        this.context = context;
        this.list = IMAGES;
        this.url = url;
        this.type = type;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object instantiateItem(final ViewGroup view, final int position) {

        PhotoView img = new PhotoView(context);

        if(type==1) {

            Glide.with(context)
                    .load(url + list[position].toString())
                    .centerCrop()
                    .into(img);

        }

        else {
            Glide.with(context)
                    .load(url + list[position].toString())
                    .into(img);
        }

        view.addView(img);

        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<String> elements = new ArrayList<>();
                for(Object element : list) {
                    elements.add(element.toString());
                }
                ((SingleProductPage)context).ViewImage(url,elements,position);
            }
        });
        return img;
    }




    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
