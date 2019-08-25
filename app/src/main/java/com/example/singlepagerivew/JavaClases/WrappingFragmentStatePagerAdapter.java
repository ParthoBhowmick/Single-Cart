package com.example.singlepagerivew.JavaClases;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public abstract class WrappingFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private int mCurrentPosition = -1;

    public WrappingFragmentStatePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        super.setPrimaryItem(viewGroup, i, obj);
        if (!(viewGroup instanceof WrappingViewPager)) {
            throw new UnsupportedOperationException("ViewPager is not a WrappingViewPager");
        } else if (i != this.mCurrentPosition) {
            Fragment fragment = (Fragment) obj;
            WrappingViewPager wrappingViewPager = (WrappingViewPager) viewGroup;
            if (fragment != null && fragment.getView() != null) {
                this.mCurrentPosition = i;
                wrappingViewPager.onPageChanged(fragment.getView());
            }
        }
    }
}
