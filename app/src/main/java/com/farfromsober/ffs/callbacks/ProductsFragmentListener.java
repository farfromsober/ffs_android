package com.farfromsober.ffs.callbacks;
import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.ProductImage;

import java.util.ArrayList;

/**
 * Created by joanbiscarri on 04/12/15.
 */
public interface ProductsFragmentListener {
    public void onProductsFragmentAddProductClicked();
    public void onProductsFragmentNewProductImagesUploaded(ArrayList<ProductImage> productImages);
    public void onProductsFragmentNewProductCreated();
    public void onProductsFragmentProductClicked(Product product);
}
