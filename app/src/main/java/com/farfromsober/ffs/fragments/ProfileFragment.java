package com.farfromsober.ffs.fragments;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.farfromsober.customviews.CustomFontTextView;
import com.farfromsober.ffs.R;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.network.APIManager;
import com.farfromsober.ffs.utils.MapUtils;
import com.farfromsober.ffs.utils.SharedPreferencesManager;
import com.farfromsober.network.callbacks.OnDataParsedCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.farfromsober.ffs.fragments.FullProfileFragment.ARG_USER;

public class ProfileFragment extends Fragment implements OnDataParsedCallback<Object> {

    private User mUser;
    protected APIManager apiManager;
    private String apiCall;

    private SupportMapFragment mMapFragment;
    private GoogleMap map;

    private static View view;

    @Bind(R.id.profile_image) CircleImageView mProfileImage;
    @Bind(R.id.profile_selling_number) CustomFontTextView mProfileSelling;
    @Bind(R.id.profile_sales_number) CustomFontTextView mProfileSales;
    @Bind(R.id.profile_bought_number) CustomFontTextView mProfileBought;
    @Bind(R.id.profile_name) CustomFontTextView mProfileName;
    @Bind(R.id.profile_location) CustomFontTextView mProfileLocation;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();

        // 2) Nos creamos los argumentos y los empaquetamos
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_USER, user);

        // 3) Asignamos los argumentos al fragment y lo devolvemos ya creado
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(ARG_USER);
            if ((mUser == null) ) {
                mUser = SharedPreferencesManager.getPrefUserData(getActivity());
            }
            return;
        }
        mUser = SharedPreferencesManager.getPrefUserData(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_profile, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        ButterKnife.bind(this, view);
        setHasOptionsMenu(false);

        if (mUser.getAvatarURL() != null && mUser.getAvatarURL() != "") {
            Picasso.with(getActivity())
                    .load(mUser.getAvatarURL())
                    .placeholder(R.drawable.mavatar_placeholder)
                    .resize(500, 500)
                    .centerCrop()
                    .into(mProfileImage);
        }

        mProfileName.setText(getString(R.string.profile_fragment_user_name, mUser.getFirstName(), mUser.getLastName()));
        mProfileLocation.setText(getString(R.string.profile_fragment_user_location, mUser.getCity(), mUser.getState()));
        mProfileSelling.setText(getString(R.string.profile_fragment_products_selling, 0));
        mProfileSales.setText(getString(R.string.profile_fragment_products_sold, 0));
        mProfileBought.setText(getString(R.string.profile_fragment_products_bought, 0));

        apiManager = new APIManager(getActivity());
        apiCall = "Selling";
        apiManager.userSellingProducts(mUser, this);
        configureMap();

        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void configureMap() {

        mMapFragment =   ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.profile_map));

        if (mMapFragment != null) {

            map = mMapFragment.getMap();

            User user = SharedPreferencesManager.getPrefUserData(getActivity());
            LatLng center;
            if (user.getLatitude() == "" || user.getLongitude() == "") {
                center = MapUtils.getLocationFromAddress(getActivity(), String.format("%s, %s", user.getCity(), user.getState()));
            } else {
                center = new LatLng(Double.parseDouble(user.getLatitude()), Double.parseDouble(user.getLongitude()));
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

    public void getNextApiCall (ArrayList<Object> data) {
        switch (apiCall) {
            case "Selling":
                if (data != null) {
                    mProfileSelling.setText(getString(R.string.profile_fragment_products_selling, data.size()));
                }
                apiManager.userSoldProducts(mUser, this);
                apiCall = "Sold";
                break;
            case "Sold":
                if (data != null) {
                    mProfileSales.setText(getString(R.string.profile_fragment_products_sold, data.size()));
                }
                apiManager.userBoughtProducts(mUser, this);
                apiCall = "Bought";
                break;
            case "Bought":
                if (data != null) {
                    mProfileBought.setText(getString(R.string.profile_fragment_products_bought, data.size()));
                }
                apiCall = "Selling";
                break;
        }
    }

    @Override
    public void onDataArrayParsed(int responseCode, ArrayList<Object> data) {
        getNextApiCall(data);
    }

    @Override
    public void onDataObjectParsed(int responseCode, Object data) {
    }

    @Override
    public void onExceptionReceived(Exception e) {
    }
}
