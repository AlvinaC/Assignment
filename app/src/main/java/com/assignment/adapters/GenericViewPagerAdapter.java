package com.assignment.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.assignment.R;


/**
 * Summary: Shows pager indicators
 */
public abstract class GenericViewPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private int dotsCount;
    private ImageView[] dots;
    private Context mContext;
    private int selectedposition;
    private ViewPager viewPager;
    private int mCurrentPosition;
    private int mScrollState;

    public GenericViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public GenericViewPagerAdapter(Context context, FragmentManager fm) {
        this(fm);
        this.mContext = context;
    }

    public void setPageViewIndicator(final ViewPager mViewPager, final LinearLayout pager_indicator) {
        viewPager = mViewPager;
        Log.d("###setPageViewIndicator", " : called");
        dotsCount = this.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(mContext.getResources().getDrawable(R.drawable.intro_nonselecteditem_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            final int presentPosition = i;
            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mViewPager.setCurrentItem(presentPosition);
                    return true;
                }

            });
            pager_indicator.addView(dots[i], params);
        }
        if (mContext != null) {

            if (dots.length > 0) {

                dots[0].setImageDrawable(mContext.getResources().getDrawable(R.drawable.intro_selecteditem_dot));

            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("###onPageSelected, pos ", String.valueOf(position));
        for (int i = 0; i < dotsCount; i++) {

            dots[i].setImageDrawable(mContext.getResources().getDrawable(R.drawable.intro_nonselecteditem_dot));

        }
        if (mContext != null) {

            dots[position].setImageDrawable(mContext.getResources().getDrawable(R.drawable.intro_selecteditem_dot));

        }
        if (position + 1 == dotsCount) {
        }
        selectedposition = position;
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        handleScrollState(state);
        mScrollState = state;
    }

    public int getposition() {
        return selectedposition;
    }


    private void handleScrollState(final int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            setNextItemIfNeeded();
        }
    }

    private void setNextItemIfNeeded() {
        if (!isScrollStateSettling()) {
            handleSetNextItem();
        }
    }

    private boolean isScrollStateSettling() {
        return mScrollState == ViewPager.SCROLL_STATE_SETTLING;
    }

    private void handleSetNextItem() {
        final int lastPosition = viewPager.getAdapter().getCount() - 1;
        if (mCurrentPosition == 0) {
            viewPager.setCurrentItem(lastPosition, false);
        } else if (mCurrentPosition == lastPosition) {
            viewPager.setCurrentItem(0, false);
        }
    }


}
