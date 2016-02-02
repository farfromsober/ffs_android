package com.farfromsober.ffs.callbacks;

import com.farfromsober.ffs.model.User;

public interface ProductDetailFragmentListener {
    void onProductsDetailFragmentPurchaseSucceed();
    void onProductsDetailFragmentPurchaseFailed();
    void onProductsDetailProfilePressed(User seller);
}
