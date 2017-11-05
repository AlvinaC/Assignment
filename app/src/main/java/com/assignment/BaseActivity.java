package com.assignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.assignment.fragments.SlidingMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class BaseActivity extends AppCompatActivity {
    private SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Chnage the toolbar navigation icon to drawer navigator
     *
     * @param toolbar_title
     */
    public void setToolbarForSliderMenu(String toolbar_title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ham_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setToolbarTitle(toolbar, toolbar_title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });
    }

    public void toggleMenu() {
        menu.toggle();
    }

    /**
     * Changes the toolbar title for respective activity
     *
     * @param toolbar
     * @param toolbar_title
     */
    public void setToolbarTitle(Toolbar toolbar, String toolbar_title) {
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar_title);
    }

    /**
     * Adds Drawer for the respective activity
     */
    public void setSlidingMenu(int behind_offset) {

        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidth(15);
        menu.setShadowDrawable(R.drawable.drawer_drop_shadow);
        menu.setBehindOffset(behind_offset);
        menu.setFadeDegree(0.35f);
        menu.setMode(SlidingMenu.LEFT); // Use SlidingMenu.RIGHT to start the menu from right
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.fragment_sliding_drawer);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_frame, SlidingMenuFragment.newInstance())
                .commit();
    }

    /**
     * Change the toolbar to have the default back navigation
     *
     * @param toolbar_title
     */
    public void setToolbarWithBackNavigation(String toolbar_title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setToolbarTitle(toolbar, toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Back pressed on Actionbar back button
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isMenuShowing() {
        return menu.isMenuShowing();
    }

}
