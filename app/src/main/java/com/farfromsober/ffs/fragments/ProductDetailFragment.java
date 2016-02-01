package com.farfromsober.ffs.fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.farfromsober.customviews.CustomFontButton;
import com.farfromsober.customviews.CustomFontTextView;
import com.farfromsober.ffs.R;
import com.farfromsober.ffs.adapters.ImagePagerAdapter;
import com.farfromsober.ffs.callbacks.OnMenuSelectedCallback;
import com.farfromsober.ffs.callbacks.ProductDetailFragmentListener;
import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.Transaction;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.network.APIManager;
import com.farfromsober.ffs.utils.MapUtils;
import com.farfromsober.ffs.utils.SharedPreferencesManager;
import com.farfromsober.generalutils.DateManager;
import com.farfromsober.network.callbacks.OnDataParsedCallback;
import com.farfromsober.networkviews.callbacks.OnNetworkActivityCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.farfromsober.ffs.activities.MainActivity.*;

public class ProductDetailFragment extends Fragment implements OnDataParsedCallback<Transaction> {

    public static final String ARG_PRODUCT = "com.farfromsober.ffs.fragments.ProductDetailFragment.ARG_PRODUCT";
    private static final String DEFAULT_CITY = "Madrid";
    private static final String DEFAULT_STATE = "Spain";
    private Product mProduct;
    public WeakReference<ProductDetailFragmentListener>  mListener;

    private APIManager apiManager;
    private WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;

    @Bind(R.id.detail_images_viewpager)
    ViewPager mViewPager;
    @Bind(R.id.detail_images_viewpager_indicator)
    CirclePageIndicator mCirclePageIndicator;
    @Bind(R.id.detail_product_number_of_photos)
    CustomFontTextView mNumberOfPhotos;
    @Bind(R.id.detail_product_for_sale)
    CustomFontTextView mForSale;
    @Bind(R.id.detail_seller_image)
    CircleImageView mSellerImageView;
    @Bind(R.id.detail_seller_name)
    CustomFontTextView mSellerName;
    @Bind(R.id.detail_published_date)
    CustomFontTextView mPublishedDate;
    @Bind(R.id.detail_product_price)
    CustomFontTextView mProductPrice;
    @Bind(R.id.detail_product_title)
    CustomFontTextView mProductTitle;
    @Bind(R.id.detail_product_description)
    CustomFontTextView mProductDescription;
    @Bind(R.id.purchase_button)
    CustomFontButton mPurchaseButton;
    @Bind(R.id.detail_seller_profile_button)
    Button mProfileButton;

    private MapFragment mMapFragment;
    private GoogleMap map;
    private WeakReference<OnMenuSelectedCallback> mOnMenuSelectedCallback;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();

