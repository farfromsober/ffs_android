package com.farfromsober.ffs.callbacks;
import com.farfromsober.ffs.model.Product;

/**
 * Created by joanbiscarri on 04/12/15.
 */
public interface ProductsFragmentListener {
    public void ProductsFragmentAddProductClicked();
    public void ProductsFragmentProductClicked(Product product);

}
