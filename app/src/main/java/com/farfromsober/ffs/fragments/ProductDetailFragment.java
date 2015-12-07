package com.farfromsober.ffs.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farfromsober.customviews.CustomFontTextView;
import com.farfromsober.ffs.R;
import com.farfromsober.ffs.adapters.ImagePagerAdapter;
import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.utils.SharedPreferencesManager;
import com.farfromsober.generalutils.DateManager;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailFragment extends Fragment {

    public static final String ARG_PRODUCT = "com.farfromsober.ffs.fragments.ProductDetailFragment.ARG_PRODUCT";
    private Product mProduct;


    @Bind(R.id.detail_images_viewpager) ViewPager mViewPager;
    @Bind(R.id.detail_product_number_of_photos) CustomFontTextView mNumberOfPhotos;
    @Bind(R.id.detail_product_for_sale) CustomFontTextView mForSale;
    @Bind(R.id.detail_seller_image) CircleImageView mSellerImageView;
    @Bind(R.id.detail_seller_name) CustomFontTextView mSellerName;
    @Bind(R.id.detail_published_date) CustomFontTextView mPublishedDate;
    @Bind(R.id.detail_product_price) CustomFontTextView mProductPrice;
    @Bind(R.id.detail_product_title) CustomFontTextView mProductTitle;
    @Bind(R.id.detail_product_description) CustomFontTextView mProductDescription;

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

        return root;
    }

    private void loadViewPagerImages() {
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(getActivity(), mProduct);
        mViewPager.setAdapter(imagePagerAdapter);
    }

    private void populateFields() {
        mForSale.setText(mProduct.getIsSelling() ? getActivity().getResources().getString(R.string.selling) : getActivity().getResources().getString(R.string.sold));
        mNumberOfPhotos.setText(String.format("%d %s", mProduct.getImages().size(), getActivity().getResources().getString(R.string.photos)));

        User user = SharedPreferencesManager.getPrefUserData(getActivity());
        if (user.getAvatarURL() != null && user.getAvatarURL() != "") {
            Picasso.with(getActivity())
                    .load(user.getAvatarURL())
                    .placeholder(R.drawable.no_user)
                    .resize(500, 500)
                    .centerCrop()
                    .into(mSellerImageView);
        }

        mSellerName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        mPublishedDate.setText(String.format("%s %s", getActivity().getResources().getString(R.string.published), DateManager.stringFromDate(mProduct.getPublished(), "dd/MM/yyyy")));
        mProductPrice.setText(String.format("%s €", mProduct.getPrice()));
        mProductTitle.setText(mProduct.getName());
        mProductDescription.setText(mProduct.getDetail());
    }
}
