package com.farfromsober.ffs.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.farfromsober.ffs.callbacks.RecyclerViewClickListener;
import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.Products;
import com.farfromsober.ffs.views.ProductView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joanbiscarri on 28/11/15.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>{
    private ArrayList<Product> mProductsList;
    private Context mContext;
    private static RecyclerViewClickListener mListener;


    public ProductsAdapter(ArrayList<Product> products, Context context, RecyclerViewClickListener listener) {
        super();
        mProductsList = products;
        mContext = context;
        mListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(new ProductView(mContext));
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product currentProduct = mProductsList.get(position);
        holder.bindProduct(currentProduct);
    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ProductView mProductView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mProductView = (ProductView) itemView;
            itemView.setOnClickListener(this);
        }

        public void bindProduct(Product product) {
            mProductView.setProductName(product.getName());
            mProductView.setProductPrice(product.getPrice());
            if (product.getImages().size() > 0)
                mProductView.setProductImage(product.getImages().get(0));
            else
                mProductView.setProductImage("http://www.macdevcenter.com/2005/05/06/graphics/Default.png");
        }

        @Override
        public void onClick(View v) {
            mListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }
}


