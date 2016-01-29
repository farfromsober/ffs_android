package com.farfromsober.ffs.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.farfromsober.ffs.R;

import butterknife.Bind;

public class ProductsListFragment extends FullProductsFragment {

    @Bind(R.id.filter_linear) LinearLayout mFilterLinear;

    public ProductsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = super.onCreateView(inflater, container, savedInstanceState);

        mAddProduct.setVisibility(View.INVISIBLE);
        mFilterLinear.setVisibility(View.GONE);

        return root;
    }

    public void askServerForProducts() {
        showPreloader(getActivity().getString(R.string.products_loading_message));
        apiManager.allProducts(this);
    }
}
