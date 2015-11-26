package com.farfromsober.ffs.activities;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.farfromsober.customviews.CustomFontTextView;
import com.farfromsober.ffs.R;
import com.farfromsober.ffs.adapters.DrawerListAdapter;
import com.farfromsober.ffs.fragments.MapFragment;
import com.farfromsober.ffs.fragments.NotificationsFragment;
import com.farfromsober.ffs.fragments.ProductsFragment;
import com.farfromsober.ffs.fragments.ProfileFragment;
import com.farfromsober.ffs.model.DrawerMenuItem;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.utils.SharedPreferencesManager;
import com.farfromsober.networkviews.NetworkPreloaderActivity;
import com.farfromsober.networkviews.callbacks.OnNetworkActivity;
import com.farfromsober.generalutils.SharedPreferencesGeneralManager;
import com.farfromsober.network.NetworkPreloaderActivity;
import com.farfromsober.network.callbacks.OnNetworkActivityCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends NetworkPreloaderActivity {

    private static final int PRODUCTS_FRAGMENT_INDEX = 0;
    private static final int MAP_FRAGMENT_INDEX = 1;
    private static final int NOTIFICATIONS_FRAGMENT_INDEX = 2;
    private static final int PROFILE_FRAGMENT_INDEX = 3;
    private static final int INITIAL_FRAGMENT_INDEX = PRODUCTS_FRAGMENT_INDEX;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.scrimInsetsFrameLayout) ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_drawer_menu_list) ListView mDrawerMenuListView;

    @Bind(R.id.drawer_profile_image) CircleImageView mDrawerProfileImageView;
    @Bind(R.id.drawer_user_name) CustomFontTextView mDrawerUserName;
    @Bind(R.id.drawer_user_location) CustomFontTextView mDrawerUseLocation;
    @Bind(R.id.drawer_user_number_of_transactions) CustomFontTextView mDrawerUseNumberOfTransactions;

    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<DrawerMenuItem> menuItems;
    private DrawerListAdapter mDrawerListAdapter;

    public WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SharedPreferencesManager.removePrefLoginUser(getApplicationContext());
        String userJson = SharedPreferencesManager.getPrefLoginUser(getApplicationContext());
        if(userJson == "")
            this.showLoginScreen();
        else {
            User user = (User) SharedPreferencesGeneralManager.JSONStringToObject(userJson, User.class);
            this.configureDrawer();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void showLoginScreen() {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
        this.finish();
    }

    private void configureDrawer() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        initializeDrawerHeader();
        initializeDrawerMenu();
        initializeDrawerLayout();
        initializeDrawerToggle();

        mOnNetworkActivityCallback = new WeakReference<>((OnNetworkActivityCallback)this);

        selectMenuItem(INITIAL_FRAGMENT_INDEX);
        loadInitialFragment(INITIAL_FRAGMENT_INDEX);
    }


    private void initializeDrawerHeader() {
        //TODO: set User Data
        mDrawerUserName.setText("Javier Alzueta");
        mDrawerUseLocation.setText("Pamplona");
        mDrawerUseNumberOfTransactions.setText(String.format(getResources().getString(R.string.drawer_number_of_transactions_format), "124", "14"));
        mDrawerUseLocation.setText("Pamplona");
    }

    private void initializeDrawerMenu() {
        String[] menuTitles = getResources().getStringArray(R.array.DrawerMenuTitles);
        String[] menuIconsOff = getResources().getStringArray(R.array.DrawerMenuIconsOff);
        String[] menuIconsOn = getResources().getStringArray(R.array.DrawerMenuIconsOn);
        menuItems = new ArrayList<>();
        for (int i=0; i<menuTitles.length; i++) {
            menuItems.add(new DrawerMenuItem(menuTitles[i],
                    getResources().getIdentifier(menuIconsOff[i], "drawable", getPackageName()),
                    getResources().getIdentifier(menuIconsOn[i], "drawable", getPackageName())
            ));
        }
        mDrawerListAdapter = new DrawerListAdapter(this, menuItems);
        mDrawerMenuListView.setAdapter(mDrawerListAdapter);

        mDrawerMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = getFragmentToNavigateTo(position);

                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                selectMenuItem(position);

                getSupportActionBar().setTitle(menuItems.get(position).getTitle());

                mDrawerLayout.closeDrawer(mScrimInsetsFrameLayout);
            }
        });
    }

    @Nullable
    private Fragment getFragmentToNavigateTo(int position) {
        Fragment fragment = null;

        switch (position) {
            case PRODUCTS_FRAGMENT_INDEX:
                fragment = new ProductsFragment();
                break;
            case MAP_FRAGMENT_INDEX:
                fragment = new MapFragment();
                break;
            case NOTIFICATIONS_FRAGMENT_INDEX:
                fragment = new NotificationsFragment();
                break;
            case PROFILE_FRAGMENT_INDEX:
                fragment = new ProfileFragment();
                break;
        }
        return fragment;
    }

    private void initializeDrawerLayout() {
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.color_primary_dark));
    }

    private void initializeDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    private void loadInitialFragment(int position) {

        Fragment fragment = getFragmentToNavigateTo(position);

        getFragmentManager().beginTransaction()
                .add(R.id.content_frame, fragment)
                .commit();

        getSupportActionBar().setTitle(menuItems.get(position).getTitle());
    }

    public void selectMenuItem(int position) {
        if (mDrawerMenuListView != null) {
            mDrawerMenuListView.setItemChecked(position, true);
            mDrawerMenuListView.setSelection(position);
        }
    }

}