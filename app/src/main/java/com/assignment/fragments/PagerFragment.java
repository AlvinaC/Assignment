package com.assignment.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignment.R;

/**
 * Fragment for Pager
 */

public class PagerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.scenario1_point_1_item, container, false);
        TextView tv = (TextView) v.findViewById(R.id.row_title);
        tv.setText(getArguments().getString("msg"));
        return v;
    }

    public static PagerFragment newInstance(String text) {
        PagerFragment f = new PagerFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }
}