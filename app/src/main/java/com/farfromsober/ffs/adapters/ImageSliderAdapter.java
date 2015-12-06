package com.farfromsober.ffs.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.farfromsober.ffs.R;


/**
 * Created by joanbiscarri on 04/12/15.
 */
public class ImageSliderAdapter extends PagerAdapter {

    Context context;
    private int[] ImagesArray = new int[] {
            R.drawable.no_user,
            R.drawable.no_user,
            R.drawable.no_user
    };

    public ImageSliderAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return ImagesArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        //int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
        //imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(ImagesArray[position]);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
