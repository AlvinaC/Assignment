package com.assignment.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.assignment.fragments.PagerFragment;


/**
 * Summary :Adapter to populate the pager component
 */
public class ViewPagerAdapter extends GenericViewPagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String[] mResources;


    @Override
    public Fragment getItem(int pos) {
        switch (pos) {

            case 0:
                return PagerFragment.newInstance("1");
            case 1:
                return PagerFragment.newInstance("2");
            case 2:
                return PagerFragment.newInstance("3");
            case 3:
                return PagerFragment.newInstance("4");
        }
        return null;
    }

    public ViewPagerAdapter(Context context, String[] resources, FragmentManager fm) {
        super(context, fm);
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources = resources;
    }


    @Override
    public int getCount() {
        return mResources.length;
    }


    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
    }

}
