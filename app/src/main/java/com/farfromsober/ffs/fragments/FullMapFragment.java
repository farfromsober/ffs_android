package com.farfromsober.ffs.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farfromsober.ffs.R;

public class FullMapFragment extends Fragment {

    public FullMapFragment() {
        // Required empty public constructor
    }

    public static FullMapFragment newInstance() {
        FullMapFragment fragment = new FullMapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_map, container, false);
    }
}
