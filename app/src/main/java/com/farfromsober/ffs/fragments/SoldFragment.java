package com.farfromsober.ffs.fragments;


import android.os.Bundle;
import android.view.View;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.callbacks.OnTabItemClickedCallback;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.utils.SharedPreferencesManager;

import static com.farfromsober.ffs.fragments.FullProfileFragment.*;

public class SoldFragment extends ProductsListFragment {

    private User mUser;
    public OnTabItemClickedCallback mListener;

    public SoldFragment() {
        // Required empty public constructor
    }

    public static SoldFragment newInstance(User user) {
        SoldFragment fragment = new SoldFragment();

        // 2) Nos creamos los argumentos y los empaquetamos
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_USER, user);

        // 3) Asignamos los argumentos al fragment y lo devolvemos ya creado
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(ARG_USER);
            if ((mUser == null)) {
                mUser = SharedPreferencesManager.getPrefUserData(getActivity());
            }
            return;
        }
        mUser = SharedPreferencesManager.getPrefUserData(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(false);
    }

    public void askServerForProducts() {
        showPreloader(getActivity().getString(R.string.products_loading_message));
        apiManager.userSoldProducts(mUser, this);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        //super.recyclerViewListClicked(v, position);
        mListener.onProductClicked(position);
    }
}
