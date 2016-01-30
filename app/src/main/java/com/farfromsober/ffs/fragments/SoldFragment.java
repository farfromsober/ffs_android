package com.farfromsober.ffs.fragments;


import com.farfromsober.ffs.R;

public class SoldFragment extends ProductsListFragment {

    public SoldFragment() {
        // Required empty public constructor
    }

    public void askServerForProducts() {
        showPreloader(getActivity().getString(R.string.products_loading_message));
        apiManager.mySoldProducts(this);
    }
}
