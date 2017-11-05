package com.assignment.custom_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.assignment.R;


/**
 * Custom ProgressBar
 */
public class CustomProgressBar extends ProgressBar {


    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        String colorName = a.getString(R.styleable.CustomProgressBar_colorProgress);
        this.setIndeterminate(true);
        Drawable drawable = this.getIndeterminateDrawable().mutate();
        if (colorName == null || colorName.isEmpty()) {
            drawable.setColorFilter(getResources().getColor(R.color.intro_page_indicator_dark), PorterDuff.Mode.SRC_IN);
        }
        else
            drawable.setColorFilter(Color.parseColor(colorName), PorterDuff.Mode.SRC_IN);
        this.setIndeterminateDrawable(drawable);
        a.recycle();
    }


}
