package com.farfromsober.ffs.fragments;


import com.farfromsober.ffs.R;

public class SellingFragment extends ProductsListFragment {

    public SellingFragment() {
        // Required empty public constructor
    }

    public void askServerForProducts() {
        showPreloader(getActivity().getString(R.string.products_loading_message));
        apiManager.mySellingProducts(this);
    }
}
