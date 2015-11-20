package com.farfromsober.ffs.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.network.APIManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

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
        apiManager.allProducts(getActivity());
    }
}
