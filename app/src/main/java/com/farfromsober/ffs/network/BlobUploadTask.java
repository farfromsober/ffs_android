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
import android.widget.TextView;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.File;
import java.io.FileInputStream;

/**
 * This sample illustrates basic usage of the various Blob Primitives provided
 * in the Storage Client Library including CloudBlobContainer, CloudBlockBlob
 * and CloudBlobClient.
 */
public class BlobUploadTask extends AsyncTask<String, Void, Void> {

    private File imageToUpload;

    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;"
            + "AccountName=farfromsober;"
            + "AccountKey=tv2oqlfCxzFUm7/dYgBGD6YW5K1eQOROVGqqDVm3ijaJpdhxwpkW5OttAFS70++IAcEReSdc0fR/zc06CKrkWQ==";

    public BlobUploadTask(File imageFile) {
        this.imageToUpload = imageFile;
    }

    @Override
    protected Void doInBackground(String... arg0) {

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
            CloudBlobContainer container = blobClient.getContainerReference("farfromsober-images-container");

            // Get a reference to a blob in the container
            CloudBlockBlob imageBlob = container.getBlockBlobReference("imagen_test.jpg");

            // Upload imageFile to blob
            imageBlob.upload(new FileInputStream(imageToUpload), imageToUpload.length());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        System.out.println("IMAGE UPLOADED");
        super.onPostExecute(aVoid);
    }
}