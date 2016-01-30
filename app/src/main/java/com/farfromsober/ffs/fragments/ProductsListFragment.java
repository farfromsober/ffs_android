package com.farfromsober.ffs.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProductsListFragment extends FullProductsFragment {

    public ProductsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = super.onCreateView(inflater, container, savedInstanceState);

        mAddProduct.setVisibility(View.INVISIBLE);
        mSearchGroup.setVisibility(View.GONE);

        setHasOptionsMenu(false);

        return root;
    }
}
