package com.farfromsober.ffs.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farfromsober.ffs.R;
import com.farfromsober.network.interfaces.OnDataParsedCallback;
import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.network.APIManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements OnDataParsedCallback<Product>{

    private APIManager apiManager;

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

    private void askServerForProducts() {
        apiManager.allProducts(this);
    }

    @Override
    public void onDataParsed(ArrayList<Product> data) {
        Log.i("ffs", data.toString());
    }

    @Override
    public void onDataParsed(Product data) {
        Log.i("ffs", data.toString());
    }
}
