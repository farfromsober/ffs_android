/**
 * Copyright Microsoft Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.farfromsober.ffs.network;

import android.os.AsyncTask;

import com.farfromsober.ffs.callbacks.BlobUploaderListener;
import com.farfromsober.ffs.model.ProductImage;
import com.farfromsober.ffs.model.User;
import com.farfromsober.generalutils.DateManager;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.FileInputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

/**
 * This sample illustrates basic usage of the various Blob Primitives provided
 * in the Storage Client Library including CloudBlobContainer, CloudBlockBlob
 * and CloudBlobClient.
 */
public class BlobUploadTask extends AsyncTask<String, Void, ArrayList<ProductImage>> {

    private WeakReference<BlobUploaderListener> mListener;
    private ArrayList<ProductImage> imagesToUpload;
    private User mCurrentUser;
    private static final String AZURE_CONTAINER = "farfromsober-images-container";

    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;"
            + "AccountName=farfromsober;"
            + "AccountKey=tv2oqlfCxzFUm7/dYgBGD6YW5K1eQOROVGqqDVm3ijaJpdhxwpkW5OttAFS70++IAcEReSdc0fR/zc06CKrkWQ==";

    public BlobUploadTask(ArrayList<ProductImage> imageFiles, BlobUploaderListener listener, User currentUser) {
        this.imagesToUpload = imageFiles;
        this.mListener = new WeakReference<>(listener);
        this.mCurrentUser = currentUser;
    }

    @Override
    protected ArrayList<ProductImage> doInBackground(String... arg0) {

        //act.printSampleStartInfo("BlobBasics");

        try {
            // Setup the cloud storage account.
            CloudStorageAccount account = CloudStorageAccount
                    .parse(storageConnectionString);

            // Create a blob service client
            CloudBlobClient blobClient = account.createCloudBlobClient();

            // Get a reference to a container
            // The container name must be lower case
            // Append a random UUID to the end of the container name so that
            // this sample can be run more than once in quick succession.
            CloudBlobContainer container = blobClient.getContainerReference(AZURE_CONTAINER);

            int currentPosition=0;
            for (ProductImage productImage: imagesToUpload) {
                if (productImage.isHasImage()) {
                    // Get a reference to a blob in the container
                    CloudBlockBlob imageBlob = container.getBlockBlobReference(mCurrentUser.getUserId()+ "-" + currentPosition + "-" +DateManager.timestampFromDate(new Date()) +".jpg");

                    productImage.setImageUrl(imageBlob.getStorageUri().getPrimaryUri().toString());
                    // Upload imageFile to blob
                    imageBlob.upload(new FileInputStream(productImage.getImageFile()), productImage.getImageFile().length());
                }
                currentPosition++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imagesToUpload;
    }

    @Override
    protected void onPostExecute(ArrayList<ProductImage> productImages) {
        if (mListener != null && mListener.get() != null) {
            mListener.get().onProductsFragmentNewProductImagesUploaded(productImages);
        }
    }
}