        // 2) Nos creamos los argumentos y los empaquetamos
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_PRODUCT, product);

        // 3) Asignamos los argumentos al fragment y lo devolvemos ya creado
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(this, root);
        setHasOptionsMenu(false);
        loadViewPagerImages();
        populateFields();
        configureMap();
        setButtonListeners();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiManager = new APIManager(getActivity());
    }

    private void setButtonListeners() {
        mPurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPurchaseProduct();
            }
        });

        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSellerProfile();
            }
        });
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
            mOnMenuSelectedCallback = new WeakReference<>((OnMenuSelectedCallback) getActivity());
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement OnMenuSelectedCallback in Activity");
        }

        try {
            mOnNetworkActivityCallback = new WeakReference<>((OnNetworkActivityCallback) getActivity());
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement OnNetworkActivityCallback in Activity");
        }

        try {
            mListener = new WeakReference<>((ProductDetailFragmentListener) getActivity());
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement ProductDetailFragmentListener in Activity");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void configureMap() {

        mMapFragment = ((MapFragment) getChildFragmentManager().findFragmentById(R.id.detail_product_map));

        if (mMapFragment != null) {

            map = mMapFragment.getMap();

            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    launchFullMapFragment();
                }
            });

            LatLng center;
            if (mProduct.getSeller().getLatitude() == "" || mProduct.getSeller().getLongitude() == "") {
                if (mProduct.getSeller().getCity() == "" || mProduct.getSeller().getState() == "") {
                    center = MapUtils.getLocationFromAddress(getActivity(), String.format("%s, %s", mProduct.getSeller().getCity(), mProduct.getSeller().getState()));
                } else {
                    center = MapUtils.getLocationFromAddress(getActivity(), String.format("%s, %s", DEFAULT_CITY, DEFAULT_STATE));
                }
            } else {
                center = new LatLng(Double.parseDouble(mProduct.getSeller().getLatitude()), Double.parseDouble(mProduct.getSeller().getLongitude()));
            }
            MapUtils.centerMap(map, center.latitude, center.longitude, 12);
            // create marker
            MarkerOptions marker = new MarkerOptions().position(center);
            // adding marker
            map.addMarker(marker);
        }
        if (map == null) {
            Toast.makeText(getActivity(), "Map died", Toast.LENGTH_LONG).show();
        }
    }

    private void launchFullMapFragment() {
        if (mOnMenuSelectedCallback != null && mOnMenuSelectedCallback.get() != null) {
            mOnMenuSelectedCallback.get().onMenuSelected(MAP_FRAGMENT_INDEX);
        }
    }

    private void loadViewPagerImages() {
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(getActivity(), mProduct);
        mViewPager.setAdapter(imagePagerAdapter);
        mCirclePageIndicator.setViewPager(mViewPager, 0);
    }

    private void populateFields() {
        mForSale.setText(mProduct.getIsSelling() ? getActivity().getResources().getString(R.string.selling) : getActivity().getResources().getString(R.string.sold));
        mNumberOfPhotos.setText(String.format("%d %s", mProduct.getImages().size(), getActivity().getResources().getString(R.string.photos)));

        //User user = SharedPreferencesManager.getPrefUserData(getActivity());
        User user = mProduct.getSeller();
        if (user.getAvatarURL() != null && user.getAvatarURL().length() > 0) {
            Picasso.with(getActivity())
                    .load(user.getAvatarURL())
                    .placeholder(R.drawable.mavatar_placeholder)
                    .resize(200, 200)
                    .centerCrop()
                    .into(mSellerImageView);
        } else {
            mSellerImageView.setImageResource(R.drawable.mavatar_placeholder);
        }

        mSellerName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        mPublishedDate.setText(String.format("%s %s", getActivity().getResources().getString(R.string.published), DateManager.stringFromDate(mProduct.getPublished(), "dd/MM/yyyy")));
        mProductPrice.setText(String.format("%s €", mProduct.getPrice()));
        mProductTitle.setText(mProduct.getName());
        mProductDescription.setText(mProduct.getDetail());
    }

    private void requestPurchaseProduct() {
        Log.i("ffs", "requestPurchaseProduct");
        showPreloader(getActivity().getString(R.string.transaction_creation_message));
        User user = SharedPreferencesManager.getPrefUserData(getActivity());
        apiManager.createTransaction(mProduct.getId(), user.getUserId(), this);
    }

    private void showSellerProfile() {
        if (mListener != null && mListener.get() != null) {
            mListener.get().onProductsDetailProfilePressed(mProduct.getSeller());
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
    public void onDataArrayParsed(int responseCode, ArrayList<Transaction> data) {
    }

    @Override
    public void onDataObjectParsed(int responseCode, Transaction data) {
        if (responseCode != HttpsURLConnection.HTTP_CREATED) {
            return;
        }
        if (data != null) {
            Log.i("ffs", "Transaction succeed");
            if (mListener != null && mListener.get() != null) {
                mListener.get().onProductsDetailFragmentPurchaseSucceed();
            }
        } else if (data == null) {
            Log.i("ffs", "Transaction failed");
            if (mListener != null && mListener.get() != null) {
                mListener.get().onProductsDetailFragmentPurchaseFailed();
            }
        }
        hidePreloader();
    }

    @Override
    public void onExceptionReceived(Exception e) {
        if (mOnNetworkActivityCallback != null && mOnNetworkActivityCallback.get() != null) {
            mOnNetworkActivityCallback.get().onExceptionReceived(e);
        }
    }
}
