package com.farfromsober.ffs.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.adapters.ProductsAdapter;
import com.farfromsober.ffs.callbacks.FiltersFragmentListener;
import com.farfromsober.ffs.callbacks.ProductsFragmentListener;
import com.farfromsober.ffs.callbacks.RecyclerViewClickListener;
import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.Products;
import com.farfromsober.ffs.network.APIManager;
import com.farfromsober.network.callbacks.OnDataParsedCallback;
import com.farfromsober.networkviews.callbacks.OnNetworkActivityCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements OnDataParsedCallback<Product>, RecyclerViewClickListener {

    private APIManager apiManager;
    private WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;
    public ProductsFragmentListener mListener;

    //private RecyclerView mProductsList;
    @Bind(R.id.products_list) RecyclerView mProductsList;
    @Bind(R.id.add_product_button) FloatingActionButton mAddProduct;


    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_products, container, false);
        ButterKnife.bind(this, root);

        mProductsList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mProductsList.setItemAnimator(new DefaultItemAnimator());
        setHasOptionsMenu(true);

        mAddProduct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onProductsFragmentAddProductClicked();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiManager = new APIManager(getActivity());
        askServerForProducts();
        //mProductsList.swapAdapter(new ProductsAdapter(Products.getInstance(getActivity()).getProducts(), getActivity(), this), false);
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
    }

    private void askServerForProducts() {
        showPreloader(getActivity().getString(R.string.products_loading_message));
        apiManager.allProducts(this);
    }

    private void showPreloader(String message) {
        if (mOnNetworkActivityCallback != null && mOnNetworkActivityCallback.get() != null) {
            mOnNetworkActivityCallback.get().onNetworkActivityStarted(message);
        }
    }

    private void hidePreloader() {
        if (mOnNetworkActivityCallback != null && mOnNetworkActivityCallback.get() != null) {
            mOnNetworkActivityCallback.get().onNetworkActivityFinished();
        }
    }


    public void filterBycategory(ArrayList<String> categories) {
        showPreloader(getActivity().getString(R.string.products_loading_message));
        apiManager.allProductsFilterByCategories(categories,this);

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

                // Create fragment and give it an argument for the selected article
                CategoryFilterFragment newFragment = new CategoryFilterFragment();
                newFragment.mListener=(FiltersFragmentListener)mListener;

                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.content_frame, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
                return true;

            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }



    @Override
    public void onDataParsed(ArrayList<Product> data) {
        if (data != null) {
            Log.i("ffs", data.toString());

            Products products = Products.getInstance(getActivity());
            if (data.size() > 0)
                products.deleteProducts();

            for (int i = 0; i < data.size(); i++) {
                Product product = data.get(i);
                products.addProduct(product);
            }
            //Update Adapter
            mProductsList.swapAdapter(new ProductsAdapter(products.getProducts(), getActivity(), this), false);
            hidePreloader();
        }else{

        }
    }

    @Override
    public void onDataParsed(Product data) {
        Log.i("ffs", data.toString());

        hidePreloader();
    }

    @Override
    public void onExceptionReceived(Exception e) {
        if (mOnNetworkActivityCallback != null && mOnNetworkActivityCallback.get() != null) {
            mOnNetworkActivityCallback.get().onExceptionReceived(e);
        }
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Products products = Products.getInstance(getActivity());
        Product product = products.getProducts().get(position);
        mListener.onProductsFragmentProductClicked(product);
    }
}
