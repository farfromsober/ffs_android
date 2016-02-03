package com.farfromsober.ffs.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.callbacks.ProductsFragmentListener;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.utils.SharedPreferencesManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FullProfileFragment extends Fragment {

    public static final String ARG_USER = "com.farfromsober.ffs.fragments.FullProfileFragment.ARG_USER";

    private AppCompatActivity mContext;
    public WeakReference<ProductsFragmentListener> mProductsFragmentListener;

    private ViewPagerAdapter adapter;

    private User mUser;

    //@Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    public FullProfileFragment() {
        // Required empty public constructor
    }

    public static FullProfileFragment newInstance(User user) {
        FullProfileFragment fragment = new FullProfileFragment();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_full_profile, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onAttach(Context context) {
        setCallbacks(context);
        super.onAttach(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        setCallbacks(activity);
        super.onAttach(activity);
    }

    private void setCallbacks(Context context) {
        try {
            mProductsFragmentListener = new WeakReference<>((ProductsFragmentListener) getActivity());
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement OnMenuSelectedCallback in Activity");
        }
        mContext = (AppCompatActivity) context;
    }

    private void setupViewPager(ViewPager viewPager) {
        if (adapter == null) {
            adapter = new ViewPagerAdapter(getChildFragmentManager());
        }
        adapter.addFragment(ProfileFragment.newInstance(mUser), mContext.getString(R.string.profile_tab_title));
        adapter.addFragment(SellingFragment.newInstance(mUser), mContext.getString(R.string.selling_tab_title));
        adapter.addFragment(SoldFragment.newInstance(mUser), mContext.getString(R.string.sold_tab_title));
        adapter.addFragment(BoughtFragment.newInstance(mUser), mContext.getString(R.string.bought_tab_title));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
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
        public void destroyItem(ViewGroup container, int position, Object object) {
            FragmentManager manager = ((Fragment) object).getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove((Fragment) object);
            trans.commit();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
