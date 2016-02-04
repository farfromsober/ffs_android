package com.farfromsober.ffs.callbacks;
import com.farfromsober.ffs.model.ProductImage;

import java.util.ArrayList;

public interface BlobUploaderListener {
    void onProductsFragmentNewProductImagesUploaded(ArrayList<ProductImage> productImages);
}
