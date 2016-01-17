package com.farfromsober.ffs.fragments;

import android.app.Activity;
import android.app.Fragment;
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
import com.farfromsober.ffs.network.APIManager;
import com.farfromsober.generalutils.PictureUtils;
import com.farfromsober.networkviews.callbacks.OnNetworkActivityCallback;

import java.io.File;
import java.lang.ref.WeakReference;
import java.sql.SQLOutput;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private APIManager apiManager;
    private WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;
    public ProductsFragmentListener mListener;
    private File mActualImageFile;
    private ArrayList<File> mImageFiles;

    @Bind(R.id.new_product_category_spinner) Spinner mCategorySpinner;
    @Bind(R.id.new_product_sell_button) Button mSellButton;
    @Bind(R.id.new_product_image_1) ImageButton mImage1Button;
    @Bind(R.id.new_product_image_2) ImageButton mImage2Button;
    @Bind(R.id.new_product_image_3) ImageButton mImage3Button;
    @Bind(R.id.new_product_image_4) ImageButton mImage4Button;

    private static final int REQUEST_PHOTO = 2;

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

        mImageFiles = new ArrayList<File>();

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
                takePhoto(1);
            }
        });
    }

    private void takePhoto(int imageNumber) {

        File externalFilesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        String imageName = "IMG_" + "user_id" + ts + ".jpg";

        mActualImageFile = new File(externalFilesDir, imageName);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //boolean canTakePhoto = imageFile != null && captureImage.resolveActivity(getActivity().getPackageManager()) != null;

        Uri uri = Uri.fromFile(mActualImageFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(captureImage, REQUEST_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO && resultCode == getActivity().RESULT_OK) {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mActualImageFile.getPath(), getActivity());
            mImage1Button.setImageBitmap(bitmap);
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
        mListener.onProductsFragmentNewProductCreated();
    }
}
