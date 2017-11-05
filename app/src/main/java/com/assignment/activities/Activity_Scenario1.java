package com.assignment.activities;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assignment.BaseActivity;
import com.assignment.R;
import com.assignment.adapters.SlidingMenuRecyclerViewAdapter;
import com.assignment.adapters.ViewPagerAdapter;
import com.assignment.interfaces.ClickListener;
import com.assignment.models.SlidingItemMenu;
import com.assignment.util.Constants;
import com.assignment.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Summary: Scenario 1
 */
public class Activity_Scenario1 extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "Activity_Scenario1";

    private RecyclerView recycler_point_1;
    private String[] items_point_1, items_point_2;
    private ViewPager view_pager;
    private ViewPagerAdapter mAdapter;
    private LinearLayout pager_indicator;
    private TextView txt_fragment_clicked;
    private LinearLayout ll_buttons;
    private Button btn_red, btn_blue, btn_green;

    public enum Colors {
        RED,
        BLUE,
        GREEN,
        TRANSPARENT

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_1);
        setToolbarForSliderMenu(Constants.SCENARIO_1);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Portrait Mode
            setSlidingMenu(250);
        } else {
            // Landscape Mode
            setSlidingMenu(600);
        }

        init();
        loadData();
        setUpRecyclerPoint1();
        setListeners();
        setUpPagerPoint2();
    }

    private void loadData() {
        ll_buttons.setTag(Colors.TRANSPARENT);
    }

    private void init() {
        recycler_point_1 = (RecyclerView) findViewById(R.id.recycler_point_1);
        items_point_1 = getResources().getStringArray(R.array.items_point_1);
        items_point_2 = getResources().getStringArray(R.array.items_point_2);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        pager_indicator = (LinearLayout) findViewById(R.id.introViewPagerCountDots);
        txt_fragment_clicked = (TextView) findViewById(R.id.txt_fragment_clicked);
        ll_buttons = (LinearLayout) findViewById(R.id.ll_buttons);
        btn_blue = (Button) findViewById(R.id.btn_blue);
        btn_red = (Button) findViewById(R.id.btn_red);
        btn_green = (Button) findViewById(R.id.btn_green);
    }

    //region Point 1
    private void setUpRecyclerPoint1() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        SlidingMenuRecyclerViewAdapter menuAdapter = new SlidingMenuRecyclerViewAdapter(getData(), this, 1);
        recycler_point_1.setAdapter(menuAdapter);
        recycler_point_1.setLayoutManager(layoutManager);

    }

    private List<SlidingItemMenu> getData() {
        List<SlidingItemMenu> menuList = new ArrayList<>();
        for (int i = 0; i < items_point_1.length; i++) {
            menuList.add(new SlidingItemMenu(items_point_1[i]));
        }
        return menuList;
    }
    //endregion

    //region Point 2
    private void setUpPagerPoint2() {
        mAdapter = new ViewPagerAdapter(this, items_point_2, getSupportFragmentManager());
        view_pager.setAdapter(mAdapter);
        view_pager.setCurrentItem(0);
        view_pager.setOnPageChangeListener(mAdapter);
        mAdapter.setPageViewIndicator(view_pager, pager_indicator);
    }
    //endregion

    //region Point 3,Point 4
    public void setListeners() {

        recycler_point_1.addOnItemTouchListener(new RecyclerTouchListener(this, recycler_point_1, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                view.setSelected(true);
                txt_fragment_clicked.setText(((TextView) view.findViewById(R.id.row_title)).getText());
            }

        }));
        btn_blue.setOnClickListener(this);
        btn_red.setOnClickListener(this);
        btn_green.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_red:
                ll_buttons.setBackgroundColor(Color.RED);
                ll_buttons.setTag(Colors.RED);
                break;
            case R.id.btn_blue:
                ll_buttons.setBackgroundColor(Color.BLUE);
                ll_buttons.setTag(Colors.BLUE);
                break;
            case R.id.btn_green:
                ll_buttons.setBackgroundColor(Color.GREEN);
                ll_buttons.setTag(Colors.GREEN);
                break;
            default:
                ll_buttons.setBackgroundColor(Color.TRANSPARENT);
                ll_buttons.setTag(Colors.TRANSPARENT);
                break;
        }

    }
    //endregion

    //region config change handle
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("Point4", txt_fragment_clicked.getText().toString());
        outState.putSerializable("Point5", (Colors) ll_buttons.getTag());
        outState.putBoolean("DrawerState", isMenuShowing());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String fragment_clicked_text = savedInstanceState.getString("Point4");
        Boolean isDrawerOpen = savedInstanceState.getBoolean("DrawerState");
        Colors background_color_selected = (Colors) savedInstanceState.getSerializable("Point5");
        restoreData(fragment_clicked_text, background_color_selected, isDrawerOpen);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void restoreData(String fragment_clicked, Colors color, Boolean isDrawerOpen) {
        txt_fragment_clicked.setText(fragment_clicked);
        switch (color) {
            case RED:
                ll_buttons.setBackgroundColor(Color.RED);
                ll_buttons.setTag(Colors.RED);
                break;
            case BLUE:
                ll_buttons.setBackgroundColor(Color.BLUE);
                ll_buttons.setTag(Colors.BLUE);
                break;
            case GREEN:
                ll_buttons.setBackgroundColor(Color.GREEN);
                ll_buttons.setTag(Colors.GREEN);
                break;
            case TRANSPARENT:
                ll_buttons.setBackgroundColor(Color.TRANSPARENT);
                ll_buttons.setTag(Colors.TRANSPARENT);
                break;
        }
        if (isDrawerOpen)
            toggleMenu();
    }
    //endregion
}
