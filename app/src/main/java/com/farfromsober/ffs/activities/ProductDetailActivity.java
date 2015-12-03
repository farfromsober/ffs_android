package com.farfromsober.ffs.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.adapters.ImageSliderAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ProductDetailActivity extends AppCompatActivity {

    @Bind(R.id.detail_viewpager)
    ViewPager mViewPager;

    public static final String TAG = "detailsFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        ImageSliderAdapter adapter = new ImageSliderAdapter(this);
        mViewPager.setAdapter(adapter);
    }
}
