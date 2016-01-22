package com.farfromsober.ffs.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.callbacks.ProductsFragmentListener;
import com.farfromsober.ffs.fragments.dialogs.ProductImageDialogFragment;
import com.farfromsober.ffs.model.ProductImage;
import com.farfromsober.ffs.network.APIManager;
import com.farfromsober.ffs.network.BlobUploadTask;
import com.farfromsober.generalutils.PictureUtils;
import com.farfromsober.networkviews.callbacks.OnNetworkActivityCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private APIManager apiManager;
    private WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;
    public ProductsFragmentListener mListener;
    private File mActualImageFile;
    private ProductImage mActualProductImage;
    private int mActualImagePosition;
    private ArrayList<ProductImage> mProductImages = new ArrayList<ProductImage>();

    @Bind(R.id.new_product_category_spinner) Spinner mCategorySpinner;
    @Bind(R.id.new_product_sell_button) Button mSellButton;
    @Bind(R.id.new_product_image_1) ImageButton mImage1Button;
    @Bind(R.id.new_product_image_2) ImageButton mImage2Button;
    @Bind(R.id.new_product_image_3) ImageButton mImage3Button;
    @Bind(R.id.new_product_image_4) ImageButton mImage4Button;

    private ArrayList<ImageButton> mImageButtons = new ArrayList<ImageButton>();

    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final String DIALOG_PHOTO = "DialogPhoto";
    private static final int REQUEST_PHOTO = 3;

    public NewProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_product, container, false);
        ButterKnife.bind(this, root);
        setHasOptionsMenu(false);

        mCategorySpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.CategoryList, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mCategorySpinner.setAdapter(adapter);

        mProductImages.add(new ProductImage());

        setButtonListeners();

        return root;
    }

    private void setButtonListeners() {
        mSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProductToSell();
            }
        });

        mImage1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActualImagePosition = 0;
                setProductPhoto();
            }
        });
        mImageButtons.add(mImage1Button);

        mImage2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActualImagePosition = 1;
                setProductPhoto();
            }
        });
        mImageButtons.add(mImage2Button);

        mImage3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActualImagePosition = 2;
                setProductPhoto();
            }
        });
        mImageButtons.add(mImage3Button);

        mImage4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActualImagePosition = 3;
                setProductPhoto();
            }
        });
        mImageButtons.add(mImage4Button);
    }

    private void setProductPhoto() {
        if(mProductImages.get(mActualImagePosition).isHasImage()) {
            // Preguntar qué quiere hacer con la foto
            FragmentManager manager = getFragmentManager();
            ProductImageDialogFragment dialog = new ProductImageDialogFragment();
            dialog.setTargetFragment(NewProductFragment.this, REQUEST_PHOTO);
            dialog.show(manager, DIALOG_PHOTO);
        } else {
            takePhoto();
        }
    }

    private void takePhoto() {

        mActualProductImage = mProductImages.get(mActualImagePosition);

        File externalFilesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        String imageName = "IMG_" + "user_id" + ts + ".jpg";
        mActualProductImage.setImageName(imageName);

        //mActualImageFile = new File(externalFilesDir, imageName);
        mActualProductImage.setImageFile(new File(externalFilesDir, imageName));
        mActualProductImage.setHasImage(true);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri uri = Uri.fromFile(mActualProductImage.getImageFile());
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(captureImage, REQUEST_TAKE_PHOTO);
    }

    private void removePhoto() {
        mProductImages.remove(mActualImagePosition);
        if (mProductImages.size()>0) {
            mProductImages.remove(mProductImages.size()-1);

        }
        updateProductImages();
    }

    private void updateProductImages() {
        for (int i = 0; i < mImageButtons.size(); i++) {
            ImageButton imageButton = mImageButtons.get(i);
            if (i < mProductImages.size()) {
                ProductImage productImage = mProductImages.get(i);
                Bitmap bitmap = PictureUtils.getScaledBitmap(productImage.getImageFile().getPath(), getActivity());
                imageButton.setImageBitmap(bitmap);
            } else {
                imageButton.setImageResource(R.drawable.photo_placeholder);
                if (i==mProductImages.size()) {
                    imageButton.setVisibility(View.VISIBLE);
                } else {
                    imageButton.setVisibility(View.INVISIBLE);
                }
            }
        }
        mProductImages.add(new ProductImage());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == getActivity().RESULT_OK) {
            //Bitmap bitmap = PictureUtils.getScaledBitmap(mActualProductImage.getImageFile().getPath(), getActivity());
            //mImage1Button.setImageBitmap(bitmap);
            mProductImages.set(mActualImagePosition, mActualProductImage);
            updateProductImages();
        } else if (requestCode == REQUEST_PHOTO) {
            if (resultCode == getActivity().RESULT_OK) {
                // Eliminar foto
                removePhoto();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // Reemplazamos foto
                mProductImages.remove(mProductImages.size()-1);
                takePhoto();
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiManager = new APIManager(getActivity());
    }

    @Override
    public void onAttach(Context context) {
        setCallbacks(context);
        super.onAttach(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        setCallbacks(activity);
        super.onAttach(activity);
    }

    private void setCallbacks(Context context) {
        try {
            mOnNetworkActivityCallback = new WeakReference<>((OnNetworkActivityCallback) getActivity());
        } catch (Exception e) {
            throw new ClassCastException(context.toString()+" must implement OnNetworkActivityCallback in Activity");
        }
    }

    private void showPreloader(String message) {
        if (mOnNetworkActivityCallback != null && mOnNetworkActivityCallback.get() != null) {
            mOnNetworkActivityCallback.get().onNetworkActivityStarted(message);
        }
    }

    private void hidePreloader() {
        if (mOnNetworkActivityCallback != null && mOnNetworkActivityCallback.get() != null) {
            mOnNetworkActivityCallback.get().onNetworkActivityFinished();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void createProductToSell() {
        System.out.println(mCategorySpinner.getSelectedItem());
        //mListener.onProductsFragmentNewProductCreated();

        new BlobUploadTask(mActualImageFile)
                .execute();
    }


}
