package com.farfromsober.ffs.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.model.Product;
import com.squareup.picasso.Picasso;

public class ImagePagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    Product mProduct;

    public ImagePagerAdapter(Context context, Product product) {
        mContext = context;
        mProduct = product;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mProduct.getImages().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Picasso.with(mContext)
                .load(mProduct.getImages().get(position))
                .placeholder(R.drawable.photo_placeholder)
                .resize(500, 500)
                .centerCrop()
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
