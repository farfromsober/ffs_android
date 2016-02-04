package com.farfromsober.ffs.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
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
import com.farfromsober.ffs.callbacks.FiltersFragmentListener;
import com.farfromsober.ffs.callbacks.OnMenuSelectedCallback;
import com.farfromsober.ffs.callbacks.OnOptionsFilterListener;
import com.farfromsober.ffs.callbacks.ProductDetailFragmentListener;
import com.farfromsober.ffs.callbacks.ProductsFragmentListener;
import com.farfromsober.ffs.fragments.CategoryFilterFragment;
import com.farfromsober.ffs.fragments.FullMapFragment;
import com.farfromsober.ffs.fragments.FullProductsFragment;
import com.farfromsober.ffs.fragments.NewProductFragment;
import com.farfromsober.ffs.fragments.NotificationsFragment;
import com.farfromsober.ffs.fragments.ProductDetailFragment;
import com.farfromsober.ffs.fragments.FullProfileFragment;
import com.farfromsober.ffs.model.DrawerMenuItem;
import com.farfromsober.ffs.model.LoginData;
import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.ProductImage;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.utils.SharedPreferencesManager;
import com.farfromsober.networkviews.NetworkPreloaderActivity;
import com.farfromsober.networkviews.callbacks.OnNetworkActivityCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends NetworkPreloaderActivity implements ProductsFragmentListener, OnMenuSelectedCallback, OnOptionsFilterListener,FiltersFragmentListener,ProductDetailFragmentListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final int PRODUCTS_FRAGMENT_INDEX = 0;
    public static final int MAP_FRAGMENT_INDEX = 1;
    public static final int NOTIFICATIONS_FRAGMENT_INDEX = 2;
    public static final int PROFILE_FRAGMENT_INDEX = 3;
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

    private Fragment mCurrentFragment;

    public WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginData data = SharedPreferencesManager.getPrefLoginUser(getApplicationContext());

        if(data == null) {
            this.showLoginScreen();
        }
        else {
            this.configureDrawer();
        }

        configureGoogleApiClient();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
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

    @Override
    public void onBackPressed() {
        hidePreloader();
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            if (mCurrentFragment.getClass().equals(FullProductsFragment.class)) {
                if (getFragmentManager().getBackStackEntryCount() > 1) {
                    goBackToProductDetail();
                    return;
                }
                goBackToProductList(null);
                return;
            }
            if (mCurrentFragment.getClass().equals(FullProfileFragment.class)) {
                goBackToProfile();
                return;
            }
        }
        super.onBackPressed();
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

        initializeDrawerHeader();
        initializeDrawerMenu();
        initializeDrawerLayout();
        initializeDrawerToggle();

        mOnNetworkActivityCallback = new WeakReference<>((OnNetworkActivityCallback)this);

        selectMenuItem(INITIAL_FRAGMENT_INDEX);
        loadInitialFragment(INITIAL_FRAGMENT_INDEX);
    }

    private void initializeDrawerHeader() {
        User user = SharedPreferencesManager.getPrefUserData(this);

        mDrawerUserName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        mDrawerUseLocation.setText(user.getCity());
        mDrawerUseNumberOfTransactions.setText(String.format(getResources().getString(R.string.drawer_number_of_transactions_format), (int) user.getSales()));

        if (user.getAvatarURL() != null && !user.getAvatarURL().equals("")) {
            Picasso.with(this)
                    .load(user.getAvatarURL())
                    .placeholder(R.drawable.mavatar_placeholder)
                    .resize(500, 500)
                    .centerCrop()
                    .into(mDrawerProfileImageView);
        }
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
        DrawerListAdapter drawerListAdapter = new DrawerListAdapter(this, menuItems);
        mDrawerMenuListView.setAdapter(drawerListAdapter);

        mDrawerMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadFragment(position);

                mDrawerLayout.closeDrawer(mScrimInsetsFrameLayout);
            }
        });
    }

    public void loadFragment(int position) {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            mCurrentFragment.setHasOptionsMenu(true);
        }
        Fragment fragment = getFragmentToNavigateTo(position);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        selectMenuItem(position);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(menuItems.get(position).getTitle());
        }
    }

    private Fragment getFragmentToNavigateTo(int position) {
        mCurrentFragment= null;

        switch (position) {
            case PRODUCTS_FRAGMENT_INDEX:
                mCurrentFragment = FullProductsFragment.newInstance();
                break;
            case MAP_FRAGMENT_INDEX:
                mCurrentFragment = FullMapFragment.newInstance();
                break;
            case NOTIFICATIONS_FRAGMENT_INDEX:
                mCurrentFragment = NotificationsFragment.newInstance();
                break;
            case PROFILE_FRAGMENT_INDEX:
                mCurrentFragment = FullProfileFragment.newInstance(SharedPreferencesManager.getPrefUserData(this));
                break;
        }
        return mCurrentFragment;
    }

    @SuppressWarnings("deprecation")
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

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(menuItems.get(position).getTitle());
        }
    }

    public void selectMenuItem(int position) {
        if (mDrawerMenuListView != null) {
            mDrawerMenuListView.setItemChecked(position, true);
            mDrawerMenuListView.setSelection(position);
        }
    }

    @Override
    public void onMenuSelected(int position) {
        loadFragment(position);
    }

    @Override
    public void onProductsFragmentAddProductClicked() {
        mCurrentFragment.setHasOptionsMenu(false);
        NewProductFragment fragment = NewProductFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
        fragmentTransaction.add(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack("NewProductFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onProductsFragmentNewProductImagesUploaded(ArrayList<ProductImage> productImages) {
        NewProductFragment newProductFragment = ((NewProductFragment) getFragmentManager().findFragmentByTag("newProductFragment"));
        newProductFragment.mProductImages = productImages;
        newProductFragment.uploadNewProduct();
    }

    @Override
    public void onProductsFragmentNewProductCreated() {
        goBackToProductList(null);
    }

    @Override
    public void onProductsFragmentProductClicked(Product product) {
        mCurrentFragment.setHasOptionsMenu(false);
        ProductDetailFragment fragment = ProductDetailFragment.newInstance(product);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_right);
        fragmentTransaction.add(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack("ProductDetailFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onFilterMenuSelected(HashMap<String,Integer> lastFilterSelectedItems) {

        // Create fragment and give it an argument for the selected article
        mCurrentFragment.setHasOptionsMenu(false);
        CategoryFilterFragment fragment = CategoryFilterFragment.newInstance(lastFilterSelectedItems);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_right);
        transaction.add(R.id.content_frame, fragment);
        transaction.addToBackStack("CategoryFilterFragment");
        transaction.commit();
    }

    @Override
    public void onProductsDetailFragmentPurchaseSucceed() {
        goBackToProductList(null);
        showInfoDialogFragment(R.string.transaction_succeed, R.string.enjoy_new_product, R.string.button_ok);
    }

    @Override
    public void onProductsDetailFragmentPurchaseFailed() {
        showInfoDialogFragment(R.string.transaction_failed, R.string.please_try_again, R.string.button_ok);
    }

    @Override
    public void onProductsDetailProfilePressed(User seller) {
        if (seller.getUserId().equals(SharedPreferencesManager.getPrefUserData(this).getUserId())) {
            loadFragment(PROFILE_FRAGMENT_INDEX);
            return;
        }
        FullProfileFragment fragment = FullProfileFragment.newInstance(seller);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
        fragmentTransaction.add(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack("FullProfileFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onProductFilter(String word){
        ((FullProductsFragment)mCurrentFragment).filterByWord(word);
    }

    @Override
    public void onProductFiltersSelected(HashMap<String, Integer> filterSelectedItems) {
        goBackToProductList(filterSelectedItems);
    }

    private void goBackToProductList(HashMap<String,Integer> filterSelectedItems) {
        getFragmentManager().popBackStack();
        mCurrentFragment.setHasOptionsMenu(true);
        ((FullProductsFragment) mCurrentFragment).reloadProductsList(filterSelectedItems, mLocation);
    }

    private void goBackToProductDetail() {
        getFragmentManager().popBackStack();
    }

    private void goBackToProfile() {
        getFragmentManager().popBackStack();
    }

    private void configureGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnectionSuspended(int i) {
        System.out.print("Pause");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.print("Error");
    }

    @Override
    public void onConnected(Bundle bundle) {

        android.location.Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            handleNewLocation(location);
        }
    }

    private void handleNewLocation(android.location.Location location) {

        mLocation = location;
    }

}