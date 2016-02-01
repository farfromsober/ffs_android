package com.farfromsober.ffs.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.farfromsober.customviews.CustomFontEditText;
import com.farfromsober.ffs.R;
import com.farfromsober.ffs.callbacks.ProductsFragmentListener;
import com.farfromsober.ffs.fragments.dialogs.ProductImageDialogFragment;
import com.farfromsober.ffs.model.Category;
import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.ProductImage;
import com.farfromsober.ffs.network.APIManager;
import com.farfromsober.ffs.network.BlobUploadTask;
import com.farfromsober.ffs.utils.CategoryManager;
import com.farfromsober.ffs.utils.SharedPreferencesManager;
import com.farfromsober.generalutils.PictureUtils;
import com.farfromsober.network.callbacks.OnDataParsedCallback;
import com.farfromsober.networkviews.callbacks.OnNetworkActivityCallback;

import java.io.File;
import java.lang.ref.WeakReference;
import java.sql.SQLOutput;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewProductFragment extends Fragment implements OnDataParsedCallback<Product>, AdapterView.OnItemSelectedListener {

    private APIManager apiManager;
    private WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;
    public WeakReference<ProductsFragmentListener> mListener;
    private ProductImage mActualProductImage;
    private int mActualImagePosition;
    public ArrayList<ProductImage> mProductImages = new ArrayList<>();


    @Bind(R.id.new_product_sell_button)
    Button mSellButton;
    @Bind(R.id.new_product_image_1)
    ImageButton mImage1Button;
    @Bind(R.id.new_product_image_2)
    ImageButton mImage2Button;
    @Bind(R.id.new_product_image_3)
    ImageButton mImage3Button;
    @Bind(R.id.new_product_image_4)
    ImageButton mImage4Button;

    @Bind(R.id.new_product_title)
    CustomFontEditText mProductTitle;
    @Bind(R.id.new_product_description)
    CustomFontEditText mProductDescription;
    @Bind(R.id.new_product_category_spinner)
    Spinner mProductCategorySpinner;
    @Bind(R.id.new_product_price)
    CustomFontEditText mProductPrice;

    private Product mNewProduct;

    private ArrayList<ImageButton> mImageButtons = new ArrayList<>();

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

        Category[] categories = CategoryManager.getmSharedInstance().getCategories();

        mProductCategorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mProductCategorySpinner.setAdapter(adapter);

        mProductImages.add(new ProductImage());

        setButtonListeners();

        return root;
    }

    private void setButtonListeners() {
        mSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreloader(getResources().getString(R.string.new_product_uploading_images));
                if (mListener != null && mListener.get() != null) {
                    new BlobUploadTask(mProductImages, mListener.get(), SharedPreferencesManager.getPrefUserData(getActivity()))
                            .execute();
                }
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
        if (mProductImages.get(mActualImagePosition).isHasImage()) {
            // Preguntar qué quiere hacer con la foto
            FragmentManager manager = getFragmentManager();
            ProductImageDialogFragment dialog = new ProductImageDialogFragment();
            dialog.setTargetFragment(NewProductFragment.this, REQUEST_PHOTO);
            dialog.show(manager, DIALOG_PHOTO);
        } else {
            //Tomamos la foto.
            takePhoto();
        }
    }

    private void takePhoto() {

        mActualProductImage = mProductImages.get(mActualImagePosition);

        File externalFilesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Long tsLong = System.currentTimeMillis() / 1000;
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
        ProductImage productImageToRemove = mProductImages.get(mActualImagePosition);
        productImageToRemove.getImageFile().delete();
        mProductImages.remove(mActualImagePosition);
        if (mProductImages.size() > 0) {
            mProductImages.remove(mProductImages.size() - 1);

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
                imageButton.setImageResource(R.drawable.mphoto_placeholder);
                if (i == mProductImages.size()) {
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
                mProductImages.remove(mProductImages.size() - 1);
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
            throw new ClassCastException(context.toString() + " must implement OnNetworkActivityCallback in Activity");
        }
        try {
            mListener = new WeakReference<>((ProductsFragmentListener) getActivity());
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement ProductsFragmentListener in Activity");
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

    public void uploadNewProduct() {
        hidePreloader();
        showPreloader(getResources().getString(R.string.new_product_uploading_product_info));

        mNewProduct = new Product();

        mNewProduct.setName(mProductTitle.getText().toString());
        mNewProduct.setDetail(mProductDescription.getText().toString());
        mNewProduct.setCategory((Category) mProductCategorySpinner.getSelectedItem());
        mNewProduct.setPrice(mProductPrice.getText().toString());

        apiManager.createProduct(mNewProduct, this);
    }

    private void removeImagesFromStorage() {
        for (ProductImage productImage : mProductImages) {
            if (productImage.isHasImage()) {
                productImage.getImageFile().delete();
            }
        }
    }

    @Override
    public void onDataArrayParsed(int responseCode, ArrayList<Product> data) {
        System.out.println("Aqui");
    }

    @Override
    public void onDataObjectParsed(int responseCode, Product data) {
        if (responseCode != HttpsURLConnection.HTTP_CREATED) {
            return;
        }
        if (data != null) {
            mNewProduct = data;
            ArrayList<String> imageUrls = new ArrayList<>();
            for (ProductImage productImage : mProductImages) {
                if (productImage.isHasImage()) {
                    imageUrls.add(productImage.getImageUrl());
                }
            }
            mNewProduct.setImages(imageUrls);
            apiManager.uploadNewProductImages(mNewProduct, this);
        } else if (data == null) {
            removeImagesFromStorage();
            hidePreloader();
            if (mListener != null && mListener.get() != null) {
                mListener.get().onProductsFragmentNewProductCreated();
            }
        }
    }

    @Override
    public void onExceptionReceived(Exception e) {
        e.printStackTrace();
    }
}
