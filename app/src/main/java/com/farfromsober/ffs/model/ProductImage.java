package com.farfromsober.ffs.model;

import java.io.File;

public class ProductImage {

    private String mImageName;
    private boolean mHasImage;
    private String mImageUrl;
    private File mImageFile;

    public ProductImage() {
        this.mImageName = "";
        this.mHasImage = false;
        this.mImageUrl = "";
        this.mImageFile = null;
    }

    public File getImageFile() {
        return mImageFile;
    }

    public void setImageFile(File imageFile) {
        mImageFile = imageFile;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public boolean isHasImage() {
        return mHasImage;
    }

    public void setHasImage(boolean hasImage) {
        mHasImage = hasImage;
    }

    public String getImageName() {
        return mImageName;
    }

    public void setImageName(String imageName) {
        mImageName = imageName;
    }
}
