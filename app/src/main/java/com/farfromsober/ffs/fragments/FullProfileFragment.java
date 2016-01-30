package com.farfromsober.ffs.fragments;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.callbacks.ProductsFragmentListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FullProfileFragment extends Fragment {

    private FragmentActivity mContext;
    public ProductsFragmentListener mListener;

    //@Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tabs) TabLayout mTabLayout;
    @Bind(R.id.viewpager) ViewPager mViewPager;

    public FullProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_full_profile, container, false);
        ButterKnife.bind(this, root);

        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        mContext = (FragmentActivity)context;
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity)activity;
        super.onAttach(activity);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(mContext.getSupportFragmentManager());
        adapter.addFragment(new ProfileFragment(), "Perfil");
        adapter.addFragment(new SellingFragment(), "En venta");
        adapter.addFragment(new SoldFragment(), "Vendidos");
        adapter.addFragment(new BoughtFragment(), "Comprados");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
