package com.farfromsober.ffs.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.utils.SharedPreferencesManager;

import java.util.ArrayList;

import static com.farfromsober.ffs.fragments.FullProfileFragment.ARG_USER;


public class BoughtFragment extends ProductsListFragment {

    private User mUser;

    public BoughtFragment() {
        // Required empty public constructor
    }

    public static BoughtFragment newInstance(User user) {
        BoughtFragment fragment = new BoughtFragment();

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
        apiManager.userBoughtProducts(mUser, this);
    }

    @Override
    public void onDataArrayParsed(int responseCode, ArrayList<Object> data) {
        Log.i("", "data");
        super.onDataArrayParsed(responseCode, data);
    }
}
