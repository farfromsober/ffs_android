package com.farfromsober.ffs.fragments;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.farfromsober.customviews.CustomFontTextView;
import com.farfromsober.ffs.R;
import com.farfromsober.ffs.adapters.ProductsAdapter;
import com.farfromsober.ffs.callbacks.OnOptionsFilterListener;
import com.farfromsober.ffs.callbacks.ProductsFragmentListener;
import com.farfromsober.ffs.callbacks.RecyclerViewClickListener;
import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.Products;
import com.farfromsober.ffs.network.APIManager;
import com.farfromsober.network.callbacks.OnDataParsedCallback;
import com.farfromsober.networkviews.callbacks.OnNetworkActivityCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FullProductsFragment extends Fragment implements OnDataParsedCallback<Product>, RecyclerViewClickListener {

    protected APIManager apiManager;
    private WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;
    public WeakReference<ProductsFragmentListener> mProductsListener;
    public WeakReference<OnOptionsFilterListener> mOptionslistener;

    HashMap<String,Integer> mFilterSelectedItems;

    //private RecyclerView mProductsList;
    @Bind(R.id.no_products_label) CustomFontTextView mNoProductsLabel;
    @Bind(R.id.products_list) RecyclerView mProductsList;
    @Bind(R.id.add_product_button) FloatingActionButton mAddProduct;
    @Bind(R.id.products_swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.searchGroup) RelativeLayout mSearchGroup;
    @Bind(R.id.searchView) SearchView mSearchView;
    @Bind(R.id.searchViewLabel) CustomFontTextView mSearchLabel;

    public FullProductsFragment() {
        // Required empty public constructor
    }

    public static FullProductsFragment newInstance() {
        FullProductsFragment fragment = new FullProductsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_full_products, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProductsList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mProductsList.setItemAnimator(new DefaultItemAnimator());
        setHasOptionsMenu(true);

        mAddProduct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mProductsListener != null && mProductsListener.get() != null) {
                    mProductsListener.get().onProductsFragmentAddProductClicked();
                }
            }
        });

        apiManager = new APIManager(getActivity());
        askServerForProducts();
        //mProductsList.swapAdapter(new ProductsAdapter(Products.getInstance(getActivity()).getProducts(), getActivity(), this), false);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.app_dark_orange);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                askServerForProducts();
            }
        });

        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mSearchView.setQuery("", false);
                filterByWord(mSearchView.getQuery().toString());
                return false;
            }
        });

        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mSearchView.setQuery("", false);
                    filterByWord(mSearchView.getQuery().toString());
                }
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterByWord(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && newText.length() != 0) {
                    mSearchLabel.setVisibility(View.INVISIBLE);
                    return false;
                }
                mSearchLabel.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        setCallbacks(context);
        super.onAttach(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        setCallbacks(activity);
        super.onAttach(activity);
    }

    private void setCallbacks(Context context) {
        try {
            mOnNetworkActivityCallback = new WeakReference<>((OnNetworkActivityCallback) getActivity());
        } catch (Exception e) {
            throw new ClassCastException(context.toString()+" must implement OnNetworkActivityCallback in Activity");
        }
        try {
            mProductsListener = new WeakReference<>((ProductsFragmentListener) getActivity());
        } catch (Exception e) {
            throw new ClassCastException(context.toString()+" must implement ProductsFragmentListener in Activity");
        }
        try {
            mOptionslistener = new WeakReference<>((OnOptionsFilterListener) getActivity());
        } catch (Exception e) {
            throw new ClassCastException(context.toString()+" must implement ProductsFragmentListener in Activity");
        }
    }

    public void reloadProductsList(HashMap<String,Integer> filterSelectedItems, Location location) {
        mFilterSelectedItems = filterSelectedItems;
        if (filterSelectedItems == null) {
            askServerForProducts();
        }else{
            filterBycategoryAndDistance(mFilterSelectedItems, location);
        }
    }

    public void askServerForProducts() {
        showPreloader(getActivity().getString(R.string.products_loading_message));
        apiManager.allProducts(this);
    }

    protected void showPreloader(String message) {
        if (mOnNetworkActivityCallback != null && mOnNetworkActivityCallback.get() != null) {
            mOnNetworkActivityCallback.get().onNetworkActivityStarted(message);
        }
    }

    private void hidePreloader() {
        Handler mainHandler = new Handler(getActivity().getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                if (mOnNetworkActivityCallback != null && mOnNetworkActivityCallback.get() != null) {
                    mOnNetworkActivityCallback.get().onNetworkActivityFinished();
                }
            }
        };
        mainHandler.post(myRunnable);
    }


    public void filterBycategoryAndDistance(HashMap<String,Integer> categories, Location location) {
        showPreloader(getActivity().getString(R.string.products_loading_message));
        apiManager.allProductsFilterByCategoriesAndDistance(categories, this, location);
    }

    public void filterByWord(String word) {
        showPreloader(getActivity().getString(R.string.products_loading_message));
        apiManager.allProductsFilterByWord(word, this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_products_list, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_filter:{
                if (mOptionslistener != null && mOptionslistener.get() != null) {
                    mOptionslistener.get().onFilterMenuSelected(mFilterSelectedItems);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDataArrayParsed(int responseCode, ArrayList<Product> data) {
        if (responseCode != HttpsURLConnection.HTTP_OK) {
            hidePreloader();
            return;
        }
        if (data != null) {
            Log.i("ffs", data.toString());

            Products products = Products.getInstance(getActivity());
            if (data.size() > 0) {
                mProductsList.setVisibility(View.VISIBLE);
                mNoProductsLabel.setVisibility(View.INVISIBLE);
                products.deleteProducts();
            }
            else {
                mProductsList.setVisibility(View.INVISIBLE);
                mNoProductsLabel.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < data.size(); i++) {
                Product product = data.get(i);
                // ONLY Products that have selling = true will be shown
                if (product.getIsSelling() == true) { products.addProduct(product);}
            }
            //Update Adapter
            mProductsList.swapAdapter(new ProductsAdapter(products.getProducts(), getActivity(), this), false);
            hidePreloader();
        }
    }

    @Override
    public void onDataObjectParsed(int responseCode, Product data) {
        hidePreloader();
    }

    @Override
    public void onExceptionReceived(Exception e) {
        if (mOnNetworkActivityCallback != null && mOnNetworkActivityCallback.get() != null) {
            mOnNetworkActivityCallback.get().onExceptionReceived(e);
        }
        hidePreloader();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Products products = Products.getInstance(getActivity());
        Product product = products.getProducts().get(position);
        if (mProductsListener != null && mProductsListener.get() != null) {
            mProductsListener.get().onProductsFragmentProductClicked(product);
        }
    }
}
