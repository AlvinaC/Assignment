package com.assignment.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assignment.R;
import com.assignment.activities.Activity_Scenario2;
import com.assignment.adapters.SlidingMenuRecyclerViewAdapter;
import com.assignment.interfaces.ClickListener;
import com.assignment.models.SlidingItemMenu;
import com.assignment.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for Sliding Drawer
 */
public class SlidingMenuFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private String[] sliding_menu_items;
    private RecyclerView recyclerView;

    public SlidingMenuFragment() {
        // Empty constructor required

    }

    public static SlidingMenuFragment newInstance() {
        SlidingMenuFragment fragment = new SlidingMenuFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sliding_drawer, parent, false);
        init();
        setListeners();
        setUpRecyclerView();
        return rootView;
    }

    public void init() {
        sliding_menu_items = getResources().getStringArray(R.array.sliding_menu_items);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.menu_recycler_view);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void setListeners() {

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                view.setSelected(true);
                switch (position) {
                    case 0:
                        if (position == 0) {
                            Intent scenario = new Intent(getActivity(), Activity_Scenario2.class);
                            getActivity().startActivity(scenario);

                        }
                        break;

                }
            }

        }));
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SlidingMenuRecyclerViewAdapter menuAdapter = new SlidingMenuRecyclerViewAdapter(getData(), getContext(), 0);
        recyclerView.setAdapter(menuAdapter);
    }

    private List<SlidingItemMenu> getData() {
        List<SlidingItemMenu> menuList = new ArrayList<>();
        for (int i = 0; i < sliding_menu_items.length; i++) {
            menuList.add(new SlidingItemMenu(sliding_menu_items[i]));
        }
        return menuList;
    }

    @Override
    public void onClick(View view) {

    }


}
