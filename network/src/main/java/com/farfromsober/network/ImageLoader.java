package com.farfromsober.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by joanbiscarri on 01/12/15.
 */
public class ImageLoader extends AsyncTask<String, String, Bitmap> {
    ImageView mImageView;
    Bitmap mBitmap;

    public ImageLoader(ImageView imageView) {
        this.mImageView = imageView;
    }

    @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected Bitmap doInBackground(String... args) {
            try {
                mBitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mBitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                mImageView.setImageBitmap(image);
            }
        }

}
