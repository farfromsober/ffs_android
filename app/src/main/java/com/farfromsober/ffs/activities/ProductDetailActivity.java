package com.farfromsober.ffs.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.adapters.ImageSliderAdapter;
import com.farfromsober.ffs.model.Product;

import static com.farfromsober.ffs.activities.MainActivity.*;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ProductDetailActivity extends AppCompatActivity {

    @Bind(R.id.detail_viewpager)
    ViewPager mViewPager;

    public static final String TAG = "detailsFragment";

    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        mProduct = (Product) getIntent().getExtras().get(ARGS_PRODUCT);

        ImageSliderAdapter adapter = new ImageSliderAdapter(this);
        mViewPager.setAdapter(adapter);
    }
}
