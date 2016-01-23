package com.farfromsober.ffs.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.farfromsober.customviews.CustomFontTextView;
import com.farfromsober.ffs.R;
import com.farfromsober.ffs.adapters.ProductsAdapter;
import com.farfromsober.ffs.callbacks.FiltersFragmentListener;
import com.farfromsober.ffs.callbacks.OnOptionsFilterMenuSelected;
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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements OnDataParsedCallback<Product>, RecyclerViewClickListener {

    private APIManager apiManager;
    private WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;
    public ProductsFragmentListener mListener;
    public OnOptionsFilterMenuSelected optionsListener;

    Button button;

    //private RecyclerView mProductsList;
    @Bind(R.id.no_products_label) CustomFontTextView mNoProductsLabel;
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

        button = (Button) getActivity().findViewById(R.id.filtrar_submit);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText mText = (EditText) getActivity().findViewById(R.id.filter_product);
                mListener.onProductFilter(mText.getText().toString());

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
    }

    public void reloadProductsList() {
        askServerForProducts();
    }

    public void askServerForProducts() {
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


    public void filterBycategoryAndDistance(HashMap<String,Integer> categories, Location location) {
        showPreloader(getActivity().getString(R.string.products_loading_message));
        apiManager.allProductsFilterByCategoriesAndDistance(categories,this,location);
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

                optionsListener.onFilterMenuSelected(1);
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
            if (data.size() > 0) {
                mProductsList.setVisibility(View.VISIBLE);
                products.deleteProducts();
            }
            else {
                mProductsList.setVisibility(View.INVISIBLE);
            }
            for (int i = 0; i < data.size(); i++) {
                Product product = data.get(i);
                // ONLY Products that have selling = true will be shown
                if (product.getIsSelling() == true) { products.addProduct(product);}
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
