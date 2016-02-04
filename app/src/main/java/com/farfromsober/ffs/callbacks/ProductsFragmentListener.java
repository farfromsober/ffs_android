package com.farfromsober.ffs.callbacks;
import com.farfromsober.ffs.model.Product;


public interface ProductsFragmentListener {
    void onProductsFragmentAddProductClicked();
    void onProductsFragmentProductClicked(Product product);
    void onProductFilter(String word);
}
