package com.farfromsober.ffs.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farfromsober.ffs.R;
import com.farfromsober.generalutils.SharedPreferencesGeneralManager;
import com.farfromsober.network.callbacks.OnDataParsedCallback;
import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.network.APIManager;
import com.farfromsober.networkviews.callbacks.OnNetworkActivityCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements OnDataParsedCallback<Product>{

    private APIManager apiManager;
    private WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;

    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiManager = new APIManager();
        askServerForProducts();
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

    @Override
    public void onDataParsed(ArrayList<Product> data) {
        Log.i("ffs", data.toString());

        //Save in SharedPreferences example
        String json = SharedPreferencesGeneralManager.objectToJSONString(data.get(0));
        Product product = (Product) SharedPreferencesGeneralManager.JSONStringToObject(json, Product.class);

        hidePreloader();
    }

    @Override
    public void onDataParsed(Product data) {
        Log.i("ffs", data.toString());

        hidePreloader();
    }
}
