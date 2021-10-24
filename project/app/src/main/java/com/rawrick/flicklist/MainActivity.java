package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.tools.SettingsManager.getLoginStatus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.rawrick.flicklist.ui.home.HomeFragment;
import com.rawrick.flicklist.ui.movies.MoviesFragment;
import com.rawrick.flicklist.ui.profile.ProfileFragment;
import com.rawrick.flicklist.ui.series.SeriesFragment;
import com.rawrick.flicklist.ui.watchlist.WatchlistFragment;

public class MainActivity extends FragmentActivity {

    //private ActivityMainBinding binding;
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;
    private final ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                navView.setSelectedItemId(R.id.navigation_home);
            } else if (position == 1) {
                navView.setSelectedItemId(R.id.navigation_movies);
            } else if (position == 2) {
                navView.setSelectedItemId(R.id.navigation_series);
            } else if (position == 3) {
                navView.setSelectedItemId(R.id.navigation_watchlist);
            } else if (position == 4) {
                navView.setSelectedItemId(R.id.navigation_profile);
            }
            super.onPageSelected(position);
        }
    };
    private BottomNavigationView navView;
    private final NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.navigation_home) {
                viewPager.setCurrentItem(0, true);
            } else if (item.getItemId() == R.id.navigation_movies) {
                viewPager.setCurrentItem(1, true);
            } else if (item.getItemId() == R.id.navigation_series) {
                viewPager.setCurrentItem(2, true);
            } else if (item.getItemId() == R.id.navigation_watchlist) {
                viewPager.setCurrentItem(3, true);
            } else if (item.getItemId() == R.id.navigation_profile) {
                viewPager.setCurrentItem(4, true);
            }
            return true;
        }
    };
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeNavigation();
        // makes status bar fully transparent
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        //setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.unregisterOnPageChangeCallback(pageChangeCallback);
    }

    // sets up bottom nav bar + listener and viewpager listener
    private void initializeNavigation() {
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new MainActivity.ScreenSlidePagerAdapter(this);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pagerAdapter);
        navView = findViewById(R.id.bottom_nav);
        navView.setOnItemSelectedListener(navListener);
        viewPager.registerOnPageChangeCallback(pageChangeCallback);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            //viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            viewPager.setCurrentItem(0);
        }
    }

    /**
     * A simple pager adapter that represents 5 Fragment objects, in sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new MoviesFragment();
                case 2:
                    return new SeriesFragment();
                case 3:
                    return new WatchlistFragment();
                case 4:
                    return new ProfileFragment();
                default:
                    return new HomeFragment();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }



    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}