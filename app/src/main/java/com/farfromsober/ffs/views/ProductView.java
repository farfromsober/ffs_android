package com.farfromsober.ffs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.farfromsober.ffs.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joanbiscarri on 28/11/15.
 */
public class ProductView extends LinearLayout {


    @Bind(R.id.product_image) ImageView mProductImageView;
    @Bind(R.id.product_name) TextView mProductNameTextView;
    @Bind(R.id.product_price) TextView mProductPriceTextView;


    private String mProductImage;
    private String mProductName;
    private String  mProductPrice;
    private Context  mContext;


    public ProductView(Context context) {
        this(context,null);
        mContext = context;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
    }

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_product, this, true);
        ButterKnife.bind(this);
    }

    public void setProductImage(String mProductImage) {
        this.mProductImageView.setImageResource(R.drawable.photo_placeholder);
        if (mProductImage != null) {
            this.mProductImage = mProductImage;
            //show Image
            Picasso.with(mContext)
                    .load(this.mProductImage)
                            //.placeholder(R.drawable.user_placeholder)
                    .resize(150, 150)
                    .centerCrop()
                    .into(mProductImageView);
        }
        this.mProductImageView.setAdjustViewBounds(true);

    }

    public void setProductName(String mProductName) {
        this.mProductName = mProductName;
        this.mProductNameTextView.setText(mProductName);
    }

    public void setProductPrice(String mProductPrice) {
        this.mProductPrice = mProductPrice;
        this.mProductPriceTextView.setText(mProductPrice + "€");
    }
}
